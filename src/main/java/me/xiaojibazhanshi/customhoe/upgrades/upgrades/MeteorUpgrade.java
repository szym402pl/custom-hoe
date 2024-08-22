package me.xiaojibazhanshi.customhoe.upgrades.upgrades;

import me.xiaojibazhanshi.customhoe.common.CommonUtil;
import me.xiaojibazhanshi.customhoe.data.playerdata.PlayerDataManager;
import me.xiaojibazhanshi.customhoe.upgrades.Level;
import me.xiaojibazhanshi.customhoe.upgrades.Upgrade;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;

import java.util.List;

import static me.xiaojibazhanshi.customhoe.common.CommonUtil.color;

public class MeteorUpgrade extends Upgrade {

    public MeteorUpgrade(List<Level> levels) {
        super("Meteor", levels);
    }
    @Override
    public List<String> getDescription() {
        return List.of("", color("&7This upgrade will break"), color("&7crop in a given radius."));
    }

    @Override
    public void onCropBreak(BlockBreakEvent event, Player player, PlayerDataManager playerDataManager) {
        int levelInt = playerDataManager.getPlayerUpgradeLevel(player, this);
        Level level = this.getLevel(levelInt);

        double chance = level.chanceToTrigger();
        int radius = level.getExtraValue("radius", int.class);
        if (CommonUtil.isLuckNotOnYourSide(chance)) return;

        CommonUtil.replantCropsInRadiusAround(event.getBlock(), radius);
        player.sendTitle("", "Meteor upgrade triggered", 10, 15, 5);
    }
}
