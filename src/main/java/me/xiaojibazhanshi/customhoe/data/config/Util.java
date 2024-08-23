package me.xiaojibazhanshi.customhoe.data.config;

import me.xiaojibazhanshi.customhoe.upgrades.Level;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Util {

    /**
     * Null checks a config object in a specified path
     */
    protected <T> T nullCheckCI(FileConfiguration config, String path, Class<T> clazz) {
        Object obj = config.get(path);

        if (obj == null) {
            Bukkit.getLogger().severe("Necessary config item at path: " + path + " doesn't exist!");
            return null;
        }

        try {
            return clazz.cast(obj);
        } catch (ClassCastException e) {
            Bukkit.getLogger().severe("Config item at path: " + path + " cannot be cast to " + clazz.getSimpleName());
            return null;
        }
    }

    protected List<Level> getUpgradeLevels(FileConfiguration config, String configName, @Nullable String extraValueId) {
        List<Level> list = new ArrayList<>();

        ConfigurationSection section =
                nullCheckCI(config, "upgrades." + configName + ".levels", ConfigurationSection.class);

        for (String level : section.getKeys(false)) {
            String currentPath = "upgrades." + configName + ".levels.";

            int levelInt = Integer.parseInt(level);
            double chance = nullCheckCI(config, currentPath + level + ".chance-to-trigger", Double.class);
            int cost = nullCheckCI(config, currentPath + level + ".cost", Integer.class);

            Map<String, Object> extraValueMap = extraValueId == null
                    ? null
                    : Map.of(extraValueId, nullCheckCI(config,
                    currentPath + level + "." + extraValueId,
                    Integer.class));

            list.add(new Level(levelInt, chance, cost, extraValueMap));
        }

        if (list.isEmpty()) Bukkit.getLogger().severe("Upgrade " + configName + " has no levels set!");
        return list;
    }

}
