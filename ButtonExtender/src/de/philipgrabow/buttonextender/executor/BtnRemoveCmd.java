package de.philipgrabow.buttonextender.executor;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.philipgrabow.buttonextender.Main;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;

public class BtnRemoveCmd implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(cmd.getName().equalsIgnoreCase("btndel")) {
			if(args.length == 0) {
//				Wenn ein Vorgang aktiv ist....
				if(Main.map2.containsKey(sender.getName())) {
					sender.sendMessage("Vorgang abgebrochen!");
					Main.map2.remove(sender.getName());
					return true;
				} else {
					Main.map2.put(sender.getName(), "");
					TextComponent message = new TextComponent( "Löschmodus Button aktiviert!" );
					message.setUnderlined(true);
					message.setColor( ChatColor.RED );
					Player p = (Player) sender;
					p.spigot().sendMessage(ChatMessageType.ACTION_BAR, message);
					sender.sendMessage("Löschmodus:");
					sender.sendMessage("Bitte klicke den Knopf mit Rechtsklick an den du löschen willst...");
					sender.sendMessage(ChatColor.YELLOW + "Du kannst diesen Modus beenden indem du einen Knopf angeklickt hast oder du nochmals /btndel eingibst!");
					return true;
				}
			}
			if(args.length != 0) {
				return false;
			}
		}
		return false;
	}
	
	

}
