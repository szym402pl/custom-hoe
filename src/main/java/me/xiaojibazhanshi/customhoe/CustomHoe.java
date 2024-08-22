package me.xiaojibazhanshi.customhoe;

import lombok.Getter;
import me.xiaojibazhanshi.customhoe.commands.HoeCommand;
import me.xiaojibazhanshi.customhoe.data.config.ConfigManager;
import me.xiaojibazhanshi.customhoe.data.playerdata.PlayerDataManager;
import me.xiaojibazhanshi.customhoe.listeners.BlockBreakListener;
import me.xiaojibazhanshi.customhoe.listeners.JoinListener;
import me.xiaojibazhanshi.customhoe.listeners.RightClickListener;
import me.xiaojibazhanshi.customhoe.upgrades.UpgradeManager;
import org.bukkit.Bukkit;
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

        getCommand("hoetool").setExecutor(new HoeCommand(playerDataManager));

        Bukkit.getPluginManager().registerEvents(new JoinListener(playerDataManager), this);
        Bukkit.getPluginManager().registerEvents(new BlockBreakListener(upgradeManager, playerDataManager), this);
        Bukkit.getPluginManager().registerEvents
                (new RightClickListener(configManager, upgradeManager, playerDataManager), this);
    }

    @Override
    public void onDisable() {
        playerDataManager.saveAllData();
    }
}
