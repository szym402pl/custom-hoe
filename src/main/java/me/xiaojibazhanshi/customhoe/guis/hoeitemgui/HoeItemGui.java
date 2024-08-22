package me.xiaojibazhanshi.customhoe.guis.hoeitemgui;

import dev.triumphteam.gui.builder.item.ItemBuilder;
import dev.triumphteam.gui.components.GuiType;
import dev.triumphteam.gui.guis.Gui;
import me.xiaojibazhanshi.customhoe.common.CommonUtil;
import me.xiaojibazhanshi.customhoe.data.playerdata.PlayerData;
import me.xiaojibazhanshi.customhoe.data.playerdata.PlayerDataManager;
import net.kyori.adventure.text.Component;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import static me.xiaojibazhanshi.customhoe.common.CommonUtil.color;
import static me.xiaojibazhanshi.customhoe.common.CommonUtil.containsHoe;

public class HoeItemGui {

    private final PlayerDataManager playerDataManager;

    public HoeItemGui(PlayerDataManager playerDataManager) {
        this.playerDataManager = playerDataManager;
    }

    public void openGui(Player player) {
        Gui gui = Gui.gui()
                .type(GuiType.DISPENSER)
                .title(Component.text(color("&7Your tool")))
                .create();

        PlayerData playerData = playerDataManager.getPlayerData(player);
        ItemStack hoe = CommonUtil.applyPlayerData(CommonUtil.getBaseHoe(player), playerData);

        assert hoe != null;

        gui.setItem(1, 5, ItemBuilder.from(hoe).asGuiItem(event -> {
            event.setCancelled(true);
            player.closeInventory();

            handleHoeRetrieval(player, hoe);
        }));
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
