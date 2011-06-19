package com.celeo.signcommands;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerListener;

public class SignPlayerListener extends PlayerListener {
	
	public final SignCommands plugin;
	
	public SignPlayerListener(SignCommands instance) {
		plugin = instance;
	}
	
	 public void onPlayerInteract(PlayerInteractEvent event) {
		 Player player = event.getPlayer();
		 plugin.log.info(Util.pre + player.getDisplayName() + " is interacting with a sign");
		 if(event.getClickedBlock() == null || event.isCancelled())
		 {
			 return;
		 }
		 Block b = event.getClickedBlock();
		 Material t = b.getType();
		 if(t == Material.SIGN_POST || t == Material.WALL_SIGN)
		 {
			 String[] lines = ((Sign) b.getState()).getLines();
			 if(lines[0].equalsIgnoreCase("[signcommands]"))
			 {
				 if(lines[2].equalsIgnoreCase(""))
				 {
					 
				 }
			 }
		 }
	 }
	
}