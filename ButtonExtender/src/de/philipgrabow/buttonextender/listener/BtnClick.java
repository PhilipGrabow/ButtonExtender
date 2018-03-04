package de.philipgrabow.buttonextender.listener;

import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.material.Button;

import de.philipgrabow.buttonextender.Main;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;

public class BtnClick implements Listener {
	
	private Main plugin;
	public BtnClick(Main plugin) {
		this.plugin = plugin;
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler(priority = EventPriority.NORMAL)
	public void onBtnClick(PlayerInteractEvent e) {
		if(e.getAction() == Action.RIGHT_CLICK_BLOCK) {
			if(e.getClickedBlock().getType() == Material.WOOD_BUTTON || e.getClickedBlock().getType() == Material.STONE_BUTTON) {
				//Überprüfung ob Aktivierungsmodus aktiv ist....
				if(Main.map.containsKey(e.getPlayer().getName())) {
					e.setCancelled(true);
					String name = Main.map.get(e.getPlayer().getName());
					int x = e.getClickedBlock().getX();
					int y = e.getClickedBlock().getY();
					int z = e.getClickedBlock().getZ();
					String coord = x+","+y+","+z;
					Main.cfg.set(coord + ".Name", name);
					try {
						Main.cfg.save(Main.file);
					} catch (IOException e1) {
						e.getPlayer().sendMessage("Fehler beim Speichern!");
						return;
					}
					TextComponent message = new TextComponent( "Button erfolgreich bestimmt!" );
					message.setColor( ChatColor.RED );
					e.getPlayer().spigot().sendMessage(ChatMessageType.ACTION_BAR, message);
					Main.map.remove(e.getPlayer().getName());
					return;
				}
				if(Main.map2.containsKey(e.getPlayer().getName())) {
					e.setCancelled(true);
					int x = e.getClickedBlock().getX();
					int y = e.getClickedBlock().getY();
					int z = e.getClickedBlock().getZ();
					String coord = x+","+y+","+z;
					Main.cfg.set(coord, null);
					try {
						Main.cfg.save(Main.file);
					} catch (IOException e1) {
						e.getPlayer().sendMessage("Fehler beim Speichern!");
						return;
					}
					TextComponent message = new TextComponent( "Button erfolgreich gelöscht!" );
					message.setColor( ChatColor.RED );
					e.getPlayer().spigot().sendMessage(ChatMessageType.ACTION_BAR, message);
					Main.map2.remove(e.getPlayer().getName());
					return;
				}
				int x = e.getClickedBlock().getX();
				int y = e.getClickedBlock().getY();
				int z = e.getClickedBlock().getZ();
				String coord = x+","+y+","+z;
				//
				//
				//
				if(Main.cfg.contains(coord)) {
					int i = 0;
					Block block = e.getClickedBlock();
					Button btn = (Button)block.getState().getData();
					if(btn.isPowered() == false) {
						Button button = new Button(Material.WOOD_BUTTON, block.getData());
						button.setPowered(true);
						block.setData(button.getData());
						TextComponent message = new TextComponent( "Der Button wurde aktiviert er schaltet sich nach 10 Sekunden aus!" );
						message.setColor( ChatColor.RED );
						e.getPlayer().spigot().sendMessage(ChatMessageType.ACTION_BAR, message);
						i = Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {

							@Override
							public void run() {
								button.setPowered(false);
								TextComponent message = new TextComponent( "Button wurde abgeschaltet!" );
								message.setColor( ChatColor.RED );
								e.getPlayer().spigot().sendMessage(ChatMessageType.ACTION_BAR, message);
								block.setData(button.getData());
								return;
							}
							
						}, 200);
					}
					if(btn.isPowered() == true) {
						e.setCancelled(true);
						Button button = new Button(Material.WOOD_BUTTON, block.getData());
						button.setPowered(false);
						TextComponent message = new TextComponent( "Befehl abgebrochen!!" );
						message.setColor( ChatColor.RED );
						e.getPlayer().spigot().sendMessage(ChatMessageType.ACTION_BAR, message);
						block.setData(button.getData());
						Bukkit.getScheduler().cancelTask(i);
						return;
					}
				}
				return;
			}
		}
	}

}
