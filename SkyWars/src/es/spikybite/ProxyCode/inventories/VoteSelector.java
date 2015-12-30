package es.spikybite.ProxyCode.inventories;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import es.spikybite.ProxyCode.Skywars;
import es.spikybite.ProxyCode.utils.ItemBuilder;

public class VoteSelector extends Menu{

	public VoteSelector() {
		super(Skywars.lang.get("inventories.vote_selector"), 3);
		s(11, ItemBuilder.crearItem(Skywars.lang.getint("inventories_material.vote_chest_id"), 1, 0, Skywars.lang.get("inventories.vote_chest")));
		s(15, ItemBuilder.crearItem(Skywars.lang.getint("inventories_material.vote_time_id"), 1, 0, Skywars.lang.get("inventories.vote_time")));
		
	}

	@Override
	public void onClick(Player p, ItemStack stack) {
		if(stack.getTypeId() == Skywars.lang.getint("inventories_material.vote_chest_id")){
			new VoteChest(p);
		}else 	if(stack.getTypeId() == Skywars.lang.getint("inventories_material.vote_time_id")){
			new VoteTime(p);
		}
	}

}
