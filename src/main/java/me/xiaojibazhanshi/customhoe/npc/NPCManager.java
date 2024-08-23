package me.xiaojibazhanshi.customhoe.npc;

import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;
import net.citizensnpcs.api.npc.NPCRegistry;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

public class NPCManager {

    private final NPCRegistry npcRegistry;

    public NPCManager() {
        this.npcRegistry = CitizensAPI.getNPCRegistry();
    }

    public NPC createHarvestNPC(Player player, Material harvestedMaterial, int npcLifetimeSeconds) {
        NPC npc = npcRegistry.createNPC(EntityType.VILLAGER, "Farmer");
        HarvestTrait trait = new HarvestTrait(player, harvestedMaterial, npcLifetimeSeconds);

        npc.addTrait(trait);
        npc.spawn(player.getLocation());

        return npc;
    }
}