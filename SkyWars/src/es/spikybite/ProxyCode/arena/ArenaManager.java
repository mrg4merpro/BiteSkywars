package es.spikybite.ProxyCode.arena;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Difficulty;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import es.spikybite.ProxyCode.Skywars;

public class ArenaManager {
public static ArrayList<String> arenas = new ArrayList<String>();
public static ArrayList<Arena> a = new ArrayList<Arena>();
public static ArrayList<Arena> getAllArenas(){
	return a;
}
	public static String setLoc(Location loc)
	{
	
		return loc.getWorld().getName()+","+loc.getBlockX()+","+loc.getBlockY()+","+loc.getBlockZ();
	}

	public void RemoveMonster(World world){
		for(Entity e : world.getEntities()){
			if(e instanceof Monster)
			{
				e.remove();
			}
		}
	}
	
	public static Location getNewLoc(String path){
			String[] locs = path.split(",");

		return new Location(Bukkit.getWorld(locs[0]), Integer.parseInt(locs[1]), Integer.parseInt(locs[2]), Integer.parseInt(locs[3]));
    
	}

	public static Location getLoc(String path){
		Location loc = null;
		String[] locs = path.split(",");

		loc = new Location(Bukkit.getWorld(locs[0]), Integer.parseInt(locs[1]), Integer.parseInt(locs[2]), Integer.parseInt(locs[3]));
        loc.add(0.5D,0.0,0.5D);
		return loc;
	}
	public void createArenas(){
		a.clear();
		
		File f= new File("plugins/Skywars/arenas");
		File carpeta = new File("plugins/Skywars/mapas");
		if(!carpeta.exists()){
			carpeta.mkdirs();
		}
        if(!f.exists()){
        	f.mkdir();
        }
       
         
		Skywars.log("Loading...!");
		for(File file : f.listFiles()){
	         
			if(file.isFile() && !file.isDirectory()){
			Skywars.log("File found load arena..");
		String name = file.getName().replaceAll(".yml", "");
            File s = new File("plugins/Skywars/arenas/"+file.getName());
            YamlConfiguration config = new YamlConfiguration().loadConfiguration(s);
	String s11 = config.getString("aName");
         	Skywars.log("Name: " + name);
			
				File source = new File("plugins/Skywars/mapas/"+file.getName().replaceAll(".yml", ""));
				Skywars.log("Map " +file.getName());
				if(!source.exists()){
					Skywars.log("File arenas not found");
					return;
				}
				try {
					delete(new File(file.getName().replaceAll(".yml", "")));
					copyDir(source, new File(file.getName().replaceAll(".yml", "")));
					  WorldCreator c = new WorldCreator(name);
					  c.generateStructures(false);
					  World localWorld = c.createWorld();
					  localWorld.setAutoSave(false);
					  localWorld.setKeepSpawnInMemory(false);
					  localWorld.setGameRuleValue("doMobSpawning", "false");
					  localWorld.setGameRuleValue("doDaylightCycle", "false");
					  localWorld.setGameRuleValue("mobGriefing", "false");
					  localWorld.setTime(0L);
					  Skywars.log("Imported succesful!");
				} catch (IOException e) {
					Skywars.log("Failed imported");
					e.printStackTrace();
				} 
		
			Arena a = new Arena(name, s11, config.getInt("aMin"), config.getInt("aMax"));
		this.a.add(a);
		this.arenas.add(name);
			Skywars.log("Arenas: "+a);
			}
		}
	}
	
public Arena getArena(String name){
	for(Arena as : a)
	{
		if(as.arena().equals(name)){
			return as;
		}
	}
	return null;
}
	
public boolean exist(String name){
File file = new File("plugins/Skywars/arenas/"+name+".yml");	
return file.exists();
}

public Arena getArena(Player p){
	for(Arena alls : a){
		if(alls.p().contains(p)){
			return alls;
		}
	}
return null;
}

public void add(World world, Location loc){
File file = new File("plugins/Skywars/arenas/"+world.getName()+".yml");
YamlConfiguration config = new YamlConfiguration().loadConfiguration(file);
List<String> l = config.getStringList("spawns");
l.add(setLoc(loc));
config.set("spawns", l);
save(config, file);
}
public void remove(World world){
		File file = new File("plugins/Skywars/arenas/"+world.getName()+".yml");
		YamlConfiguration config = new YamlConfiguration().loadConfiguration(file);
		List<String> l = config.getStringList("spawns");
		if(l.isEmpty()){
			return;
		}
		l.remove(l.get(l.size()-1));
		config.set("spawns", l);
		save(config, file);
		
}


public static void save(YamlConfiguration config, File file){
	   try {
		config.save(file);
	} catch (Exception e) {
		e.printStackTrace();
	}
}

	
public static void delete(File paramFile)
{
  if (paramFile.isDirectory())
  {
    if (paramFile.list().length == 0)
    {
      paramFile.delete();
    }
    else
    {
      String[] arrayOfString1 = paramFile.list();
      for (String str : arrayOfString1)
      {
        File localFile = new File(paramFile, str);
        

        delete(localFile);
      }
      if (paramFile.list().length == 0)
      {
        paramFile.delete();
      }
    }
  }
  else
  {
    paramFile.delete();
  }
}
	  
public void importworld(String name){
	File localFile1 = new File("plugins/Skywars/mapas");
    for (File localFile2 : localFile1.listFiles()) {
      if ((localFile2.getName().equals(name)) && 
        (localFile2.isDirectory())) {
        try
        {
          delete(new File(localFile2.getName()));
          copyDir(localFile2, new File(localFile2.getName()));
        }
        catch (Exception localException)
        {
          getArena(name).error(true);
          getArena(name).esperando(false);
          getArena(name).comienza(false);
          getArena(name).juego(false);
          getArena(name).end(false);
          localException.printStackTrace();
        }
      }
    }
}
	  
	  public void copyDir(File source, File target)
	    throws IOException
	  {
	    if (source.isDirectory())
	    {
	      if (!target.exists()) {
	        target.mkdir();
	      }
	      String[] files = source.list();
	      for (String file : files)
	      {
	        File srcFile = new File(source, file);
	        File destFile = new File(target, file);
	        copyDir(srcFile, destFile);
	      }
	    }
	    else
	    {
	      InputStream in = new FileInputStream(source);
	      OutputStream out = new FileOutputStream(target);
	      byte[] buffer = new byte[1024];
	      int length;
	      while ((length = in.read(buffer)) > 0)
	      {
	        out.write(buffer, 0, length);
	      }
	      in.close();
	      out.close();
	    }
	  }
	  
	  public static void setGlass(Location p, byte dataByte){
		   ItemStack i = new ItemStack(Material.STAINED_GLASS);
		   if(dataByte == 333){
			   i = new ItemStack(Material.GLASS);
			   dataByte = 0;
		   }
		      int a = p.getBlockX();
		      int b = p.getBlockY();
		      int c = p.getBlockZ();
		  p.getWorld().getBlockAt(a, b - 1, c).setTypeIdAndData(i.getTypeId(), dataByte, true);
	      p.getWorld().getBlockAt(a, b, c + 1).setTypeIdAndData(i.getTypeId(), dataByte, true);
	      p.getWorld().getBlockAt(a, b, c - 1).setTypeIdAndData(i.getTypeId(), dataByte, true);
	      p.getWorld().getBlockAt(a, b + 1, c + 1).setTypeIdAndData(i.getTypeId(), dataByte, true);
	      p.getWorld().getBlockAt(a, b + 2, c + 1).setTypeIdAndData(i.getTypeId(), dataByte, true);
	      p.getWorld().getBlockAt(a, b + 2, c + 1).setTypeIdAndData(i.getTypeId(), dataByte, true);
	      p.getWorld().getBlockAt(a, b + 1, c - 1).setTypeIdAndData(i.getTypeId(), dataByte, true);
	      p.getWorld().getBlockAt(a, b + 2, c - 1).setTypeIdAndData(i.getTypeId(), dataByte, true);
	      p.getWorld().getBlockAt(a, b + 2, c - 1).setTypeIdAndData(i.getTypeId(), dataByte, true);
	      p.getWorld().getBlockAt(a - 1, b, c).setTypeIdAndData(i.getTypeId(), dataByte, true);
	      p.getWorld().getBlockAt(a - 1, b + 1, c).setTypeIdAndData(i.getTypeId(), dataByte, true);
	      p.getWorld().getBlockAt(a - 1, b + 2, c).setTypeIdAndData(i.getTypeId(), dataByte, true);
	      p.getWorld().getBlockAt(a - 1, b + 2, c).setTypeIdAndData(i.getTypeId(), dataByte, true);
	      p.getWorld().getBlockAt(a + 1, b, c).setTypeIdAndData(i.getTypeId(), dataByte, true);
	      p.getWorld().getBlockAt(a + 1, b + 1, c).setTypeIdAndData(i.getTypeId(), dataByte, true);
	      p.getWorld().getBlockAt(a + 1, b + 2, c).setTypeIdAndData(i.getTypeId(), dataByte, true);
	      p.getWorld().getBlockAt(a + 1, b + 2, c).setTypeIdAndData(i.getTypeId(), dataByte, true);
	  }
	
	  public static void setGlass(Location p){
          ItemStack i = new ItemStack(Material.GLASS);
	      int a = p.getBlockX();
	      int b = p.getBlockY();
	      int c = p.getBlockZ();
	      p.getWorld().getBlockAt(a, b - 1, c).setType(i.getType());
	      p.getWorld().getBlockAt(a, b, c + 1).setType(i.getType());
	      p.getWorld().getBlockAt(a, b, c - 1).setType(i.getType());
	      p.getWorld().getBlockAt(a, b + 1, c + 1).setType(i.getType());
	      p.getWorld().getBlockAt(a, b + 2, c + 1).setType(i.getType());
	      p.getWorld().getBlockAt(a, b + 2, c + 1).setType(i.getType());
	      p.getWorld().getBlockAt(a, b + 1, c - 1).setType(i.getType());
	      p.getWorld().getBlockAt(a, b + 2, c - 1).setType(i.getType());
	      p.getWorld().getBlockAt(a, b + 2, c - 1).setType(i.getType());
	      p.getWorld().getBlockAt(a - 1, b, c).setType(i.getType());
	      p.getWorld().getBlockAt(a - 1, b + 1, c).setType(i.getType());
	      p.getWorld().getBlockAt(a - 1, b + 2, c).setType(i.getType());
	      p.getWorld().getBlockAt(a - 1, b + 2, c).setType(i.getType());
	      p.getWorld().getBlockAt(a + 1, b, c).setType(i.getType());
	      p.getWorld().getBlockAt(a + 1, b + 1, c).setType(i.getType());
	      p.getWorld().getBlockAt(a + 1, b + 2, c).setType(i.getType());
	      p.getWorld().getBlockAt(a + 1, b + 2, c).setType(i.getType());
}
	
}
