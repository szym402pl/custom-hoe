package me.xiaojibazhanshi.customhoe.upgrades.upgrades;

import me.xiaojibazhanshi.customhoe.common.CommonUtil;
import me.xiaojibazhanshi.customhoe.data.playerdata.PlayerDataManager;
import me.xiaojibazhanshi.customhoe.upgrades.Level;
import me.xiaojibazhanshi.customhoe.upgrades.Upgrade;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.List;

import static me.xiaojibazhanshi.customhoe.common.CommonUtil.color;

public class SpeedUpgrade extends Upgrade {

    public SpeedUpgrade(List<Level> levels) {
        super("Speed", levels);
    }

    @Override
    public List<String> getDescription() {
        return List.of("", color("&7This upgrade will give you"), color("&7a temporary speed boost."));
    }



    @Override
    public void onCropBreak(BlockBreakEvent event, Player player, PlayerDataManager playerDataManager) {
        int levelInt = playerDataManager.getPlayerUpgradeLevel(player, this);
        if (levelInt <= 0) return; // no upgrade, don't do anything

        Level level = this.getLevel(levelInt);

        double chance = level.chanceToTrigger();
        if (CommonUtil.isLuckNotOnYourSide(chance)) return;

        int potionAmplifier = level.getExtraValue("potion-amplifier", int.class);
        PotionEffect potionEffect = new PotionEffect(PotionEffectType.SPEED, 10 * 20, potionAmplifier);

        player.addPotionEffect(potionEffect);
        player.sendTitle("", "Speed upgrade triggered", 10, 15, 5);
    }
}
