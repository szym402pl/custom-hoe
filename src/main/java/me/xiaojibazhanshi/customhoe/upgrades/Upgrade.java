package me.xiaojibazhanshi.customhoe.upgrades;

import lombok.Getter;
import me.xiaojibazhanshi.customhoe.common.CommonUtil;
import me.xiaojibazhanshi.customhoe.data.playerdata.PlayerDataManager;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;

import java.util.List;

@Getter
public abstract class Upgrade {

    public Upgrade(String name, List<Level> levels) {
        this.levels = levels;
        this.name = name;
    }

    private final List<Level> levels;
    private final String name;

    public String getColoredName() {
        return CommonUtil.color("&a" + getName());
    }
    public Level getLevel(int level) {
        return getLevels().get(level - 1);
    } // - 1 to match the index

    public abstract List<String> getDescription();
    public abstract void onCropBreak(BlockBreakEvent event, Player player, PlayerDataManager playerDataManager);
}
