package me.xiaojibazhanshi.customhoe.upgrades.upgrades;

import me.xiaojibazhanshi.customhoe.upgrades.Level;
import me.xiaojibazhanshi.customhoe.upgrades.Upgrade;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;

import java.util.List;
import java.util.TreeSet;

public class AutoReplantUpgrade implements Upgrade {
    @Override
    public List<Level> getLevels() {
        return null;
    }

    @Override
    public String getName() {
        return "Auto Replant Upgrade";
    }

    @Override
    public void onCropBreak(BlockBreakEvent event, Player player) {

    }
}
