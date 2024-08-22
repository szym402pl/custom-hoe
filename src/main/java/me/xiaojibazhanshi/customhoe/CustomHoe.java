package me.xiaojibazhanshi.customhoe;

import lombok.Getter;
import me.xiaojibazhanshi.customhoe.data.config.ConfigManager;
import me.xiaojibazhanshi.customhoe.data.playerdata.PlayerDataManager;
import org.bukkit.plugin.java.JavaPlugin;

@Getter
public final class CustomHoe extends JavaPlugin {

    private CustomHoe main;
    private ConfigManager configManager;
    private PlayerDataManager playerDataManager;

    @Override
    public void onEnable() {
        main = this;
        configManager = new ConfigManager(main);
        playerDataManager = new PlayerDataManager(getDataFolder());
    }

    @Override
    public void onDisable() {
        playerDataManager.saveAllData();
    }
}
