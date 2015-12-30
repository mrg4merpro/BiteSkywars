package es.spikybite.ProxyCode.utils;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import es.spikybite.ProxyCode.Skywars;

public class SettingsDefaults {

	
	public void addComplementLang(){
		Skywars.pl.getConfig().addDefault("item-lobby.lobbyItem", true);
		Skywars.pl.getConfig().addDefault("item-lobby.habilitiesItem", true);
		Skywars.pl.getConfig().addDefault("canDestroyAndBuild", false);
		Skywars.pl.getConfig().addDefault("mysql.enable", false);
		Skywars.pl.getConfig().addDefault("mysql.host", "localhost");
		Skywars.pl.getConfig().addDefault("mysql.port", 3306);
		Skywars.pl.getConfig().addDefault("mysql.username", "root");
		Skywars.pl.getConfig().addDefault("mysql.password", "cheese");
		Skywars.pl.getConfig().addDefault("mysql.database", "skywars");
		Skywars.pl.getConfig().addDefault("lobby.pressure_plate_join", true);
		Skywars.pl.getConfig().addDefault("lobby.abilityItemSlotPosition", 3);
		Skywars.pl.getConfig().addDefault("lobby.lobbySettingsItemSlotPosition", 6);

		Skywars.pl.getConfig().options().copyDefaults(true);
		Skywars.pl.getConfig().options().copyDefaults();
		Skywars.pl.saveConfig();
		File file = new File("plugins/Skywars/lang.yml");
		YamlConfiguration c = new YamlConfiguration().loadConfiguration(file);
		c.addDefault("prefix", "&8[&aSkywars&8] &7");
		c.addDefault("good_luck", text("&6Selected &a%chest &6chests!", "&6Selected &a%time &6!"));
		c.addDefault("player_win_arena", text("&a--------------------", "&e%player win the game", "&a--------------------"));
		c.addDefault("max_votes_used", "&cYou cant vote two times");
		c.addDefault("no_permission", "&cYou dont have permission");
		c.addDefault("rewards", "&a+%points points");
        c.addDefault("chat", "&a(<POINTS>) <PREFIX> &7<USERNAME>&8: &b<MESSAGE>");
		c.addDefault("player_join_to_arena", "56%player joined the game (%online/%max)");

		c.addDefault("player_leave_to_arena", "&6%player leave the game (%online/%max)");
		c.addDefault("player_death_by_void", "&3%player &6killed by void");
		c.addDefault("player_death_by_player", "&3%player &6killed by &3%killer &a(+%points points)");
		c.addDefault("countdown", "&9Skywars game starting in %time");
		c.addDefault("countdown_release", "&cYou will release in %time");
		c.addDefault("teleport_to_map", "&9Teleporting to map..");
		c.addDefault("game_starting", "&9The game is starting!!");
		c.addDefault("game_stopped", "&cThe game stopped!");
		c.addDefault("arena_full", "&cArena full!");
		c.addDefault("arena_restarting", "&9Arena restarting!");
		c.addDefault("arena_ingame", "&cArena already started!");
		c.addDefault("arena_not_found", "&cArena not found!");
		c.addDefault("not_these_in_arena", "&cYou not these in any arena!");
		c.addDefault("vote_basic", "&5%name &6voted for &aBasic &6chests! &e%votes votes!");
		c.addDefault("vote_normal", "&5%name &6voted for &aNormal &6chests! &e%votes votes!");
		c.addDefault("vote_overpowered", "&5%name &6voted for &aOverpowered &6chests! &e%votes votes!");
		c.addDefault("vote_day", "&5%name &6voted for &aDay &6! &e%votes votes!");
		c.addDefault("vote_sunset", "&5%name &6voted for &aSunset &6! &e%votes votes!");
		c.addDefault("vote_night", "&5%name &6voted for &aNight &6! &e%votes votes!");
		c.addDefault("kit_selected", "&aKit selected %kit");
		c.addDefault("kit_not_enough_money", "&cYou not have enough money for buy this kit");
		c.addDefault("kit_purchase", "&aYou buy kit %kit success!");
		c.addDefault("kit_already_buy", "&aYou already have %kit!");
		
		
		c.addDefault("sign.sign_header", "&lSkywars");
		c.addDefault("sign.sign_state", "%state");
		c.addDefault("sign.sign_map", "&nMap: %map");
		c.addDefault("sign.sign_players", "&8%on/%max");
		

		c.addDefault("game.time_countdown", 20);
		c.addDefault("game.kill_reward_points", 20);
		c.addDefault("game.win_reward_points", 10);
		c.addDefault("game.state_waiting", "&2Waiting");
		c.addDefault("game.state_starting", "&6Starting");
		c.addDefault("game.state_ingame", "&4In-Game");
		c.addDefault("game.state_restarting", "&9Restarting");
		c.addDefault("game.state_full", "&5Full");
		c.addDefault("game.block_state_waiting", "95:5");
		c.addDefault("game.block_state_starting", "95:4");
		c.addDefault("game.block_state_ingame", "95:14");
		c.addDefault("game.block_state_restarting", "95:15");
		c.addDefault("game.block_state_full", "95:10");
		

		c.addDefault("scoreboard.name", "&6&lSkywars");
		c.addDefault("scoreboard.lines", text("&5Map", "&a%map", "&f", "&5Players", "&a%players/%max", "&f&f", "&fwww.spikybite.net"));
		
		c.addDefault("inventories.vote_chest", "&c&lVote Chest");
		c.addDefault("inventories.vote_time", "&b&lVote Time");
		c.addDefault("inventories.vpte_refill", "&2&lVote Refill Chest");
		c.addDefault("inventories.vote_selector", "&e&lVote Menu");
		

		c.addDefault("inventories_material.vote_chest_id", 54);
		c.addDefault("inventories_material.vote_time_id", 175);
		c.addDefault("inventories_material.vote_refill_id", 325);
		

		c.addDefault("items_inventories.vote_chest_basic", "&e&lChest Basic");
		c.addDefault("items_inventories.vote_chest_normal", "&6&lChest Normal");
		c.addDefault("items_inventories.vote_chest_overpowered", "&4&lChest Overpowered");
		c.addDefault("items_inventories.vote_time_day", "&e&lTime Day");
		c.addDefault("items_inventories.vote_time_sunset", "&6&lSunset");
		c.addDefault("items_inventories.vote_time_night", "&7&lTime Night");
		c.addDefault("items_inventories.vote_selector_chest", "&e&lChest vote");
		c.addDefault("items_inventories.vote_selector_refill", "&b&lRefill Vote");
		c.addDefault("items_inventories.vote_selector_time", "&6&lTime Vote");
		

		c.addDefault("items_inventories.vote_chest_basic_lore", text("&7Vote basic chest", "&4%votes votes"));

		c.addDefault("items_inventories.vote_chest_normal_lore", text("&7Vote normal chest", "&4%votes votes"));

		c.addDefault("items_inventories.vote_chest_overpowered_lore",  text("&7Vote overpowered chest", "&4%votes votes"));
		

		c.addDefault("items_inventories.vote_time_day_lore",  text("&7Vote day time", "&4%votes votes"));
		
		c.addDefault("items_inventories.vote_time_sunset_lore",  text("&7Vote sunset", "&4%votes votes"));

		c.addDefault("items_inventories.vote_time_night_lore",  text("&7Vote night time", "&4%votes votes"));
		

		c.addDefault("player_inventory.name.item_kit_name",  "&aKits");
		c.addDefault("player_inventory.name.item_vote_name",  "&eVote");
		c.addDefault("player_inventory.name.item_leave_name",  "&cLeave");

			c.addDefault("player_inventory.name.item_player_settings_name",  "&aSkywars Settings &7(Right Click)"); 
		c.addDefault("player_inventory.name.item_lobby",  "&2&lSkywars Settings &7(Right Click");
		c.addDefault("player_inventory.name.habilitiesItem",  "&b&lHabilities &7(Right Click");
		

		c.addDefault("player_inventory.id.item_kit_id",  399);
		c.addDefault("player_inventory.id.item_vote_id",  339);
		c.addDefault("player_inventory.id.item_player_settings_id",  262);
		c.addDefault("player_inventory.id.item_leave_id",  378);
		c.options().copyDefaults(true);
		c.options().copyDefaults();
		try {
			c.save(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	
	
	public List<String> text(String... text){
		return Arrays.asList(text);
	}
}
