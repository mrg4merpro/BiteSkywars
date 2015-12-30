package es.spikybite.ProxyCode.inventories.settings.lobby_settings;

import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import es.spikybite.ProxyCode.Skywars;
import es.spikybite.ProxyCode.arena.ArenaManager;
import es.spikybite.ProxyCode.inventories.Menu;
import es.spikybite.ProxyCode.inventories.settings.PlayerLobbySettings;
import es.spikybite.ProxyCode.player.SPlayer;
import es.spikybite.ProxyCode.utils.ItemBuilder;

public class CambiarColor extends Menu{
	ArenaManager am = new ArenaManager();
	public CambiarColor() {
		super(Skywars.glass.get("inventory_name"), Skywars.glass.getConfig().getInt("inventory_rows"));
		for(String key : Skywars.glass.getConfig().getConfigurationSection("glass").getKeys(false)){
			ItemStack item = new ItemStack(95, 1, (short) Skywars.glass.getConfig().getInt("glass."+key+".colorID"));
			ItemMeta meta = item.getItemMeta();
			meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', Skywars.glass.getConfig().getString("glass."+key+".display_name")));
		    ArrayList<String> lore = new ArrayList<String>(); 
			for(String k : Skywars.glass.getConfig().getStringList("glass."+key+".description")){
		    	lore.add(ChatColor.translateAlternateColorCodes('&', k));
		    	
		    }
			meta.setLore(lore);
			item.setItemMeta(meta);
			s(Skywars.glass.getConfig().getInt("glass."+key+".slot"), item);
			  s(i().getSize()-1,  ItemBuilder.crearItem1(355, 1, 0, "&c&lBACK", "&7Back to menu settings"));
		}
	}
	
	

	@SuppressWarnings("static-access")
	@Override
	public void onClick(Player p, ItemStack stack) {
		if(stack.getTypeId() == 355){
			new PlayerLobbySettings(p);
		}
		  ConfigurationSection config = Skywars.glass.getConfig().getConfigurationSection("glass");
			for(String key : Skywars.glass.getConfig().getConfigurationSection("glass").getKeys(false)){
				String name = ChatColor.translateAlternateColorCodes('&', config.getString(key+".display_name"));
				String node = Skywars.glass.getConfig().getString("permission_node");
				if(stack.getItemMeta().getDisplayName().contains(name)){
				String perm = config.getString(key+".permission");
				if(!p.hasPermission(node+perm))
				{
					p.sendMessage(Skywars.lang.get("no_permission"));
	                p.closeInventory();
					return;
				}
				   int i = config.getInt(key+".colorID");
				   SPlayer player = SPlayer.getDPlayer(p);
				player.setGlass(key);
				   p.closeInventory();
				   p.sendMessage(Skywars.p + "§aGlass selected " + key);
				}
			}
		
	}
	
}
