package org.maboroshi.ordinal.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.maboroshi.ordinal.Ordinal;
import org.maboroshi.ordinal.manager.OrdinalManager;

public class JoinListener implements Listener {
    private final Ordinal plugin;
    private final OrdinalManager ordinalManager;

    public JoinListener(Ordinal plugin) {
        this.plugin = plugin;
        this.ordinalManager = plugin.getOrdinalManager();
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        if (!plugin.getConfigManager().getMainConfig().seniority.enabled) {
            return;
        }
        int currentOrdinal = ordinalManager.getOrdinal(event.getPlayer());
        int legacyOrdinal = ordinalManager.checkExistingOrdinal(event.getPlayer());
        if (currentOrdinal == -1) {
            if (legacyOrdinal > 0) {
                plugin.getLogger()
                        .info("Player " + event.getPlayer().getName() + " is a legacy player. Migrating to ordinal #"
                                + legacyOrdinal);
                ordinalManager.assignOrdinal(event.getPlayer(), legacyOrdinal);
            } else {
                plugin.getLogger()
                        .info("Player " + event.getPlayer().getName() + " is a new joiner. Assigning new ordinal.");
                ordinalManager.assignOrdinal(event.getPlayer());
            }
        } else {
            plugin.getLogger()
                    .info("Player " + event.getPlayer().getName() + " has rejoined with ordinal: " + currentOrdinal);
        }
    }
}
