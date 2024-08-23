package me.xiaojibazhanshi.customhoe.common;

import me.xiaojibazhanshi.customhoe.data.playerdata.PlayerData;
import me.xiaojibazhanshi.customhoe.upgrades.Upgrade;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.Ageable;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import javax.annotation.Nullable;
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

        return hoe;
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

        if (upgradeLevels.isEmpty()) {
            lore.add(color("&7&lUpgrades: &c&lnone"));
        } else {
            lore.add(color("&7&lUpgrades: (&a&l" + upgradeLevels.size() + "&7&l)"));
            lore.add("");

            for (String upgrade : upgradeLevels.keySet()) {
                lore.add(color("&a&l" + upgrade + " &8-> &7Level &b" + upgradeLevels.get(upgrade)));
            }
        }

        customHoeMeta.setLore(lore);
        customHoe.setItemMeta(customHoeMeta);

        return customHoe;
    }

    public static boolean isCustomHoe(ItemStack item) {
        return item != null
                && item.getType().equals(Material.DIAMOND_HOE)
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

    public static boolean containsHoe(Inventory inventory) {
        if (inventory.isEmpty()) return false;

        for (ItemStack item : inventory.getContents()) {
            if (item == null || item.getType() == Material.AIR) continue;
            if (CommonUtil.isCustomHoe(item)) return true;
        }

        return false;
    }

    public static void updateHoe(Inventory inventory, Player player, PlayerData playerData) {
        if (inventory.isEmpty()) return;

        for (ItemStack item : inventory.getContents()) {
            if (item == null || item.getType() == Material.AIR) continue;

            if (CommonUtil.isCustomHoe(item)) {
                inventory.remove(item);
                inventory.addItem(applyPlayerData(getBaseHoe(player), playerData));
            }
        }
    }

    public static ItemStack makeItem(String name, Material material, @Nullable List<String> lore) {
        ItemStack item = new ItemStack(material);
        ItemMeta itemMeta = item.getItemMeta();
        assert itemMeta != null;

        itemMeta.setDisplayName(color(name));
        List<String> coloredLore = new ArrayList<>();

        if (lore != null)
            lore.forEach(line -> coloredLore.add(color(line)));

        itemMeta.setLore(coloredLore);
        item.setItemMeta(itemMeta);

        return item;
    }

}
