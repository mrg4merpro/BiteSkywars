package es.spikybite.ProxyCode.controllers;


import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.SignChangeEvent;

import es.spikybite.ProxyCode.Skywars;
import es.spikybite.ProxyCode.arena.Arena;
import es.spikybite.ProxyCode.arena.ArenaManager;
import es.spikybite.ProxyCode.controllers.cartel.Cartel;

public class SignController implements Listener{

	
	@EventHandler
	public void changeEvent(SignChangeEvent event){

		if(event.getLine(0).equalsIgnoreCase("[SW]")){
			Player p = event.getPlayer();
			ArenaManager am = new ArenaManager();
			if(!p.hasPermission("skywars.sign")){
				event.getBlock().breakNaturally();
				p.sendMessage(Skywars.p + "§cYou dont have permissions");
				return;
			}
			if(!am.exist(event.getLine(1))){
				event.getBlock().breakNaturally();
				p.sendMessage(Skywars.p + "§cThis arena name not found!");
				return;
			}
		
            Skywars.signs.getConfig().set("signs."+event.getLine(1), am.setLoc(event.getBlock().getLocation()));
			Skywars.signs.save();

			p.sendMessage(Skywars.p + "§aSign created successful for arena "+ event.getLine(1));

		}
	}

	
	
	
	 
	@EventHandler
	public void breakEvent(BlockBreakEvent event){
		Block block = event.getBlock();
      
		Player p = event.getPlayer();
		BlockState state = block.getState();
	if(Cartel.hasBlock(event.getBlock().getLocation())){
		p.sendMessage(Skywars.p + "§cYou cant break this block!");
		event.setCancelled(true);
		return;
	}
		if(state instanceof Sign){
			Sign sign = (Sign)state;
			Block block1 = sign.getLocation().getBlock();
			
			    
		
			
					if(!p.hasPermission("skywars.sign")){
						p.sendMessage(Skywars.p + "§cYou dont have permissions for break signs!");
						event.setCancelled(true);
						return;
					}
					
					
					event.getBlock().breakNaturally();

				    Cartel cartel = Cartel.getSign(event.getBlock().getLocation());
				    if(cartel==null)return;
					p.sendMessage(Skywars.p + "§aSign "+cartel.getName() +" removed!");
					Skywars.signs.getConfig().set("signs."+cartel.getName(), null);
					Skywars.signs.save();
			
		
			}
		}

}
