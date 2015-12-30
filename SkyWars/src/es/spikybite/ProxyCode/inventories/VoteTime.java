package es.spikybite.ProxyCode.inventories;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import es.spikybite.ProxyCode.Skywars;
import es.spikybite.ProxyCode.arena.Arena;
import es.spikybite.ProxyCode.arena.ArenaManager;
import es.spikybite.ProxyCode.utils.ItemBuilder;

public class VoteTime extends Menu{
	public static HashMap<Player, Integer> maxVote = new HashMap<Player, Integer>();
	public VoteTime(Player p) {
		super(Skywars.lang.get("inventories.vote_time"), 3);
		Arena a = new ArenaManager().getArena(p);
		int IB = a.dataTime().get("Day");
		int IN = a.dataTime().get("Sunset");
		int IO = a.dataTime().get("Night");
		ArrayList<String> lore = new ArrayList<String>();
		for(String basic : Skywars.lang.getConfig().getStringList("items_inventories.vote_time_day_lore")){
			lore.add(basic.replaceAll("%votes", ""+IB));
		}
		s(10, ItemBuilder.crearItem2(159, 1, 4, Skywars.lang.get("items_inventories.vote_time_day"), lore));
		lore.clear();
		for(String normal : Skywars.lang.getConfig().getStringList("items_inventories.vote_time_sunset_lore")){
			lore.add(normal.replaceAll("%votes", ""+IN));
		}
		s(13, ItemBuilder.crearItem2(159, 1, 1, Skywars.lang.get("items_inventories.vote_time_sunset"), lore));
		lore.clear();
		for(String op : Skywars.lang.getConfig().getStringList("items_inventories.vote_time_night_lore")){
		lore.add(op.replaceAll("%votes", ""+IO));
		}
		s(16, ItemBuilder.crearItem2(159, 1, 15, Skywars.lang.get("items_inventories.vote_time_night"), lore));
		o(p);
	}
	

	@Override
	public void onClick(Player p, ItemStack stack) {
		String name = ChatColor.translateAlternateColorCodes('&', stack.getItemMeta().getDisplayName());
        ArenaManager am = new ArenaManager();		
        Arena a = am.getArena(p);
		if(a==null){
			p.closeInventory();
			return;
		}
		if(a.isGame()){
			p.closeInventory();
			return;
		}
		if(name.contains(Skywars.lang.get("items_inventories.vote_time_day"))){
			if(!p.hasPermission("skywars.vote.time.day")){
				p.sendMessage(Skywars.lang.get("no_permission"));p.closeInventory();
				return;
			}
			if(maxVote.containsKey(p)){
				p.sendMessage(Skywars.lang.get("max_votes_used"));p.closeInventory();
				return;
			}

			maxVote.put(p, 1);
			a.dataTime().put("Day", a.timeData("Day")+1);
			a.sender(Skywars.p +  Skywars.lang.get("vote_day").replaceAll("%name", p.getName()).replaceAll("%votes", ""+a.timeData("Day")));
			p.closeInventory();}else if(name.contains(Skywars.lang.get("items_inventories.vote_time_sunset"))){
				if(maxVote.containsKey(p)){
					p.sendMessage(Skywars.lang.get("max_votes_used"));p.closeInventory();
					return;
				}
			if(!p.hasPermission("skywars.vote.time.sunset")){
				p.sendMessage(Skywars.lang.get("no_permission"));p.closeInventory();
				return;
			}
			maxVote.put(p, 1);
	      	a.dataTime().put("Sunset", a.timeData("Sunset")+1);
			a.sender(Skywars.p +  Skywars.lang.get("vote_sunset").replaceAll("%name", p.getName()).replaceAll("%votes", ""+a.timeData("Sunset")));
			p.closeInventory();}else if(name.contains(Skywars.lang.get("items_inventories.vote_time_night"))){
				if(maxVote.containsKey(p)){
					p.sendMessage(Skywars.lang.get("max_votes_used"));p.closeInventory();
					return;
				}
			if(!p.hasPermission("skywars.vote.time.night")){
				p.sendMessage(Skywars.lang.get("no_permission"));
				p.closeInventory();
				return;
			}
			maxVote.put(p, 1);
			a.dataTime().put("Night", a.timeData("Night")+1);
			a.sender(Skywars.p +  Skywars.lang.get("vote_night").replaceAll("%name", p.getName()).replaceAll("%votes", ""+a.timeData("Night")));
			p.closeInventory();}
		
	}

}
