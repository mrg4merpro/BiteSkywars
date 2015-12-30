package es.spikybite.ProxyCode;


import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import net.milkbowl.vault.chat.Chat;
import net.milkbowl.vault.economy.Economy;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import com.google.common.collect.Lists;

import es.spikybite.ProxyCode.arena.Arena;
import es.spikybite.ProxyCode.arena.ArenaManager;
import es.spikybite.ProxyCode.commands.SWCommand;
import es.spikybite.ProxyCode.controllers.ArrowController;
import es.spikybite.ProxyCode.controllers.ChestController;
import es.spikybite.ProxyCode.controllers.KitController;
import es.spikybite.ProxyCode.controllers.SignController;
import es.spikybite.ProxyCode.controllers.cartel.Cartel;
import es.spikybite.ProxyCode.database.DataStorage;
import es.spikybite.ProxyCode.database.Database;
import es.spikybite.ProxyCode.events.PlayerChestFilled;
import es.spikybite.ProxyCode.events.PlayerListener;
import es.spikybite.ProxyCode.lobby.habilidades.TripleArrow;
import es.spikybite.ProxyCode.player.SPlayer;
import es.spikybite.ProxyCode.utils.ItemBuilder;
import es.spikybite.ProxyCode.utils.Server;
import es.spikybite.ProxyCode.utils.SettingsDefaults;
import es.spikybite.ProxyCode.utils.SettingsManager;
import es.spikybite.ProxyCode.utils.Vault;

public class Skywars extends JavaPlugin{

	public static Plugin pl;
	public static SettingsManager board,lang,signs,kits,glass, holo;
	public static String p;
	public static KitController kc;
	public static ChestController cc;
	public static Economy econ;
	public static boolean mysql;
	public static Connection conn;
	public static DataStorage ds;
	public static Chat chat;
	private static List<Location> holoLocs = Lists.newArrayList();
	public void onEnable(){
		pl = this;
	    getConfig();
	    saveDefaultConfig();
		lang = new SettingsManager(this, "lang");
		signs = new SettingsManager(this, "signs");
		kits = new SettingsManager(this, "kits");
		glass = new SettingsManager(this, "glasscolors");
		holo = new SettingsManager(this, "holograms");
		board = new SettingsManager(this, "scoreboard");
        mysql = getConfig().getBoolean("mysql.enable");
		new SettingsDefaults().addComplementLang();
		p = lang.get("prefix");
		getCommand("sw").setExecutor(new SWCommand());
		getCommand("stats").setExecutor(new SWCommand());
		getServer().getPluginManager().registerEvents(new SignController(), this);	 
		getServer().getPluginManager().registerEvents(new ArrowController(), this);	
		getServer().getPluginManager().registerEvents(new PlayerListener(), this);	
		getServer().getPluginManager().registerEvents(new TripleArrow(), this);	
		getServer().getPluginManager().registerEvents(new PlayerChestFilled(), this);	
		new ArenaManager().createArenas();
		kc = new KitController(this);
		cc = new ChestController(this);
		if(!holo.getConfig().getStringList("locations").isEmpty()){
			for(String locs : holo.getConfig().getStringList("locations")){
			Location loc =	new ArenaManager().getLoc(locs);
			holoLocs.add(loc);
			log("Added a hologram location succesful! ");
			}
		}
		if(mysql){
			conn = new Database(Skywars.pl.getConfig().getString("mysql.host"), Skywars.pl.getConfig().getString("mysql.port"), Skywars.pl.getConfig().getString("mysql.database"), Skywars.pl.getConfig().getString("mysql.username"),Skywars.pl.getConfig().getString("mysql.password")).openConnection();

		}
	new BukkitRunnable() {
		
		@Override
		public void run() {
			for(Player p : Bukkit.getOnlinePlayers()){
				SPlayer b = SPlayer.getDPlayer(p);
				if(b!=null){
				b.getMyScoreBoard();
				}
				}
		}
	}.runTaskTimer(this, 0L, 40L);
		ds = new DataStorage();
		
	    hooks();
		Vault.setupEconomy();
		Vault.setupChat();
		CreateAllKits();
	    vanish();	
	    newCartel();
	}
	
	
	
	public void onDisable(){
		
		for(SPlayer players : SPlayer.dplayer.values())
		{
			getStorage().unloadData(players);
			Skywars.log("Players unload data successful!");
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	
	public static boolean useData(){
		return mysql;
	}
 
	public void newCartel(){
		if(!signs.getConfig().contains("signs")){
			signs.getConfig().set("signs", Lists.newArrayList());
			signs.save();
		log("[Cartel] Signs game not found!");
	
		}else{
			   if (Skywars.signs.getConfig().getConfigurationSection("signs") == null) {
				      return;
				    }
		for(String neww : signs.getConfig().getConfigurationSection("signs").getKeys(false)){
			new Cartel(neww);
		}
		u();
			}
		
	}
	public static List<Location> getHoloLocs(){
		return holoLocs;
	}
	private void u()
	  {
	    Bukkit.getScheduler().runTaskTimer(this, new Runnable()
	    {
	      public void run() {
	    	  Cartel.updateSigns();
	      }
	    }, 20L, 30L);
	  }
	public void hooks(){

	    Plugin e = Bukkit.getPluginManager().getPlugin("Vault");
	    if(e == null){
	    	log("&cVault not found, disable plugin..");
	    getServer().getPluginManager().disablePlugin(this);
	    return;
	    }
	    if(e != null){

		    log("&aVault Economy and Chat  found, starting plugin.");
	    	
	    	
	    }
	 
	  
	}
	
	public static void bungeeMode(){
		if(pl.getConfig().getBoolean("bungeeMode")==true){
			Server.setServer(Server.BUNGEE);
		}else{
			Server.setServer(Server.MULTIARENA);
		}
	}
	
	public static void giveLobbyItem(Player p){
		if(pl.getConfig().getBoolean("item-lobby.lobbyItem")==true){
		p.getInventory().setItem(pl.getConfig().getInt("lobby.lobbySettingsItemSlotPosition"), ItemBuilder.crearItem(342, 1, 0, lang.get("player_inventory.name.item_lobby")));
		}
		if(pl.getConfig().getBoolean("item-lobby.habilitiesItem")==true){

			p.getInventory().setItem(pl.getConfig().getInt("lobby.abilityItemSlotPosition"), ItemBuilder.crearItem(340, 1, 0, lang.get("player_inventory.name.habilitiesItem")));
		}
	}


	
	
	public static void log(String b){
	Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&a[BiteSkywars] " + b));
	}
	
	
	public static void vanish(){
		Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(Skywars.pl, new Runnable() {
			
			@Override
			public void run() {
				for(Player p : Bukkit.getOnlinePlayers()){
					for(World world : Bukkit.getWorlds()){
						for(Player p1 : world.getPlayers()){
							if(p.getWorld() != p1.getWorld()){
								p.hidePlayer(p1);
								p1.hidePlayer(p);
							}else {
								p.showPlayer(p1);
								p1.showPlayer(p);
							}
						}
					}
				}
				
				
				for(String name : new ArenaManager().arenas){
					Arena a = new ArenaManager().getArena(name);
				    for(Player espectadores : a.getout()){
				    	espectadores.setGameMode(GameMode.SPECTATOR);
				    }
				}
				    
				
			}
		}, 0L, 1L);
		
	}

	
	
	public static void CreateAllKits(){
		ConfigurationSection config = kits.getConfig().getConfigurationSection("kits");
		for(String key : kits.getConfig().getConfigurationSection("kits").getKeys(false)){
			log("Kit " + key+ " loading...");
			String name = ChatColor.translateAlternateColorCodes('&', config.getString(key+".name"));
			int slot = config.getInt(key+".slot");
			ItemStack icon = new ItemStack(config.getInt(key+".icon"));
			int cost = 0;
			ArrayList<String>desc =  new ArrayList<String>();			
			ArrayList<String> desc2 =  new ArrayList<String>();
			ItemStack[] items = null;
			
			
			if(config.contains(key+".price")){
			 cost = config.getInt(key+".price");
				
			}
			
			if(config.getStringList(key + ".items").isEmpty()){
				log("Kit " + key + " not have items!");
				return;
			}else {
				for(String resolver : config.getStringList(key+".items")){
					ItemStack added = ItemBuilder.toKit(resolver);
					items = new ItemStack[] {added};
				}
			}
			
			if(config.getStringList(key +".description.locked").isEmpty()||config.getStringList(key +".description.unlocked").isEmpty()){
				desc = null;
			    desc2 = null;
			}else {
				for(String added : config.getStringList(key +".description.locked")){
					desc.add(added.replaceAll("%cost", ""+cost));

				
				}
				for(String added : config.getStringList(key +".description.unlocked")){
					desc2.add(added);


					
				
				}
			}
			if(config.contains(key+".permission")&&config.contains(key+".price")){
				String permission = config.getString(key +".permission");
		
				KitController newKit = new KitController(icon,key, name, permission, cost, slot, desc, desc2, items);
				log("Register kit  " + key + " success!");
			}else {
				KitController newKit = new KitController(icon,key, name, slot,  desc, desc2, items);
				log("Register kit  " + key + " success!");
				
	
				
			}
		}
	}
	public static Connection getData(){
		return conn;
	}
	public static DataStorage getStorage(){
		return ds;
	}
	public static KitController getKit(){
		return kc;
	}
	public static ChestController getCC(){
		return cc;
	}


	
}
