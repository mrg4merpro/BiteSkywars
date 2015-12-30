package es.spikybite.ProxyCode.inventories;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import es.spikybite.ProxyCode.arena.Arena;
import es.spikybite.ProxyCode.arena.ArenaManager;
import es.spikybite.ProxyCode.utils.ItemBuilder;
import es.spikybite.ProxyCode.Skywars;

public class VoteChest extends Menu{
public static HashMap<Player, Integer> maxVote = new HashMap<Player, Integer>();
	public VoteChest(Player p) {
		super(Skywars.lang.get("inventories.vote_chest"), 3);
		Arena a = new ArenaManager().getArena(p);
		int IB = a.dataChest().get("Basic");
		int IN = a.dataChest().get("Normal");
		int IO = a.dataChest().get("Overpowered");
		ArrayList<String> lore = new ArrayList<String>();
		for(String basic : Skywars.lang.getConfig().getStringList("items_inventories.vote_chest_basic_lore")){
			lore.add(basic.replaceAll("%votes", ""+IB));
		}
		s(10, ItemBuilder.crearItem2(298, 1, 0, Skywars.lang.get("items_inventories.vote_chest_basic"), lore));
		lore.clear();
		for(String normal : Skywars.lang.getConfig().getStringList("items_inventories.vote_chest_normal_lore")){
			lore.add(normal.replaceAll("%votes", ""+IN));
		}
		s(13, ItemBuilder.crearItem2(302, 1, 0, Skywars.lang.get("items_inventories.vote_chest_normal"), lore));
		lore.clear();
		for(String op : Skywars.lang.getConfig().getStringList("items_inventories.vote_chest_overpowered_lore")){
		lore.add(op.replaceAll("%votes", ""+IO));
		}
		s(16, ItemBuilder.crearItem2(310, 1, 0, Skywars.lang.get("items_inventories.vote_chest_overpowered"), lore));
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
		if(name.contains(Skywars.lang.get("items_inventories.vote_chest_basic"))){
			if(!p.hasPermission("skywars.vote.basic")){
				p.sendMessage(Skywars.lang.get("no_permission"));p.closeInventory();
				return;
			}
			if(maxVote.containsKey(p)){
				p.sendMessage(Skywars.lang.get("max_votes_used"));p.closeInventory();
				return;
			}

			maxVote.put(p, 1);
			a.dataChest().put("Basic", a.chestData("Basic")+1);
			a.sender(Skywars.p +  Skywars.lang.get("vote_basic").replaceAll("%name", p.getName()).replaceAll("%votes", ""+a.chestData("Basic")));
			p.closeInventory();}else if(name.contains(Skywars.lang.get("items_inventories.vote_chest_normal"))){
				if(maxVote.containsKey(p)){
					p.sendMessage(Skywars.lang.get("max_votes_used"));p.closeInventory();
					return;
				}
			if(!p.hasPermission("skywars.vote.normal")){
				p.sendMessage(Skywars.lang.get("no_permission"));p.closeInventory();
				return;
			}
			maxVote.put(p, 1);
	      	a.dataChest().put("Normal", a.chestData("Normal")+1);
			a.sender(Skywars.p +  Skywars.lang.get("vote_normal").replaceAll("%name", p.getName()).replaceAll("%votes", ""+a.chestData("Normal")));
			p.closeInventory();}else if(name.contains(Skywars.lang.get("items_inventories.vote_chest_overpowered"))){
				if(maxVote.containsKey(p)){
					p.sendMessage(Skywars.lang.get("max_votes_used"));p.closeInventory();
					return;
				}
			if(!p.hasPermission("skywars.vote.op")){
				p.sendMessage(Skywars.lang.get("no_permission"));
				p.closeInventory();
				return;
			}
			maxVote.put(p, 1);
			a.dataChest().put("Overpowered", a.chestData("Overpowered")+1);
			a.sender(Skywars.p +  Skywars.lang.get("vote_overpowered").replaceAll("%name", p.getName()).replaceAll("%votes", ""+a.chestData("Overpowered")));
			p.closeInventory();}
		
	}

}
