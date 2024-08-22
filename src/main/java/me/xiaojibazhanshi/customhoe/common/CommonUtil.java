package me.xiaojibazhanshi.customhoe.common;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.Ageable;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

public class CommonUtil {

    public static String color(String text) {
        return ChatColor.translateAlternateColorCodes('&', text);
    }

    public static boolean isLuckNotOnYourSide(double chance) {
        chance = chance > 100.0 ? 100 : (chance < 0 ? 0 : chance);

        double randomValue = ThreadLocalRandom.current().nextDouble(0, 100 + 1);
        return !(randomValue <= chance);
    }

    public static ItemStack getBaseHoe(Player player) {
        return null;
    }

    public static boolean isFullyGrown(Block block) {
        Ageable ageable = (Ageable) block.getBlockData();

        return ageable.getAge() >= 7;
    }

    public static void replantCrop(Block block, Material material, int age) {
        Material replantMaterial;
        switch (material) {
            case WHEAT:
                replantMaterial = Material.WHEAT;
                break;
            case CARROTS:
                replantMaterial = Material.CARROTS;
                break;
            case POTATOES:
                replantMaterial = Material.POTATOES;
                break;
            case BEETROOTS:
                replantMaterial = Material.BEETROOTS;
                break;
            default:
                return;
        }

        block.setType(replantMaterial);
        Ageable ageable = (Ageable) block.getBlockData();
        ageable.setAge(1);
    }

    public static boolean isCustomHoe(ItemStack item) {
        return true;
    }



}
