package org.maboroshi.ordinal.util;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.concurrent.CompletableFuture;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import org.maboroshi.ordinal.Ordinal;

public class UpdateChecker implements Listener {
    private final Ordinal plugin;
    private boolean updateAvailable = false;
    private String latestVersion = "";
    private String prefix = "<color:#74c7ec><bold>Ordinal</bold></color> <dark_gray>➟</dark_gray>";

    public UpdateChecker(Ordinal plugin) {
        this.plugin = plugin;
        checkForUpdates();
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        if (updateAvailable && player.hasPermission("ordinal.admin")) {
            player.sendRichMessage(prefix + " A new update is available: <green>" + latestVersion + "</green>.");
            player.sendRichMessage(prefix + " Download on <click:open_url:https://github.com/MaboroshiKobo/Ordinal/releases><color:#74c7ec><underline>GitHub</underline></color></click>.");
        }
    }

    public void checkForUpdates() {
        CompletableFuture.supplyAsync(() -> {
            try {
                HttpClient client = HttpClient.newHttpClient();
                HttpRequest request = HttpRequest.newBuilder()
                        .uri(URI.create("https://api.github.com/repos/MaboroshiKobo/Ordinal/releases/latest"))
                        .build();
                HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
                if (response.statusCode() != 200) {
                    return null;
                }
                return response.body();
            } catch (Exception e) {
                return null;
            }
        }).thenAccept(jsonResponse -> {
            if (jsonResponse == null) return;
            try {
                JsonObject json = JsonParser.parseString(jsonResponse).getAsJsonObject();
                if (json.has("tag_name")) {
                    String tagName = json.get("tag_name").getAsString();
                    
                    if (isNewer(plugin.getPluginMeta().getVersion(), tagName)) {
                        this.updateAvailable = true;
                        this.latestVersion = tagName;
                        plugin.getLogger().info("Update detected! New version available: " + latestVersion);
                    }
                }
            } catch (Exception e) {
            }
        });
    }

    private boolean isNewer(String current, String remote) {
        String currentRaw = current.replace("v", "");
        String remoteRaw = remote.replace("v", "");

        String[] currentSplit = currentRaw.split("-");
        String[] remoteSplit = remoteRaw.split("-");

        String currentBase = currentSplit[0];
        String remoteBase = remoteSplit[0];

        String[] currentParts = currentBase.split("\\.");
        String[] remoteParts = remoteBase.split("\\.");

        int length = Math.max(currentParts.length, remoteParts.length);
        for (int i = 0; i < length; i++) {
            int v1 = i < currentParts.length ? Integer.parseInt(currentParts[i]) : 0;
            int v2 = i < remoteParts.length ? Integer.parseInt(remoteParts[i]) : 0;

            if (v2 > v1) return true;
            if (v2 < v1) return false;
        }

        boolean iAmUnstable = currentSplit.length > 1;
        boolean remoteIsStable = remoteSplit.length == 1;

        if (iAmUnstable && remoteIsStable) {
            return true;
        }

        return false;
    }
}
