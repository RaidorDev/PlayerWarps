package me.raidor.playerwarps.commands.subcommands;

import me.raidor.playerwarps.PlayerWarps;
import me.raidor.playerwarps.utils.Command;
import me.raidor.playerwarps.utils.Message;
import me.raidor.playerwarps.warps.PlayerWarp;
import me.raidor.playerwarps.warps.WarpManager;
import org.bukkit.entity.Player;

public class CreateCommand extends Command {

    private final WarpManager warpManager;
    private final int max, maxName, maxPassword;

    public CreateCommand(PlayerWarps plugin) {
        super("create");
        this.warpManager = plugin.getWarpManager();
        this.max = plugin.getConfig().getInt("default-max-warps");
        this.maxPassword = plugin.getConfig().getInt("warp-password-max");
        this.maxName = plugin.getConfig().getInt("warp-name-max");
    }

    @Override
    public boolean run(Player player, String[] args) {
        if (!player.hasPermission("playerwarps.create")) {
            player.sendMessage(Message.NO_PERMISSION.toString());
            return false;
        }
        String password = null;
        int warps = 0;
        if (args.length < 2) {
            player.sendMessage(Message.CREATE_USAGE.toString());
            return false;
        }
        for (PlayerWarp warp : warpManager.getActiveWarps()) {
            if (warp.getName().equalsIgnoreCase(args[1])) {
                player.sendMessage(Message.CREATE_EXISTS.toString());
                return false;
            }
            if (warp.getOwner().equals(player.getUniqueId())) {
                warps++;
            }
        }
        if (args.length == 3) {
            if (!player.hasPermission("playerwarps.visit")) {
                player.sendMessage(Message.NO_PERMISSION.toString());
                return false;
            }
            if (args[2].length() > maxPassword) {
                player.sendMessage(Message.SET_PASSWORD_TOO_LONG.toString().replace("{0}", max+""));
                return false;
            }
            password = args[2];
        }
        if (!player.hasPermission("playerwarps.max." + (warps+1)) && warps >= max) {
            player.sendMessage(Message.CREATE_MAX.toString());
            return false;
        }
        if (args[1].length() > maxName) {
            player.sendMessage(Message.CREATE_MAX_NAME.toString().replace("{0}", maxName +""));
            return false;
        }
        PlayerWarp warp = new PlayerWarp(player.getLocation(), player.getUniqueId(), args[1], password);
        warpManager.addWarp(warp);
        player.sendMessage(Message.CREATE_SUCCESS.toString().replace("{0}", warp.getName()));
        return false;
    }
}
