package me.xiaojibazhanshi.customhoe.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class JoinListener implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        // check if the player's inv contains the custom hoe, if not, show the GUI
    }

}
