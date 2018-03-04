package de.philipgrabow.buttonextender.executor;


import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.philipgrabow.buttonextender.Main;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;

public class BtnCreateCmd implements CommandExecutor {
	
//	215,45,30:
//		Name: Gehege
//	
//	
//	
//	
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(cmd.getName().equalsIgnoreCase("btnadd")) {
			if(args.length == 1) {
				String name = args[0];
//				Wenn ein Vorgang aktiv ist...
				if(Main.map.containsKey(sender.getName())) {
					sender.sendMessage("Vorgang abgebrochen!");
					Main.map.remove(sender.getName());
					return true;
				} else {
					Main.map.put(sender.getName(), name);
					TextComponent message = new TextComponent( "Aktivierungsmodus Button aktiv!" );
					message.setUnderlined(true);
					message.setColor( ChatColor.RED );
					Player p = (Player) sender;
					p.spigot().sendMessage(ChatMessageType.ACTION_BAR, message);
					sender.sendMessage("Aktivierungsmodus:");
					sender.sendMessage("Bitte klicke den Knopf mit Rechtsklick an den du umwandeln willst...");
					sender.sendMessage(ChatColor.YELLOW + "Du kannst diesen Modus beenden indem du einen Knopf bestimmt hast oder du nochmals /btnadd ("+name+") eingibst!");
					return true;
				}
			}
//			Wenn falsche Argumente verwendet wurden....
			if(args.length != 1) {
				return false;
			}
		}
		return false;
	}

}
