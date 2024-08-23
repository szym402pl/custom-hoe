package me.xiaojibazhanshi.customhoe.listeners;

import me.xiaojibazhanshi.customhoe.common.CommonUtil;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.List;

public class ContainerBreakListener implements Listener {

    List<Material> containers = Arrays.asList(
            Material.CHEST,
            Material.TRAPPED_CHEST,
            Material.FURNACE,
            Material.BREWING_STAND,
            Material.DISPENSER,
            Material.DROPPER,
            Material.HOPPER,
            Material.BARREL,
            Material.LECTERN,
            Material.SHULKER_BOX
    );

    @EventHandler
    public void onContainerBreak(BlockBreakEvent event) {
        Block block = event.getBlock();
        Material blockType = block.getType();

        if (!containers.contains(blockType)) return;

        List<ItemStack> drops = block.getDrops().stream()
                .filter(item -> !CommonUtil.isCustomHoe(item))
                .toList();

        event.setCancelled(true);
        block.setType(Material.AIR);
        drops.forEach(item -> block.getWorld().dropItemNaturally(block.getLocation(), item));
    }

}
