package es.spikybite.ProxyCode.controllers;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.scheduler.BukkitRunnable;

import es.spikybite.ProxyCode.Skywars;
import es.spikybite.ProxyCode.arena.Arena;
import es.spikybite.ProxyCode.arena.ArenaManager;
import es.spikybite.ProxyCode.utils.ParticleEffect;
import es.spikybite.ProxyCode.utils.Trails;

public class ArrowController implements Listener{
	@EventHandler
	public void trail(EntityShootBowEvent paramEntityShootBowEvent)
	{
	  LivingEntity localLivingEntity = paramEntityShootBowEvent.getEntity();
	  Entity localEntity = paramEntityShootBowEvent.getProjectile();
	  if ((localEntity instanceof Projectile))
	  {
	    final Projectile localProjectile = (Projectile)localEntity;
	    if (((localProjectile instanceof Arrow)) && ((localLivingEntity instanceof Player)))
	    {
	    	
	    	
	      Player localPlayer = (Player)localLivingEntity;
	      Arena a = new ArenaManager().getArena(localPlayer);
	      if(a==null){
	    	  return;
	      }
	 if(!retornar(localPlayer)){
		 return;
	 }
	          ParticleEffect localParticleEffect = effect(localPlayer);
	         final float f1 = 0.3F;
	         final float f2 = 0.3F;
	         final  float f3 = 0.3F;
	         final  float f4 = 0.3F;
	         final  int i = 20;
	         final double d = 20.0D;
	          
	         ArrowTrailSender(f1, f2, f3, f4, i, i, localParticleEffect, localProjectile, d);
	    }
	  }
	          
	    
	}
	public void ArrowTrailSender(final float paramFloat1, final float paramFloat2, final float paramFloat3, final float paramFloat4, final int paramInt1, final int paramInt2, final ParticleEffect paramParticleEffect, final Projectile paramProjectile, final double paramDouble)
	{
	  if ((!paramProjectile.isOnGround()) && (!paramProjectile.isDead())) {
	    Bukkit.getServer().getScheduler().scheduleAsyncDelayedTask(Skywars.pl, new BukkitRunnable()
	    {
	      public void run()
	      {
	        Location localLocation = paramProjectile.getLocation();
	        paramParticleEffect.display(paramFloat1, paramFloat2, paramFloat3, paramFloat4, paramInt1, localLocation, paramDouble);
	        ArrowController.this.ArrowTrailSender(paramFloat1, paramFloat2, paramFloat3, paramFloat4, paramInt1, paramInt2, paramParticleEffect, paramProjectile, paramDouble);
	      }
	    }, 1L);
	  }
	}
	
	public boolean retornar(Player p){
		
		for(Trails t : Trails.values()){
			if(t.trail(p)!=null){
				return true;
			}
		}
		
		return false;
	}
	
	public ParticleEffect effect(Player p){
			switch (Trails.data.get(p)) {
			case SLIME:
		    return	ParticleEffect.SLIME;

			case WATER:
		    return	ParticleEffect.WATER_DROP;

			case LAVA:
		    return	ParticleEffect.LAVA;
			case MAGIC:
			    return	ParticleEffect.ENCHANTMENT_TABLE;
			case VILLAGER:
			    return	ParticleEffect.VILLAGER_HAPPY;
			case NOTE:
			    return	ParticleEffect.NOTE;
			
		}
		return null;
		
	}
}

