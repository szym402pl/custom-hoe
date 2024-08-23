package me.xiaojibazhanshi.customhoe.guis.maingui;

import dev.triumphteam.gui.builder.item.ItemBuilder;
import dev.triumphteam.gui.guis.Gui;
import dev.triumphteam.gui.guis.GuiItem;
import me.xiaojibazhanshi.customhoe.common.CommonUtil;
import me.xiaojibazhanshi.customhoe.data.config.ConfigManager;
import me.xiaojibazhanshi.customhoe.data.playerdata.PlayerData;
import me.xiaojibazhanshi.customhoe.data.playerdata.PlayerDataManager;
import me.xiaojibazhanshi.customhoe.guis.upgradegui.UpgradeGui;
import me.xiaojibazhanshi.customhoe.upgrades.Upgrade;
import me.xiaojibazhanshi.customhoe.upgrades.UpgradeManager;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static me.xiaojibazhanshi.customhoe.common.CommonUtil.color;
import static me.xiaojibazhanshi.customhoe.common.CommonUtil.makeItem;

public class MainGui {

    private final ConfigManager configManager;
    private final UpgradeManager upgradeManager;
    private final PlayerDataManager playerDataManager;

    public MainGui(ConfigManager configManager, UpgradeManager upgradeManager, PlayerDataManager playerDataManager) {
        this.playerDataManager = playerDataManager;
        this.upgradeManager = upgradeManager;
        this.configManager = configManager;
    }

    public void openGui(Player player) {
        String guiName = configManager.getMainGuiName();
        int rows = configManager.getMainGuiRows();
        boolean fill = configManager.isFillGuis();
        Material fillerMaterial = configManager.getFillerMaterial();

        Gui gui = Gui.gui()
                .rows(rows)
                .title(Component.text(color(guiName)))
                .create();

        gui.setItem(2,3, ItemBuilder.from(createUpgradeItemStack(
                upgradeManager.getAutoReplantUpgrade(),
                Material.WHEAT_SEEDS,
                playerDataManager.getPlayerUpgradeLevel(player, upgradeManager.getAutoReplantUpgrade()))
        ).asGuiItem(event -> {
            event.setCancelled(true);
            player.closeInventory();

            //UpgradeGui upgradeGui = new UpgradeGui();
            //upgradeGui.openGui(player);
        }));

        gui.setItem(2,4, ItemBuilder.from(createUpgradeItemStack(
                upgradeManager.getLootingUpgrade(),
                Material.DIAMOND,
                playerDataManager.getPlayerUpgradeLevel(player, upgradeManager.getLootingUpgrade()))
        ).asGuiItem(event -> {
            event.setCancelled(true);
            player.closeInventory();

            //UpgradeGui upgradeGui = new UpgradeGui();
            //upgradeGui.openGui(player);
        }));

        gui.setItem(2,5, ItemBuilder.from(createUpgradeItemStack(
                upgradeManager.getMeteorUpgrade(),
                Material.NETHERITE_INGOT,
                playerDataManager.getPlayerUpgradeLevel(player, upgradeManager.getMeteorUpgrade()))
        ).asGuiItem(event -> {
            event.setCancelled(true);
            player.closeInventory();

            //UpgradeGui upgradeGui = new UpgradeGui();
            //upgradeGui.openGui(player);
        }));

        gui.setItem(2,6, ItemBuilder.from(createUpgradeItemStack(
                upgradeManager.getNpcUpgrade(),
                Material.VILLAGER_SPAWN_EGG,
                playerDataManager.getPlayerUpgradeLevel(player, upgradeManager.getNpcUpgrade()))
        ).asGuiItem(event -> {
            event.setCancelled(true);
            player.closeInventory();

            //UpgradeGui upgradeGui = new UpgradeGui();
            //upgradeGui.openGui(player);
        }));

        gui.setItem(2,7, ItemBuilder.from(createUpgradeItemStack(
                upgradeManager.getSpeedUpgrade(),
                Material.IRON_BOOTS,
                playerDataManager.getPlayerUpgradeLevel(player, upgradeManager.getSpeedUpgrade()))
        ).asGuiItem(event -> {
            event.setCancelled(true);
            player.closeInventory();

            //UpgradeGui upgradeGui = new UpgradeGui();
            //upgradeGui.openGui(player);
        }));

        if (fill) {
            gui.getFiller().fill(ItemBuilder.from(makeItem(" ", fillerMaterial, null)).asGuiItem(event -> {
                event.setCancelled(true);
            }));
        }

        gui.open(player);
    }

    private ItemStack createUpgradeItemStack(Upgrade upgrade, Material material, int level) {
        String name = upgrade.getColoredName();
        List<String> lore = new ArrayList<>(upgrade.getDescription());
        lore.add("");
        lore.add(color("&aCurrent level: &b" + level));

        return makeItem(name, material, lore);
    }



}
