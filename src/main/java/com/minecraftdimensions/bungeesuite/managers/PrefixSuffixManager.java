package com.minecraftdimensions.bungeesuite.managers;

import java.util.HashMap;
import java.util.List;

import com.minecraftdimensions.bungeesuite.configlibrary.Config;
import com.minecraftdimensions.bungeesuite.configs.ChatConfig;

public class PrefixSuffixManager {
	public static boolean usingPrefix = ChatConfig.usingPrefix;
	public static boolean usingSuffix = ChatConfig.usingSuffix;
	public static HashMap<String, String> prefixes;
	public static HashMap<String, String> suffixes;
	
	public static void loadPrefixes() {
		Config chat = ChatConfig.c;
		prefixes = new HashMap<String, String>();
		chat.getString("Prefix.Groups.Default", "&5[Member]");
		List<String> grouplist = chat.getSubNodes("Prefix.Groups");
		for (String data : grouplist) {
			prefixes.put(data, chat.getString("Prefix.Groups." + data, null));
		}
	}

	public static void loadSuffixes() {
		Config chat = ChatConfig.c;
		suffixes = new HashMap<String, String>();
		chat.getString("Suffix.Groups.Default", "&4");
		List<String> grouplist = chat.getSubNodes("Suffix.Groups");
		for (String data : grouplist) {
			suffixes.put(data, chat.getString("Suffix.Groups." + data, null));
		}
	}
	
	public static boolean groupHasPrefix(String group){
		return prefixes.containsKey(group);
	}
	
	public static String getGroupPrefix(String group){
		return prefixes.get(group);
	}
	
	public static boolean groupHasSuffix(String group){
		return suffixes.containsKey(group);
	}
	
	public static String getGroupSuffix(String group){
		return suffixes.get(group);
	}
	
	
}
