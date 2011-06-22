package com.celeo.signcommands;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Logger;

import org.bukkit.ChatColor;
import org.bukkit.util.config.Configuration;

public class Util {
	
	public static SignCommands plugin;
	
	public Util(SignCommands instance) {
		plugin = instance;
	}

	public static void load(SignCommands plugin) {
		config = plugin.getConfiguration();
		try
		{
			admins = (ArrayList<String>) config.getStringList("admins.", admins);
		}
		catch(Exception e)
		{
			admins = new ArrayList<String>();
			saveAll();
		}
		log.info(pre + "settings loaded");
	}
	
	public static void saveAll() {
		config.setProperty("admins.", admins);
		config.save();
		log.info(pre + "settings saved");
	}
	
	public static ChatColor cgreen = ChatColor.GREEN;
	public static ChatColor cdgreen = ChatColor.DARK_GREEN;
	public static ChatColor cwhite = ChatColor.WHITE;
	public static ChatColor cred = ChatColor.RED;
	public static ChatColor cgray = ChatColor.GRAY;
	
	public final static Logger log = Logger.getLogger("Minecraft");	
	public static boolean isDebugging = true;
	public static Configuration config;
	public static String pre = cdgreen + "[" + cgreen + "Sign Commands" + cdgreen + "] ";
	public static String cpre = "[Sign Commands] ";
	
	public static ArrayList<String> admins = new ArrayList<String>();
	public static HashMap<String, String> purchases = new HashMap<String, String>();
	
	public static int numPurchases = 0;
	
}