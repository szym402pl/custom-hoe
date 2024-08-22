package me.xiaojibazhanshi.customhoe.upgrades;

import lombok.Getter;
import me.xiaojibazhanshi.customhoe.CustomHoe;
import me.xiaojibazhanshi.customhoe.data.config.ConfigManager;
import me.xiaojibazhanshi.customhoe.data.playerdata.PlayerDataManager;
import me.xiaojibazhanshi.customhoe.upgrades.upgrades.*;

import java.util.Arrays;
import java.util.List;

@Getter
public class UpgradeManager {

    private final AutoReplantUpgrade autoReplantUpgrade;
    private final LootingUpgrade lootingUpgrade;
    private final MeteorUpgrade meteorUpgrade;
    private final NPCUpgrade npcUpgrade;
    private final SpeedUpgrade speedUpgrade;

    public UpgradeManager(ConfigManager configManager) {
        this.autoReplantUpgrade = new AutoReplantUpgrade(configManager.getAutoReplantLevels());
        this.lootingUpgrade = new LootingUpgrade(configManager.getLootingLevels());
        this.meteorUpgrade = new MeteorUpgrade(configManager.getMeteorLevels());
        this.npcUpgrade = new NPCUpgrade(configManager.getNpcLevels());
        this.speedUpgrade = new SpeedUpgrade(configManager.getSpeedLevels());
    }

    public List<Upgrade> getAllUpgrades() {
        return Arrays.asList(
                autoReplantUpgrade,
                lootingUpgrade,
                meteorUpgrade,
                npcUpgrade,
                speedUpgrade);
    }



}
