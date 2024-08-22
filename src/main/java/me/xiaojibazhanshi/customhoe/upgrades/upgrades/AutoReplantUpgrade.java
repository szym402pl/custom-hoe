package me.xiaojibazhanshi.customhoe.upgrades.upgrades;


import me.xiaojibazhanshi.customhoe.CustomHoe;
import me.xiaojibazhanshi.customhoe.common.CommonUtil;
import me.xiaojibazhanshi.customhoe.data.config.ConfigManager;
import me.xiaojibazhanshi.customhoe.data.playerdata.PlayerData;
import me.xiaojibazhanshi.customhoe.data.playerdata.PlayerDataManager;
import me.xiaojibazhanshi.customhoe.upgrades.Level;
import me.xiaojibazhanshi.customhoe.upgrades.Upgrade;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.List;


public class AutoReplantUpgrade extends Upgrade {

    public AutoReplantUpgrade(List<Level> levels) {
        super("Auto Replant", levels);
    }

    @Override
    protected void onCropBreak(BlockBreakEvent event, Player player, PlayerDataManager playerDataManager) {
        int levelInt = playerDataManager.getPlayerUpgradeLevel(player, this);
        Level level = this.getLevel(levelInt);

        double chance = level.chanceToTrigger();
        if (!CommonUtil.isLuckOnYourSide(chance)) return;

        int potionAmplifier = level.getExtraValue("potion-amplifier", int.class);
        PotionEffect potionEffect = new PotionEffect(PotionEffectType.SPEED, 10 * 20, potionAmplifier);

        player.addPotionEffect(potionEffect);
    }
}
