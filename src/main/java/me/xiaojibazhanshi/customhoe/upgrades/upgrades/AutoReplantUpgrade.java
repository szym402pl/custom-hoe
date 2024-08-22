package me.xiaojibazhanshi.customhoe.upgrades.upgrades;


import me.xiaojibazhanshi.customhoe.CustomHoe;
import me.xiaojibazhanshi.customhoe.upgrades.Level;
import me.xiaojibazhanshi.customhoe.upgrades.Upgrade;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;

import java.util.List;


public class AutoReplantUpgrade extends Upgrade {

    public AutoReplantUpgrade(List<Level> levels) {
        super("Auto Replant", levels);
    }

    @Override
    protected void onCropBreak(BlockBreakEvent event, Player player) {

    }
}
