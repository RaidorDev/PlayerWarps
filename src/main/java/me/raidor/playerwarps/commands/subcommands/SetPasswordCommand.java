package me.raidor.playerwarps.commands.subcommands;

import me.raidor.playerwarps.PlayerWarps;
import me.raidor.playerwarps.utils.Command;
import me.raidor.playerwarps.utils.Message;
import me.raidor.playerwarps.warps.PlayerWarp;
import me.raidor.playerwarps.warps.WarpManager;
import org.bukkit.entity.Player;

public class SetPasswordCommand extends Command {

    private final WarpManager warpManager;
    private int max;

    public SetPasswordCommand(PlayerWarps plugin) {
        super("setpassword");
        this.warpManager = plugin.getWarpManager();
        this.max = plugin.getConfig().getInt("warp-password-max");
    }

    @Override
    public boolean run(Player player, String[] args) {
        if (!player.hasPermission("playerwarps.setpassword")) {
            player.sendMessage(Message.NO_PERMISSION.toString());
            return false;
        }
        if (args.length < 2 || args.length > 3) {
            player.sendMessage(Message.SET_PASSWORD_USAGE.toString());
            return false;
        }
        if (args[2].length() > max) {
            player.sendMessage(Message.SET_PASSWORD_TOO_LONG.toString().replace("{0}", max+""));
            return false;
        }
        for (PlayerWarp warp : warpManager.getActiveWarps()) {
            if (warp.getName().equalsIgnoreCase(args[1])) {
                if (!warp.getOwner().equals(player.getUniqueId())) {
                    player.sendMessage(Message.SET_PASSWORD_NOT_OWNER.toString());
                    return false;
                }
                warp.setPassword(args[2]);
                warpManager.updateList();
                player.sendMessage(Message.SET_PASSWORD_SUCCESSFUL.toString().replace("{0}", warp.getName()));
                return false;
            }
        }
        player.sendMessage(Message.SET_PASSWORD_NOT_FOUND.toString());
        return false;
    }
}
