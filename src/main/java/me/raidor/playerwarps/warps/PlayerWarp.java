package me.raidor.playerwarps.warps;

import me.raidor.playerwarps.utils.Message;
import org.bukkit.Location;

import java.util.UUID;

public class PlayerWarp {

    private Location location;
    private UUID owner;
    private String name, welcomeMessage, password;

    public PlayerWarp(Location location, UUID owner, String name, String password) {
        this.location = location;
        this.owner = owner;
        this.name = name;
        this.welcomeMessage = Message.DEFAULT_WELCOME_MESSAGE.toUnformattedString();
        this.password = password;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setWelcomeMessage(String welcomeMessage) {
        this.welcomeMessage = welcomeMessage;
    }

    public void setPassword(String password) { this.password = password; }

    public String getWelcomeMessage() {
        return this.welcomeMessage;
    }

    public UUID getOwner() {
        return this.owner;
    }

    public Location getLocation() { return this.location; }

    public String getName() { return this.name; }

    public String getPassword() { return this.password; }

}
