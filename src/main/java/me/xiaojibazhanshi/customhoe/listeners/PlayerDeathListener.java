package me.xiaojibazhanshi.customhoe.listeners;

import me.xiaojibazhanshi.customhoe.common.CommonUtil;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class PlayerDeathListener implements Listener {

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        List<ItemStack> drops = event.getDrops();
        if (drops.isEmpty()) return;

        event.getDrops().removeIf(CommonUtil::isCustomHoe);
    }
}
