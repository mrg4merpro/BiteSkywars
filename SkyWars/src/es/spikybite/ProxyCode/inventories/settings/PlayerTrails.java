package es.spikybite.ProxyCode.inventories.settings;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import es.spikybite.ProxyCode.Skywars;
import es.spikybite.ProxyCode.database.DataStorage;
import es.spikybite.ProxyCode.inventories.Menu;
import es.spikybite.ProxyCode.player.SPlayer;
import es.spikybite.ProxyCode.utils.ItemBuilder;
import es.spikybite.ProxyCode.utils.Trails;

public class PlayerTrails extends Menu{

	public PlayerTrails() {
		super("&5Arrow trails", 1);
		s(0, ItemBuilder.crearItem(341, 1, 0, "&a&lSlime Trail"));
		s(1, ItemBuilder.crearItem(373, 1, 0, "&9&lWater Trail"));
		s(2, ItemBuilder.crearItem(327, 1, 0, "&c&lLava Trail"));
		s(3, ItemBuilder.crearItem(116, 1, 0, "&5&lMagic Trail"));
		s(4, ItemBuilder.crearItem(388, 1, 0, "&2&lVillager Trail"));
		s(5, ItemBuilder.crearItem(84, 1, 0, "&7&lNote Trail"));
	}

	@Override
	public void onClick(Player p, ItemStack stack) {
		Material name = stack.getType();
		   SPlayer player = SPlayer.getDPlayer(p);
		if(name.equals(Material.SLIME_BALL)){
		if(p.hasPermission(Trails.SLIME.get())){
		
			p.sendMessage(Skywars.p + "§aYou selected Slime trail!");
			player.setTrail("slime");
		}else {

			p.sendMessage(Skywars.p + "§cYou dont have permissions!");
		}	p.closeInventory();
		}else 	if(name.equals(Material.POTION)){
			if(p.hasPermission(Trails.WATER.get())){

				p.sendMessage(Skywars.p + "§aYou selected Water trail!");			player.setTrail("water");
			}else {
				p.sendMessage(Skywars.p + "§cYou dont have permissions!");
			}	p.closeInventory();
			}else 	if(name.equals(Material.LAVA_BUCKET)){
				if(p.hasPermission(Trails.LAVA.get())){
		p.sendMessage(Skywars.p + "§aYou selected Lava trail!");			player.setTrail("lava");
				}else {
					p.sendMessage(Skywars.p + "§cYou dont have permissions!");
				}	p.closeInventory();
				}else 	if(name.equals(Material.ENCHANTMENT_TABLE)){
					if(p.hasPermission(Trails.MAGIC.get())){
	p.sendMessage(Skywars.p + "§aYou selected Magic trail!");			player.setTrail("magic");
					}else {
						p.sendMessage(Skywars.p + "§cYou dont have permissions!");
					}	p.closeInventory();
					}else 	if(name.equals(Material.EMERALD)){
						if(p.hasPermission(Trails.VILLAGER.get())){
		p.sendMessage(Skywars.p + "§aYou selected Villager trail!");			player.setTrail("villager");
						}else {
							p.sendMessage(Skywars.p + "§cYou dont have permissions!");
						}	p.closeInventory();
						}else 	if(name.equals(Material.JUKEBOX)){
							if(p.hasPermission(Trails.NOTE.get())){
	p.sendMessage(Skywars.p + "§aYou selected Note trail!");			player.setTrail("note");
							
							}else {
								p.sendMessage(Skywars.p + "§cYou dont have permissions!");
							}	p.closeInventory();
							}
		
	}

	
}
