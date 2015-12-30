package es.spikybite.ProxyCode.inventories.settings.lobby_settings;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import es.spikybite.ProxyCode.Skywars;
import es.spikybite.ProxyCode.inventories.Menu;
import es.spikybite.ProxyCode.inventories.settings.PlayerLobbySettings;
import es.spikybite.ProxyCode.player.SPlayer;
import es.spikybite.ProxyCode.utils.ItemBuilder;

public class Estadisticas extends Menu{

	public Estadisticas(Player p) {
		super("&e&l"+p.getName()+" Statics", 5);

		   SPlayer player = SPlayer.getDPlayer(p);
	    s(4,  ItemBuilder.crearItem1(310, 1, 0, "&e&lWINS", "&7Wins: &e"+player.getWins()));
	    s(19,  ItemBuilder.crearItem1(276, 1, 0, "&a&lKILLS", "&7Kills: &e"+player.getKills()));
	    s(22,  ItemBuilder.crearItem1(377, 1, 0, "&2&lKDR", "&7KDR: &e"+player.getKdr()));
	    s(25,  ItemBuilder.crearItem1(331, 1, 0, "&c&lDEATHS", "&7Deaths: &e"+player.getDeaths()));
	    s(40,  ItemBuilder.crearItem1(261, 1, 0, "&9&lGAMES PLAYEDS", "&7Games playeds: &e"+player.getGames()));
	    s(44,  ItemBuilder.crearItem1(355, 1, 0, "&c&lBACK", "&7Back to menu settings"));
	    o(p);
	}

	@Override
	public void onClick(Player p, ItemStack stack) {
		if(stack.getTypeId() == 355){
			new PlayerLobbySettings(p);
		}
		
	}

}
