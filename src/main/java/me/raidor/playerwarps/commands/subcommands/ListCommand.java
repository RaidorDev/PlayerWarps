package me.raidor.playerwarps.commands.subcommands;

import me.raidor.playerwarps.PlayerWarps;
import me.raidor.playerwarps.utils.Chat;
import me.raidor.playerwarps.utils.Command;
import me.raidor.playerwarps.utils.Message;
import me.raidor.playerwarps.warps.PlayerWarp;
import me.raidor.playerwarps.warps.WarpManager;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.hover.content.Text;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.List;

public class ListCommand extends Command {

    private final WarpManager warpManager;

    public ListCommand(PlayerWarps plugin) {
        super("list");
        this.warpManager = plugin.getWarpManager();
    }

    @Override
    public boolean run(Player player, String[] args) {
        if (warpManager.getActiveWarps().size() < 1) {
            player.sendMessage(Message.LIST_INVALID_PAGE.toString());
            return true;
        }
        if (args.length == 1) {
            player.sendMessage(Message.LIST_HEADER.toString());
            List<PlayerWarp> content = warpManager.getPage(0);
            for (PlayerWarp warp : content) {
                String status = ChatColor.GREEN + "public";
                if (warp.getPassword() != null) {
                    status = ChatColor.RED + "private";
                }
                TextComponent mainComponent = new TextComponent(Message.LIST_WARP.toUnformattedString().replace("{0}", warp.getName()).replace("{1}", status).replace("{2}", Bukkit.getPlayer(warp.getOwner()).getDisplayName()).replace(Chat.prefix, ""));
                mainComponent.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text(Message.LIST_HOVER_TEXT.toUnformattedString())));
                mainComponent.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/warp visit " + warp.getName()));
                player.spigot().sendMessage(mainComponent);
            }
            player.sendMessage(Message.LIST_FOOTER.toString().replace("{0}", 1 + "").replace("{1}", warpManager.getPageCount() + ""));
            return true;
        }
        try {
            Integer.parseInt(args[1]);
        } catch (Exception e) {
            player.sendMessage(Message.LIST_INVALID_PAGE.toString());
            return true;
        }

        int index = Integer.valueOf(args[1]) - 1;
        List<PlayerWarp> content;

        try {
            content = warpManager.getPage(index);
        } catch (Exception e) {
            player.sendMessage(Message.LIST_INVALID_PAGE.toString());
            return true;
        }

        player.sendMessage(Message.LIST_HEADER.toString());
        for (PlayerWarp warp : content) {
            player.sendMessage(Message.LIST_WARP.toUnformattedString().replace("{0}", warp.getName()).replace("{1}", Bukkit.getPlayer(warp.getOwner()).getDisplayName()).replace(Chat.prefix, ""));
        }

        player.sendMessage(Message.LIST_FOOTER.toString().replace("{0}", (index+1) + "").replace("{1}", warpManager.getPageCount() + ""));
        return true;
    }
}
