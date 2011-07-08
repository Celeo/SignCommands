package com.celeo.signcommands;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockListener;
import org.bukkit.event.block.BlockPlaceEvent;

public class SignPlaceListener extends BlockListener {
	
	public final SignCommands plugin;
	
	public SignPlaceListener(SignCommands instance) {
		plugin = instance;
	}
	
	public void blockPlaceEvent(BlockPlaceEvent event) {
		Player player = event.getPlayer();
		Block b = event.getBlock();
		if(b.getType() == Material.SIGN || b.getType() == Material.SIGN_POST)
		{
			Sign s = (Sign)b.getState();
			String[] lines = s.getLines();
			if(lines[0].equalsIgnoreCase("<Buy Region>") || lines[0].equalsIgnoreCase("<Weather>") || lines[0].equalsIgnoreCase("<Mail>"))
			{
				if(SignCommands.Permissions.has(player, "sc.admin"))
				{
				}
				else
				{
					player.sendMessage(Util.cred + "You cannot place a sign with that command");
					event.setCancelled(true);
				}
			}
		}
	}
	
}