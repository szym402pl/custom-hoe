package me.xiaojibazhanshi.customhoe.listeners;

import me.xiaojibazhanshi.customhoe.common.CommonUtil;
import me.xiaojibazhanshi.customhoe.data.config.ConfigManager;
import me.xiaojibazhanshi.customhoe.data.playerdata.PlayerDataManager;
import me.xiaojibazhanshi.customhoe.guis.maingui.MainGui;
import me.xiaojibazhanshi.customhoe.upgrades.UpgradeManager;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class RightClickListener implements Listener {

    private final ConfigManager configManager;
    private final UpgradeManager upgradeManager;
    private final PlayerDataManager playerDataManager;

    public RightClickListener(ConfigManager configManager,
                              UpgradeManager upgradeManager,
                              PlayerDataManager playerDataManager) {
        this.playerDataManager = playerDataManager;
        this.upgradeManager = upgradeManager;
        this.configManager = configManager;
    }

    @EventHandler
    public void onRightClick(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        ItemStack item = player.getInventory().getItemInMainHand();

        if (event.getAction() != Action.RIGHT_CLICK_AIR) return;
        if (item.getType() == Material.AIR) return;
        if (!CommonUtil.isCustomHoe(item)) return;

        MainGui mainGui = new MainGui(configManager, upgradeManager, playerDataManager);
        mainGui.openGui(player);
    }

}
