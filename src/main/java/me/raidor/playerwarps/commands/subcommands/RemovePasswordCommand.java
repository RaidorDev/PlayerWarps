package me.raidor.playerwarps.commands.subcommands;

import me.raidor.playerwarps.PlayerWarps;
import me.raidor.playerwarps.utils.Command;
import me.raidor.playerwarps.utils.Message;
import me.raidor.playerwarps.warps.PlayerWarp;
import me.raidor.playerwarps.warps.WarpManager;
import org.bukkit.entity.Player;

public class RemovePasswordCommand extends Command {

    private final WarpManager warpManager;

    public RemovePasswordCommand(PlayerWarps plugin) {
        super("removepassword");
        this.warpManager = plugin.getWarpManager();
    }

    @Override
    public boolean run(Player player, String[] args) {
        if (args.length != 2) {
            player.sendMessage(Message.REMOVE_PASSWORD_USAGE.toString());
            return false;
        }
        for (PlayerWarp warp : warpManager.getActiveWarps()) {
            if (warp.getName().equalsIgnoreCase(args[1])) {
                if (!warp.getOwner().equals(player.getUniqueId())) {
                    player.sendMessage(Message.REMOVE_PASSWORD_NOT_OWNER.toString());
                    return false;
                }
                warp.setPassword(null);
                warpManager.updateList();
                player.sendMessage(Message.REMOVE_PASSWORD_SUCCESSFUL.toString().replace("{0}", warp.getName()));
                return false;
            }
        }
        player.sendMessage(Message.REMOVE_PASSWORD_NOT_FOUND.toString());
        return false;
    }
}
