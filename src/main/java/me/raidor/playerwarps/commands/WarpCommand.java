package me.raidor.playerwarps.commands;

import me.raidor.playerwarps.PlayerWarps;
import me.raidor.playerwarps.utils.Command;
import me.raidor.playerwarps.utils.Message;
import me.raidor.playerwarps.warps.WarpManager;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;

import java.util.*;

public class WarpCommand extends Command implements TabCompleter {

    private final WarpManager warpManager;
    private final PlayerWarps plugin;
    private Set<Command> commands;

    public WarpCommand(PlayerWarps plugin) {
        super("warp");
        this.warpManager = plugin.getWarpManager();
        this.commands = new HashSet<>();
        this.plugin = plugin;
    }

    @Override
    public boolean run(Player player, String[] args) {
        if (!player.hasPermission("playerwarps.use")) {
            player.sendMessage(Message.NO_PERMISSION.toString());
            return true;
        }
        if (args.length > 0) {
            for (Command command : commands) {
                if (command.toString().equalsIgnoreCase(args[0])) {
                    command.run(player, args);
                    return true;
                }
            }
            player.sendMessage(Message.UNKNOWN_COMMAND.toString());
            return true;
        }
        player.sendMessage(Message.HELP_HEADER.toUnformattedString().replace("{0}", plugin.getDescription().getVersion()));
        for (String line : Message.HELP_MESSAGE.toList()) {
            player.sendMessage(line);
        }
        return true;
    }

    public void initialise(Command... commands) {
        this.commands.addAll(Arrays.asList(commands));
    }

    public Set<Command> getCommands() {
        return Collections.unmodifiableSet(commands);
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, org.bukkit.command.Command command, String s, String[] strings) {

        if (!(commandSender instanceof Player)) {
            return null;
        }

        List<String> list = new ArrayList<>();
        if (strings.length == 1) {
            List<String> auto = new ArrayList<>();
            commands.forEach(subCommand -> {
                auto.add(subCommand.toString());
                StringUtil.copyPartialMatches(strings[0], auto, list);
                Collections.sort(list);
            });
        }
        if (strings.length == 2) {
            if (strings[0].equalsIgnoreCase("visit")) {
                List<String> auto = new ArrayList<>();
                warpManager.getActiveWarps().forEach(warp -> {
                    auto.add(warp.getName());
                    StringUtil.copyPartialMatches(strings[1], auto, list);
                    Collections.sort(list);
                });
            }
            if (strings[0].equalsIgnoreCase("delete")) {
                List<String> auto = new ArrayList<>();
                warpManager.getActiveWarps().forEach(warp -> {
                    if (warp.getOwner().equals(((Player) commandSender).getUniqueId())) {
                        auto.add(warp.getName());
                    }
                    StringUtil.copyPartialMatches(strings[1], auto, list);
                    Collections.sort(list);
                });
            }
            if (strings[0].equalsIgnoreCase("removepassword")) {
                List<String> auto = new ArrayList<>();
                warpManager.getActiveWarps().forEach(warp -> {
                    if (warp.getOwner().equals(((Player) commandSender).getUniqueId())) {
                        auto.add(warp.getName());
                    }
                    StringUtil.copyPartialMatches(strings[1], auto, list);
                    Collections.sort(list);
                });
            }
            if (strings[0].equalsIgnoreCase("setmessage")) {
                List<String> auto = new ArrayList<>();
                warpManager.getActiveWarps().forEach(warp -> {
                    if (warp.getOwner().equals(((Player) commandSender).getUniqueId())) {
                        auto.add(warp.getName());
                    }
                    StringUtil.copyPartialMatches(strings[1], auto, list);
                    Collections.sort(list);
                });
            }
            if (strings[0].equalsIgnoreCase("setpassword")) {
                List<String> auto = new ArrayList<>();
                warpManager.getActiveWarps().forEach(warp -> {
                    if (warp.getOwner().equals(((Player) commandSender).getUniqueId())) {
                        auto.add(warp.getName());
                    }
                    StringUtil.copyPartialMatches(strings[1], auto, list);
                    Collections.sort(list);
                });
            }
        }
        return list;
    }
}
