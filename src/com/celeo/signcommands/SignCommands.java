package com.celeo.signcommands;

import org.bukkit.Server;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import com.nijiko.permissions.PermissionHandler;
import com.nijikokun.bukkit.Permissions.Permissions;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import com.iConomy.*;

public class SignCommands extends JavaPlugin {
	public static PermissionHandler Permissions;
	public iConomy iConomy = null;
	public SignInteractListener interactListener = new SignInteractListener(this);
	public SignPlaceListener placeListener = new SignPlaceListener(this);
	
	@Override
	public void onDisable() {
		Util.saveAll();
		Util.log.info(Util.cpre + "disabled");
	}

	@Override
	public void onEnable() {
		Util.load(this);
		PluginManager mngr = getServer().getPluginManager();
		mngr.registerEvent(Event.Type.PLAYER_INTERACT, this.interactListener, Event.Priority.Normal, this);
		mngr.registerEvent(Event.Type.BLOCK_PLACE, this.placeListener, Event.Priority.Normal, this);
		setupPermissions();
		if (iConomy == null)
		{
			Plugin test = getServer().getPluginManager().getPlugin("iConomy");
			if (test != null)
			{
				if (test.isEnabled() && test.getClass().getName().equals("com.iConomy.iConomy"))
				{
					iConomy = (iConomy)test;
					debug(Util.cpre + "iConomy loaded");
				}
			}
		}
		Util.log.info(Util.cpre + "enabled");
	}
	
	public void debug(String str) {
		if(Util.isDebugging)
			Util.log.info(str);
	}
	
	public void setupPermissions() {
	    Plugin test = getServer().getPluginManager().getPlugin("Permissions");
	    if (Permissions == null)
	      if (test != null)
		  {
	        getServer().getPluginManager().enablePlugin(test);
	        Permissions = ((Permissions)test).getHandler();
	      }
		  else
			{
			Util.log.info(Util.cpre + "requires Permissions, disabling...");
	        getServer().getPluginManager().disablePlugin(this);
	      }
	  }
	
	public boolean onCommand(CommandSender sender, Command command, String commandLabel, String[] args) {
		if(commandLabel.equalsIgnoreCase("sc") && args.length >= 0 && sender instanceof Player)
		{
			Player player = (Player)sender;
			Server server = sender.getServer();
			String cmd = args[0];
			if(Permissions.has(player, "sc.admin"))
			{
				if(cmd.equalsIgnoreCase("-a"))
				{
					if(args.length >= 1)
					{
						Util.admins.add(args[1]);
						player.sendMessage(Util.cgreen + args[1] + " added to the admin list");
						Util.saveAll();
					}
					else
					{
						player.sendMessage(Util.cred + "Specify a player to add");
					}
				}
				if(cmd.equalsIgnoreCase("-r") || cmd.equalsIgnoreCase("-d"))
				{
					if(args.length >= 2)
					{
						if(Util.purchases.containsKey(args[1]))
						{
							Player removed = server.getPlayer(args[1]);
							removed.sendMessage(Util.cgreen + "Your payment for land has been serviced. Enjoy!");
							player.sendMessage(Util.cgreen + args[1] + "'s payment has been completed.");
							Util.purchases.remove(args[1]);
						}
						else
						{
							player.sendMessage(Util.cred + "Player not in purchases list or incorrectly spelled.");
						}
					}
					else
						player.sendMessage(Util.cred + "Incorrect parameters");
				}
				if(cmd.equalsIgnoreCase("-s") || cmd.equalsIgnoreCase("-v"))
				{
					String list = "";
					for(String str : Util.purchases.keySet())
					{
						list += str + " at " + Util.purchases.get(str);
					}
					player.sendMessage(Util.cgray + Util.pre + "The following players have purchased land: ");
					player.sendMessage(Util.cgreen + list);
				}
			}
			else
			{
				if(cmd.equalsIgnoreCase("mail"))
				{
					if(args.length >= 2)
					{
						String msg = "";
						for(int i = 1; i < args.length; i++)
						{
							msg += args[i];
						}
						Util.makingMail.add(player);
						player.sendMessage(Util.cgreen + "Message created");
						player.sendMessage(Util.cgreen + "Hit the mail box for the player you wish to send mail to");
					}
					else
					{
						player.sendMessage(Util.cred + "Command is /mail [your message]");
					}
				}
			}
		}
		return true;
	}
	
}