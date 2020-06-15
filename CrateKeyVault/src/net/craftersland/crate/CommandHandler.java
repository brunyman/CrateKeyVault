package net.craftersland.crate;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

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
			} else if (args.length == 2) {
				if (args[0].equalsIgnoreCase("open") == true) {
					if (!(sender instanceof Player)) {
						sender.sendMessage(pl.getConfigHandler().getStringWithColor("ChatMessages.CmdForPlayer"));
						return true;
					}
					Player p = (Player) sender;

					if (sender.hasPermission("CKV.open") == false) {
						pl.getSoundHandler().sendFailedSound(p);
						p.sendMessage(pl.getConfigHandler().getStringWithColor("ChatMessages.NoPermission"));
						return true;
					} else {
						if (p.getGameMode() != GameMode.SURVIVAL) {
							pl.getSoundHandler().sendFailedSound(p);
							p.sendMessage(pl.getConfigHandler().getStringWithColor("ChatMessages.CrateOpenSurvival"));
							return true;
						}
						Bukkit.getScheduler().runTask((Plugin) this.pl, new Runnable() {
							public void run() {
								pl.getVaultHandler().adminOpenVault(p, args[1]);
							}
						});
					}
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
