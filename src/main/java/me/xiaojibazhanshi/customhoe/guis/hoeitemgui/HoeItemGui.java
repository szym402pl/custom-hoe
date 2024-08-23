package me.xiaojibazhanshi.customhoe.guis.hoeitemgui;

import dev.triumphteam.gui.builder.item.ItemBuilder;
import dev.triumphteam.gui.guis.Gui;
import me.xiaojibazhanshi.customhoe.common.CommonUtil;
import me.xiaojibazhanshi.customhoe.data.config.ConfigManager;
import me.xiaojibazhanshi.customhoe.data.playerdata.PlayerData;
import me.xiaojibazhanshi.customhoe.data.playerdata.PlayerDataManager;
import net.kyori.adventure.text.Component;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.List;

import static me.xiaojibazhanshi.customhoe.common.CommonUtil.color;
import static me.xiaojibazhanshi.customhoe.common.CommonUtil.containsHoe;

public class HoeItemGui {

    private final PlayerDataManager playerDataManager;
    private final ConfigManager configManager;

    public HoeItemGui(PlayerDataManager playerDataManager, ConfigManager configManager) {
        this.configManager = configManager;
        this.playerDataManager = playerDataManager;
    }

    public void openGui(Player player) {
        Gui gui = Gui.gui()
                .rows(1)
                .title(Component.text(color("&8Your tool")))
                .create();

        PlayerData playerData = playerDataManager.getPlayerData(player);
        ItemStack hoe = CommonUtil.applyPlayerData(CommonUtil.getBaseHoe(player), playerData);

        gui.setItem(4, ItemBuilder.from(hoe).asGuiItem(event -> {
            event.setCancelled(true);
            player.closeInventory();

            handleHoeRetrieval(player, hoe);
        }));

        if (configManager.isFillGuis()) {
            gui.getFiller()
                    .fill(ItemBuilder
                            .from(CommonUtil.makeItem(" ", configManager.getFillerMaterial(), List.of("")))
                            .asGuiItem(event -> event.setCancelled(true)));
        }

        gui.open(player);
    }

    private void handleHoeRetrieval(Player player, ItemStack hoe) {
        Inventory inventory = player.getInventory();

        if (containsHoe(inventory)) {
            player.sendMessage(color("&cYou already have your tool with you!"));
            return;
        }

        if (inventory.firstEmpty() == -1) {
            player.sendMessage(color("&cYour inventory is full!"));
            player.playSound(player, Sound.ENTITY_VILLAGER_NO, 1.0F, 1.0F);
        } else {
            inventory.addItem(hoe);
            player.sendMessage(color("&aSuccessfully retrieved your tool!"));
            player.playSound(player, Sound.ENTITY_VILLAGER_CELEBRATE, 1.0F, 1.0F);
        }
    }


}
