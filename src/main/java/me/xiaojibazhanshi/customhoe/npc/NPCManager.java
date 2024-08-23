package me.xiaojibazhanshi.customhoe.npc;

import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;
import net.citizensnpcs.api.npc.NPCRegistry;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

public class NPCManager {

    private final NPCRegistry npcRegistry;

    public NPCManager() {
        this.npcRegistry = CitizensAPI.getNPCRegistry();
    }

    public NPC createHarvestNPC(Player player, int npcLifetimeSeconds) {
        NPC npc = npcRegistry.createNPC(EntityType.PLAYER, "Farmer");
        HarvestTrait trait = new HarvestTrait(npcLifetimeSeconds, player);
        npc.addTrait(trait);
        npc.spawn(player.getLocation());
        return npc;
    }
}