package me.xiaojibazhanshi.customhoe.listeners;

import me.xiaojibazhanshi.customhoe.common.CommonUtil;
import me.xiaojibazhanshi.customhoe.data.playerdata.PlayerDataManager;
import me.xiaojibazhanshi.customhoe.upgrades.Upgrade;
import me.xiaojibazhanshi.customhoe.upgrades.UpgradeManager;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

public class BlockBreakListener implements Listener {

    UpgradeManager upgradeManager;
    PlayerDataManager playerDataManager;

    public BlockBreakListener(UpgradeManager upgradeManager, PlayerDataManager playerDataManager) {
        this.upgradeManager = upgradeManager;
        this.playerDataManager = playerDataManager;
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Block block = event.getBlock();
        ItemStack itemInMainHand = event.getPlayer().getItemInUse();

        if (itemInMainHand == null) return;
        if (!(block.isPreferredTool(itemInMainHand) && CommonUtil.isCustomHoe(itemInMainHand))) return;

        if (!CommonUtil.isFullyGrown(block)) {
            event.setCancelled(true);
            event.getPlayer().sendMessage(ChatColor.RED + "This crop is not fully grown yet!");
            return;
        }

        CommonUtil.replantCrop(block, block.getType(), 1);

        for (Upgrade upgrade : upgradeManager.getAllUpgrades()) {
            upgrade.onCropBreak(event, event.getPlayer(), playerDataManager);
        }
    }

}
