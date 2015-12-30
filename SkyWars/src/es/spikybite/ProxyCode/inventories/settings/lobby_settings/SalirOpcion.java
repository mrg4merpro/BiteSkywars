package es.spikybite.ProxyCode.inventories.settings.lobby_settings;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import es.spikybite.ProxyCode.arena.Arena;
import es.spikybite.ProxyCode.arena.ArenaManager;
import es.spikybite.ProxyCode.inventories.Menu;
import es.spikybite.ProxyCode.utils.ItemBuilder;

public class SalirOpcion extends Menu{

	public SalirOpcion() {
		super("&c&lLeave Menu", 1);
		s(2, ItemBuilder.crearItem(133, 1, 0, "&aLeave"));
		s(6, ItemBuilder.crearItem(152, 1, 0, "&cCancel"));
		
	}

	@Override
	public void onClick(Player p, ItemStack stack) {
		if(stack.getTypeId()==133){
			Arena a = new ArenaManager().getArena(p);
		   if(a==null){
			   return;
		   }
		   a.leave(p);
		   p.closeInventory();
		}else if(stack.getTypeId() == 152)
		{
			p.closeInventory();
		}
		
	}

}
