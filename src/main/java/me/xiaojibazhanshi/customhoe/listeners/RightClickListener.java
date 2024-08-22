package me.xiaojibazhanshi.customhoe.listeners;

import me.xiaojibazhanshi.customhoe.common.CommonUtil;
import me.xiaojibazhanshi.customhoe.guis.maingui.MainGui;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class RightClickListener implements Listener {

    @EventHandler
    public void onRightClick(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        ItemStack item = player.getItemInUse();

        if (item == null) return;
        if (event.getAction() != Action.RIGHT_CLICK_AIR) return;
        if (!CommonUtil.isCustomHoe(item)) return;

        MainGui mainGui = new MainGui();
        mainGui.openGui(player);
    }

}
