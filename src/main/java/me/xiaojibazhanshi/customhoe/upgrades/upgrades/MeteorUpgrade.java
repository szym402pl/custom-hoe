package me.xiaojibazhanshi.customhoe.upgrades.upgrades;

import me.xiaojibazhanshi.customhoe.upgrades.Level;
import me.xiaojibazhanshi.customhoe.upgrades.Upgrade;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;

import java.util.List;
import java.util.TreeSet;

public class MeteorUpgrade extends Upgrade {

    public MeteorUpgrade(List<Level> levels) {
        super("Meteor", levels);
    }

    @Override
    protected void onCropBreak(BlockBreakEvent event, Player player) {

    }
}
