package me.raidor.playerwarps.commands.subcommands;

import me.raidor.playerwarps.PlayerWarps;
import me.raidor.playerwarps.utils.Command;
import me.raidor.playerwarps.utils.Message;
import me.raidor.playerwarps.warps.PlayerWarp;
import me.raidor.playerwarps.warps.WarpManager;
import org.bukkit.entity.Player;

public class DeleteCommand extends Command {

    private final WarpManager warpManager;

    public DeleteCommand(PlayerWarps plugin) {
        super("delete");
        this.warpManager = plugin.getWarpManager();
    }

    @Override
    public boolean run(Player player, String[] args) {
        if (args.length != 2) {
            player.sendMessage(Message.DELETE_USAGE.toString());
            return false;
        }
        for (PlayerWarp warp : warpManager.getActiveWarps()) {
            if (warp.getName().equalsIgnoreCase(args[1])) {
                if (warp.getOwner().equals(player.getUniqueId())) {
                    warpManager.removeWarp(warp);
                    player.sendMessage(Message.DELETE_SUCCESSFUL.toString().replace("{0}", warp.getName()));
                    return false;
                }
                player.sendMessage(Message.DELETE_NOT_OWNER.toString());
                return false;
            }
        }
        player.sendMessage(Message.DELETE_NOT_FOUND.toString());
        return false;
    }
}
