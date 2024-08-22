package me.xiaojibazhanshi.customhoe.common;

import me.xiaojibazhanshi.customhoe.data.playerdata.PlayerData;
import me.xiaojibazhanshi.customhoe.upgrades.Upgrade;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.Ageable;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
        ItemStack hoe = new ItemStack(Material.DIAMOND_HOE);
        ItemMeta hoeMeta = hoe.getItemMeta();

        assert hoeMeta != null;
        hoeMeta.setDisplayName(color("&4" + player.getName() + "&c's farming tool"));
        hoeMeta.setLore(new ArrayList<>());
        hoeMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        hoeMeta.setEnchantmentGlintOverride(true);

        hoe.setItemMeta(hoeMeta);

        return null;
    }

    public static ItemStack applyPlayerData(ItemStack customHoe, PlayerData playerData) {
        if (!isCustomHoe(customHoe)) return customHoe;

        ItemMeta customHoeMeta = customHoe.getItemMeta();
        assert customHoeMeta != null;

        Map<Upgrade, Integer> copy = new HashMap<>(playerData.upgradeLevels());
        Map<String, Integer> upgradeLevels = new HashMap<>();
        List<String> lore = new ArrayList<>();

        for (Upgrade upgrade : copy.keySet()) {
            upgradeLevels.put(upgrade.getColoredName(), copy.get(upgrade));
        }

        lore.add("");
        lore.add(color("&7Upgrades: (&a5&7)"));

        for (String upgrade : upgradeLevels.keySet()) {
            lore.add(color("&a" + upgrade + " &7(&aLevel &b" + upgradeLevels.get(upgrade) + "&7)"));
        }

        customHoeMeta.setLore(lore);
        customHoe.setItemMeta(customHoeMeta);

        return customHoe;
    }

    public static boolean isCustomHoe(ItemStack item) {
        return item.getType().equals(Material.DIAMOND_HOE)
                && item.getItemMeta() != null
                && item.getItemMeta().hasEnchantmentGlintOverride()
                && item.getItemMeta().hasItemFlag(ItemFlag.HIDE_ATTRIBUTES);
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
        ageable.setAge(age);
    }

    public static void replantCropsInRadiusAround(Block start, int radius) {
        if (radius < 0) return;

        int startY = start.getY();

        for (int x = -radius; x <= radius; x++) {
            for (int z = -radius; z <= radius; z++) {
                Block targetBlock = start.getRelative(x, startY, z);
                replantCrop(targetBlock, targetBlock.getType(), 1);
            }
        }
    }

}
