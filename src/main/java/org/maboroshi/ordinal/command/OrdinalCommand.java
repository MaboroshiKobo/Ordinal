package org.maboroshi.ordinal.command;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.tree.LiteralCommandNode;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import io.papermc.paper.command.brigadier.Commands;
import org.bukkit.command.CommandSender;
import org.maboroshi.ordinal.Ordinal;
import org.maboroshi.ordinal.config.ConfigManager;
import org.maboroshi.ordinal.util.Logger;
import org.maboroshi.ordinal.util.MessageUtils;

public class OrdinalCommand {
    private final Ordinal plugin;
    private final Logger log;
    private final MessageUtils messageUtils;

    public OrdinalCommand(Ordinal plugin) {
        this.plugin = plugin;
        this.log = plugin.getPluginLogger();
        this.messageUtils = plugin.getMessageUtils();
    }

    public LiteralCommandNode<CommandSourceStack> createCommand(final String commandName) {
        ConfigManager config = plugin.getConfigManager();
        return Commands.literal(commandName)
                .executes(ctx -> {
                    CommandSender sender = ctx.getSource().getSender();

                    messageUtils.send(
                            sender,
                            "<prefix>Plugin version: <green><version></green>",
                            messageUtils.tag("version", plugin.getPluginMeta().getVersion()));

                    messageUtils.send(
                            sender,
                            "<green>🛈</green> <gray>Type <white>/ordinal reload</white> to reload the configuration.</gray>");
                    return Command.SINGLE_SUCCESS;
                })
                .then(Commands.literal("reload")
                        .requires(sender -> sender.getSender().hasPermission("ordinal.reload"))
                        .executes(ctx -> {
                            CommandSender sender = ctx.getSource().getSender();
                            if (plugin.reload()) {
                                log.info("Configuration reloaded by " + sender.getName());
                                messageUtils.send(sender, "<prefix>" + config.getMessageConfig().messages.reloadSuccess);
                            } else {
                                log.warn("Failed to reload configuration by " + sender.getName());
                                messageUtils.send(sender, "<prefix>" + config.getMessageConfig().messages.reloadFail);
                            }
                            return Command.SINGLE_SUCCESS;
                        }))
                .build();
    }
}
