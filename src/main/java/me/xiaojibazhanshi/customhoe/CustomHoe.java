package me.xiaojibazhanshi.customhoe;

import lombok.Getter;
import me.xiaojibazhanshi.customhoe.data.config.ConfigManager;
import me.xiaojibazhanshi.customhoe.data.playerdata.PlayerDataManager;
import me.xiaojibazhanshi.customhoe.upgrades.UpgradeManager;
import org.bukkit.plugin.java.JavaPlugin;

@Getter
public final class CustomHoe extends JavaPlugin {

    @Getter
    private CustomHoe main;
    private ConfigManager configManager;
    private PlayerDataManager playerDataManager;
    private UpgradeManager upgradeManager;

    @Override
    public void onEnable() {
        main = this;
        configManager = new ConfigManager(main);
        upgradeManager = new UpgradeManager(configManager);
        playerDataManager = new PlayerDataManager(getDataFolder(), upgradeManager);
    }

    @Override
    public void onDisable() {
        playerDataManager.saveAllData();
    }
}
