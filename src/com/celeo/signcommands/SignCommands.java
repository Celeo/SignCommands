package com.celeo.signcommands;

import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.event.Event;

import com.nijiko.permissions.PermissionHandler;
import com.nijikokun.bukkit.Permissions.Permissions;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;

import java.util.logging.Logger;

public class SignCommands extends JavaPlugin {
	
	public final Logger log = Logger.getLogger("Minecraft");
	
	public static PermissionHandler Permissions;
	
	public static ChatColor cgreen = ChatColor.GREEN;
	public static ChatColor cwhite = ChatColor.WHITE;
	public static ChatColor cred = ChatColor.RED;
	public static ChatColor cgray = ChatColor.GRAY;
	
	public SignPlayerListener playerListener = new SignPlayerListener(this);
	
	@Override
	public void onDisable() {
		log.info(Util.pre + "disabled");
	}

	@Override
	public void onEnable() {
		PluginManager mngr = getServer().getPluginManager();
		mngr.registerEvent(Event.Type.PLAYER_INTERACT, this.playerListener, Event.Priority.Normal, this);
		setupPermissions();
		log.info(Util.pre + "enabled");
	}
	
	public void debug(String str) {
		
	}
	
	public void setupPermissions() {
	    Plugin test = getServer().getPluginManager().getPlugin("Permissions");
	    if (Permissions == null)
	      if (test != null) {
	        getServer().getPluginManager().enablePlugin(test);
	        Permissions = ((Permissions)test).getHandler();
	      } else {
	        log.info("[FriendList] requires Permissions, disabling...");
	        getServer().getPluginManager().disablePlugin(this);
	      }
	  }
	
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		return true;
	}
	
}