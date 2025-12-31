package org.maboroshi.ordinal.hook;

import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.maboroshi.ordinal.Ordinal;
import org.maboroshi.ordinal.manager.OrdinalManager;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;

public class OrdinalExpansion extends PlaceholderExpansion {
    private final Ordinal plugin;
    private final OrdinalManager ordinalManager;

    public OrdinalExpansion(Ordinal plugin) {
        this.plugin = plugin;
        this.ordinalManager = plugin.getOrdinalManager();
    }

    @Override
    @NotNull
    public String getAuthor() {
        return String.join(", ", plugin.getPluginMeta().getAuthors());
    }

    @Override
    @NotNull
    public String getIdentifier() {
        return "ordinal";
    }

    @Override
    @NotNull
    public String getVersion() {
        return plugin.getPluginMeta().getVersion();
    }

    @Override
    public boolean persist() {
        return true;
    }

    @Override
    public String onPlaceholderRequest(Player player, @NotNull String params) {
        if (player == null) {
            return "";
        }

        if (params.equalsIgnoreCase("rank")) {
            int rank = ordinalManager.getOrdinal(player);

            if (rank == -1) {
                return "";
            }
            return String.valueOf(rank);
        }
        return null;
    }
}
