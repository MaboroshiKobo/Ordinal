package org.maboroshi.ordinal.manager;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataType;
import org.maboroshi.ordinal.Ordinal;
import org.maboroshi.ordinal.config.ConfigManager;
import org.maboroshi.ordinal.util.Logger;

public class OrdinalManager {
    private final ConfigManager config;
    private final Logger log;
    private final NamespacedKey ordinal_rank;
    private final HashMap<UUID, Integer> legacyCache = new HashMap<>();
    private final HashMap<String, Integer> legacyNameCache = new HashMap<>();

    public OrdinalManager(Ordinal plugin) {
        this.config = plugin.getConfigManager();
        this.log = plugin.getPluginLogger();
        this.ordinal_rank = new NamespacedKey(plugin, "ordinal_rank");
        loadExistingPlayers();
    }

    public int getOrdinal(Player player) {
        if (!player.getPersistentDataContainer().has(ordinal_rank, PersistentDataType.INTEGER)) {
            log.debug("Player " + player.getName() + " does not have an ordinal rank assigned");
            return -1;
        }
        int ordinal = player.getPersistentDataContainer().get(ordinal_rank, PersistentDataType.INTEGER);
        return ordinal;
    }

    public void assignOrdinal(Player player) {
        int nextOrdinal = config.ordinalData.nextOrdinal;
        player.getPersistentDataContainer().set(ordinal_rank, PersistentDataType.INTEGER, nextOrdinal);
        config.updateNextOrdinal(nextOrdinal + 1);
        log.debug("Assigned Ordinal Rank #" + nextOrdinal + " to " + player.getName());
    }

    public void assignOrdinal(Player player, int providedOrdinal) {
        player.getPersistentDataContainer().set(ordinal_rank, PersistentDataType.INTEGER, providedOrdinal);
        log.debug("Migrated " + player.getName() + " to Legacy Ordinal #" + providedOrdinal);
    }

    public NamespacedKey getOrdinalRankKey() {
        return ordinal_rank;
    }

    private void loadExistingPlayers() {
        log.debug("Calculating join order for existing players...");
        long start = System.currentTimeMillis();

        List<OfflinePlayer> allPlayers = Arrays.asList(Bukkit.getOfflinePlayers());
        log.debug("Found " + allPlayers.size() + " offline players to process");

        allPlayers.sort(Comparator.comparingLong(OfflinePlayer::getFirstPlayed));

        int index = 1;
        for (OfflinePlayer p : allPlayers) {
            legacyCache.put(p.getUniqueId(), index++);
            if (p.getName() != null) {
                legacyNameCache.put(p.getName().toLowerCase(), index - 1);
            }
        }

        if (config.ordinalData.nextOrdinal < index) {
            log.debug("Updating nextOrdinal from " + config.ordinalData.nextOrdinal + " to " + index);
            config.updateNextOrdinal(index);
        } else {
            log.debug("nextOrdinal already at " + config.ordinalData.nextOrdinal + ", no update needed");
        }

        long time = System.currentTimeMillis() - start;
        log.debug("Calculation complete. Loaded " + (index - 1) + " players in " + time + "ms.");
    }

    public int checkExistingOrdinal(Player player) {
        log.debug("Checking existing ordinal for " + player.getName() + " (UUID: " + player.getUniqueId() + ")");
        if (legacyCache.containsKey(player.getUniqueId())) {
            int ordinal = legacyCache.get(player.getUniqueId());
            log.debug("Found ordinal #" + ordinal + " in UUID cache");
            return ordinal;
        }
        int ordinal = legacyNameCache.getOrDefault(player.getName().toLowerCase(), -1);
        log.debug("Found ordinal #" + ordinal + " in name cache (or -1 if not found)");
        return ordinal;
    }

    public void resetAndRecalculate(Player player) {
        if (player.getPersistentDataContainer().has(ordinal_rank, PersistentDataType.INTEGER)) {
            player.getPersistentDataContainer().remove(ordinal_rank);
        }

        int legacyOrdinal = checkExistingOrdinal(player);
        log.debug("Resetting and recalculating ordinal for " + player.getName() + ". Legacy ordinal: " + legacyOrdinal);

        if (legacyOrdinal > 0) {
            assignOrdinal(player, legacyOrdinal);
        } else {
            assignOrdinal(player);
        }
    }
}
