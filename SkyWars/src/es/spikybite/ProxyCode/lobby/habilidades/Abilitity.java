package es.spikybite.ProxyCode.lobby.habilidades;

import java.util.HashMap;
import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import es.spikybite.ProxyCode.lobby.Habilidad;
import es.spikybite.ProxyCode.lobby.Habilidad.HabilidadType;

public class Abilitity {
private String named;
private ItemStack no;
private ItemStack si;
private String needPermission;
private int upgrated;
public static HashMap<String, Abilitity> todas = new HashMap();

public Abilitity(String name, String needPermission, int cost, ItemStack locked, ItemStack unlocked){
	this.named = name;
	this.needPermission = needPermission;
	this.upgrated = cost;
	this.si = unlocked;
	this.no = locked;
}

public ItemStack getNoItem(){
	return no;
}
public ItemStack getSiItem(){
	return si;
}
public void senderToInventory(Player p , Inventory inv, int rows, HabilidadType tipo){
	if(rows == 0){
		for (int i = 1 ; i < 8 ; i++ ){
			inv.setItem(0, Habilidad.getAbilityMaterial(tipo));
			if(Habilidad.getLevelAility(p, tipo) >= i){
			inv.setItem(i, getSiItem());
			}else {
			inv.setItem(i, getNoItem());
			}
		}
	}
}

public String getNamed(){
	return this.named;
}

public boolean hasPermission(){
	return needPermission == null;
}

public void setNewCost(int cost){
	this.upgrated = cost;
}
}
