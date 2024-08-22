package me.xiaojibazhanshi.customhoe.upgrades.upgrades;

import me.xiaojibazhanshi.customhoe.upgrades.Level;
import me.xiaojibazhanshi.customhoe.upgrades.Upgrade;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;

import java.util.List;
import java.util.TreeSet;

public class SpeedUpgrade extends Upgrade {

    public SpeedUpgrade(List<Level> levels) {
        super("Speed", levels);
    }

    @Override
    protected void onCropBreak(BlockBreakEvent event, Player player) {

    }
}
