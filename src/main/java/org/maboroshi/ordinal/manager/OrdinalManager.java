package org.maboroshi.ordinal.manager;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataType;
import org.maboroshi.ordinal.Ordinal;
import org.maboroshi.ordinal.config.ConfigManager;
import org.maboroshi.ordinal.util.Logger;

public class OrdinalManager {
    private final Ordinal plugin;
    private final ConfigManager config;
    private final Logger log;
    private final NamespacedKey ordinal_rank;
    private final ConcurrentHashMap<UUID, Integer> legacyCache = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<String, Integer> legacyNameCache = new ConcurrentHashMap<>();

    public OrdinalManager(Ordinal plugin) {
        this.plugin = plugin;
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
        Bukkit.getAsyncScheduler().runNow(plugin, (task) -> {
            long start = System.currentTimeMillis();

            List<OfflinePlayer> allPlayers = Arrays.asList(Bukkit.getOfflinePlayers());
            log.debug("Found " + allPlayers.size() + " offline players to process");

            allPlayers.sort(Comparator.comparingLong(OfflinePlayer::getFirstPlayed));

            HashMap<UUID, Integer> tempLegacyCache = new HashMap<>();
            HashMap<String, Integer> tempLegacyNameCache = new HashMap<>();

            int index = 1;
            for (OfflinePlayer p : allPlayers) {
                tempLegacyCache.put(p.getUniqueId(), index++);
                if (p.getName() != null) {
                    tempLegacyNameCache.put(p.getName().toLowerCase(), index - 1);
                }
            }

            final int finalNextOrdinal = index;
            final long time = System.currentTimeMillis() - start;

            Bukkit.getGlobalRegionScheduler().run(plugin, (scheduledTask) -> {
                legacyCache.putAll(tempLegacyCache);
                legacyNameCache.putAll(tempLegacyNameCache);
                if (config.ordinalData.nextOrdinal < finalNextOrdinal) {
                    log.debug(
                            "Updating nextOrdinal from " + config.ordinalData.nextOrdinal + " to " + finalNextOrdinal);
                    config.updateNextOrdinal(finalNextOrdinal);
                } else {
                    log.debug("nextOrdinal already at " + config.ordinalData.nextOrdinal + ", no update needed");
                }

                log.debug("Calculation complete. Loaded " + (finalNextOrdinal - 1) + " players in " + time + "ms.");
            });
        });
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
