package me.xiaojibazhanshi.customhoe.upgrades.upgrades;


import me.xiaojibazhanshi.customhoe.common.CommonUtil;
import me.xiaojibazhanshi.customhoe.data.playerdata.PlayerDataManager;
import me.xiaojibazhanshi.customhoe.upgrades.Level;
import me.xiaojibazhanshi.customhoe.upgrades.Upgrade;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;

import java.util.List;

import static me.xiaojibazhanshi.customhoe.common.CommonUtil.color;


public class AutoReplantUpgrade extends Upgrade {

    public AutoReplantUpgrade(List<Level> levels) {
        super("Auto Replant", levels);
    }

    @Override
    public List<String> getDescription() {
        return List.of("", color("&7This upgrade will replant"), color("&7a broken crop for you."));
    }

    @Override
    public void sendTriggerMessage(Player player) {
        player.sendMessage(color("&aYour crop has been replanted!"));
    }

    @Override
    public void onCropBreak(BlockBreakEvent event, Player player, PlayerDataManager playerDataManager, boolean notify) {
        int levelInt = playerDataManager.getPlayerUpgradeLevel(player, this);
        if (levelInt <= 0) return; // no upgrade, don't do anything

        Level level = this.getLevel(levelInt);

        double chance = level.chanceToTrigger();
        if (CommonUtil.isLuckNotOnYourSide(chance)) return;

        Block block = event.getBlock();
        event.setCancelled(true);

        CommonUtil.replantCrop(block, block.getType(), 1);
        player.playSound(player, Sound.ENTITY_PLAYER_LEVELUP, 1.0F, 1.0F);

        if (notify) {
            sendTriggerMessage(player);
        }
    }
}
