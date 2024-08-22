package me.xiaojibazhanshi.customhoe.data.config;

import lombok.Getter;
import me.xiaojibazhanshi.customhoe.CustomHoe;
import me.xiaojibazhanshi.customhoe.upgrades.Level;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.List;

@Getter
public class ConfigManager extends Util {

    private final FileConfiguration config;

    private String mainGuiName;
    private int mainGuiRows;

    private int xUpgradeGuiRows;
    private boolean hideFarLevels;

    private boolean fillGuis;
    private Material fillerMaterial;

    private List<Level> speedLevels;
    private List<Level> lootingLevels;
    private List<Level> autoReplantLevels;
    private List<Level> meteorLevels;
    private List<Level> npcLevels;

    public ConfigManager(CustomHoe instance) {
        config = instance.getConfig();

        setup(instance);
    }

    public void setup(CustomHoe instance) {
        instance.saveDefaultConfig();
        instance.getConfig().options().copyDefaults(true);

        initializeConfigValues();
    }

    private void initializeConfigValues() {
        this.mainGuiName = nullCheckCI(config, "guis.main-gui.name", String.class);
        this.mainGuiRows = nullCheckCI(config, "guis.main-gui.rows", Integer.class);

        this.xUpgradeGuiRows = nullCheckCI(config, "guis.upgrade-guis.rows", Integer.class);
        this.hideFarLevels = nullCheckCI(config, "guis.upgrade-guis.hide-far-levels", Boolean.class);

        this.fillGuis = nullCheckCI(config, "guis.fill.enabled", Boolean.class);
        this.fillerMaterial = Material.valueOf(nullCheckCI(config, "guis.fill.material", String.class).toUpperCase());

        this.speedLevels = getUpgradeLevels(config, "player-speed", "potion-amplifier");
        this.lootingLevels = getUpgradeLevels(config, "looting", "crop-multiplier");
        this.autoReplantLevels = getUpgradeLevels(config, "auto-replant", null);
        this.meteorLevels = getUpgradeLevels(config, "meteor", "radius");
        this.npcLevels = getUpgradeLevels(config, "npc", "npc-lifetime-seconds");
    }


}
