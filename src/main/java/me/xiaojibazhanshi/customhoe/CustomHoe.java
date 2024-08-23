package me.xiaojibazhanshi.customhoe;

import lombok.Getter;
import me.xiaojibazhanshi.customhoe.commands.HoeCMDCompleter;
import me.xiaojibazhanshi.customhoe.commands.HoeCommand;
import me.xiaojibazhanshi.customhoe.data.config.ConfigManager;
import me.xiaojibazhanshi.customhoe.data.playerdata.PlayerDataManager;
import me.xiaojibazhanshi.customhoe.listeners.*;
import me.xiaojibazhanshi.customhoe.upgrades.UpgradeManager;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

@Getter
public final class CustomHoe extends JavaPlugin {

    public static Economy econ;
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

        getCommand("hoetool").setExecutor(new HoeCommand(this));
        getCommand("hoetool").setTabCompleter(new HoeCMDCompleter());


        Bukkit.getPluginManager().registerEvents(new InventoryListener(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerDeathListener(), this);
        Bukkit.getPluginManager().registerEvents(new ContainerBreakListener(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerRespawnListener(playerDataManager), this);
        Bukkit.getPluginManager().registerEvents(new JoinListener(playerDataManager, configManager), this);

        Bukkit.getPluginManager().registerEvents(new BlockBreakListener
                (upgradeManager, playerDataManager, configManager), this);

        Bukkit.getPluginManager().registerEvents
                (new RightClickListener(configManager, upgradeManager, playerDataManager), this);

        if (!setupEconomy()) {
            getLogger().severe("- Disabled due to no Vault dependency found!");
            getServer().getPluginManager().disablePlugin(this);
        }
    }

    @Override
    public void onDisable() {
        playerDataManager.saveAllData();
    }

    private boolean setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }

        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);

        if (rsp == null) {
            return false;
        }

        econ = rsp.getProvider();
        return true;
    }
}
