package me.xiaojibazhanshi.customhoe.listeners;

import me.xiaojibazhanshi.customhoe.common.CommonUtil;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import static me.xiaojibazhanshi.customhoe.common.CommonUtil.isCustomHoe;

public class InventoryListener implements Listener {

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        ItemStack item = event.getCurrentItem();

        if (!isCustomHoe(item)) {
            return;
        }

        Player player = (Player) event.getWhoClicked();
        Inventory clickedInventory = event.getClickedInventory();

        if (clickedInventory != null && clickedInventory.getHolder() instanceof Player clickedPlayer) {
            if (player.equals(clickedPlayer)) {
                return;
            }
        }

        event.setCancelled(true);
    }

    @EventHandler
    public void onInventoryDrag(InventoryDragEvent event) {
        event.getNewItems();
        ItemStack item = event.getNewItems().values().stream().filter(CommonUtil::isCustomHoe).findFirst().orElse(null);

        if (item != null) {
            Player player = (Player) event.getWhoClicked();
            Inventory inventory = event.getInventory();

            if (inventory.getHolder() instanceof Player clickedPlayer && player.equals(clickedPlayer)) {
                return;
            }

            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlayerDropItem(PlayerDropItemEvent event) {
        ItemStack item = event.getItemDrop().getItemStack();

        if (isCustomHoe(item)) {
            event.setCancelled(true);
        }
    }
}
