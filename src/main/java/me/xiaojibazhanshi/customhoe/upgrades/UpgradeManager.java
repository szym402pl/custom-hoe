package me.xiaojibazhanshi.customhoe.upgrades;

import lombok.Getter;
import me.xiaojibazhanshi.customhoe.data.config.ConfigManager;
import me.xiaojibazhanshi.customhoe.upgrades.upgrades.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
public class UpgradeManager {

    private final ConfigManager configManager;

    private AutoReplantUpgrade autoReplantUpgrade;
    private LootingUpgrade lootingUpgrade;
    private MeteorUpgrade meteorUpgrade;
    private NPCUpgrade npcUpgrade;
    private SpeedUpgrade speedUpgrade;

    private final Map<String, Upgrade> upgradeRegistry = new HashMap<>();

    public UpgradeManager(ConfigManager configManager) {
        this.configManager = configManager;
        initializeUpgrades();
    }

    private void initializeUpgrades() {
        this.autoReplantUpgrade = new AutoReplantUpgrade(configManager.getAutoReplantLevels());
        this.lootingUpgrade = new LootingUpgrade(configManager.getLootingLevels());
        this.meteorUpgrade = new MeteorUpgrade(configManager.getMeteorLevels());
        this.npcUpgrade = new NPCUpgrade(configManager.getNpcLevels());
        this.speedUpgrade = new SpeedUpgrade(configManager.getSpeedLevels());

        registerUpgrade(autoReplantUpgrade);
        registerUpgrade(lootingUpgrade);
        registerUpgrade(meteorUpgrade);
        registerUpgrade(npcUpgrade);
        registerUpgrade(speedUpgrade);
    }

    public void reload() {
        initializeUpgrades();
    }

    public List<Upgrade> getAllUpgrades() {
        return Arrays.asList(
                autoReplantUpgrade,
                lootingUpgrade,
                meteorUpgrade,
                npcUpgrade,
                speedUpgrade);
    }

    private void registerUpgrade(Upgrade upgrade) {
        upgradeRegistry.put(upgrade.getClass().getName(), upgrade);
    }

    public String getUpgradeKey(Upgrade upgrade) {
        return upgradeRegistry.entrySet()
                .stream()
                .filter(entry -> entry.getValue().equals(upgrade))
                .map(Map.Entry::getKey)
                .findFirst()
                .orElse(null);
    }

    public Upgrade getUpgradeFromKey(String key) {
        return upgradeRegistry.get(key);
    }
}
