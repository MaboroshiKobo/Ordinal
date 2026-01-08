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

public class OrdinalManager {
    private final Ordinal plugin;
    private final ConfigManager config;
    private final NamespacedKey ordinal_rank;
    private final HashMap<UUID, Integer> legacyCache = new HashMap<>();
    private final HashMap<String, Integer> legacyNameCache = new HashMap<>();

    public OrdinalManager(Ordinal plugin) {
        this.plugin = plugin;
        this.config = plugin.getConfigManager();
        this.ordinal_rank = new NamespacedKey(plugin, "ordinal_rank");
        loadExistingPlayers();
    }

    public int getOrdinal(Player player) {
        if (!player.getPersistentDataContainer().has(ordinal_rank, PersistentDataType.INTEGER)) {
            return -1;
        }
        return player.getPersistentDataContainer().get(ordinal_rank, PersistentDataType.INTEGER);
    }

    public void assignOrdinal(Player player) {
        int nextOrdinal = config.ordinalData.nextOrdinal;
        player.getPersistentDataContainer().set(ordinal_rank, PersistentDataType.INTEGER, nextOrdinal);
        config.updateNextOrdinal(nextOrdinal + 1);
        plugin.getLogger().info("Assigned Ordinal Rank #" + nextOrdinal + " to " + player.getName());
    }

    public void assignOrdinal(Player player, int providedOrdinal) {
        player.getPersistentDataContainer().set(ordinal_rank, PersistentDataType.INTEGER, providedOrdinal);
        plugin.getLogger().info("Migrated " + player.getName() + " to Legacy Ordinal #" + providedOrdinal);
    }

    public NamespacedKey getOrdinalRankKey() {
        return ordinal_rank;
    }

    private void loadExistingPlayers() {
        plugin.getLogger().info("Calculating join order for existing players...");
        long start = System.currentTimeMillis();

        List<OfflinePlayer> allPlayers = Arrays.asList(Bukkit.getOfflinePlayers());

        allPlayers.sort(Comparator.comparingLong(OfflinePlayer::getFirstPlayed));

        int index = 1;
        for (OfflinePlayer p : allPlayers) {
            legacyCache.put(p.getUniqueId(), index++);
            if (p.getName() != null) {
                legacyNameCache.put(p.getName().toLowerCase(), index - 1);
            }
        }

        if (config.ordinalData.nextOrdinal < index) {
            config.updateNextOrdinal(index);
        }

        long time = System.currentTimeMillis() - start;
        plugin.getLogger().info("Calculation complete. Loaded " + (index - 1) + " players in " + time + "ms.");
    }

    public int checkExistingOrdinal(Player player) {
        if (legacyCache.containsKey(player.getUniqueId())) {
            return legacyCache.get(player.getUniqueId());
        }
        return legacyNameCache.getOrDefault(player.getName().toLowerCase(), -1);
    }

    public void resetAndRecalculate(Player player) {
        if (player.getPersistentDataContainer().has(ordinal_rank, PersistentDataType.INTEGER)) {
            player.getPersistentDataContainer().remove(ordinal_rank);
        }

        int legacyOrdinal = checkExistingOrdinal(player);

        if (legacyOrdinal > 0) {
            assignOrdinal(player, legacyOrdinal);
        } else {
            assignOrdinal(player);
        }
    }
}
