package es.spikybite.ProxyCode.inventories.settings;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import es.spikybite.ProxyCode.inventories.Menu;
import es.spikybite.ProxyCode.inventories.settings.lobby_settings.CambiarColor;
import es.spikybite.ProxyCode.inventories.settings.lobby_settings.CambiarTrail;
import es.spikybite.ProxyCode.inventories.settings.lobby_settings.Estadisticas;
import es.spikybite.ProxyCode.inventories.settings.lobby_settings.ShopKits;
import es.spikybite.ProxyCode.utils.ItemBuilder;
import es.spikybite.ProxyCode.utils.Vault;

public class PlayerLobbySettings extends Menu{

	public PlayerLobbySettings(Player p) {
		super("&0&nLobby Menu", 5);
		s(20, ItemBuilder.crearItem(95, 1, 15, "&bGlass Colors"));
		s(22, ItemBuilder.crearItem(276, 1, 0, "&eSkywars Statics"));
		s(40, ItemBuilder.crearItem(371, 1, 0, "&6Coins: &e&l"+Vault.getMoney(p) ));
		s(24, ItemBuilder.crearItem(262, 1, 0, "&5Arrow Trails"));

		s(4, ItemBuilder.crearItem(261, 1, 0, "&aKits"));
		o(p);
	}

	@Override
	public void onClick(Player p, ItemStack stack) {
		if(stack.getTypeId() == 276)
		{
			new Estadisticas(p);
		}else if(stack.getTypeId() == 95){
			new CambiarColor().o(p);
		}else if(stack.getTypeId() == 262){
			new CambiarTrail().o(p);
		}else if(stack.getTypeId() == 261){
			new ShopKits(p).o(p);;
		}
	}

}
