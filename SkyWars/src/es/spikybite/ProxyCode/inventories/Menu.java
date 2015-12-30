package es.spikybite.ProxyCode.inventories;



import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import es.spikybite.ProxyCode.Skywars;


public abstract class Menu implements Listener {
	Inventory _inv;

	public Menu(String name, int rows){
		_inv = Bukkit.createInventory(null, 9 * rows, ChatColor.translateAlternateColorCodes('&', name));
		Skywars.pl.getServer().getPluginManager().registerEvents(this, Skywars.pl);
	}
	
	public  void a(ItemStack stack){
		_inv.addItem(stack);
	}
	public void s(int i , ItemStack stack){
		_inv.setItem(i, stack);
	}
	public Inventory i(){
		return _inv;
	}
	public String n()
	{
		return _inv.getName();
	}
	public void o(Player p){
		p.openInventory(_inv);
	}

	  @EventHandler
	    public void onInventoryClick(InventoryClickEvent event) {
	        if (event.getInventory().equals(this.i())) {
	            if (event.getCurrentItem() != null && this.i().contains(event.getCurrentItem()) && event.getWhoClicked() instanceof Player) {
	                this.onClick((Player) event.getWhoClicked(), event.getCurrentItem());
	                event.setCancelled(true);
	            }
	        }
	    }
	  @EventHandler
	    public void onInventoryClose(InventoryCloseEvent event) {
	        if (event.getInventory().equals(this.i()) && event.getPlayer() instanceof Player) {
	            this.onClose((Player) event.getPlayer());
	        }
	    }
	  public void onClose(Player player) {}
	  public abstract void onClick(Player p, ItemStack stack);
}
