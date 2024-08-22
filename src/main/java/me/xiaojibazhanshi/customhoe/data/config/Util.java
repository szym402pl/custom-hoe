package me.xiaojibazhanshi.customhoe.data.config;

import me.xiaojibazhanshi.customhoe.upgrades.Level;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Util {

    /** Null checks a config object in a specified path */
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
            int levelInt = Integer.parseInt(level);
            double chance = nullCheckCI
                    (config, section.getCurrentPath() + "." + level + ".chance-to-trigger", double.class);
            int cost = nullCheckCI(config, section.getCurrentPath() + "." + level + ".cost", int.class);

            Map<String, Object> extraValueMap = extraValueId == null
                    ? null
                    : Map.of(extraValueId, Integer.parseInt(
                    nullCheckCI(config,
                            section.getCurrentPath() + "." + level + "." + extraValueId,
                            String.class)));

            list.add(new Level(levelInt, chance, cost, extraValueMap));
        }

        if (list.isEmpty()) Bukkit.getLogger().severe("Upgrade " + configName + " has no levels set!");
        return list;
    }

}
