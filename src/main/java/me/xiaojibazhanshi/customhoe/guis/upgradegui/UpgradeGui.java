package me.xiaojibazhanshi.customhoe.guis.upgradegui;

import dev.triumphteam.gui.builder.item.ItemBuilder;
import dev.triumphteam.gui.guis.Gui;
import me.xiaojibazhanshi.customhoe.common.CommonUtil;
import me.xiaojibazhanshi.customhoe.data.config.ConfigManager;
import me.xiaojibazhanshi.customhoe.data.playerdata.PlayerDataManager;
import me.xiaojibazhanshi.customhoe.upgrades.Level;
import me.xiaojibazhanshi.customhoe.upgrades.Upgrade;
import net.kyori.adventure.text.Component;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.*;

import static me.xiaojibazhanshi.customhoe.common.CommonUtil.color;
import static me.xiaojibazhanshi.customhoe.common.CommonUtil.makeItem;

public class UpgradeGui {

    private final ConfigManager configManager;
    private final PlayerDataManager playerDataManager;

    public UpgradeGui(ConfigManager configManager, PlayerDataManager playerDataManager) {
        this.playerDataManager = playerDataManager;
        this.configManager = configManager;
    }

    public void openGui(Player player, Upgrade upgrade) {
        int rows = configManager.getXUpgradeGuiRows();
        boolean hideFarLevels = configManager.isHideFarLevels();
        boolean fill = configManager.isFillGuis();
        Material fillerMaterial = configManager.getFillerMaterial();
        String extraValueName = getExtraValueName(upgrade);
        int currentLevel = playerDataManager.getPlayerUpgradeLevel(player, upgrade);

        Gui gui = Gui.gui()
                .rows(rows)
                .title(Component.text(upgrade.getColoredName()))
                .create();

        List<Level> levels = upgrade.getSortedLevels();
        Map<ItemStack, Level> itemLevelMap = new HashMap<>();

        for (Level level : levels) {
            String name = color("&a&lLevel &b&l" + level.level());
            int extraValue = getExtraValue(upgrade, level);
            boolean canBeBought = currentLevel < level.level()
                    && (level.level() - currentLevel < 2); // && !(vaultCheck) - to be added

            List<String> lore = new ArrayList<>(List.of("",
                    color("&7Chance to trigger&7: &b" + level.chanceToTrigger() + "&a%"),
                    color("&7Cost&7: " + "&b" + level.cost() + "&a$"),
                    extraValue >= 0 ? color("&7" + extraValueName + "&b" + extraValue) : "", "",
                    canBeBought ? color("&aYou can buy this upgrade.") : color("&cYou cannot buy this upgrade")
            ));

            ItemStack item = makeItem(name, Material.EXPERIENCE_BOTTLE, lore);
            item.setAmount(level.level());

            if (currentLevel == level.level()) {
                assert item.getItemMeta() != null;
                item.getItemMeta().setEnchantmentGlintOverride(true);
            }

            itemLevelMap.put(item, level);
        }

        Comparator<ItemStack> levelComparator = Comparator.comparingInt(ItemStack::getAmount);
        Map<ItemStack, Level> sortedMap = new TreeMap<>(levelComparator);

        sortedMap.putAll(itemLevelMap);

        for (ItemStack item : sortedMap.keySet()) {
            gui.addItem(ItemBuilder.from(item).asGuiItem(event -> {
                event.setCancelled(true);
                player.closeInventory();
                Level level = itemLevelMap.get(item);

                if (currentLevel >= level.level()) {
                    player.sendMessage(ChatColor.RED + "Your level of this upgrade is already the same or higher!");
                    player.playSound(player, Sound.ENTITY_VILLAGER_NO, 1.0F, 1.0F);
                    return;
                }

                assert item.getItemMeta() != null && item.getItemMeta().getLore() != null;
                boolean vaultCheck = item.getItemMeta().getLore().contains("You can buy this upgrade");

                if (vaultCheck) {
                    player.sendMessage(ChatColor.RED + "You don't have enough money to buy this upgrade!");
                    player.playSound(player, Sound.ENTITY_VILLAGER_NO, 1.0F, 1.0F);
                    return;
                }

                playerDataManager.setPlayerUpgradeLevel(player, upgrade, level.level());
                CommonUtil.updateHoe(player.getInventory(), player, playerDataManager.getPlayerData(player));
                player.sendMessage(color("&aSuccessfully bought the upgrade!"));
                player.playSound(player, Sound.ENTITY_VILLAGER_CELEBRATE, 1.0F, 1.0F);
            }));
        }

        if (hideFarLevels) {
            for (int i = currentLevel + 1; i < levels.size(); i++) {
                gui.setItem(i, ItemBuilder
                        .from(makeItem("&cYou can't view this level's details yet!", Material.BARRIER, null))
                        .asGuiItem(event -> event.setCancelled(true)));
            }
        }

        if (fill) {
            gui.getFiller().fill(ItemBuilder.from(makeItem(" ", fillerMaterial, null)).asGuiItem(event -> {
                event.setCancelled(true);
            }));
        }

        gui.open(player);
    }

    private String getExtraValueName(Upgrade upgrade) {
        switch (upgrade.getName().toLowerCase()) {
            case "speed" -> {
                return color("Effect strength&7: ");
            }
            case "looting" -> {
                return color("Crop amount&7: ");
            }
            case "meteor" -> {
                return color("Radius&7: ");
            }
            case "npc" -> {
                return color("Harvesting time&7: ");
            }
            default -> {
                return null;
            }
        }
    }

    private int getExtraValue(Upgrade upgrade, Level level) {
        switch (upgrade.getName().toLowerCase()) {
            case "speed" -> {
                return level.getExtraValue("potion-amplifier", Integer.class);
            }
            case "looting" -> {
                return level.getExtraValue("crop-multiplier", Integer.class);
            }
            case "meteor" -> {
                return level.getExtraValue("radius", Integer.class);
            }
            case "npc" -> {
                return level.getExtraValue("npc-lifetime-seconds", Integer.class);
            }
            default -> {
                return -1;
            }
        }
    }

}
