package me.xiaojibazhanshi.customhoe.upgrades.upgrades;

import me.xiaojibazhanshi.customhoe.common.CommonUtil;
import me.xiaojibazhanshi.customhoe.data.playerdata.PlayerData;
import me.xiaojibazhanshi.customhoe.data.playerdata.PlayerDataManager;
import me.xiaojibazhanshi.customhoe.upgrades.Level;
import me.xiaojibazhanshi.customhoe.upgrades.Upgrade;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

public class LootingUpgrade extends Upgrade {

    public LootingUpgrade(List<Level> levels) {
        super("Looting", levels);
    }

    @Override
    protected void onCropBreak(BlockBreakEvent event, Player player, PlayerDataManager playerDataManager) {
        int levelInt = playerDataManager.getPlayerUpgradeLevel(player, this);
        Level level = this.getLevel(levelInt);

        double chance = level.chanceToTrigger();
        if (!CommonUtil.isLuckOnYourSide(chance)) return;

        List<ItemStack> drops = (List<ItemStack>) event.getBlock().getDrops();
        drops.forEach(drop -> { event.getBlock().getDrops().add(drop); });

        player.sendTitle("", "");
    }
}
