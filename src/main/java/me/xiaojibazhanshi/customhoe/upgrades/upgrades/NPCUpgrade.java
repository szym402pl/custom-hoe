package me.xiaojibazhanshi.customhoe.upgrades.upgrades;

import me.xiaojibazhanshi.customhoe.upgrades.Level;
import me.xiaojibazhanshi.customhoe.upgrades.Upgrade;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;

import java.util.List;
import java.util.TreeSet;

public class NPCUpgrade extends Upgrade {

    public NPCUpgrade(List<Level> levels) {
        super("NPC", levels);
    }

    @Override
    protected void onCropBreak(BlockBreakEvent event, Player player) {

    }
}
