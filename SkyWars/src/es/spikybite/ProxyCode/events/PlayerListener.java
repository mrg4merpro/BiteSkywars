package es.spikybite.ProxyCode.events;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerPreLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.scheduler.BukkitRunnable;

import es.spikybite.ProxyCode.Skywars;
import es.spikybite.ProxyCode.arena.Arena;
import es.spikybite.ProxyCode.arena.ArenaManager;
import es.spikybite.ProxyCode.controllers.SignController;
import es.spikybite.ProxyCode.controllers.cartel.Cartel;
import es.spikybite.ProxyCode.inventories.KitMenu;
import es.spikybite.ProxyCode.inventories.SettingsSelector;
import es.spikybite.ProxyCode.inventories.VoteSelector;
import es.spikybite.ProxyCode.inventories.settings.PlayerLobbySettings;
import es.spikybite.ProxyCode.inventories.settings.lobby_settings.SalirOpcion;
import es.spikybite.ProxyCode.lobby.SkywarsLobby;
import es.spikybite.ProxyCode.player.SPlayer;
import es.spikybite.ProxyCode.utils.Vault;

public class PlayerListener implements Listener{
private static ArenaManager am = new ArenaManager();
	@EventHandler
	public void onInteractEvent(PlayerInteractEvent event){
		Player p = event.getPlayer();
		Arena arenaPlayer = am.getArena(p);
		if(arenaPlayer==null){
		if(event.getAction() == Action.PHYSICAL  && event.getClickedBlock().getType() == Material.STONE_PLATE){
		if(Skywars.pl.getConfig().getBoolean("lobby.pressure_plate_join")){
		Arena randomArena = am.getAllArenas().get(new Random().nextInt(am.getAllArenas().size()));
		randomArena.join(p);
		}
		}
		}
		if (( (event.getAction() == Action.RIGHT_CLICK_BLOCK)) && (event.getClickedBlock().getType() == Material.WALL_SIGN))
	      {
		   Cartel c = Cartel.getSign(event.getClickedBlock().getLocation());
		   event.setCancelled(true);
		   if(c==null){
			   return;
		   }
		   if(c.getArena() == null){
			   return;
		   }
		   c.getArena().join(p);
	      }
		else if(event.getAction().equals(Action.LEFT_CLICK_AIR)||event.getAction().equals(Action.RIGHT_CLICK_AIR)
				||event.getAction().equals(Action.LEFT_CLICK_BLOCK)||event.getAction().equals(Action.RIGHT_CLICK_BLOCK)){
			
		
			if(existName(p, Skywars.lang.get("player_inventory.name.item_vote_name"))){
				if(event.getItem().getType() == Material.EMPTY_MAP){
				event.setCancelled(true);
			   }
				Arena a = new ArenaManager().getArena(p);
				if(a==null){
					return;
				}
				new VoteSelector().o(p);
			}else if(existName(p, Skywars.lang.get("player_inventory.name.item_player_settings_name"))){
				if(event.getItem().getType() == Material.EMPTY_MAP){
				event.setCancelled(true);
			   }
				Arena a = new ArenaManager().getArena(p);
				if(a==null){
					return;
				}
				new SettingsSelector().o(p);
			}else if(existName(p, Skywars.lang.get("player_inventory.name.item_kit_name"))){
				if(event.getItem().getType() == Material.EMPTY_MAP){
				event.setCancelled(true);
			   }
				Arena a = new ArenaManager().getArena(p);
				if(a==null){
					return;
				}
				new KitMenu(p).o(p);
			}else if(existName(p, Skywars.lang.get("player_inventory.name.item_leave_name"))){
				if(event.getItem().getType() == Material.EMPTY_MAP){
				event.setCancelled(true);
			   }
				new SalirOpcion().o(p);
			}else if(existName(p, Skywars.lang.get("player_inventory.name.item_lobby"))){
				if(event.getItem().getType() == Material.EMPTY_MAP){
				event.setCancelled(true);
			   }
				
				Arena a = new ArenaManager().getArena(p);
				if(a!=null){
					p.getInventory().removeItem(p.getItemInHand());
					return;
				}
				new PlayerLobbySettings(p);
			}else if(existName(p, Skywars.lang.get("player_inventory.name.habilitiesItem"))){
				if(event.getItem().getType() == Material.EMPTY_MAP){
				event.setCancelled(true);
			   }
				
				Arena a = new ArenaManager().getArena(p);
				if(a!=null){
					p.getInventory().removeItem(p.getItemInHand());
					return;
				}
				p.sendMessage(Skywars.p + ChatColor.RED + " Coming Soon");
			}
			}
		
		
		
	}
	
	
	
	@EventHandler
	public void onChat(AsyncPlayerChatEvent event){
		Player p1 = event.getPlayer();
		
		event.setCancelled(true);
		for(Player p : Bukkit.getOnlinePlayers()){
		
			if(p1.getWorld() == p.getWorld()){
				p.sendMessage(stringTo(p1, event.getMessage()));
			}
		}
	}
	
	public String stringTo(Player p, String msg){
		String replaced = Skywars.lang
				.get("chat")
				.replaceAll("<USERNAME>", p.getName())
				.replaceAll("<MESSAGE>", msg)
				.replaceAll("<POINTS>", ""+Vault.getStringMoney(p))
				.replaceAll(
						"<PREFIX>",
						ChatColor.translateAlternateColorCodes('&',
								Skywars.chat.getPlayerPrefix(p)));
		
		return replaced;
	}
	
	@EventHandler
	public void onLeave(PlayerQuitEvent event){
		Player p = event.getPlayer();
		SPlayer player = SPlayer.getDPlayer(p);
		Skywars.getStorage().unloadData(player);
		event.setQuitMessage(null);
		Arena a = new ArenaManager().getArena(p);
		if(a==null){
			return;
		}
		a.leave(p);
		SPlayer.removeHolo(player);
		
		
		
	}
	
	
	 @EventHandler
	 public void onPlayerMoveEvent(PlayerMoveEvent event){
		 Player p = event.getPlayer();
			Arena a = am.getArena(p);
			if(a!=null){
				if(a.isGame()){
					SPlayer player = SPlayer.getDPlayer(p);
					 if(event.getFrom().getBlockX() == event.getTo().getBlockX()&&event.getFrom().getBlockY() == event.getTo().getBlockY()&&event.getFrom().getBlockZ() == event.getTo().getBlockZ()){
						 return;		
					}
					 player.setWalked(1);
				}
				
			}
		
		 
	 }
	 @EventHandler
	 public void onShotArrow(EntityShootBowEvent event)
	 {
		 
		 if(event.getEntity() instanceof Player){
			 Player p = (Player)event.getEntity();
			 if(event.getProjectile() instanceof Arrow){
				 Arena a = am.getArena(p);
				 if(a!=null){
					 if(a.isGame()){
						 SPlayer player = SPlayer.getDPlayer(p);
						 player.addShots(1);
					 }
				 }
			 }
		 }
		 
	 }
	@EventHandler
	public void onBlockPlaced(BlockPlaceEvent event){
		Player p = event.getPlayer();
		Arena a = am.getArena(p);
		if(a!=null){
			if(!a.isGame()){
				event.setCancelled(true);
			}else if(a.isGame()){
				SPlayer player = SPlayer.getDPlayer(p);
				player.setPlacedBlocks(1);
			}
		}
	}
	@EventHandler
	public void onLeave(PlayerKickEvent event){
		Player p = event.getPlayer();
		SPlayer player = SPlayer.getDPlayer(p);
		Skywars.getStorage().unloadData(player);
		event.setLeaveMessage(null);
		Arena a = new ArenaManager().getArena(p);
		if(a==null){
			return;
		}
		a.leave(p);

		player.removeHolo(player);
		
		
	}

	@EventHandler
	public void FoodLevel(FoodLevelChangeEvent event){
		Entity p = event.getEntity();
		if(!(p instanceof Player)){
			return;
		}
		Player p1 = (Player)p;
		Arena a = new ArenaManager().getArena(p1);
	
		if(a==null||!a.isGame()){
			event.setCancelled(true);
		}
	
	}
	
	
	@SuppressWarnings("static-access")
	@EventHandler
	public void forceJoin(PlayerJoinEvent event){
		final Player p = event.getPlayer();
		event.setJoinMessage(null);

		SPlayer player = new SPlayer(p);
		Skywars.getStorage().createData(p);
		if(!SPlayer.dplayer.containsKey(p)){
			SPlayer.dplayer.put(p, player);
		}

		Skywars.getStorage().loadData(player);
	
		p.getInventory().clear();
		Skywars.giveLobbyItem(p);
		if(!Skywars.pl.getConfig().contains("spawn")){
			p.sendMessage(Skywars.p + ChatColor.RED +  "The Global Spawn Does Not Exist Please Set With /sw spawn");
			p.sendMessage(Skywars.p + ChatColor.RED +  "The Global Spawn Does Not Exist Please Set With /sw spawn");
			p.sendMessage(Skywars.p + ChatColor.RED +  "The Global Spawn Does Not Exist Please Set With /sw spawn");
			return;
		}
		new BukkitRunnable() {
			
			@Override
			public void run() {
				SPlayer ph = SPlayer.getDPlayer(p);
				
				if(ph != null){
					SPlayer.setHolo(ph);
					}else {
						Skywars.log("Player null can't load a data, please check a plugin");
						return;
					}
p.setGameMode(GameMode.SURVIVAL);
				p.teleport(new ArenaManager().getLoc(Skywars.pl.getConfig().getString("spawn")));
			}
		}.runTaskLater(Skywars.pl, 20L);
	}
	
	@EventHandler
	public void forceRespawn(final PlayerRespawnEvent event){	
		final Player p = event.getPlayer();		
		Skywars.giveLobbyItem(p);
		if(new ArenaManager().getArena(p)==null){
		
		if(!Skywars.pl.getConfig().contains("spawn")){
		p.sendMessage(Skywars.p + ChatColor.RED +  "The Global Spawn Does Not Exist Please Set With /sw spawn");
		p.sendMessage(Skywars.p + ChatColor.RED +  "The Global Spawn Does Not Exist Please Set With /sw spawn");
		p.sendMessage(Skywars.p + ChatColor.RED +  "The Global Spawn Does Not Exist Please Set With /sw spawn");
		return;
		}
new BukkitRunnable() {
			
			@SuppressWarnings("static-access")
			@Override
			public void run() {
				
				p.teleport(new ArenaManager().getLoc(Skywars.pl.getConfig().getString("spawn")));
				
			}
		}.runTaskLater(Skywars.pl, 1L);
	}
		
	}
	
	@EventHandler
	public void onCancell(EntityDamageEvent event){
	Entity e = event.getEntity();
	if(!(e instanceof Player)){
		return;
	}
	Player p = (Player)e;
	Arena a = new ArenaManager().getArena(p);

	if(a==null){
		event.setCancelled(true);
		return;
	}
	if(a.isEnd()){
		event.setCancelled(true);
		
	}
	if(a.fallMode()==true){
		if(event.getCause() == DamageCause.FALL||event.getCause() == DamageCause.VOID){
		event.setCancelled(true);
		}
	}
	}
	
@EventHandler
public void onDrop(PlayerDropItemEvent event){
	Player p = event.getPlayer();
	Arena a = am.getArena(p);
	if(a==null){
		return;
	}
	if(!a.isGame()){
		event.setCancelled(true);
	}
}
	
	@EventHandler
	public void onPlayerkill(PlayerDeathEvent event)
	{
		event.setDeathMessage(null);
		Player p = event.getEntity();
		SPlayer player = SPlayer.getDPlayer(p);
		player.setDeaths(1);
		Arena a = new ArenaManager().getArena(p);
	
		if(a==null){
			event.getDrops().clear();
			return;
		}
		if(p.getKiller() instanceof Player){
			Player killer = p.getKiller();
			Arena a1 = new ArenaManager().getArena(killer);
			if(a == a1){	
				a.sender(Skywars.p + Skywars.lang.get("player_death_by_player").replaceAll("%player", p.getName()).replaceAll("%killer", killer.getName()).replaceAll("%points", ""+Vault.getPointRewardKill()));
			SPlayer player1 = SPlayer.getDPlayer(killer);
player1.setKills(1);
			   Vault.setMoney(p, Vault.getPointRewardKill());
			   a.spect(p);
			   }
		}else{
			//Player killer = null;

			a.sender(Skywars.p + Skywars.lang.get("player_death_by_void").replaceAll("%player", p.getName()));			
a.spect(p);
			}
	}
	@EventHandler
	public void onBreak(BlockBreakEvent event){
		Player p = event.getPlayer();
		Arena a = am.getArena(p);
		if(a!=null){
			if(!a.isGame()){
				event.setCancelled(true);
			}else if(a.isGame()){
				SPlayer player = SPlayer.getDPlayer(p);
				player.setBreakBlocks(1);
			}
		}
	}

    public static boolean existName(Player p, String s){
    	return p.getItemInHand().hasItemMeta()&&p.getItemInHand().getItemMeta().hasDisplayName()&&p.getItemInHand().getItemMeta().getDisplayName().contains(ChatColor.translateAlternateColorCodes('&', s));
    }
	
}
