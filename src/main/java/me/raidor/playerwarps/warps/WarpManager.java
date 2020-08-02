package me.raidor.playerwarps.warps;

import com.google.common.collect.Lists;
import me.raidor.playerwarps.PlayerWarps;
import me.raidor.playerwarps.utils.Config;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class WarpManager {

    private PlayerWarps plugin;
    private List<PlayerWarp> activeWarps;
    private List<List<PlayerWarp>> pages;
    private Config dataConfig;

    public WarpManager(PlayerWarps plugin) {
        this.dataConfig = plugin.getDataConfig();
        this.activeWarps = new ArrayList<>();
        this.pages = new ArrayList<>();
    }

    public void loadWarps() {
        ConfigurationSection section = dataConfig.getFileConfiguration();
        for (String string : section.getKeys(false)) {
            ConfigurationSection section1 = section.getConfigurationSection(string);
            String stringName = string;
            String stringLocation = section1.getString("location");
            String stringUUID = section1.getString("uuid");
            String stringMessage = section1.getString("message");
            String passwordMessage = section1.getString("password");
            Location location = buildLocation(stringLocation);
            PlayerWarp warp = new PlayerWarp(location, UUID.fromString(stringUUID), stringName, passwordMessage);
            warp.setWelcomeMessage(stringMessage);
            activeWarps.add(warp);
            section.set(string, null);
        }
//        dataConfig.save();
        updateList();
    }

    public void saveWarps() {
        for (PlayerWarp warp : activeWarps) {
            ConfigurationSection section = dataConfig.getFileConfiguration();
            ConfigurationSection warpData = section.createSection(warp.getName());
            String stringLocation = unbuildLocation(warp.getLocation());
            warpData.set("location", stringLocation);
            warpData.set("uuid", warp.getOwner().toString());
            warpData.set("message", warp.getWelcomeMessage());
            warpData.set("password", warp.getPassword());
        }
        dataConfig.save();
    }

    public List<PlayerWarp> getActiveWarps() {
        return this.activeWarps;
    }

    public Location buildLocation(String stringLocation) {
        // WORLD:x:y:z:yaw:pitch
        String[] strings = stringLocation.split(":");
        String world = strings[0];
        double x = Double.valueOf(strings[1]);
        double y = Double.valueOf(strings[2]);
        double z = Double.valueOf(strings[3]);
        float yaw = Float.valueOf(strings[4]);
        float pitch = Float.valueOf(strings[5]);
        return new Location(Bukkit.getWorld(world), x, y, z, yaw, pitch);
    }

    public String unbuildLocation(Location location) {
        String world = location.getWorld().getName();
        String x = String.valueOf(location.getX());
        String y = String.valueOf(location.getY());
        String z = String.valueOf(location.getZ());
        String yaw = String.valueOf(location.getYaw());
        String pitch = String.valueOf(location.getPitch());
        String stringLocation = world + ":" + x + ":" + y + ":" + z + ":" + yaw + ":" + pitch;
        return stringLocation;
    }

    public void updateList() {
        List<PlayerWarp> publicWarps = new ArrayList<>();
        List<PlayerWarp> privateWarps = new ArrayList<>();
        for (PlayerWarp warp : activeWarps) {
            if (warp.getPassword() == null) {
                publicWarps.add(warp);
                continue;
            }
            privateWarps.add(warp);
        }
        List<PlayerWarp> allWarps = new ArrayList<>();
        allWarps.addAll(publicWarps);
        allWarps.addAll(privateWarps);
        this.pages = Lists.partition(allWarps, 8);
    }

    public int getPageCount() {
        return this.pages.size();
    }

    public List<PlayerWarp> getPage(int index) {
        if (index < 0 || index > pages.size() - 1) {
            throw new IllegalArgumentException("Index cannot be less than 0 or more than" + pages.size());
        }
        return pages.get(index);
    }

    public void addWarp(PlayerWarp warp) {
        activeWarps.add(warp);
        updateList();
    }

    public void removeWarp(PlayerWarp warp) {
        activeWarps.remove(warp);
        updateList();
    }
}
