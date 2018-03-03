package de.philipgrabow.buttonextender;

import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.material.Button;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin implements Listener {

	@Override
	public void onDisable() {
		this.getLogger().log(Level.INFO, "disabled!");
	}

	@Override
	public void onEnable() {
		PluginManager pm = Bukkit.getPluginManager();
		pm.registerEvents(this, this);
		this.getLogger().log(Level.INFO, "enabled!");
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler(priority = EventPriority.NORMAL)
	public void onBtnClick(PlayerInteractEvent e) {
		if(e.getAction() == Action.RIGHT_CLICK_BLOCK) {	
			if(e.getClickedBlock().getType() == Material.WOOD_BUTTON ) {
				int i = 0;
				Block block = e.getClickedBlock();
				Button btn = (Button)block.getState().getData();
				if(btn.isPowered() == false) {
					Button button = new Button(Material.WOOD_BUTTON, block.getData());
					button.setPowered(true);
					block.setData(button.getData());
					e.getPlayer().sendMessage("Der Button wurde aktiviert er schaltet sich nach 10 Sekunden aus!");
					i =Bukkit.getScheduler().scheduleSyncDelayedTask(this, new Runnable() {

						@Override
						public void run() {
							button.setPowered(false);
							e.getPlayer().sendMessage("Button wurde abgeschaltet!");
							block.setData(button.getData());
							return;
						}
						
					}, 200);
				}
				if(btn.isPowered() == true) {
					Button button = new Button(Material.WOOD_BUTTON, block.getData());
					button.setPowered(false);
					e.getPlayer().sendMessage("Befehl abgebrochen!!");
					block.setData(button.getData());
					Bukkit.getScheduler().cancelTask(i);
					return;
				}
			}
			return;
		}
		return;
	}
	
	

}
