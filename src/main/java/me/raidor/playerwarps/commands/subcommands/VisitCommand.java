package me.raidor.playerwarps.commands.subcommands;

import me.raidor.playerwarps.PlayerWarps;
import me.raidor.playerwarps.utils.Command;
import me.raidor.playerwarps.utils.Message;
import me.raidor.playerwarps.warps.PlayerWarp;
import me.raidor.playerwarps.warps.WarpManager;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

public class VisitCommand extends Command {

    private final WarpManager warpManager;

    public VisitCommand(PlayerWarps plugin) {
        super("visit");
        this.warpManager = plugin.getWarpManager();
    }

    @Override
    public boolean run(Player player, String[] args) {
        if (!player.hasPermission("playerwarps.visit")) {
            player.sendMessage(Message.NO_PERMISSION.toString());
            return false;
        }
        if (args.length < 2) {
            player.sendMessage(Message.VISIT_USAGE.toString());
            return false;
        }
        for (PlayerWarp warp : warpManager.getActiveWarps()) {
            if (warp.getName().equalsIgnoreCase(args[1])) {
                Location location = warp.getLocation();
                if (!isSafe(location)) {
                    player.sendMessage(Message.VISIT_UNSAFE.toString());
                    return false;
                }
                if (hasPassword(warp) && !warp.getOwner().equals(player.getUniqueId())) {
                    if (args.length < 3) {
                        player.sendMessage(Message.VISIT_NEEDS_PASSWORD.toString());
                        return false;
                    }
                    if (!args[2].equals(warp.getPassword())) {
                        player.sendMessage(Message.VISIT_INCORRECT_PASSWORD.toString());
                        return false;
                    }
                }
                player.teleport(warp.getLocation());
                player.sendMessage(Message.VISIT_SUCCESSFUL.toString().replace("{0}", warp.getName()));
                player.sendMessage(warp.getWelcomeMessage());
                return false;
            }
        }
        player.sendMessage(Message.VISIT_NOT_FOUND.toString());
        return false;
    }

    public boolean isSafe(Location location) {
        Block ground = location.getWorld().getBlockAt(location.getBlockX(), location.getBlockY()-1, location.getBlockZ());
        if (ground.isLiquid() || ground.isPassable() || ground.isEmpty()) {
            return false;
        }
        return true;
    }

    public boolean hasPassword(PlayerWarp warp) {
        if (warp.getPassword() == null) {
            return false;
        }
        return true;
    }
}
