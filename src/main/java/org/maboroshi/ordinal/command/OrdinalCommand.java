package org.maboroshi.ordinal.command;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.tree.LiteralCommandNode;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import io.papermc.paper.command.brigadier.Commands;
import io.papermc.paper.command.brigadier.argument.ArgumentTypes;
import io.papermc.paper.command.brigadier.argument.resolvers.selector.PlayerSelectorArgumentResolver;
import java.util.List;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
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

                    messageUtils.send(sender, config.getMessageConfig().messages.helpInfo);
                    return Command.SINGLE_SUCCESS;
                })
                .then(Commands.literal("reload")
                        .requires(sender -> sender.getSender().hasPermission("ordinal.reload"))
                        .executes(ctx -> {
                            CommandSender sender = ctx.getSource().getSender();
                            if (plugin.reload()) {
                                log.info("Configuration reloaded by " + sender.getName());
                                messageUtils.send(
                                        sender, "<prefix>" + config.getMessageConfig().messages.reloadSuccess);
                            } else {
                                log.warn("Failed to reload configuration by " + sender.getName());
                                messageUtils.send(sender, "<prefix>" + config.getMessageConfig().messages.reloadFail);
                            }
                            return Command.SINGLE_SUCCESS;
                        }))
                .then(Commands.literal("reset")
                        .requires(ctx -> ctx.getSender().hasPermission("ordinal.admin"))
                        .then(Commands.argument("target", ArgumentTypes.player())
                                .executes(ctx -> {
                                    CommandSender sender = ctx.getSource().getSender();

                                    PlayerSelectorArgumentResolver resolver =
                                            ctx.getArgument("target", PlayerSelectorArgumentResolver.class);
                                    List<Player> targets = resolver.resolve(ctx.getSource());

                                    if (targets.isEmpty()) {
                                        messageUtils.send(sender, config.getMessageConfig().messages.playerNotFound);
                                        return 0;
                                    }

                                    Player target = targets.get(0);

                                    plugin.getOrdinalManager().resetAndRecalculate(target);

                                    int newRank = plugin.getOrdinalManager().getOrdinal(target);
                                    messageUtils.send(
                                            sender,
                                            config.getMessageConfig().messages.resetSuccess,
                                            messageUtils.tagParsed("player", target.getName()),
                                            messageUtils.tag("rank", newRank));
                                    return Command.SINGLE_SUCCESS;
                                })))
                .build();
    }
}
