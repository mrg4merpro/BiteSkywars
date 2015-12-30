package es.spikybite.ProxyCode.api;

import org.bukkit.entity.Player;

import es.spikybite.ProxyCode.Skywars;
import es.spikybite.ProxyCode.player.SPlayer;

public class BiteAPI {

	public static int getKills(Player p){
		SPlayer player = SPlayer.getDPlayer(p);
		return player.getKills();
	}
	public static int getDeaths(Player p){
		SPlayer player = SPlayer.getDPlayer(p);
		return player.getDeaths();
	}
	public static int getWins(Player p){
		SPlayer player = SPlayer.getDPlayer(p);
		return player.getWins();
	}
	public static int getGamesPlayed(Player p){
		SPlayer player = SPlayer.getDPlayer(p);
		return player.getGames();
	}
	public static int getDistanceWalked(Player p){
		SPlayer player = SPlayer.getDPlayer(p);
		return player.getWalked();
	}
	public static int getArrowShots(Player p){
		SPlayer player = SPlayer.getDPlayer(p);
		return player.getShots();
	}
	public static int getBlocksBreaks(Player p){
		SPlayer player = SPlayer.getDPlayer(p);
		return player.getBlockBreaks();
	}
	public static int getBlockPlaceds(Player p){
		SPlayer player = SPlayer.getDPlayer(p);
		return player.getBlockPlaceds();
	}
	public static void unloadPlayerData(Player p){
		SPlayer player = SPlayer.getDPlayer(p);
		Skywars.getStorage().unloadData(player);
	}
	public static void loadPlayerData(Player p){
		SPlayer player = SPlayer.getDPlayer(p);
		Skywars.getStorage().loadData(player);
	}
	public static SPlayer getSkyWarsPlayer(Player p){
		SPlayer player = SPlayer.getDPlayer(p);
		return player;
	}
	
}
