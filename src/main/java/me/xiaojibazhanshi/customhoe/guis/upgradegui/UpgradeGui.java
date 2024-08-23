package me.xiaojibazhanshi.customhoe.guis.upgradegui;

import dev.triumphteam.gui.builder.item.ItemBuilder;
import dev.triumphteam.gui.guis.Gui;
import me.xiaojibazhanshi.customhoe.data.config.ConfigManager;
import me.xiaojibazhanshi.customhoe.data.playerdata.PlayerDataManager;
import me.xiaojibazhanshi.customhoe.upgrades.Level;
import me.xiaojibazhanshi.customhoe.upgrades.Upgrade;
import me.xiaojibazhanshi.customhoe.upgrades.UpgradeManager;
import me.xiaojibazhanshi.customhoe.upgrades.upgrades.AutoReplantUpgrade;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static me.xiaojibazhanshi.customhoe.common.CommonUtil.color;
import static me.xiaojibazhanshi.customhoe.common.CommonUtil.makeItem;

public class UpgradeGui {

    private final ConfigManager configManager;
    private final UpgradeManager upgradeManager;
    private final PlayerDataManager playerDataManager;

    public UpgradeGui(ConfigManager configManager, UpgradeManager upgradeManager, PlayerDataManager playerDataManager) {
        this.playerDataManager = playerDataManager;
        this.upgradeManager = upgradeManager;
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

        List<Level> levels = upgrade.getLevels();
        Map<ItemStack, Level> itemLevelMap = new HashMap<>();

        for (Level level : levels) {
            String name = color("");

            List<String> lore = new ArrayList<>(List.of(
                    color("a"),
                    color("b"),
                    color("c")
            ));


        }

        if (hideFarLevels) {
            for (int i = currentLevel + 2; i < levels.size() -1; i++) {
                gui.setItem(i, ItemBuilder
                        .from(makeItem("&cYou can't view this upgrade yet!", Material.BARRIER, null))
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
        switch(upgrade.getName().toLowerCase()) {
            case "speed" -> {
                return "Effect strength: ";
            }
            case "looting" -> {
                return "Crop amount: ";
            }
            case "meteor" -> {
                return "Radius: ";
            }
            case "npc" -> {
                return "Harvesting time: ";
            }
            default -> {
                return null;
            }
        }
    }

}
