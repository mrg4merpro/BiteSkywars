package es.spikybite.ProxyCode.storage;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import es.spikybite.ProxyCode.Skywars;

public class YMLStorage {
private File file;
private FileConfiguration config;
private Player p;

public YMLStorage(Player p){
	this.p = p;

     this.file = new File("plugins/Skywars/skyplayer_data/"+this.p.getUniqueId().toString() + ".yml");

      copyDefaults(file);
      this.config = new YamlConfiguration().loadConfiguration(this.file);
}



   
	
	public  void createDataPlayer(){
		
            	try{
            	
 	           if(!this.file.exists()){
 	        	   this.file.createNewFile();
 	           }
 	            
 	            this.config.set("name", this.p.getName());
 	          

 	            this.config.save(this.file);
            	}catch(Exception e){
            		e.printStackTrace();
            	}

   
	}	
	

	@SuppressWarnings("static-access")
	public  FileConfiguration get(){
	
		return this.config;
	}
	public void save(){
		try{
			this.config.save(this.file) ;
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	private static void copyDefaults(File playerFile) {
        FileConfiguration playerConfig = YamlConfiguration.loadConfiguration(playerFile);
		Reader defConfigStream = new InputStreamReader(Skywars.pl.getResource("player_file.yml"));
		if (defConfigStream != null) {
			YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(defConfigStream);
			playerConfig.options().copyDefaults(true);
			playerConfig.setDefaults(defConfig);
			try {
				playerConfig.save(playerFile);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
}
