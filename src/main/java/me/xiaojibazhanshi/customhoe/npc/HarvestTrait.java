package me.xiaojibazhanshi.customhoe.npc;

import me.xiaojibazhanshi.customhoe.CustomHoe;
import me.xiaojibazhanshi.customhoe.common.CommonUtil;
import net.citizensnpcs.api.event.DespawnReason;
import net.citizensnpcs.api.npc.NPC;
import net.citizensnpcs.api.trait.Trait;
import net.citizensnpcs.api.trait.TraitName;
import net.citizensnpcs.api.util.DataKey;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import static me.xiaojibazhanshi.customhoe.common.CommonUtil.color;

@TraitName("HarvestTrait")
public class HarvestTrait extends Trait {

    private int npcLifetimeSeconds;
    private int harvestAmount;
    private Player owner;
    CustomHoe instance;
    Material harvestedMaterial;
    String harvestedName;
    HarvestState state;

    public HarvestTrait(Player owner, Material harvestedMaterial, int npcLifetimeSeconds) {
        super("HarvestTrait");
        this.harvestedMaterial = harvestedMaterial;
        this.npcLifetimeSeconds = npcLifetimeSeconds;
        this.harvestAmount = npcLifetimeSeconds;
        this.owner = owner;
        this.harvestedName = harvestedMaterial.name().toLowerCase();
        instance = JavaPlugin.getPlugin(CustomHoe.class);
    }

    @Override
    public void onAttach() {
        owner.sendMessage(color("&aA local &eFarmer &ahas decided to help you!"));
        owner.playSound(owner, Sound.ENTITY_VILLAGER_CELEBRATE, 1.0F, 1.0F);
        state = HarvestState.GATHERING;
    }

    private void startDespawnTimer() {
        state = HarvestState.READY;

        new BukkitRunnable() {
            int secondsLeft = 5 * 60; // 5 min basically

            @Override
            public void run() {
                getNPC().setName(color("&eFarmer &7(&aREADY &7- &aCLICK ME&7) &8| &7Leaves in: &c" + secondsLeft));

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
                getNPC().setName(color
                        ("&eFarmer &7(&aGathering " + harvestedName + "&7: &b" + secondsLeft + "&7s left)"));

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
            ItemStack item = new ItemStack(harvestedMaterial, npcLifetimeSeconds);
            CommonUtil.handleItemAddition(owner, item);
            owner.sendMessage(color
                    ("&eFarmer &ahas given you &b" + item.getAmount() + " &a" + harvestedName + "!"));
            owner.playSound(owner, Sound.ENTITY_VILLAGER_CELEBRATE, 1.0F, 1.0F);
        } else {
            owner.sendMessage(color("&eFarmer &chas grown bored of waiting for you and left!"));
            owner.playSound(owner, Sound.ENTITY_VILLAGER_NO, 1.0F, 1.0F);
        }

    }

    @EventHandler
    public void onRightClick(PlayerInteractEntityEvent event) {

        if (event.getRightClicked().equals(getNPC().getEntity())) {

            if (!event.getPlayer().equals(owner)) {
                event.getPlayer().sendMessage(color("&eFarmer&7: &fI won't give my crops to a stranger!"));
                return;
            }

            if (state == HarvestState.READY) {
                getNPC().despawn(DespawnReason.PLUGIN);
            } else {
                owner.sendMessage(color("&eFarmer&7: &fI'm not ready yet!"));
            }

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

    private enum HarvestState{
        GATHERING,
        READY;
    }
}
