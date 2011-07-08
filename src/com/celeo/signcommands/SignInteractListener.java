package com.celeo.signcommands;

import org.bukkit.Material;
import org.bukkit.Server;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerListener;

import com.iConomy.iConomy;
import com.iConomy.system.Holdings;

public class SignInteractListener extends PlayerListener {
	
	public final SignCommands plugin;
	
	public SignInteractListener(SignCommands instance) {
		plugin = instance;
	}
	
	public void onPlayerInteract(PlayerInteractEvent event) {
		Player player = event.getPlayer();
		Server server = event.getPlayer().getServer();
		if(event.getClickedBlock() == null || event.isCancelled())
		{
			return;
		}
		Block b = event.getClickedBlock();
		if(b.getType() == Material.SIGN_POST || b.getType() == Material.WALL_SIGN)
		{
			Util.log.info(Util.cpre + player.getDisplayName() + " is interacting with a sign");
			Sign s = (Sign)b.getState();
			String[] lines = s.getLines();
			
			//Buying Regions
			if(lines[0].equalsIgnoreCase("<Buy Region>"))
			{
				Integer x = s.getX();
				Integer y = s.getY();
				Integer z = s.getZ();
				double cost = Double.parseDouble(lines[2]);
				if(iConomy.hasAccount(player.getDisplayName()))
				{
					Holdings balance = iConomy.getAccount(player.getDisplayName()).getHoldings();
					if(balance.hasEnough(cost))
					{
						String msg = 
							Util.pre + player.getDisplayName() + " paid for a region at " +
							x.toString() + " " + y.toString() + " " + z.toString();
						if(Util.admins != null)
						{
							msg = Util.cgreen + msg;
							for(Player p : server.getOnlinePlayers())
							{
								if(Util.admins.contains(p.getDisplayName()))
								{
									p.sendMessage(msg);
								}
							}
						}
						balance.subtract(cost);
						Util.numPurchases ++;
						Util.purchases.put(player.getDisplayName(), x.toString() +
							", " + y.toString() + ", " + z.toString());
						player.sendMessage(Util.cgreen + "You have succesfully purchased the land at " + Util.cdgreen +
							x.toString() + ", " + y.toString() + ", " + z.toString() + Util.cgreen +
							" for " + Util.cdgreen + cost + Util.cgreen + " coins.");
						s.setLine(0, "<Region Bought>");
						s.setLine(2, "");
						s.setLine(3, player.getDisplayName());
						s.update();
					}
					else
					{
						player.sendMessage(Util.cred + "You do not have enough money for this purchase");
					}
				}
				else
				{
					player.sendMessage(Util.cred + "You do not have an iConomy account");
				}
			}
			//Weather control
			if(lines[0].equalsIgnoreCase("<Weather>"))
			{
				double cost = Double.parseDouble(lines[2]);
				if(lines[1].equalsIgnoreCase("Rain"))
				{
					if(iConomy.hasAccount(player.getDisplayName()))
					{
						Holdings balance = iConomy.getAccount(player.getDisplayName()).getHoldings();
						if(balance.hasEnough(cost))
						{
							balance.subtract(cost);
							player.getWorld().setStorm(true);
							player.sendMessage(Util.cblue + "Weaher set to rain");
						}
						else
						{
							player.sendMessage(Util.cred + "You do not have enough money for this purchase");
						}
					}
					else
					{
						player.sendMessage(Util.cred + "You do not have an iConomy account");
					}
				}
				else if(lines[1].equalsIgnoreCase("Storm"))
				{
					if(iConomy.hasAccount(player.getDisplayName()))
					{
						Holdings balance = iConomy.getAccount(player.getDisplayName()).getHoldings();
						if(balance.hasEnough(cost))
						{
							balance.subtract(cost);
							player.getWorld().setThundering(true);
							player.sendMessage(Util.cblue + "Weaher set to storm");
						}
						else
						{
							player.sendMessage(Util.cred + "You do not have enough money for this purchase");
						}
					}
					else
					{
						player.sendMessage(Util.cred + "You do not have an iConomy account");
					}
				}
				else if(lines[1].equalsIgnoreCase("Clear"))
				{
					if(iConomy.hasAccount(player.getDisplayName()))
					{
						Holdings balance = iConomy.getAccount(player.getDisplayName()).getHoldings();
						if(balance.hasEnough(cost))
						{
							balance.subtract(cost);
							player.getWorld().setStorm(false);
							player.getWorld().setThundering(false);
							player.sendMessage(Util.cblue + "Weaher set to clear");
						}
						else
						{
							player.sendMessage(Util.cred + "You do not have enough money for this purchase");
						}
					}
					else
					{
						player.sendMessage(Util.cred + "You do not have an iConomy account");
					}
				}
			}
			//Mail sending
			if(lines[0].equalsIgnoreCase("<Mail>"))
			{
				if(Util.makingMail.contains(player))
				{
					//Send the mail
					
					Util.makingMail.remove(player);
					player.sendMessage(Util.cgreen + "Message delivered to " + lines[2]);
				}
				else
				{
					player.sendMessage(Util.cred + "You have not created a message yet. /mail [your message],");
					player.sendMessage(Util.cred + "then hit a Mail Sign");
				}
			}
		}
	}
	
}