package es.spikybite.ProxyCode.inventories;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import es.spikybite.ProxyCode.inventories.settings.PlayerGlass;
import es.spikybite.ProxyCode.inventories.settings.PlayerTrails;
import es.spikybite.ProxyCode.utils.ItemBuilder;

public class SettingsSelector extends Menu{

	public SettingsSelector() {
		super("&0&nSkywars Settings", 3);
        s(11, ItemBuilder.crearItem1(20, 1, 0, "&7Glass Colour!", "&3Select your glass colour!"));
        s(15, ItemBuilder.crearItem1(262, 1, 0, "&6Arrow Trails", "&5Select your arrow trail!"));
	}

	@Override
	public void onClick(Player p, ItemStack stack) {
		String name = stack.getItemMeta().getDisplayName();
		if(name.contains("§7Glass Colour!")){
			new PlayerGlass().o(p);
		}else if(name.contains("§6Arrow Trails")){
			new PlayerTrails().o(p);
		}
		
	}
}
