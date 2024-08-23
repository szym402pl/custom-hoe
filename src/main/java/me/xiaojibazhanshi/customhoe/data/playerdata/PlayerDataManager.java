package me.xiaojibazhanshi.customhoe.data.playerdata;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import me.xiaojibazhanshi.customhoe.upgrades.Upgrade;
import me.xiaojibazhanshi.customhoe.upgrades.UpgradeManager;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PlayerDataManager {

    private final UpgradeManager upgradeManager;
    private final File dataFile;
    private final Gson gson = new Gson();
    private Map<UUID, PlayerData> playerDataMap;

    public PlayerDataManager(File dataFolder, UpgradeManager upgradeManager) {
        this.upgradeManager = upgradeManager;
        this.dataFile = new File(dataFolder, "playerdata.json");
        this.playerDataMap = new HashMap<>();
        loadPlayerData();
    }

    private void loadPlayerData() {
        if (!dataFile.exists()) {
            return;
        }
        try (FileReader reader = new FileReader(dataFile)) {
            Type type = new TypeToken<Map<UUID, PlayerData>>() {
            }.getType();
            playerDataMap = gson.fromJson(reader, type);
            if (playerDataMap == null) {
                playerDataMap = new HashMap<>();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void savePlayerData() {
        try (FileWriter writer = new FileWriter(dataFile)) {
            gson.toJson(playerDataMap, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public PlayerData getPlayerData(Player player) {
        return playerDataMap.computeIfAbsent(player.getUniqueId(), uuid -> {
            Map<Upgrade, Integer> upgradeLevels = new HashMap<>();

            return new PlayerData(uuid, upgradeLevels);
        });
    }

    public void setPlayerUpgradeLevel(Player player, Upgrade upgrade, int level) {
        PlayerData playerData = getPlayerData(player);
        playerData.upgradeLevels().put(upgrade, level);
        savePlayerData();
    }

    public int getPlayerUpgradeLevel(Player player, Upgrade upgrade) {
        PlayerData playerData = getPlayerData(player);
        return playerData.upgradeLevels().getOrDefault(upgrade, 0);
    }

    public void saveAllData() {
        savePlayerData();
    }
}
