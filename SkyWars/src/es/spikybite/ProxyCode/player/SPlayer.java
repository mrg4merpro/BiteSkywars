package es.spikybite.ProxyCode.player;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;

import es.spikybite.ProxyCode.Skywars;
import es.spikybite.ProxyCode.arena.ArenaManager;
import es.spikybite.ProxyCode.hologram.Holo;
import es.spikybite.ProxyCode.utils.BoardAPI;
import es.spikybite.ProxyCode.utils.Trails;
import es.spikybite.ProxyCode.utils.Vault;

public class SPlayer {
public static ArrayList<Player> splayer = new ArrayList<Player>();
public static HashMap<Player, SPlayer> dplayer = new HashMap<Player, SPlayer>();
public static HashMap<SPlayer, Holo> hologram = new HashMap<SPlayer,  Holo>();
private Player p;
private int shots = 0, wins= 0,kills= 0,deaths= 0,gamesplayed= 0,blocksplaced= 0,blockbreak = 0,walked = 0;
private String glass;
private String trail;
public  SPlayer(Player p)
{
	this.p = p;
	

}
public static SPlayer getDPlayer(Player p){
	if(dplayer.containsKey(p)){
		return dplayer.get(p);
	}
	return null;
}
ArenaManager am = new ArenaManager();
public void getMyScoreBoard()
{
	if(Skywars.board.getConfig().getBoolean("lobby-board-enabled")){
		if(am.getArena(getPlayer()) == null){
			HashMap<String, Integer> cache = new HashMap<String, Integer>();
		Player p = getPlayer();
		int i = 15;
		for(String lines : getLines()){
  
			cache.put(setVars(lines), i);
			i--;
		}
		BoardAPI.ScoreboardUtil.rankedSidebarDisplay(p, setVars(getNamed()), cache);
		}
	}	
}

public String getNamed(){
	return Skywars.board.getConfig().getString("lobby-displayname");
}
public List<String> getLines(){
	
	return Skywars.board.getConfig().getStringList("lobby-lines");
}
public String setVars(String a){
	
	String b = null;
	b = a.replaceAll("%kills%", ""+getKills())
			.replaceAll("%deaths%", ""+getDeaths())
			.replaceAll("%wins%", ""+getWins())
			.replaceAll("%games%", ""+getGames())
			.replaceAll("%shots%", ""+getShots())
			.replaceAll("%walked%", ""+getWalked())
			.replaceAll("%breakblocks%", ""+getBlockBreaks())
			.replaceAll("%placedblocks%", ""+getBlockPlaceds())
			.replaceAll("%kdr%", ""+getKdr())
			.replaceAll("%prefix%", Skywars.chat.getPlayerPrefix(p))
			.replaceAll("%trail%", ""+getTrail())
			.replaceAll("%glass%", ""+getGlass())
			.replaceAll("%coins%", ""+Vault.getStringMoney(p))
			.replaceAll("%name%", getPlayer().getName());


	
	
	return ChatColor.translateAlternateColorCodes('&',b);
	
}
public void setWins(int add){
	this.wins += add;
}
public void setKills(int add){
	this.kills += add;
}
public void addShots(int add) {
	this.shots += add;
}
public void setDeaths(int add){
	this.deaths += add;
}
public void setGames(int add){
	this.gamesplayed += add;
}
public void setPlacedBlocks(int add){
	this.blocksplaced += add;
}
public void setBreakBlocks(int add){
	this.blockbreak += add;
}
public void setWalked(int add){
	this.walked +=add;
}
public void setGlass(String add){
	this.glass = add;
}
public void setTrail(String add){
	this.trail = add;
}
public String getGlass(){
	return this.glass;
}
public String getTrail(){
	return this.trail;
}
public int getWins(){
	return this.wins;
}
public int getKills(){
	return this.kills;
}
public int getDeaths(){
	return this.deaths;
}
public int getGames(){
	return this.gamesplayed;
}
public int getBlockPlaceds(){
	return this.blocksplaced;
}
public int getBlockBreaks(){
	return this.blockbreak;
}
public int getShots(){
return this.shots;
}
public int getWalked(){
	return this.walked;
}
public Player getPlayer()
{
	return this.p;
}
public String getKdr(){
	if(kills == 0 || deaths == 0){
		return "0.0";
	}
	DecimalFormat format = new DecimalFormat("#.##");
	double kdr = kills/deaths;
	return format.format(kdr);
}

public static void setHolo(SPlayer player){
	if(Skywars.getHoloLocs().isEmpty()){
		
		return;
	}
	for(Location allset : Skywars.getHoloLocs()){
List<String> data = new ArrayList<String>();
	for(String text : Skywars.holo.getConfig().getStringList("lines")){
data.add(getVariables(text,player ));
	}
	Holo api = new Holo(allset, data);
	api.display(player.getPlayer());
	hologram.put(player, api);
	
	
	}
}

public static void removeHolo(SPlayer player){
	if(hologram.containsKey(player)){
		hologram.get(player).destroy(player.getPlayer());
	}
}

public static String getVariables(String text, SPlayer p){
	return ChatColor.translateAlternateColorCodes('&', text.replaceAll("%arrowshots%", ""+p.getShots()).replaceAll("%player%", ""+p.getPlayer().getName()).replaceAll("%walked%", ""+p.getWalked()).replaceAll("%placed%", ""+p.getBlockPlaceds()).replaceAll("%break%", ""+p.getBlockBreaks()).replaceAll("%games%", ""+p.getGames()).replaceAll("%deaths%", ""+p.getDeaths()).replaceAll("%wins%", ""+p.getWins()).replaceAll("%kills%", ""+p.getKills()));
}
public void setCustomGlass(){
	if(getGlass().equalsIgnoreCase("normal")||getGlass() == null){
		return;
	}
	byte dataByte = Byte.valueOf((byte) Skywars.glass.getConfig().getInt("glass."+glass+".colorID"));
  new  ArenaManager().setGlass(p.getLocation(), dataByte);
}
public Trails setCustomTrail(){
	if(getTrail().equalsIgnoreCase("normal")||getTrail() == null){
		return null;
	}
	if(getTrail().equalsIgnoreCase("slime")){		
		Trails.data.remove(p);
	Trails.data.put(p, Trails.SLIME);
		return Trails.SLIME;
	}else if(getTrail().equalsIgnoreCase("water")){		
		Trails.data.remove(p);
	Trails.data.put(p, Trails.WATER);
		return Trails.WATER;
	}else if(getTrail().equalsIgnoreCase("lava")){	
		Trails.data.remove(p);
	Trails.data.put(p, Trails.LAVA);
		return Trails.LAVA;
	}else if(getTrail().equalsIgnoreCase("magic")){	
		Trails.data.remove(p);
	Trails.data.put(p, Trails.MAGIC);
		return Trails.MAGIC;
	}else if(getTrail().equalsIgnoreCase("villager")){	
		Trails.data.remove(p);
	Trails.data.put(p, Trails.VILLAGER);
		return Trails.VILLAGER;
	}else if(getTrail().equalsIgnoreCase("note")){
		Trails.data.remove(p);
		Trails.data.put(p, Trails.NOTE);
		return Trails.NOTE;
	}
	
	return null;
}


}
