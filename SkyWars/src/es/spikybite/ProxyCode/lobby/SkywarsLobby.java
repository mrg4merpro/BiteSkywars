package es.spikybite.ProxyCode.lobby;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import es.spikybite.ProxyCode.inventories.Menu;
import es.spikybite.ProxyCode.lobby.Habilidad.HabilidadType;
import es.spikybite.ProxyCode.lobby.habilidades.Abilitity;
import es.spikybite.ProxyCode.utils.ItemBuilder;

public class SkywarsLobby extends Menu{

	public SkywarsLobby(Player p) {
		super("Skywars Abilities - Page 1", 6);
		// ARROW
		ItemStack locked = ItemBuilder.crearItem1(95, 1, 14, "§cArrow Locked", "§7Unlocked this now!", "§cCost: §b200");
	ItemStack unlocked = ItemBuilder.crearItem1(95, 1, 5, "§aArrow ready", "§5¡You already has this!");
		Abilitity a = new Abilitity("Arrow", "skywars.arrow", 20, locked, unlocked);
		a.senderToInventory(p, i(), 0, HabilidadType.TRIPLE_ARROW);
		
		s(53, ItemBuilder.crearItem(338, 1, 0, "§6Next page"));
	}

	@Override
	public void onClick(Player p, ItemStack stack) {
	
		
	}
	
	
	

}
