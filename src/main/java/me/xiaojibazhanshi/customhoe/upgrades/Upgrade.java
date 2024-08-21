package me.xiaojibazhanshi.customhoe.upgrades;

import me.xiaojibazhanshi.customhoe.utils.CommonUtil;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;

import java.util.List;

public interface Upgrade {

    List<Level> getLevels();
    String getName();

    default String getColoredName() {
        return CommonUtil.color("&7" + getName());
    }
    default Level getLevel(int level) { return getLevels().get(level - 1); } // - 1 to match the index

    void onCropBreak(BlockBreakEvent event, Player player);
}
