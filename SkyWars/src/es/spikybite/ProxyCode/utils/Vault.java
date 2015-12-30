package es.spikybite.ProxyCode.utils;

import java.text.DecimalFormat;

import net.milkbowl.vault.chat.Chat;
import net.milkbowl.vault.economy.Economy;

import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;

import es.spikybite.ProxyCode.Skywars;

public class Vault {
public static double getMoney(Player p){
return Skywars.econ.getBalance(p.getName());
}
public static String getStringMoney(Player p){
	DecimalFormat newformat = new DecimalFormat("################");
return newformat.format(Skywars.econ.getBalance(p.getName()));
}

public static void setMoney(Player p, double money){
	if(p.hasPermission("skywars.money.x2")){
		money = money*2;
	}else if (p.hasPermission("skywars.money.x3")){
	money = money*3;	
	}
Skywars.econ.depositPlayer(p.getName(), money);
}
public static void removeMoney(Player p, double money){
Skywars.econ.withdrawPlayer(p.getName(), money);
}
public static int getPointRewardKill(){
	return Skywars.lang.getint("game.kill_reward_points");
}
public static int getPointRewardWin(){
	return Skywars.lang.getint("game.win_reward_points");
}
public static boolean setupEconomy()
{
  if (Skywars.pl.getServer().getPluginManager().getPlugin("Vault") == null) {
    return false;
  }
  RegisteredServiceProvider<Economy> rsp = Skywars.pl.getServer().getServicesManager().getRegistration(Economy.class);
  if (rsp == null) {
    return false;
  }
  Skywars.econ = ((Economy)rsp.getProvider());
  return Skywars.econ != null;
}

public static boolean setupChat()
{
    RegisteredServiceProvider<Chat> chatProvider = Skywars.pl.getServer().getServicesManager().getRegistration(net.milkbowl.vault.chat.Chat.class);
    if (chatProvider != null) {
      Skywars.chat = chatProvider.getProvider();
    }

    return (Skywars.chat != null);
}
}