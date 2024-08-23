package me.xiaojibazhanshi.customhoe.upgrades.upgrades;

import me.xiaojibazhanshi.customhoe.common.CommonUtil;
import me.xiaojibazhanshi.customhoe.data.playerdata.PlayerDataManager;
import me.xiaojibazhanshi.customhoe.npc.NPCManager;
import me.xiaojibazhanshi.customhoe.upgrades.Level;
import me.xiaojibazhanshi.customhoe.upgrades.Upgrade;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;

import java.util.List;

import static me.xiaojibazhanshi.customhoe.common.CommonUtil.color;

public class NPCUpgrade extends Upgrade {

    public NPCUpgrade(List<Level> levels) {
        super("NPC", levels);
    }

    @Override
    public List<String> getDescription() {
        return List.of("", color("&7This upgrade will spawn a NPC, that"),
                color("&7harvests one of broken crop a second."),
                color("&7Make sure to approach him when he's finished!"));
    }

    @Override
    public void onCropBreak(BlockBreakEvent event, Player player, PlayerDataManager playerDataManager) {
        int levelInt = playerDataManager.getPlayerUpgradeLevel(player, this);
        if (levelInt <= 0) return; // no upgrade, don't do anything

        Level level = this.getLevel(levelInt);

        double chance = level.chanceToTrigger();
        if (CommonUtil.isLuckNotOnYourSide(chance)) return;

        int npcLifetimeSeconds = level.getExtraValue("npc-lifetime-seconds", Integer.class);

        NPCManager npcManager = new NPCManager();
        npcManager.createHarvestNPC(player, event.getBlock().getType(), npcLifetimeSeconds);

        player.sendTitle("", "Npc upgrade triggered", 10, 15, 5);
    }
}
