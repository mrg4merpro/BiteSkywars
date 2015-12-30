package es.spikybite.ProxyCode.controllers.cartel;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;

import es.spikybite.ProxyCode.Skywars;
import es.spikybite.ProxyCode.arena.Arena;
import es.spikybite.ProxyCode.arena.ArenaManager;

public class Cartel {

private  static HashMap<String, Cartel> arenaSign = new HashMap(); 
private static HashMap<Location, Cartel> locationSign = new HashMap();
private static ArrayList<Block> getBackBlock = new ArrayList();
private String arena;
private Location loc;
private Location bloc;
private Sign sign;
private Block atras;
private Arena a;
private ArenaManager am = new ArenaManager();
public Cartel(String arena){
this.arena = arena;
this.a = am.getArena(arena);
this.loc = am.getNewLoc(Skywars.signs.getConfig().getString("signs."+arena));
this.bloc = getBlock(this.loc.getBlock()).getLocation();
this.atras = bloc.getBlock();
this.sign = (Sign) loc.getBlock().getState();
arenaSign.put(arena, this);
locationSign.put(loc, this);
getBackBlock.add(atras);
Skywars.log("Added "+this);
}


public Block getAttachedBlock(){
	return this.atras;
}
public Sign getSign(){
	return this.sign;
}

public static Cartel getSign(Location location){
	if(locationSign.containsKey(location)){
		return locationSign.get(location);
	}
	return null;
}

public Arena getArena(){
	return this.a;
}

public static int get(String a, int c){
return Integer.parseInt(a.split(":")[c]);
}


public static void updateSigns(){
for(Cartel a : getSigns()){
	if(a.getAttachedBlock() == null){

		Skywars.log("&cBlock Attached not found ("+a.getName()+")");
		return;
	}
	if(a.getSign() == null){
		Skywars.log("&cSign not found ("+a.getName()+")");
		return;
	}
	int uno = 0;
	byte dos = 0;
String state = null;
	if(a.a.isLobby())
	{
		if(a.a.full()){

			uno = 	get(Skywars.lang.get("game.block_state_full"), 0);
			dos = 	(byte) get(Skywars.lang.get("game.block_state_full"), 1);
			state = Skywars.lang.get("game.state_full");
		}else {
	uno = 	get(Skywars.lang.get("game.block_state_waiting"), 0);
	dos = 	(byte) get(Skywars.lang.get("game.block_state_waiting"), 1);
	state = Skywars.lang.get("game.state_waiting");
		}
	}
	else if(a.a.isStarting())
	{
	uno = 	get(Skywars.lang.get("game.block_state_starting"), 0);
	dos = 	(byte) get(Skywars.lang.get("game.block_state_starting"), 1);
	state = Skywars.lang.get("game.state_starting");
	}
	else if(a.a.isGame())
	{
	uno = 	get(Skywars.lang.get("game.block_state_ingame"), 0);
	dos = 	(byte) get(Skywars.lang.get("game.block_state_ingame"), 1);
	state = Skywars.lang.get("game.state_ingame");
	}
	else if(a.a.isEnd())
	{
	uno = 	get(Skywars.lang.get("game.block_state_restarting"), 0);
	dos = 	(byte) get(Skywars.lang.get("game.block_state_restarting"), 1);
	state = Skywars.lang.get("game.state_restarting");
	}
	 	
	else if(a.a.isErrorInternal())
	{
	uno = 	95;
	dos = 	11;
	}

	
	a.atras.setTypeIdAndData(uno, dos, true);
	a.sign.setLine(0, Skywars.lang.get("sign.sign_header"));
	a.sign.setLine(1, state);
	a.sign.setLine(2, Skywars.lang.get("sign.sign_map").replaceAll("%map", a.a.getCustomName()));
	a.sign.setLine(3, Skywars.lang.get("sign.sign_players").replaceAll("%on", ""+a.a.game()).replaceAll("%max", ""+a.a.gmax()));
a.sign.update();
a.sign.update(true);
}
	

	
	

}

public static boolean hasBlock(Location loc){
   if(getBackBlock.contains(loc.getBlock())){
	   return true;
   }
   return false;
}

public String getName(){
	return this.arena;
}

public static String[] get(String i){
	return i.split(":");
}

public static Collection<Cartel> getSigns(){
	return arenaSign.values();
}



private static Block getBlock(Block b)
{
  if ((b.getType() == Material.WALL_SIGN) || (b.getType() == Material.SIGN_POST))
  {
    org.bukkit.material.Sign sign = (org.bukkit.material.Sign)b.getState().getData();
    return b.getRelative(sign.getAttachedFace());
  }
  return null;
}
}
