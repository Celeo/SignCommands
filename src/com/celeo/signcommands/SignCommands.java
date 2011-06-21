package com.celeo.signcommands;

import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.event.Event;

import com.nijiko.permissions.PermissionHandler;
import com.nijikokun.bukkit.Permissions.Permissions;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import com.iConomy.*;

public class SignCommands extends JavaPlugin {
	public static PermissionHandler Permissions;
	public iConomy iConomy = null;
	public SignPlayerListener playerListener = new SignPlayerListener(this);
	
	@Override
	public void onDisable() {
		Util.saveAll();
		Util.log.info(Util.pre + "disabled");
	}

	@Override
	public void onEnable() {
		Util.load(this);
		PluginManager mngr = getServer().getPluginManager();
		mngr.registerEvent(Event.Type.PLAYER_INTERACT, this.playerListener, Event.Priority.Normal, this);
		setupPermissions();
		if (iConomy == null)
		{
			Plugin test = getServer().getPluginManager().getPlugin("iConomy");
			if (test != null)
			{
				if (test.isEnabled() && test.getClass().getName().equals("com.iConomy.iConomy"))
				{
					iConomy = (iConomy)test;
				}
			}
			debug(Util.pre + "iConomy loaded");
		}
		Util.log.info(Util.pre + "enabled");
	}
	
	public void debug(String str) {
		if(Util.isDebugging)
			Util.log.info(str);
	}
	
	public void setupPermissions() {
	    Plugin test = getServer().getPluginManager().getPlugin("Permissions");
	    if (Permissions == null)
	      if (test != null) {
	        getServer().getPluginManager().enablePlugin(test);
	        Permissions = ((Permissions)test).getHandler();
	      } else {
	    	  Util.log.info("[FriendList] requires Permissions, disabling...");
	        getServer().getPluginManager().disablePlugin(this);
	      }
	  }
	
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		return true;
	}
	
}