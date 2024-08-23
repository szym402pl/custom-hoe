package me.xiaojibazhanshi.customhoe.listeners;

import me.xiaojibazhanshi.customhoe.common.CommonUtil;
import me.xiaojibazhanshi.customhoe.data.playerdata.PlayerData;
import me.xiaojibazhanshi.customhoe.data.playerdata.PlayerDataManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;

public class PlayerRespawnListener implements Listener {

    PlayerDataManager playerDataManager;

    public PlayerRespawnListener(PlayerDataManager playerDataManager) {
        this.playerDataManager = playerDataManager;
    }

    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent event) {
        Player player = event.getPlayer();
        PlayerData playerData = playerDataManager.getPlayerData(player);
        ItemStack hoe = CommonUtil.applyPlayerData(CommonUtil.getBaseHoe(player), playerData);

        player.getInventory().addItem(hoe);
    }
}
