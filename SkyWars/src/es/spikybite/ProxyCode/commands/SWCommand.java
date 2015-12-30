package es.spikybite.ProxyCode.commands;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Difficulty;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import es.spikybite.ProxyCode.Skywars;
import es.spikybite.ProxyCode.arena.Arena;
import es.spikybite.ProxyCode.arena.ArenaManager;
import es.spikybite.ProxyCode.player.SPlayer;

public class SWCommand implements CommandExecutor{

	
	
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label,
			String[] args) {
		if(!(sender instanceof Player)){
			sender.sendMessage("§cCommands avaibles only for players!");
			
			return true;
		}
		if(cmd.getName().equalsIgnoreCase("stats")){
			Player p = (Player)sender;
			SPlayer player = SPlayer.getDPlayer(p);
			p.sendMessage("§7--------- §bSkywars Statics §7----------");
			p.sendMessage("§7Kills: §a"+player.getKills());
			p.sendMessage("§7Deaths: §a"+player.getDeaths());
			p.sendMessage("§7Blocks Broken: §a"+player.getBlockBreaks());
			p.sendMessage("§7Blocks Placeds: §a"+player.getBlockPlaceds());
			p.sendMessage("§7Games playeds: §a"+player.getGames());
			p.sendMessage("§7Wins: §a"+player.getWins());
			p.sendMessage("§7Arrow Shots: §a"+player.getShots());
			p.sendMessage("§7Distance Walked: §a"+player.getWalked());
			p.sendMessage("§7Glass: §a"+player.getGlass());
			p.sendMessage("§7Trail: §a"+player.getTrail());
			p.sendMessage("§7--------------------------");
		}
		if(cmd.getName().equalsIgnoreCase("sw")){
			Player p = (Player)sender;
			ArenaManager am = new ArenaManager();
			if(args.length < 1){
				help(p, 1);
				return true;
			}
			World world = p.getWorld();

File file = new File("plugins/Skywars/arenas/"+world.getName()+".yml");
YamlConfiguration config = new YamlConfiguration().loadConfiguration(file);
			if(args[0].equalsIgnoreCase("create")){   if(retornar(p)){
				   return true;
			    }
		
				Location loc = p.getLocation();
				if(am.exist(world.getName())){
					p.sendMessage("§cArena with name: "+world.getName()+ " already exist");
					return true;
				}
				Arena a = new Arena(world.getName(), world.getName(), 4, 12);
			    am.save(config, file);		a.load();
				p.sendMessage("§aArena "+world.getName()+" create successful!");
			}else if(args[0].equalsIgnoreCase("rename")){
				if(retornar(p)){
				return true;
				}
				if(args.length  < 2){
					p.sendMessage("§c/sw rename <newName>");
					return true;
				}
				
				if(!am.exist(world.getName())){
					p.sendMessage("§cArena "+world.getName()+ " not exist");
					return true;
				}
				
				config.set("aName", args[1]);
				am.save(config, file);
				p.sendMessage("§aYou renamed "+world.getName() + " to "+args[1]);
				
			}else if(args[0].equalsIgnoreCase("setspect")){
				if(retornar(p)){
					
					return true;
				}
				if(!am.exist(world.getName())){
					p.sendMessage("§cArena "+world.getName()+ " not exist");
					return true;
				}
				Location loc = p.getLocation();
			config.set("aSpectatorSpawn", am.setLoc(p.getLocation()));
			am.save(config, file);
				p.sendMessage("§aSpectator spawn set to arena "+world.getName());
			}
			else if(args[0].equalsIgnoreCase("spawn")){   if(retornar(p)){
				   return true;
			    }
				Location loc = p.getLocation();
			
				Skywars.pl.getConfig().set("spawn", am.setLoc(loc));
			    Skywars.pl.saveConfig();
				p.sendMessage("§aGlobal spawn set!");
			}
			else if(args[0].equalsIgnoreCase("min")){   if(retornar(p)){
				   return true;
			    }
				if(args.length < 2){
					p.sendMessage("§c/sw min <min>");
					return true;
				}
				if(!am.exist(world.getName())){
					p.sendMessage("§cArena "+world.getName()+ " not exist");
					return true;
				}
				try{
					int i = Integer.parseInt(args[1]);
					config.set("aMin", i);
				    am.save(config, file);
				    p.sendMessage("§a"+i+" minplayers for arena "+world.getName());
				}catch(NumberFormatException e){
					p.sendMessage("§cInvalid number!");
				}
			}
			else if(args[0].equalsIgnoreCase("max")){    if(retornar(p)){
				   return true;
			    }
				if(args.length < 2){
					p.sendMessage("§c/sw max <min>");
					return true;
				}
				if(!am.exist(world.getName())){
					p.sendMessage("§cArena "+world.getName()+ " not exist");
					return true;
				}
				try{
					int i = Integer.parseInt(args[1]);
					config.set("aMax", i);
				    am.save(config, file);
				    p.sendMessage("§a"+i+" maxplayers for arena "+world.getName());
				}catch(NumberFormatException e){
					p.sendMessage("§cInvalid number!");
				}
			}
			
			
			else if(args[0].equalsIgnoreCase("add")){  
				if(retornar(p)){
				   return true;
			    }
				Location loc = p.getLocation();
				if(!am.exist(world.getName())){
					p.sendMessage("§cArena "+world.getName()+ " not exist");
					return true;
				}
				am.add(world, loc);
				p.sendMessage("§aSpawn added");
			}else if(args[0].equalsIgnoreCase("remove")){   if(retornar(p)){
				   return true;
			    }
				Location loc = p.getLocation();
				if(!am.exist(world.getName())){
					p.sendMessage("§cArena "+world.getName()+ " not exist");
					return true;
				}
				am.remove(world);
				p.sendMessage("§cSpawn removed!");
			}else if(args[0].equalsIgnoreCase("arenas")){
				StringBuilder b = new StringBuilder();
				p.sendMessage("§aArenas: " + b.append(ArenaManager.arenas.toString()));
			}else if(args[0].equalsIgnoreCase("join")){ if(retornar(p, "skywars.join")){
				   return true;
			    }
				if(args.length < 2){
					p.sendMessage("§c/sw join <arena>");
					return true;
				}
				Arena arena = am.getArena(args[1]);
			
				if(arena == null){
					p.sendMessage(Skywars.p + Skywars.lang.get("arena_not_found"));
					return true;
				}
				if(arena.p().contains(p)){
					return true;
				}
			    arena.join(p);
			}else if(args[0].equalsIgnoreCase("leave")){   if(retornar(p, "skywars.leave")){
				   return true;
			    }
				Arena arena = am.getArena(p);
				if(arena==null){
					p.sendMessage(Skywars.p + Skywars.lang.get("arena_not_found"));
					return true;
				}
                if(!arena.p().contains(p)){

					p.sendMessage(Skywars.p + Skywars.lang.get("not_these_in_arena"));
                	return true;
                }
			    arena.leave(p);
			}else if(args[0].equalsIgnoreCase("load")){   
				if(retornar(p)){
				   return true;
			    }
		        if(args.length < 2){
		        	p.sendMessage("§c/sw load <worldName>");
		        	return true;
		        }
		        String name = args[1];
		        if(Bukkit.getWorld(name)!=null){
		        	p.sendMessage("§cWorld already loaded!");
		        	return true;
		        }
		       
		        File mundo = new File(name);
		        File carpeta = new File("plugins/Skywars/mapas/"+name);
		        if(!carpeta.exists()){
	            	p.sendMessage("§cError ocurred when try copy directory to mapas directory!");
	            	return true;
	            	
	            }
		 
		        try {
					am.copyDir(carpeta, mundo);

			        p.sendMessage("§aSuccessful loaded a world "+args[1]);
			        p.sendMessage("§aCopied world from mapas to "+args[1] + " principal.");
				} catch (IOException e) {

					p.sendMessage("§cFailed to load an world please check logs console");
					e.printStackTrace();
				}
		        WorldCreator wc = new WorldCreator(args[1]);
	        	World w = wc.createWorld();
	            w.setAutoSave(false);
	            w.setThundering(false);
	            w.setStorm(false);
	            w.setDifficulty(Difficulty.PEACEFUL);
	            w.setTicksPerAnimalSpawns(1);
	            w.setTicksPerMonsterSpawns(1);
	            w.setGameRuleValue("doMobSpawning", "false");
	            w.setGameRuleValue("mobGriefing", "false");
	            w.setGameRuleValue("doFireTick", "false");
	            w.setGameRuleValue("showDeathMessages", "false");
			}else if(args[0].equalsIgnoreCase("list")){ if(retornar(p)){
				   return true;
			    }
				String cargados = "";
				for(World mundos : Bukkit.getWorlds()){
		
					cargados = cargados + mundos.getName()+", ";
				}
				
		p.sendMessage("§cWorlds loaded: "+cargados);
				
			}else if(args[0].equalsIgnoreCase("tp")){   if(retornar(p)){
				   return true;
			    }
				if(args.length < 2){
					p.sendMessage("§c/sw tp <worldName>");
					return true;
				}
				World mundo = Bukkit.getWorld(args[1]);
				if(mundo==null){
					p.sendMessage("§cThis world not exist!");
					return true;
				}
				p.teleport(mundo.getSpawnLocation());
				Block block = p.getLocation().subtract(0,1,0).getBlock();
				if(block.getType() == Material.AIR){
					block.setType(Material.STONE);
				}
				p.sendMessage("§aTeleported to "+mundo.getName());
				
			}else if(args[0].equalsIgnoreCase("saveworld")){
				if(retornar(p)){
					return true;
				}
				File from = new File(p.getLocation().getWorld().getName());
			    File to = new File("plugins/Skywars/mapas/"+p.getLocation().getWorld().getName());
			    p.getWorld().save();
			    if(to.exists()){
			    	am.delete(to);
			    }
			    try {
					am.copyDir(from, to);
					p.sendMessage("§aYou save this world succesful!");
				} catch (IOException e) {
p.sendMessage("§cFailed to try save this world :( check console logs");
					e.printStackTrace();
				}
			    
			}else if(args[0].equalsIgnoreCase("about")){
				p.sendMessage("§7§m-=-=-=-=--=-=-=-=--=-=-=-=-");
				p.sendMessage("§8Plugin: §bBiteSkywars");
				p.sendMessage("§8Version: §bv0.8.R6");
				p.sendMessage("§8Author: §bProxyCode");
				p.sendMessage("§8Compiled: §bJava SE 1.7");
				p.sendMessage("§4Thanks you!");
				p.sendMessage("§7§m-=-=-=-=--=-=-=-=--=-=-=-=-");
			}else if(args[0].equalsIgnoreCase("save")){
				if(retornar(p)){
					   return true;
				    }
				if(args.length < 2){
					p.sendMessage("§c/sw save <worldName>");
					return true;
				}
				if(Bukkit.getWorld(args[1])!=null){
					p.sendMessage("§cThis world already exist!");
					return true;
				}
	            p.sendMessage("§aCreating new world....");
	            WorldCreator wc = new WorldCreator(args[1]);
	        	wc.generateStructures(false);
	        	World w = wc.createWorld();
	            w.setAutoSave(false);
	            w.setThundering(false);
	            w.setStorm(false);
	            w.setDifficulty(Difficulty.PEACEFUL);
	            w.setTicksPerAnimalSpawns(1);
	            w.setTicksPerMonsterSpawns(1);
	            w.setGameRuleValue("doMobSpawning", "false");
	            w.setGameRuleValue("mobGriefing", "false");
	            w.setGameRuleValue("doFireTick", "false");
	            w.setGameRuleValue("showDeathMessages", "false");
	            File mundo = new File(args[1]);
	            if(!mundo.exists()){
	            	p.sendMessage("§cError ocurred when try copy directory to mapas directory!");
	            	return true;
	            	
	            }
	            File carpeta = new File("plugins/Skywars/mapas/"+args[1]);
		        if(!carpeta.exists()){
		        	carpeta.mkdirs();
		        }
		        try {
					am.copyDir(mundo, carpeta);

			        p.sendMessage("§a100% world created successful!");

			        p.sendMessage(mundo.getPath()+" copied to "+carpeta.getPath());
				} catch (IOException e) {
					p.sendMessage("§cFailed to create an world please check logs console");
					e.printStackTrace();
				}
			}else if(args[0].equalsIgnoreCase("page")){
				
				if(args.length < 2){
					p.sendMessage("§c/sw page <page-number> §7- Show other command list");
					return true;
				}
				if(args[1].equalsIgnoreCase("2")){
				help(p, 2);	
				}else if(args[1].equalsIgnoreCase("1")){
					help(p, 1);	
					
				}
			}else if(args[0].equalsIgnoreCase("forcestart")){
				if(retornar(p))
				{
					return true;
					
				}				Arena a = am.getArena(p);
				if(a==null){
					p.sendMessage(Skywars.p + Skywars.lang.get("arena_not_found"));
					
					return true;
				}
				if(a.isStarting()){
					p.sendMessage(Skywars.p + "§7This arena already starting!");
					return true;
				}
		     p.sendMessage(Skywars.p + "§aYou will force start this arena.");
		     a.second();
			}else if(args[0].equalsIgnoreCase("addholo")){
			    List<String> locs = Skywars.holo.getConfig().getStringList("locations");
			    locs.add(am.setLoc(p.getLocation()));
			    Skywars.holo.getConfig().set("locations", locs);
			    Skywars.holo.save();
				p.sendMessage("§aAdded hologram location done!");
			}else if(args[0].equalsIgnoreCase("removeholo")){
			    List<String> locs = Skywars.holo.getConfig().getStringList("locations");
			    if(locs.isEmpty()){
			    	p.sendMessage("§cHolograms list is empty!");
			    	return true;
			    }
			    locs.remove(locs.get(locs.size()-1));
			    Skywars.holo.getConfig().set("locations", locs);
			    Skywars.holo.save();
				p.sendMessage("§cRemoved spawn for hologram done!");
			}
			
			else{ 
				help(p, 1);
				return true;
			}
		}
		return false;
	}
	public boolean retornar(Player p){
		if(!p.hasPermission("skywars.admin")){
			p.sendMessage(Skywars.p + Skywars.lang.get("no_permission"));
			return true;
		}
		return false;
	}
	public boolean retornar(Player p, String perm){
		if(!p.hasPermission(perm)){
			p.sendMessage(Skywars.p + Skywars.lang.get("no_permission"));
			return true;
		}
		return false;
	}
	public void help(Player p, int page){
		if(page == 1){

			if(p.hasPermission("skywars.admin")){
		p.sendMessage("§cBiteSkywars Reloaded v0.9R1");
		p.sendMessage("");
			}
		if(p.hasPermission("skywars.join")||p.hasPermission("skywars.admin")){
		p.sendMessage("§c/sw join §7- Join to arena");
		}

		if(p.hasPermission("skywars.join")||p.hasPermission("skywars.admin")){
		p.sendMessage("§c/sw leave §7- Leave arena");
		}

		if(p.hasPermission("skywars.admin")){
		p.sendMessage("§c/sw create §7- Create arena with world name");
		p.sendMessage("§c/sw rename <newName> §7- Rename your arena");
		p.sendMessage("§c/sw setspect §7- Set spectator spawn to current arena");
		p.sendMessage("§c/sw add §7- Add spawn in arena");
		p.sendMessage("§c/sw remove §7- Remove spawn in arena");
		p.sendMessage("§c/sw min <min> §7- Add minplayers in arena");
		p.sendMessage("§c/sw max <max> §7- Add maxplayers in arena");
		p.sendMessage("§c/sw forcestart §7- Force start arena");
		p.sendMessage("§c/sw saveworld §7- Save your current worlds if you edit map");
		p.sendMessage("§c/sw addholo §7- Add location to hologram!");
		p.sendMessage("§c/sw removeholo §7- Remove last location added of hologram!");
		p.sendMessage("");
		
		
		p.sendMessage("§7-------- §c/sw page 2 §7--------");
		}
		}else {

			if(p.hasPermission("skywars.admin")){
			p.sendMessage("§cBiteSkywars Reloaded v0.9R1");
			p.sendMessage("");
			p.sendMessage("§c/sw max <max> §7- Add maxplayers in arena");
			p.sendMessage("§c/sw spawn §7- Set global spawn");
			p.sendMessage("§c/sw arenas §7- Show arena list!");
			p.sendMessage("§c/sw tp <worldName> §7- Teleport to world!!");
			p.sendMessage("§c/sw list §7- Show world list (loaded)!");
			p.sendMessage("§c/sw load <worldName> §7- Load a new world!");
			p.sendMessage("§c/sw save <worldName> §7- Create a new world!");	
			p.sendMessage("");
			p.sendMessage("§7-------- §c/sw page 1 §7--------");
			}
		}
	}

}
