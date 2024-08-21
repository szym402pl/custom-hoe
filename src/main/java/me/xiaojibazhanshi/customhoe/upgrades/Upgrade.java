package me.xiaojibazhanshi.customhoe.upgrades;

import me.xiaojibazhanshi.customhoe.utils.CommonUtil;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;

import java.util.TreeSet;

public interface Upgrade {

    TreeSet<Level> getLevels();
    String getName();

    default String getColoredName() {
        return CommonUtil.color("&7" + getName());
    }

    void onCropBreak(BlockBreakEvent event, Player player);
}
