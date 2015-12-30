package es.spikybite.ProxyCode.controllers;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import es.spikybite.ProxyCode.Skywars;
import es.spikybite.ProxyCode.utils.ItemBuilder;

public class KitController {
private static HashMap<Player, KitController> _data = new HashMap<Player, KitController>();
private static HashMap<KitController, String> _kits = new HashMap<KitController, String>();
private double _cost;
private int _slot;
private String _name,_name2;
private ArrayList<String> _desc = new ArrayList<String>();;
private ArrayList<String> _desc2  = new ArrayList<String>();
private ItemStack _icon;
private ItemStack[] _items;
private String _permission;
private Skywars wars;
public KitController(ItemStack icon , String name2, String name, String permission, double cost, int slot, ArrayList<String> desc, ArrayList<String>desc2, ItemStack[] items){
	this._icon = icon;
	this._name = name;
	this._name2 = name2;
	this._cost = cost;
	this._desc = desc;
	this._desc2 = desc2;
	this._items = items;
	this._permission = permission;
	this._slot = slot;
	
	_kits.put(this, name);
	
}
public KitController(ItemStack icon, String name2 ,String name, int slot , ArrayList<String> desc, ArrayList<String> desc2, ItemStack[] items){
	this._icon = icon;
	this._name = name;
	this._name2 = name2;
	this._desc = desc;
	this._desc2 = desc2;
	this._items = items;
	this._slot = slot;
	this._cost = 0;
	this._permission = null;
	_kits.put(this, name);
}
public KitController(Skywars wars){
this.wars = wars;
}
public ItemStack getIcon(){
	return this._icon;
}
public static HashMap<KitController, String> getKits(){
	return _kits;
}
public static HashMap<Player, KitController> getData(){
	return _data;
}
public void setKit(Player p){
 if(_data.containsKey(p)){
	 _data.remove(p);
	 _data.put(p, this);
 }else {
	 _data.put(p, this);
 }
}
public String getKit(Player p){
	if(_data.containsKey(p)){
		return _data.get(p).getName();
	}
	return null;
}
public void giveKit(Player p){
if(getKit(p) == null){
return;
}
for(String resolver : Skywars.kits.getConfig().getStringList("kits."+this._name2+".items")){
	p.getInventory().addItem(ItemBuilder.toKit(resolver));
}
}
public String getPermission(){
	return this._permission;
}
public double getCost(){
return this._cost;
}
public String getName(){
	return this._name;
}
public ArrayList<String> getDesc(){
	return this._desc;
}
public ArrayList<String> getDesc2(){
	return this._desc2;
}
public ItemStack[] getItems(){
	return this._items;
}
public int getSlot(){
	return this._slot;
}
	
}
