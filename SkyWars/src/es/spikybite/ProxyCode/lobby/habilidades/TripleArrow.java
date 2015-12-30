package es.spikybite.ProxyCode.lobby.habilidades;


import java.util.Random;

import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.util.Vector;

import es.spikybite.ProxyCode.lobby.Habilidad;
import es.spikybite.ProxyCode.lobby.Habilidad.HabilidadType;

public class TripleArrow implements Listener{
	@EventHandler
	  public void onShoot(EntityShootBowEvent e)
	  {
	    if ((e.getEntity() instanceof Player))
	    {
	      Player p = (Player)e.getEntity();
	   
	        
if(Habilidad.getPercentAbility(p, HabilidadType.TRIPLE_ARROW)==0){
	return;
}
	        if (new Random().nextInt(100)+1 <= Habilidad.getPercentAbility(p, HabilidadType.TRIPLE_ARROW))
	        {
	          Arrow a1 = (Arrow)p.launchProjectile(Arrow.class);
	          Arrow a2 = (Arrow)p.launchProjectile(Arrow.class);
	          if ((getDirection(Float.valueOf(p.getLocation().getYaw())) == "EAST") || (getDirection(Float.valueOf(p.getLocation().getYaw())) == "WEST"))
	          {
	            a1.setVelocity(new Vector(a1.getVelocity().getX(), a1.getVelocity().getY(), a1.getVelocity().getZ() - 0.1D));
	            a2.setVelocity(new Vector(a2.getVelocity().getX(), a2.getVelocity().getY(), a2.getVelocity().getZ() + 0.1D));
	          }
	          else
	          {
	            a1.setVelocity(new Vector(a1.getVelocity().getX() - 0.1D, a1.getVelocity().getY(), a1.getVelocity().getZ()));
	            a2.setVelocity(new Vector(a2.getVelocity().getX() + 0.1D, a2.getVelocity().getY(), a2.getVelocity().getZ()));
	          }
	        }
	   
	    }
	  }
	  
	  public String getDirection(Float yaw)
	  {
	    yaw = Float.valueOf(yaw.floatValue() / 90.0F);
	    yaw = Float.valueOf(Math.round(yaw.floatValue()));
	    if ((yaw.floatValue() == -4.0F) || (yaw.floatValue() == 0.0F) || (yaw.floatValue() == 4.0F)) {
	      return "SOUTH";
	    }
	    if ((yaw.floatValue() == -1.0F) || (yaw.floatValue() == 3.0F)) {
	      return "EAST";
	    }
	    if ((yaw.floatValue() == -2.0F) || (yaw.floatValue() == 2.0F)) {
	      return "NORTH";
	    }
	    if ((yaw.floatValue() == -3.0F) || (yaw.floatValue() == 1.0F)) {
	      return "WEST";
	    }
	    return "";
	  }
}
