package es.spikybite.ProxyCode.inventories;

import java.util.Arrays;
import java.util.Map.Entry;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import es.spikybite.ProxyCode.Skywars;
import es.spikybite.ProxyCode.controllers.KitController;
import es.spikybite.ProxyCode.utils.ItemBuilder;
import es.spikybite.ProxyCode.utils.Vault;

public class KitMenu extends Menu{

	public KitMenu(Player p) {
		super(Skywars.kits.get("menu_name"), Skywars.kits.getint("menu_rows"));
		for(Entry<KitController, String> kits : KitController.getKits().entrySet()){
			KitController kit = kits.getKey();
			String _name = ChatColor.translateAlternateColorCodes('&', kit.getName());
		    if(kit.getCost() >= 1 && kit.getPermission()!= null){
				if(p.hasPermission(kit.getPermission())){
					s(kit.getSlot(), ItemBuilder.crearItem2(kit.getIcon().getTypeId(), 1, 0, _name, kit.getDesc2()));
				}else {
					s(kit.getSlot(), ItemBuilder.crearItem2(kit.getIcon().getTypeId(), 1, 0, _name, kit.getDesc()));
				}
			}else {
				s(kit.getSlot(), ItemBuilder.crearItem2(kit.getIcon().getTypeId(), 1, 0, _name, kit.getDesc2()));
			}
			
			
		}
	}

	@Override
	public void onClick(Player p, ItemStack stack) {
		for(KitController kit : KitController.getKits().keySet()){
			if(stack.getTypeId() == kit.getIcon().getTypeId()){
				
				// Correct next!
				if(kit.getCost() >= 1 && kit.getPermission()!= null){
					if(p.hasPermission(kit.getPermission())){
					kit.setKit(p);
					p.sendMessage(Skywars.p + Skywars.lang.get("kit_selected").replaceAll("%kit", kit.getName()));
					}else {
					 String executeTransaction = Skywars.kits.getConfig().getString("command_buy").replaceAll("%player", p.getName()).replaceAll("%perm", kit.getPermission());
					 if(Vault.getMoney(p) >= kit.getCost()){
					 kit.setKit(p);
					 Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), executeTransaction);
					 Vault.removeMoney(p, kit.getCost());

					 p.sendMessage(Skywars.p + Skywars.lang.get("kit_purchase").replaceAll("%kit", kit.getName()));
					 p.sendMessage(Skywars.p + Skywars.lang.get("kit_selected").replaceAll("%kit", kit.getName()));
					 }else {
						p.sendMessage(Skywars.p + Skywars.lang.get("kit_not_enough_money").replaceAll("%cost", ""+kit.getCost()));
					}
					}
				}else {
					kit.setKit(p);
					p.sendMessage(Skywars.p + Skywars.lang.get("kit_selected").replaceAll("%kit", kit.getName()));
				}
			}
			p.closeInventory();
		}
		
	}

}
