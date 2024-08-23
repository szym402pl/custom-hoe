package me.xiaojibazhanshi.customhoe;

import lombok.Getter;
import me.xiaojibazhanshi.customhoe.commands.HoeCMDCompleter;
import me.xiaojibazhanshi.customhoe.commands.HoeCommand;
import me.xiaojibazhanshi.customhoe.data.config.ConfigManager;
import me.xiaojibazhanshi.customhoe.data.playerdata.PlayerDataManager;
import me.xiaojibazhanshi.customhoe.listeners.*;
import me.xiaojibazhanshi.customhoe.upgrades.UpgradeManager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

@Getter
public final class CustomHoe extends JavaPlugin {

    private CustomHoe main;
    private ConfigManager configManager;
    private PlayerDataManager playerDataManager;
    private UpgradeManager upgradeManager;

    //TODO:
    // add vault implementation,
    // add npc implementation,

    @Override
    public void onEnable() {
        main = this;
        configManager = new ConfigManager(main);
        upgradeManager = new UpgradeManager(configManager);
        playerDataManager = new PlayerDataManager(getDataFolder(), upgradeManager);

        getCommand("hoetool").setExecutor(new HoeCommand(this));
        getCommand("hoetool").setTabCompleter(new HoeCMDCompleter());


        Bukkit.getPluginManager().registerEvents(new InventoryListener(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerDeathListener(), this);
        Bukkit.getPluginManager().registerEvents(new ContainerBreakListener(), this);
        Bukkit.getPluginManager().registerEvents(new JoinListener(playerDataManager), this);
        Bukkit.getPluginManager().registerEvents(new PlayerRespawnListener(playerDataManager), this);
        Bukkit.getPluginManager().registerEvents(new BlockBreakListener(upgradeManager, playerDataManager), this);
        Bukkit.getPluginManager().registerEvents
                (new RightClickListener(configManager, upgradeManager, playerDataManager), this);

    }

    @Override
    public void onDisable() {
        playerDataManager.saveAllData();
    }
}
