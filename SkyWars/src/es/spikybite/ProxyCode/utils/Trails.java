package es.spikybite.ProxyCode.utils;

import java.util.HashMap;

import org.bukkit.entity.Player;

public enum Trails {
SLIME("skywars.trail.slime"),WATER("skywars.trail.water"),LAVA("skywars.trail.lava"),MAGIC("skywars.trail.magic")
,VILLAGER("skywars.trail.villager"),NOTE("skywars.trail.note");
public static HashMap<Player, Trails> data = new HashMap<Player, Trails>();
private String permissions;
private Trails(String permission){
this.permissions = permission;
}
public String get(){
	return this.permissions;
}
public void add(Player p, Trails type){
data.remove(p);
data.put(p, type);
}
public Trails trail(Player p){
return data.get(p);
}
}
