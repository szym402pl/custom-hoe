package me.xiaojibazhanshi.customhoe.listeners;

import me.xiaojibazhanshi.customhoe.common.CommonUtil;
import me.xiaojibazhanshi.customhoe.data.playerdata.PlayerDataManager;
import me.xiaojibazhanshi.customhoe.upgrades.Upgrade;
import me.xiaojibazhanshi.customhoe.upgrades.UpgradeManager;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.Ageable;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Optional;

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
        ItemStack itemInMainHand = event.getPlayer().getInventory().getItemInMainHand();
        Optional<ItemStack> drop = block.getDrops().stream().findFirst();
        ItemStack actualDrop = drop.orElse(new ItemStack(Material.STONE_PRESSURE_PLATE));

        if (itemInMainHand.getType() == Material.AIR) return;
        if (!(block.isPreferredTool(itemInMainHand) && CommonUtil.isCustomHoe(itemInMainHand))) return;

        System.out.println("Before ageable check");
        if (!(actualDrop instanceof Ageable)) {
            event.setCancelled(true);
            event.getPlayer().sendMessage(ChatColor.RED + "This is not a crop!");
            return;
        }

        System.out.println("After ageable check, broken block: " + event.getBlock());

        if (!CommonUtil.isFullyGrown(block)) {
            event.setCancelled(true);
            event.getPlayer().sendMessage(ChatColor.RED + "This crop is not fully grown yet!");
            return;
        }

        System.out.println("Right before replantCrop");
        CommonUtil.replantCrop(block, block.getType(), 1);

        for (Upgrade upgrade : upgradeManager.getAllUpgrades()) {
            System.out.println("Triggered onCropBreak in upgrade " + upgrade.getName());
            upgrade.onCropBreak(event, event.getPlayer(), playerDataManager);
        }
    }

}
