package me.xiaojibazhanshi.customhoe.listeners;

import me.xiaojibazhanshi.customhoe.common.CommonUtil;
import me.xiaojibazhanshi.customhoe.data.config.ConfigManager;
import me.xiaojibazhanshi.customhoe.data.playerdata.PlayerDataManager;
import me.xiaojibazhanshi.customhoe.guis.hoeitemgui.HoeItemGui;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class JoinListener implements Listener {

    PlayerDataManager playerDataManager;
    ConfigManager configManager;

    public JoinListener(PlayerDataManager playerDataManager, ConfigManager configManager) {
        this.playerDataManager = playerDataManager;
        this.configManager = configManager;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        if (!CommonUtil.containsHoe(player.getInventory())) {
            HoeItemGui gui = new HoeItemGui(playerDataManager, configManager);
            gui.openGui(player);
        } else {
            CommonUtil.updateHoe(player.getInventory(), player, playerDataManager.getPlayerData(player));
        }

    }

}
