package es.spikybite.ProxyCode.lobby;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import es.spikybite.ProxyCode.storage.YMLStorage;

public class Habilidad {
private Player p;
public static enum HabilidadType {
TRIPLE_ARROW, MORE_DAMAGE, MORE_HEARTH, TAKE_KNOCKBACK;
}
	public static int getPercentAbility(Player p , HabilidadType type){
		YMLStorage store = new YMLStorage(p);
		switch (type) {
		case TRIPLE_ARROW:
			return store.get().getInt("habilidades.triple_arrow"); 

		case MORE_DAMAGE:
			return store.get().getInt("habilidades.more_damage"); 

		case MORE_HEARTH:
			return store.get().getInt("habilidades.more_hearth"); 

		case TAKE_KNOCKBACK:
			return store.get().getInt("habilidades.take_knockback"); 


		}
		
		
		return -1;
		
		
	}
	
	public static int getLevelAility(Player p, HabilidadType type){
		YMLStorage store = new YMLStorage(p);
		switch (type) {
		case TRIPLE_ARROW:
			return store.get().getInt("level_habilidad.triple_arrow"); 

		case MORE_DAMAGE:
			return store.get().getInt("level_habilidad.more_damage"); 

		case MORE_HEARTH:
			return store.get().getInt("level_habilidad.more_hearth"); 

		case TAKE_KNOCKBACK:
			return store.get().getInt("level_habilidad.take_knockback"); 


		}
		
		
		return -1;
	}
	public static ItemStack getAbilityMaterial(HabilidadType type){
		switch (type) {
		case TRIPLE_ARROW:
			return new ItemStack(Material.ARROW);
		case MORE_DAMAGE:
			return new ItemStack(Material.DIAMOND_SWORD);
			
		case MORE_HEARTH:
			
			return new ItemStack(Material.GOLDEN_APPLE); 
		case TAKE_KNOCKBACK:

return new ItemStack(Material.DIAMOND_CHESTPLATE);
		}
return null;
	}
	public static void upgradeAtAbility(Player p, HabilidadType type){
		if(getPercentAbility(p, type) >= 5){
			
		}
	}
}
