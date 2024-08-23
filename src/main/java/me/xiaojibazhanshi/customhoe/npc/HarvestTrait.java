package me.xiaojibazhanshi.customhoe.npc;

import me.xiaojibazhanshi.customhoe.CustomHoe;
import me.xiaojibazhanshi.customhoe.common.CommonUtil;
import net.citizensnpcs.api.event.DespawnReason;
import net.citizensnpcs.api.npc.NPC;
import net.citizensnpcs.api.trait.Trait;
import net.citizensnpcs.api.util.DataKey;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import static me.xiaojibazhanshi.customhoe.common.CommonUtil.color;

public class HarvestTrait extends Trait {

    private int npcLifetimeSeconds;
    private int harvestAmount;
    private Player owner;
    CustomHoe instance;
    Material harvestedMaterial;

    public HarvestTrait(int npcLifetimeSeconds, Player owner) {
        super("HarvestTrait");
        this.npcLifetimeSeconds = npcLifetimeSeconds;
        this.harvestAmount = npcLifetimeSeconds;
        this.owner = owner;

    }

    @Override
    public void onAttach() {
        owner.sendMessage(color("&aA local farmer has decided to help you!"));
        owner.playSound(owner, Sound.ENTITY_VILLAGER_CELEBRATE, 1.0F, 1.0F);
    }

    private void startDespawnTimer() {
        new BukkitRunnable() {
            int secondsLeft = 5 * 60;

            @Override
            public void run() {
                getNPC().setName(color("&eFarmer &7(&aREADY &7- &aCLICK ME&7)"));

                if (!getNPC().isSpawned()) {
                    this.cancel();
                    return;
                }

                if (secondsLeft <= 0) {
                    getNPC().despawn();
                    this.cancel();
                }

                secondsLeft--;
            }
        }.runTaskTimer(instance, 0, 20);
    }

    @Override
    public void onSpawn() {
        new BukkitRunnable() {
            int secondsLeft = npcLifetimeSeconds;

            @Override
            public void run() {
                getNPC().setName(color("&eFarmer &7(&aGathering the crop&7: &b" + secondsLeft + "&7)"));

                if (secondsLeft <= 0) {
                    this.cancel();
                    startDespawnTimer();
                }
                secondsLeft--;
            }
        }.runTaskTimer(instance, 0, 20);
    }

    @Override
    public void onDespawn(DespawnReason reason) {
        if (reason == DespawnReason.PLUGIN) {
            CommonUtil.handleItemAddition(owner, new ItemStack(harvestedMaterial, npcLifetimeSeconds));
            owner.sendMessage(color("&eFarmer &ahas given you some crop!"));
            owner.playSound(owner, Sound.ENTITY_VILLAGER_CELEBRATE, 1.0F, 1.0F);
        }
    }

    @EventHandler
    public void onRightClick(PlayerInteractEvent event) {
        if (event.getPlayer().equals(owner)) {
            getNPC().despawn();
        }
    }

    @Override
    public void save(DataKey key) {
        key.setInt("npcLifetimeSeconds", npcLifetimeSeconds);
        key.setInt("harvestAmount", harvestAmount);
        key.setString("owner", owner.getUniqueId().toString());
    }

    @Override
    public void load(DataKey key) {
        this.npcLifetimeSeconds = key.getInt("npcLifetimeSeconds");
        this.harvestAmount = key.getInt("harvestAmount");
        this.owner = Bukkit.getPlayer(java.util.UUID.fromString(key.getString("owner")));
    }
}
