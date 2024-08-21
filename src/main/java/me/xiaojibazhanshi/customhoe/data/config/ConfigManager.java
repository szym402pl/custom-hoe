package me.xiaojibazhanshi.customhoe.data.config;

import lombok.Getter;
import me.xiaojibazhanshi.customhoe.CustomHoe;
import me.xiaojibazhanshi.customhoe.upgrades.Upgrade;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.Map;

import static me.xiaojibazhanshi.customhoe.utils.CommonUtil.color;
import static me.xiaojibazhanshi.customhoe.utils.CommonUtil.nullCheckCI;

@Getter
public class ConfigManager {

    enum Prefix {
        MAIN_GUI("guis.main-gui."),
        
        ;


        private final String path;

        Prefix(String path) {
            this.path = path;
        }

        public String getPath() {
            return path;
        }
    }

    private final FileConfiguration config;

    private String mainGuiName;
    private int mainGuiRows;
    private Map<Integer, String> mainGuiSlotLayout;

    private int xUpgradeGuiRows;
    private boolean hideFarLevels;

    private boolean fillGuis;
    private Material fillerMaterial;

    private Upgrade autoReplant;
    private Upgrade looting;
    private Upgrade meteor;
    private Upgrade npc;
    private Upgrade speed;

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
        this.mainGuiName = color(nullCheckCI(config.getString("guis.main-gui.name"), "guis.main-gui.name"));

    }

}
