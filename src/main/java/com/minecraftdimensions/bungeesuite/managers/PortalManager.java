	package com.minecraftdimensions.bungeesuite.managers;

	import java.sql.ResultSet;
	import java.sql.SQLException;
	import java.util.HashMap;
	import com.minecraftdimensions.bungeesuite.objects.Location;
	import com.minecraftdimensions.bungeesuite.objects.Messages;
import com.minecraftdimensions.bungeesuite.objects.Portal;
import com.minecraftdimensions.bungeesuite.objects.Region;
import com.minecraftdimensions.bungeesuite.objects.BSPlayer;

	public class PortalManager {
			static HashMap<String,Portal> portals;
		
		public static void loadPortals() throws SQLException{
			portals = new HashMap<String,Portal>();
			ResultSet res = SQLManager.sqlQuery("SELECT * FROM BungeePortals");
			while (res.next()){
				boolean target = false;
				String dest =res.getString("toServer");
				if(dest==null){
					target = true;
					 dest = res.getString("toWarp");
				}
				String server = res.getString("server");
				String world = res.getString("world");
//				createPortal(res.getString("portalname"), server,res.getString("filltype"),target,dest,new Region(new Location(server,world,res.getDouble("minx"),res.getDouble("miny"), res.getDouble("minz")), new Location(server,world,res.getDouble("xmax"),res.getDouble("ymax"),res.getDouble("zmax"))));
			}
			res.close();
		}
		
		public static void createPortal(String name, String server, String fillType, boolean target, String targetName, Region region){
			Portal p = new Portal(name, server, fillType, target, targetName, region);
			portals.put(name, p);
		}
		
		public static void setPortal(BSPlayer sender, String name,String fillType, boolean target, String targetName, Region region) throws SQLException{
			Portal p;
			if(doesPortalExist(name)){
				p = getPortal(name);
				p.setRegion(region);
				if(target){
					SQLManager.standardQuery("UPDATE BungeePortals SET server='" + region.getMax().getServer()
							+ "', world='" + region.getMax().getWorld() + "', filltype = '"+fillType+"', toPortal = NULL, toWarp ='"+targetName+"', xmax=" + region.getMax().getX() + ", ymax=" + region.getMax().getY() + ", zmax=" + region.getMax().getZ() + ", xmin = " + region.getMin().getX()+ ", ymin = "+ region.getMin().getY()+", zmin ="+region.getMin().getZ()
							+ " WHERE portalname='" + name + "'");
				}else{
				SQLManager.standardQuery("UPDATE BungeePortals SET server='" + region.getMax().getServer()
						+ "', world='" + region.getMax().getWorld() + "', filltype = '"+fillType+"', toPortal = '"+targetName+"', toWarp =NULL, xmax=" + region.getMax().getX() + ", ymax=" + region.getMax().getY() + ", zmax=" + region.getMax().getZ() + ", xmin = " + region.getMin().getX()+ ", ymin = "+ region.getMin().getY()+", zmin ="+region.getMin().getZ()
						+ " WHERE portalname='" + name + "'");
				}
				sender.sendMessage(Messages.PORTAL_UPDATED);
			}else{
				p = new Portal(name, region.getMax().getServer().getName(), fillType, target, targetName, region);
				portals.put(name, p);
				SQLManager.standardQuery("INSERT INTO BungeePortals VALUES('"+p.getName()+"','" + p.getServer()
						+ "', '"+targetName+"',NULL, '"+p.getWorld()+"', '"+p.getFillType()+"', "+ region.getMax().getX() + ", " + region.getMin().getX() + "," +region.getMax().getY()+","+region.getMin().getY()+","+region.getMax().getZ()+", " + region.getMin().getZ()+")");
				sender.sendMessage(Messages.PORTAL_CREATED);
			}
		}
		
		public static void deletePortal(String portal, BSPlayer sender) throws SQLException{
			portals.remove(portal);
			SQLManager.standardQuery("DELETE FROM BungeePortals WHERE portalname='"+portal+"'");
			sender.sendMessage(Messages.PORTAL_DELETED);
		}
		
		public static Portal getPortal(String name){
			return portals.get(name);
		}
		
		public static Portal getSimilarPortal(String name){
			if(portals.containsKey(name)){
				return portals.get(name);
			}
			for(String portal: portals.keySet()){
				if(portal.toLowerCase().equals(name)){
					return portals.get(portal);
				}
			}
			return null;
		}
		
		public static boolean doesPortalExist(String name){
			return portals.containsKey(name);
		}

		public static HashMap<Boolean,String> getPortalsList(){
			HashMap<Boolean,String> list = new HashMap<Boolean,String>();
			for(Portal p: portals.values()){
					list.put(true, p.getName());
			}
			return list;
		}

		
		public static void sendPlayerToPortalDestination(String player, Portal portal){
			BSPlayer p = PlayerManager.getPlayer(player);
			portal.sendPlayerToDestination(p);
		}
}
