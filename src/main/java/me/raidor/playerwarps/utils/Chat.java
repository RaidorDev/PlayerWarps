package me.raidor.playerwarps.utils;

import me.raidor.playerwarps.PlayerWarps;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import java.util.ArrayList;
import java.util.List;

public class Chat {

    public static String prefix;

    public Chat(PlayerWarps plugin) {
        try {
            this.prefix = plugin.getConfig().getString("chat-prefix");
        } catch (Exception e) {
            this.prefix = "&e&lPlayerWarps &8> ";
            Bukkit.getLogger().severe(" ");
            Bukkit.getLogger().severe(" ");
            Bukkit.getLogger().severe("PLAYERWARPS PREFIX FAILED TO LOAD...");
            Bukkit.getLogger().severe(" ");
            Bukkit.getLogger().severe(" ");
        }
    }

    public static String format(String string) {
        if (string != null) {
            String formattedString = prefix + string;
            return ChatColor.translateAlternateColorCodes('&', formattedString);
        }
        return null;
    }

    public static String string(String string) {
        if (string != null) {
            return ChatColor.translateAlternateColorCodes('&', string);
        }
        return null;
    }

    public static List<String> format(List<String> list) {
        List<String> newList = new ArrayList<>();
        list.forEach(string -> {
            newList.add(ChatColor.translateAlternateColorCodes('&', string));
        });
        return newList;
    }
}
