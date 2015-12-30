package es.spikybite.ProxyCode.utils;

public enum Server {
BUNGEE, MULTIARENA;
private static Server server;
public static void  setServer(Server sv){
server = sv;
}
public static Server getServer(){
	return server;
}
}
