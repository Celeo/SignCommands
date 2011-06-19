package com.celeo.signcommands;

import org.bukkit.util.config.Configuration;

public class Util {
	
	public SignCommands plugin;
	
	public Util(SignCommands instance) {
		plugin = instance;
	}
	
	public void load() {
		plugin.log.info(pre + "settings loaded");
	}
	
	public void saveAll() {
		plugin.log.info(pre + "settings saved");
	}
	
	public boolean isDebugging = true;
	public static Configuration config;
	public static String pre = "[Sign Commands] ";
	
}