package es.spikybite.ProxyCode.controllers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import org.bukkit.block.Chest;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import com.google.common.collect.Lists;

import es.spikybite.ProxyCode.Skywars;
import es.spikybite.ProxyCode.arena.Arena;
import es.spikybite.ProxyCode.utils.ItemBuilder;
import es.spikybite.ProxyCode.utils.SettingsManager;

public class ChestController {

    private final List<ChestItem> chestItemList = Lists.newArrayList();
    private final List<ChestItem> chestBasic = Lists.newArrayList();
    private final List<ChestItem> chestOP = Lists.newArrayList();
    private final Random random = new Random();
    
    private List<Integer> randomLoc = new ArrayList<Integer>();
    private List<Integer> randomDLoc = new ArrayList<Integer>();
    private Skywars main;
    private static SettingsManager basic,normal,op;
    public ChestController(Skywars main) {
    	this.main = main;
    	basic = new SettingsManager(main, "chest_basic");
    	normal = new SettingsManager(main, "chest_normal");
    	op = new SettingsManager(main, "chest_op");
        load();
        for (int i = 0; i < 27; i++) {
        	randomLoc.add(i);
        }
        for (int i = 0; i < 54; i++) {
        	randomDLoc.add(i);
        }
    }

    public void load() {
        chestItemList.clear();
            if (normal.getConfig().contains("items")) {
                for (String item : normal.getConfig().getStringList("items")) { 
                	List<String> itemData = new LinkedList<String>(Arrays.asList(item.split(" ")));

                     int chance = Integer.parseInt(itemData.get(0));
                     itemData.remove(itemData.get(0));
                     
                     ItemStack itemStack = ItemBuilder.parseItem(itemData);
                     
                     
                     if (itemStack != null) {
                         chestItemList.add(new ChestItem(itemStack, chance));
                     }
                }
            
        }  
           chestOP.clear();
         if (op.getConfig().contains("items")) {
            for (String item : op.getConfig().getStringList("items")) {
            	List<String> itemData = new LinkedList<String>(Arrays.asList(item.split(" ")));

                int chance = Integer.parseInt(itemData.get(0));
                itemData.remove(itemData.get(0));
                
                ItemStack itemStack = ItemBuilder.parseItem(itemData);
                
                
                if (itemStack != null) {
                	chestOP.add(new ChestItem(itemStack, chance));
                }
            }
        
    }
         chestBasic.clear();
         if (basic.getConfig().contains("items")) {
            for (String item : basic.getConfig().getStringList("items")) {
            	List<String> itemData = new LinkedList<String>(Arrays.asList(item.split(" ")));

                int chance = Integer.parseInt(itemData.get(0));
                itemData.remove(itemData.get(0));
                
                ItemStack itemStack = ItemBuilder.parseItem(itemData);
                
                
                if (itemStack != null) {
                    chestBasic.add(new ChestItem(itemStack, chance));
                }
            }
        
    }
    }

    public void populateChest(Arena a , Chest chest) {
    	if(a.getChest().equalsIgnoreCase("Overpowered")){
    		 Inventory inventory = chest.getBlockInventory();
     		inventory.clear();
             int added = 0;
             Collections.shuffle(randomLoc);

             for (ChestItem chestItem : chestOP) {
                 if (random.nextInt(100) + 1 <= chestItem.getChance()) {
                     inventory.setItem(randomLoc.get(added), chestItem.getItem());
                     if (added++ >= inventory.getSize()-3) {
                         break;
                     }
                 }
             }
    	}else 	if(a.getChest().equalsIgnoreCase("Basic")){
    		 Inventory inventory = chest.getBlockInventory();
     		inventory.clear();
             int added = 0;
             Collections.shuffle(randomLoc);

             for (ChestItem chestItem : chestBasic) {
                 if (random.nextInt(100) + 1 <= chestItem.getChance()) {
                     inventory.setItem(randomLoc.get(added), chestItem.getItem());
                     if (added++ >= inventory.getSize()-3) {
                         break;
                     }
                 }
             }
	}else {
		 Inventory inventory = chest.getBlockInventory();
 		inventory.clear();
         int added = 0;
         Collections.shuffle(randomLoc);

         for (ChestItem chestItem : chestItemList) {
             if (random.nextInt(100) + 1 <= chestItem.getChance()) {
                 inventory.setItem(randomLoc.get(added), chestItem.getItem());
                 if (added++ >= inventory.getSize()-3) {
                     break;
                 }
             }
         }
  	
	}
      
    }

    public class ChestItem {

        private ItemStack item;
        private int chance;

        public ChestItem(ItemStack item, int chance) {
            this.item = item;
            this.chance = chance;
        }

        public ItemStack getItem() {
            return item;
        }

        public int getChance() {
            return chance;
        }
    }

}
