package me.xiaojibazhanshi.customhoe.upgrades.upgrades;

import me.xiaojibazhanshi.customhoe.common.CommonUtil;
import me.xiaojibazhanshi.customhoe.data.playerdata.PlayerDataManager;
import me.xiaojibazhanshi.customhoe.upgrades.Level;
import me.xiaojibazhanshi.customhoe.upgrades.Upgrade;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class LootingUpgrade extends Upgrade {

    public LootingUpgrade(List<Level> levels) {
        super("Looting", levels);
    }

    @Override
    public void onCropBreak(BlockBreakEvent event, Player player, PlayerDataManager playerDataManager) {
        int levelInt = playerDataManager.getPlayerUpgradeLevel(player, this);
        Level level = this.getLevel(levelInt);
        int cropMultiplier = level.getExtraValue("crop-multiplier", int.class);

        double chance = level.chanceToTrigger();
        if (CommonUtil.isLuckNotOnYourSide(chance)) return;

        List<ItemStack> drops = (List<ItemStack>) event.getBlock().getDrops();
        drops.forEach(drop -> {
            for (int i = 1; i < cropMultiplier; i++) {
                event.getBlock().getDrops().add(drop);
            }
        });

        player.sendTitle("", "Looting upgrade triggered", 10, 15, 5);
    }
}
