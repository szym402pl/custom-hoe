package me.xiaojibazhanshi.customhoe.utils;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import java.util.concurrent.ThreadLocalRandom;

public class CommonUtil {

    public static String color(String text) {
        return ChatColor.translateAlternateColorCodes('&', text);
    }

    public static boolean isLuckOnYourSide(double chance) {
        chance = chance > 100.0 ? 100 : (chance < 0 ? 0 : chance);

        double randomValue = ThreadLocalRandom.current().nextDouble(0, 100 + 1);
        return randomValue <= chance;
    }

    /** Null checks a config object in a specified path */
    public static <T> T nullCheckCI(T obj, String path) { // null check a config item
        if (obj == null) Bukkit.getLogger().severe("Necessary config item in path: " + path + " doesn't exist!");

        return obj;
    }

}
