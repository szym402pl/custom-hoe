package me.xiaojibazhanshi.customhoe.upgrades.upgrades;

import me.xiaojibazhanshi.customhoe.upgrades.Level;
import me.xiaojibazhanshi.customhoe.upgrades.Upgrade;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;

import java.util.TreeSet;

public class SpeedUpgrade implements Upgrade {
    @Override
    public TreeSet<Level> getLevels() {
        return null;
    }

    @Override
    public String getName() {
        return "Player Speed Upgrade";
    }

    @Override
    public void onCropBreak(BlockBreakEvent event, Player player) {

    }
}
