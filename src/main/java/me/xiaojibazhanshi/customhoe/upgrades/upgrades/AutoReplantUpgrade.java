package me.xiaojibazhanshi.customhoe.upgrades.upgrades;


import me.xiaojibazhanshi.customhoe.common.CommonUtil;
import me.xiaojibazhanshi.customhoe.data.playerdata.PlayerDataManager;
import me.xiaojibazhanshi.customhoe.upgrades.Level;
import me.xiaojibazhanshi.customhoe.upgrades.Upgrade;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;

import java.util.List;


public class AutoReplantUpgrade extends Upgrade {

    public AutoReplantUpgrade(List<Level> levels) {
        super("Auto Replant", levels);
    }

    @Override
    public void onCropBreak(BlockBreakEvent event, Player player, PlayerDataManager playerDataManager) {
        int levelInt = playerDataManager.getPlayerUpgradeLevel(player, this);
        Level level = this.getLevel(levelInt);

        double chance = level.chanceToTrigger();
        if (CommonUtil.isLuckNotOnYourSide(chance)) return;
        Block block = event.getBlock();

        CommonUtil.replantCrop(block, block.getType(), 7);
        player.sendTitle("", "AutoReplant upgrade triggered", 10, 15, 5);
    }
}
