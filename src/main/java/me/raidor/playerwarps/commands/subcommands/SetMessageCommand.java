package me.raidor.playerwarps.commands.subcommands;

import me.raidor.playerwarps.PlayerWarps;
import me.raidor.playerwarps.utils.Command;
import me.raidor.playerwarps.utils.Message;
import me.raidor.playerwarps.warps.PlayerWarp;
import me.raidor.playerwarps.warps.WarpManager;
import org.bukkit.entity.Player;

public class SetMessageCommand extends Command {

    private final WarpManager warpManager;
    private int max;

    public SetMessageCommand(PlayerWarps plugin) {
        super("setmessage");
        this.warpManager = plugin.getWarpManager();
        this.max = plugin.getConfig().getInt("warp-message-max");
    }

    @Override
    public boolean run(Player player, String[] args) {
        if (args.length < 2) {
            player.sendMessage(Message.SET_MESSAGE_USAGE.toString());
            return false;
        }
        String[] newArgs = args.clone();
        newArgs[0] = "";
        newArgs[1] = "";
        String message = String.join(" ", newArgs).replaceFirst("  ", "");
        if (message.length() > max) {
            player.sendMessage(Message.SET_MESSAGE_TOO_LONG.toString().replace("{0}", max+""));
            return false;
        }
        for (PlayerWarp warp : warpManager.getActiveWarps()) {
            if (warp.getName().equalsIgnoreCase(args[1])) {
                if (!warp.getOwner().equals(player.getUniqueId())) {
                    player.sendMessage(Message.SET_MESSAGE_NOT_OWNER.toString());
                    return false;
                }
                warp.setWelcomeMessage(message);
                player.sendMessage(Message.SET_MESSAGE_SUCCESSFUL.toString().replace("{0}", warp.getName()));
                player.sendMessage(warp.getWelcomeMessage());
                return false;
            }
        }
        player.sendMessage(Message.SET_MESSAGE_NOT_FOUND.toString());
        return false;
    }
}
