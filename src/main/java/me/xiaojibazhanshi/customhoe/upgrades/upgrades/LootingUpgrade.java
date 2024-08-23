package me.xiaojibazhanshi.customhoe.upgrades.upgrades;

import me.xiaojibazhanshi.customhoe.common.CommonUtil;
import me.xiaojibazhanshi.customhoe.data.playerdata.PlayerDataManager;
import me.xiaojibazhanshi.customhoe.upgrades.Level;
import me.xiaojibazhanshi.customhoe.upgrades.Upgrade;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import java.util.List;

import static me.xiaojibazhanshi.customhoe.common.CommonUtil.color;

public class LootingUpgrade extends Upgrade {

    public LootingUpgrade(List<Level> levels) {
        super("Looting", levels);
    }
    @Override
    public List<String> getDescription() {
        return List.of("", color("&7This upgrade will give"), color("&7you more crop per harvest."));
    }

    @Override
    public void onCropBreak(BlockBreakEvent event, Player player, PlayerDataManager playerDataManager) {
        int levelInt = playerDataManager.getPlayerUpgradeLevel(player, this);
        if (levelInt <= 0) return; // no upgrade, don't do anything

        Level level = this.getLevel(levelInt);
        int cropMultiplier = level.getExtraValue("crop-multiplier", int.class);

        double chance = level.chanceToTrigger();
        if (CommonUtil.isLuckNotOnYourSide(chance)) return;

        List<ItemStack> drops = (List<ItemStack>) event.getBlock().getDrops();

        for (ItemStack drop : drops) {

        }

        player.sendTitle("", "Looting upgrade triggered", 10, 15, 5);
    }
}
