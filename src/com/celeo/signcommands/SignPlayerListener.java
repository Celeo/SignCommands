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

public class SignPlayerListener extends PlayerListener {
	
	public final SignCommands plugin;
	
	public SignPlayerListener(SignCommands instance) {
		plugin = instance;
	}
	
	 public void onPlayerInteract(PlayerInteractEvent event) {
		 Player player = event.getPlayer();
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
						 plugin.debug(msg);
						 if(Util.admins != null)
						 {
							 msg = Util.cgreen + msg;
							 Server server = event.getPlayer().getServer();
							 for(String p : Util.admins)
							 {
								 Player admin = server.getPlayer(p);
								 if(admin.isOnline())
									 admin.sendMessage(msg);
							 }
						 }
						 balance.subtract(cost);
						 Util.numPurchases ++;
						 Util.purchases.put(player.getDisplayName(), x.toString() +
								 " " + y.toString() + " " + z.toString());
						 plugin.debug(Util.pre + player.getDisplayName() + " purchased land for " + cost);	
						 player.sendMessage(Util.cgreen + "You have succesfully purchased the land at " + Util.cdgreen +
								 x.toString() + " " + y.toString() + " " + z.toString() + Util.cgreen +
								 " for " + Util.cdgreen + cost + Util.cgreen + " coins.");
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
	 }
	
}