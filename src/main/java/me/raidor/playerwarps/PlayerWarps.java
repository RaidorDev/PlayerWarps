package me.raidor.playerwarps;

import me.raidor.playerwarps.commands.WarpCommand;
import me.raidor.playerwarps.commands.subcommands.*;
import me.raidor.playerwarps.listeners.TestListener;
import me.raidor.playerwarps.utils.Chat;
import me.raidor.playerwarps.utils.Config;
import me.raidor.playerwarps.utils.Message;
import me.raidor.playerwarps.warps.WarpManager;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;

public class PlayerWarps extends JavaPlugin {

	private Config messageConfig, dataConfig;
	private WarpManager warpManager = null;

	@Override
	public void onEnable() {

		saveDefaultConfig();

		messageConfig = new Config(this, "messages");
		registerMessages(messageConfig);

		dataConfig = new Config(this, "data");
		dataConfig.setup();

		this.warpManager = new WarpManager(this);

		WarpCommand command = new WarpCommand(this);
		command.initialise(
				new CreateCommand(this),
				new ListCommand(this),
				new VisitCommand(this),
				new DeleteCommand(this),
				new SetPasswordCommand(this),
				new RemovePasswordCommand(this),
				new HelpCommand(this),
		        new SetMessageCommand(this)
		);
		getCommand(command.toString()).setExecutor(command);

		registerListeners(
				new TestListener(this)
		);
		warpManager.loadWarps();

		new Chat(this);
	}

	public void onDisable() {
		warpManager.saveWarps();
	}

	private void registerMessages(Config config) {
		config.setup();
		Message.setConfiguration(config.getFileConfiguration());
		for(Message message: Message.values()) {
			if (config.getFileConfiguration().getString(message.getPath()) == null) {
				if(message.getList() != null) {
					config.getFileConfiguration().set(message.getPath(), message.getList());
				} else {
					config.getFileConfiguration().set(message.getPath(), message.getDef());
				}
			}
		}
		config.save();
	}

	private void registerListeners(Listener... listeners) {
		Arrays.asList(listeners).forEach(listener -> getServer().getPluginManager().registerEvents(listener, this));
	}

	public Config getDataConfig() {
		return this.dataConfig;
	}

	public WarpManager getWarpManager() {
		return this.warpManager;
	}

}
