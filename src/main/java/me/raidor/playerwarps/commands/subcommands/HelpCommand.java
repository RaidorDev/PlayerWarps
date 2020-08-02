package me.raidor.playerwarps.commands.subcommands;

import me.raidor.playerwarps.PlayerWarps;
import me.raidor.playerwarps.utils.Command;
import me.raidor.playerwarps.utils.Message;
import me.raidor.playerwarps.warps.WarpManager;
import org.bukkit.entity.Player;

public class HelpCommand extends Command {

    private final WarpManager warpManager;
    private PlayerWarps plugin;

    public HelpCommand(PlayerWarps plugin) {
        super("help");
        this.warpManager = plugin.getWarpManager();
        this.plugin = plugin;
    }

    @Override
    public boolean run(Player player, String[] args) {
        player.sendMessage(Message.HELP_HEADER.toUnformattedString().replace("{0}", plugin.getDescription().getVersion()));
        for (String line : Message.HELP_MESSAGE.toList()) {
            player.sendMessage(line);
        }
        return true;
    }
}
