package es.spikybite.ProxyCode.arena;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Chunk;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.Chest;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;

import es.spikybite.ProxyCode.Skywars;
import es.spikybite.ProxyCode.controllers.KitController;
import es.spikybite.ProxyCode.events.PlayerChestFilled;
import es.spikybite.ProxyCode.inventories.VoteChest;
import es.spikybite.ProxyCode.inventories.VoteTime;
import es.spikybite.ProxyCode.player.SPlayer;
import es.spikybite.ProxyCode.utils.Fireworks;
import es.spikybite.ProxyCode.utils.ItemBuilder;
import es.spikybite.ProxyCode.utils.Trails;
import es.spikybite.ProxyCode.utils.Vault;

public class Arena {

private Location lobby;
private String name,layout;
private int min,max,salir,salirtime,shot,check,taskTiempo;
private boolean fall;
private World world;
private ArrayList<Player> ingame = new ArrayList<Player>();
private ArrayList<Player> out = new ArrayList<Player>();
private ArrayList<Player> all = new ArrayList<Player>();
private HashMap<Player, ItemStack[]> a = new HashMap<Player, ItemStack[]>();
private HashMap<Player, ItemStack[]> c = new HashMap<Player, ItemStack[]>();
private HashMap<String, Integer> cofre = new HashMap<String, Integer>();
private HashMap<String, Integer> tiempo = new HashMap<String, Integer>();
private HashMap<Location, Boolean> Spawns = new HashMap<Location, Boolean>();
private HashMap<Player, Location> PSpawns = new HashMap<Player, Location>();
private ArrayList<String> acofre = new ArrayList<String>();
private ArrayList<String> atiempo = new ArrayList<String>();
private ArrayList<Location> chest = new ArrayList<Location>();

private String Cofre;
private String Tiempo;
private ArenaManager am = new ArenaManager();
private  boolean comienza = false;
private boolean esperando = true;
private boolean juego = false;
private boolean end = false;
private boolean error = false;
public Arena(String name, String layouName, int min, int max){
	this.name = name;
	this.layout = layouName;
	this.lobby = lobby;
	
	this.min = min;
	this.max = max;
	this.world = Bukkit.getWorld(this.name);
   
    this.fall = true;
	salirtime = Skywars.lang.getint("game.time_countdown");
	Cofre = "Normal";
	Tiempo = "Day";	
	comienza = false;
	juego = false;
	end = false;
	 error = false;
	 esperando  = true;
	back();
	principal();
	update();
    setGlass();
    loadSpawns();
}
public void addChest(Location loc){
	chest.add(loc);
}
public void removeChest(Location loc){
	chest.remove(loc);
}
public boolean contains(Location loc){
	if(chest.contains(loc)){
		return true;
	}
	return false;
}
public void esperando(boolean set){
	this.esperando = set;
}
public void juego(boolean set){
	this.juego = set;
}public void comienza(boolean set){
	this.comienza = set;
}public void end(boolean set){
	this.end = set;
}public void error(boolean set){
	this.error = set;
}
public String getChest(){
	int m = 0;
	String win = "Normal";
	for(int i : cofre.values()){
	if(i > m){
		m=i;
	}
	}
	for(String a : cofre.keySet()){
		if(cofre.get(a)==m){
			win=a;
		}
	}
	return win;
	}
public String getTime(){
	int m = 0;
	String win = "Day";
	for(int i : tiempo.values()){
	if(i > m){
		m=i;
	}
	}

	for(String a : tiempo.keySet()){
		if(tiempo.get(a)==m){
			win=a;
		}
	}
	return win;
	}

public ArrayList<Player> p(){
return ingame;
}

public HashMap<String, Integer> dataChest(){
	return this.cofre;
}
public HashMap<String, Integer> dataTime(){
	return this.tiempo;
}
public int chestData(String de){
	return this.cofre.get(de);
}
public int timeData(String de){
	return this.tiempo.get(de);
}

public void back(){
	acofre.clear();
	atiempo.clear();
	cofre.clear();
	tiempo.clear();
	acofre.add("Overpowered");
	acofre.add("Basic");
	acofre.add("Normal");
	atiempo.add("Day");	
	atiempo.add("Sunset");
	atiempo.add("Night");
	for(String b : acofre){
		cofre.put(b, 0);
	
	}
	for(String b : atiempo){
		tiempo.put(b, 0);
	}
	
}



public void loadSpawns(){
	for(Location loc : sp){
		Spawns.put(loc, false);
	}
}
public Location getAnySpawn(){
	for(Entry<Location, Boolean> key : Spawns.entrySet()){
		if(!key.getValue().booleanValue()){
			return key.getKey();
		}
	}
	return null;
}
public boolean full(){
	if(min>=max){
		return true;
	}
	return false;
}



public void load(){
ArenaManager am = new ArenaManager();
File file = new File("plugins/Skywars/arenas/"+this.name+".yml");
YamlConfiguration config = new YamlConfiguration().loadConfiguration(file);
config.set("aName", this.name);
config.set("aMin", this.min);
config.set("aMax", this.max);
config.set("aSpawn", null);
am.save(config, file);
}
public void sender(String sender){
for(Player p : ingame){
	p.sendMessage(sender);
}
for(Player out : out){
    out.sendMessage(sender);
}
}

public void join(Player p){
all.add(p);
if(game() >= max){
p.sendMessage(Skywars.p + Skywars.lang.get("arena_full"));
return;
}
if(isErrorInternal()){
p.sendMessage(Skywars.p + "§cThis arena have errors please check console logs");
return;
}
if(isEnd()){
p.sendMessage(Skywars.p + Skywars.lang.get("arena_restarting"));
return;
}
if(isGame()){
p.sendMessage(Skywars.p + Skywars.lang.get("arena_ingame"));
return;
}
Location loc = getAnySpawn();
if(loc == null){
p.sendMessage(Skywars.p + Skywars.lang.get("spawnspoints_not_found"));
return ;
}
ingame.add(p);

sender(Skywars.p + Skywars.lang.get("player_join_to_arena").replaceAll("%player", p.getName()).replaceAll("%online",""+ ingame.size()).replaceAll("%min",""+ min).replaceAll("%max",""+ max));

p.setGameMode(GameMode.ADVENTURE);
p.setHealth(20);
p.setFoodLevel(20);
p.setFireTicks(0);
p.setLevel(0);
p.setExp(0);
a.put(p, p.getInventory().getArmorContents());
c.put(p, p.getInventory().getContents());
p.getInventory().setArmorContents(null);
p.getInventory().clear();
for(PotionEffect e : p.getActivePotionEffects()){
p.removePotionEffect(e.getType());
}


p.teleport(loc);
Spawns.put(loc, true);
PSpawns.put(p, loc);

dar(p, ItemBuilder.crearItem(Skywars.lang.getint("player_inventory.id.item_kit_id"), 1, 0, Skywars.lang.get("player_inventory.name.item_kit_name")));
dar(p, ItemBuilder.crearItem(Skywars.lang.getint("player_inventory.id.item_vote_id"), 1, 0, Skywars.lang.get("player_inventory.name.item_vote_name")));
dar(p, ItemBuilder.crearItem(Skywars.lang.getint("player_inventory.id.item_player_settings_id"), 1, 0, Skywars.lang.get("player_inventory.name.item_player_settings_name")));
dar(p, ItemBuilder.crearItem(Skywars.lang.getint("player_inventory.id.item_leave_id"), 1, 0, Skywars.lang.get("player_inventory.name.item_leave_name")), 8);

p.updateInventory();

SPlayer player = SPlayer.getDPlayer(p);
player.setCustomGlass();
player.setCustomTrail();
if(ingame.size()>=this.min){
	if(isStarting()){
		return;
	}
	sender(Skywars.p + Skywars.lang.get("game_starting"));
	second();
	comienza = true;
	end = false;
	juego = false;
	esperando = false;
	error = false;
}



}




public ArrayList<Player> getout(){
	return out;
}

public boolean isLobby()
{
	return esperando;
}
public boolean isGame()
{
	return juego;
}
public boolean isStarting()
{
	return comienza;
}
public boolean isEnd()
{
	return end;
}public boolean isErrorInternal()
{
	return error;
}


public void spect(final Player p){
	System.out.println("REMOVED PLAYER " + p.getName() +" FROM "+name);
	new BukkitRunnable() {
		
		@Override
		public void run() {
			
	p.spigot().respawn();
	ingame.remove(p); 
	out.add(p);
	VoteChest.maxVote.remove(p);
	VoteTime.maxVote.remove(p);
	Trails.data.remove(p);
	p.getInventory().clear();
	p.getInventory().setArmorContents(null);
	p.sendMessage(Skywars.p + "§6Use §e/sw leave §6for leave the game!");
	

p.setGameMode(GameMode.SPECTATOR);

ArenaManager am = new ArenaManager();
File file = new File("plugins/Skywars/arenas/"+name+".yml");
YamlConfiguration config = new YamlConfiguration().loadConfiguration(file);

if(!config.contains("aSpectatorSpawn")){
Player random = ingame.get(new Random().nextInt(ingame.size()));
if(random!=null){
p.teleport(random.getLocation());
}
}else {

Location  loc = am.getLoc(config.getString("aSpectatorSpawn"));
p.teleport(loc);
}

		}
		}.runTaskLater(Skywars.pl, 1L);
}



public void setTime(){
	taskTiempo = Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(Skywars.pl, new Runnable() {
		
		@Override
		public void run() {
			if(getTime().equalsIgnoreCase("Day")){
				world.setTime(0L);
			}else if(getTime().equalsIgnoreCase("Sunset")){
				world.setTime(12000L);
			}else if(getTime().equalsIgnoreCase("Night")){
				world.setTime(18000L);
			}
		}
	}, 0L, 20L);
	
}

public void leave(Player p){	
	all.remove(p);
	if(!isGame()){
am.setGlass(p.getLocation());
	}
VoteChest.maxVote.remove(p);
VoteTime.maxVote.remove(p);
Trails.data.remove(p);
p.getInventory().clear();
p.getInventory().setArmorContents(null);
p.getInventory().setArmorContents(a.get(p));
p.getInventory().setContents(c.get(p));
p.setGameMode(GameMode.SURVIVAL);
p.setHealth(20);
p.setFoodLevel(20);
p.setFireTicks(0);
p.setLevel(0);
p.setExp(0);
ingame.remove(p);
out.remove(p);
p.teleport(am.getLoc(Skywars.pl.getConfig().getString("spawn")));


SPlayer player = SPlayer.getDPlayer(p);
SPlayer.setHolo(player);
Spawns.put(PSpawns.get(p), false);

sender(Skywars.p + Skywars.lang.get("player_leave_to_arena").replaceAll("%player", p.getName()).replaceAll("%online",""+ ingame.size()).replaceAll("%min",""+ min).replaceAll("%max",""+ max));
if(ingame.size()<this.min){
esperando = true;
comienza = false;
juego = false;
end = false;
error = false;
sender(Skywars.p + Skywars.lang.get("game_stopped"));
stop(salir);
salirtime = Skywars.lang.getint("game.time_countdown");
}
Skywars.giveLobbyItem(p);
p.updateInventory();
if(p.getScoreboard().getObjective(DisplaySlot.SIDEBAR) == null){
	return;
}
p.getScoreboard().getObjective(DisplaySlot.SIDEBAR).unregister();
}
public void leaveReset(Player p){
	all.remove(p);
VoteChest.maxVote.remove(p);
VoteTime.maxVote.remove(p);
Trails.data.remove(p);
p.getInventory().clear();
p.getInventory().setArmorContents(null);
p.getInventory().setArmorContents(a.get(p));
p.getInventory().setContents(c.get(p));
p.setGameMode(GameMode.SURVIVAL);
p.setHealth(20);
p.setFoodLevel(20);
p.setFireTicks(0);
p.setLevel(0);
p.setExp(0);
ingame.remove(p);
out.remove(p);	
Skywars.giveLobbyItem(p);
if(p.getScoreboard().getObjective(DisplaySlot.SIDEBAR) == null){
	return;
}
p.getScoreboard().getObjective(DisplaySlot.SIDEBAR).unregister();
}


public String getCustomName(){
	return this.layout;
}
public void min(int i){
this.min = i;
}
public void max(int i){
this.max = i;
}
public void lobby(Location i){
this.lobby = i; 
}
public String arena(){
return this.name;
}
public World b(){
return this.world;
}
public int gmin(){
return this.min;
}
public int gmax(){
return this.max;
}
public Location globby(){
return this.lobby;	
}

public void gteleport(Player p){
for(Location loc : sp){
	p.teleport(loc);
}
}
public int game(){
return ingame.size();
}
public static void dar(Player p, ItemStack stack){
p.getInventory().addItem(stack);
}
public static void dar(Player p, ItemStack stack, int i){
p.getInventory().setItem(i, stack);
}

private List<Location> sp = new ArrayList<Location>();
public void principal(){

	File file = new File("plugins/Skywars/arenas/"+this.name+".yml");
	YamlConfiguration config = new YamlConfiguration().loadConfiguration(file);
	for(String g : config.getStringList("spawns")){
		sp.add(am.getLoc(g));
	}
}
public void setGlass(){
	for(Location loc : sp){
		am.setGlass(loc);
	}
}

public boolean fallMode(){
return fall;
}

public void second(){
	
	salir = Bukkit.getScheduler().scheduleSyncRepeatingTask(Skywars.pl, new Runnable() {
		
		@Override
		public void run() {
		for(Player p : p()){
			p.setLevel(salirtime);
		}
			if(salirtime % 5 == 0 || salirtime == 4 || salirtime == 3 || salirtime == 2 || salirtime == 1){

				sender(Skywars.p + Skywars.lang.get("countdown_release").replaceAll("%time", ""+salirtime));
			}else if(salirtime <= 0){
				stop(salir);
				for(Player p : p()){
					p.closeInventory();
					play(p);
					if(KitController.getData().get(p)!=null){
						KitController.getData().get(p).giveKit(p);
					}


				}
				setTime();
				listToString("good_luck");
				juego = true;
				comienza = false;
				esperando = false;
				end = false;
				error = false;
				arenaEndWinPlayer();

				salirtime = Skywars.lang.getint("game.time_countdown");
				new BukkitRunnable() {
					@Override
					public void run() {
			        fall = false;
					}
				}.runTaskLater(Skywars.pl, 100L);
			}
			
			salirtime-=1;
		
			
		}
	}, 0L, 20L);
}
public void stop(int i){
Bukkit.getScheduler().cancelTask(i);

}

public void play(Player p){
	p.setGameMode(GameMode.SURVIVAL);
	Block block = p.getLocation().subtract(0, 1, 0).getBlock();
	if(block.getType() == Material.AIR){
	 block = p.getLocation().subtract(0, 2, 0).getBlock();
	}
	block.setType(Material.AIR);
	p.getInventory().clear();
	p.setHealth(20);
	p.setFireTicks(0);
	p.setFoodLevel(20);
	p.setLevel(0);
	p.setExp(0);
	
}

public void update(){
Bukkit.getScheduler().scheduleSyncRepeatingTask(Skywars.pl, new Runnable() {
	@Override
	public void run() {
		for(Player all : p()){
			getScoreboard(all);
		}
	}
}, 0L, 35L);
}

private Objective o;
public void getScoreboard(Player p){
	this.o = Bukkit.getScoreboardManager().getNewScoreboard().registerNewObjective(this.name, "dummy");
	o.setDisplayName(Skywars.lang.get("scoreboard.name").replaceAll("%map", this.name).replaceAll("%name", p.getName()));
	o.setDisplaySlot(DisplaySlot.SIDEBAR);
	Score sc = null;
	int i = 15;
	for(String c : Skywars.lang.getConfig().getStringList("scoreboard.lines")){
		sc = o.getScore(ChatColor.translateAlternateColorCodes('&',c.replaceAll("%players", ""+ingame.size()).replaceAll("%map", name).replaceAll("%min", ""+min).replaceAll("%max", ""+max).replaceAll("%name", p.getName())));
		sc.setScore(i);
		i--;
	}
	p.setScoreboard(this.o.getScoreboard());
}

public void importNewWorld(){
	end = true;
	comienza = false;
	juego = false;
	esperando = false;
	error = false;
    for(Player p : Bukkit.getWorld(name).getPlayers()){
    	p.kickPlayer(name +" mode restart.");
    }
    chest.clear();
   final File worldto = new File(world.getName());
   final  File folderworld = new File("plugins/Skywars/mapas/"+world.getName());

	Bukkit.getServer().unloadWorld(name, false);

	new BukkitRunnable() {
		
		@Override
		public void run() {

			am.delete(worldto);
			try {
				am.copyDir(folderworld, worldto);
			} catch (IOException e) {
				
				e.printStackTrace();
			}
		}
	}.runTaskLater(Skywars.pl, 20L);
	new BukkitRunnable() {
		
		@Override
		public void run() {
				loadWorld();
		
		}
	}.runTaskLater(Skywars.pl, 40L);
	new BukkitRunnable() {
		@Override
		public void run() {
			File file = new File("plugins/Skywars/arenas/"+name+".yml");
			YamlConfiguration config = new YamlConfiguration().loadConfiguration(file);
			for(String g : config.getStringList("spawns")){
				sp.add(am.getLoc(g));
			}
		    setGlass();
		    loadSpawns();
esperando = true;
			juego = false;
			comienza = false;
			end = false;
			error = false;

		}
	}.runTaskLater(Skywars.pl, 80L);
}





public World loadWorld()
{
  WorldCreator c = new WorldCreator(name);
  c.generateStructures(false);
  World localWorld = c.createWorld();
  localWorld.setAutoSave(false);
  localWorld.setKeepSpawnInMemory(false);
  localWorld.setGameRuleValue("doMobSpawning", "false");
  localWorld.setGameRuleValue("doDaylightCycle", "false");
  localWorld.setGameRuleValue("mobGriefing", "false");
  localWorld.setTime(0L);
  return localWorld;
}
public void finishArena(){
	
	new BukkitRunnable() {
		
		@Override
		public void run() {
			
			update();
			back();
			importNewWorld();
	
		}
	}.runTaskLater(Skywars.pl, 100L);
}
public void checkPlayer(){
	for(Player p : Bukkit.getOnlinePlayers()){
if(p.getWorld().getName().equalsIgnoreCase(world.getName())){
SPlayer player = SPlayer.getDPlayer(p);
player.setGames(1);
			p.getInventory().clear();
			p.getInventory().setArmorContents(null);
			p.getInventory().setArmorContents(a.get(p));
			p.getInventory().setContents(c.get(p));
			p.setGameMode(GameMode.SURVIVAL);
			p.setHealth(20);
			p.setFoodLevel(20);
			p.setFireTicks(0);
			p.setLevel(0);
			p.setExp(0);
			Trails.data.remove(p);
			VoteChest.maxVote.remove(p);

					p.teleport(am.getLoc(Skywars.pl.getConfig().getString("spawn")));	

SPlayer.setHolo(player);
					Skywars.giveLobbyItem(p);
					p.setGameMode(GameMode.SURVIVAL);
					if(p.getScoreboard().getObjective(DisplaySlot.SIDEBAR) == null){
						return;
					}
					p.getScoreboard().getObjective(DisplaySlot.SIDEBAR).unregister();
				
			
            leaveReset(p);
}
		
	}
}

public void FireWorks(){
	shot =   Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(Skywars.pl, new Runnable()
    {
	      public void run()
	      {
	    	 Fireworks.shot(getPlayerWin()); 
	    	  
	      }
    },0L, 20L);
}
@SuppressWarnings("deprecation")
public void arenaEndWinPlayer(){
	check = Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(Skywars.pl, new BukkitRunnable() {
		
		@Override
		public void run() {
			if(all.size() <= 1 || ingame.size() <= 1  || ingame.isEmpty()){
				   fall = true;
			       stop(taskTiempo);
			       SPlayer player = SPlayer.getDPlayer(getPlayerWin());
player.setWins(1);
  stop(check);
				  end = true;
				  juego = false;
				  esperando = false;
				  comienza = false;
				  error = false;
			       FireWorks();
			       listToString("player_win_arena");
				   Vault.setMoney(getPlayerWin(), Vault.getPointRewardWin());
				   getPlayerWin().sendMessage(Skywars.p + Skywars.lang.get("rewards").replaceAll("%points", ""+Vault.getPointRewardWin()));
					new BukkitRunnable() {
						@Override
						public void run() {
							stop(shot);
							checkPlayer();

							
							
							resetData();
							
						}
					}.runTaskLater(Skywars.pl, 20L*8);
					new BukkitRunnable() {
						@Override
						public void run() {
							finishArena();
							
						}
					}.runTaskLater(Skywars.pl, 20L*9);
				
				}

		}
	}, 0L, 20L);
	
}

public void listToString(String path){
	for(String say : Skywars.lang.getConfig().getStringList(path)){
		
		String sayColour = null;
		if(getPlayerWin()==null){

			 sayColour =ChatColor.translateAlternateColorCodes('&', say.replaceAll("%chest", getChest()).replaceAll("%time", getTime()).replaceAll("%refill", "N/A"));
		}else {
 sayColour =ChatColor.translateAlternateColorCodes('&', say.replaceAll("%player", getPlayerWin().getName()).replaceAll("%chest", getChest()).replaceAll("%time", getTime()).replaceAll("%refill", "N/A"));
		}
		sender(Skywars.p + sayColour);
	}
}
public Player getPlayerWin(){
	if(ingame.size() == 1){
		for(Player player : ingame){
			return player;
		}
	}
	return null;
}


public void resetData(){
sp.clear();
Spawns.clear();
PSpawns.clear();
Cofre = "Normal";
Tiempo = "Day";
ingame.clear();
out.clear();
a.clear();
c.clear();

}


	
}
