package es.spikybite.ProxyCode.utils;

import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.Location;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.FireworkMeta;

import es.spikybite.ProxyCode.Skywars;

public class Fireworks {
	 public static void shot(final Player p)
	  {
	  
	 
	          Location loc = p.getLocation();
	          Firework fw = (Firework)loc.getWorld().spawn(loc, Firework.class);
	          FireworkMeta data = fw.getFireworkMeta();
	          Color c = null;
	          Random r = new Random();
	          int i = r.nextInt(5) + 1;
	          if (i == 1) {
	            c = Color.BLUE;
	          } else if (i == 2) {
	            c = Color.RED;
	          } else if (i == 3) {
	            c = Color.GREEN;
	          } else if (i == 4) {
	            c = Color.MAROON;
	          } else if (i == 5) {
	            c = Color.ORANGE;
	          }
	          data.addEffects(new FireworkEffect[] { FireworkEffect.builder().withColor(c).with(FireworkEffect.Type.STAR).build() });
	          data.setPower(1);
	          fw.setFireworkMeta(data);
	   
	  }
	  
}
