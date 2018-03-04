package de.philipgrabow.buttonextender;

import java.io.File;
import java.util.HashMap;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import de.philipgrabow.buttonextender.executor.BtnCreateCmd;
import de.philipgrabow.buttonextender.executor.BtnRemoveCmd;
import de.philipgrabow.buttonextender.listener.BtnClick;

public class Main extends JavaPlugin implements Listener {
	
	public static File file = new File("plugins/ButtonExtender/Buttons", "Button.yml");
	public static FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);
	
//	HashMap zur Aktivierung der Button-Erkennung
//	Key: SpielerName Value: ButtonName
	public static HashMap<String, String> map = new HashMap<String, String>();
	
//	HashMap zur Aktivierung des Entfernen-Modus für Buttons
//	Key: SpielerName Value: ButtonName
	public static HashMap<String, String> map2 = new HashMap<String, String>();

	@Override
	public void onDisable() {
		this.getLogger().log(Level.INFO, "disabled!");
	}

	@Override
	public void onEnable() {
		
		getCommand("btnadd").setExecutor(new BtnCreateCmd());
		getCommand("btndel").setExecutor(new BtnRemoveCmd());
//		getCommand("btncheck").setExecutor(new BtnCheckCmd());
		
		
		PluginManager pm = Bukkit.getPluginManager();
		pm.registerEvents(new BtnClick(this), this);
		this.getLogger().log(Level.INFO, "enabled!");
	}
}
