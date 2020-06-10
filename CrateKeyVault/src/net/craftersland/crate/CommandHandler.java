package net.craftersland.crate;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandHandler implements CommandExecutor {
	
	private CKV pl;
	
	public CommandHandler(CKV pl) {
		this.pl = pl;
	}
	
	@Override
	public boolean onCommand(final CommandSender sender, Command command, String cmdlabel, String[] args) {
		if (cmdlabel.equalsIgnoreCase("ckv") == true) {
			if (args.length == 1) {
				if (args[0].equalsIgnoreCase("reload") == true) {
					if (sender instanceof Player) {
						if (sender.hasPermission("CKV.admin") == false) {
							pl.getSoundHandler().sendFailedSound((Player) sender);
							sender.sendMessage(pl.getConfigHandler().getStringWithColor("ChatMessages.NoPermission"));
							return true;
						} else {
							pl.getSoundHandler().sendCompleteSound((Player) sender);
							sender.sendMessage(pl.getConfigHandler().getStringWithColor("ChatMessages.CmdReload"));
						}
					}
					pl.getConfigHandler().loadConfig();
					pl.getConfigHandler().loadVaultLocation();
				} else {
					if (sender instanceof Player) {
						pl.getSoundHandler().sendConfirmSound((Player) sender);
					}
					sender.sendMessage(pl.getConfigHandler().getStringWithColor("ChatMessages.CmdHelp"));
				}
			} else {
				if (sender instanceof Player) {
					pl.getSoundHandler().sendConfirmSound((Player) sender);
				}
				sender.sendMessage(pl.getConfigHandler().getStringWithColor("ChatMessages.CmdHelp"));
			}
		}
		return true;
	}

}
