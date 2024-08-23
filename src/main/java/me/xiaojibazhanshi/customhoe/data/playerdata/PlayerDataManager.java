package me.xiaojibazhanshi.customhoe.data.playerdata;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import me.xiaojibazhanshi.customhoe.upgrades.Upgrade;
import me.xiaojibazhanshi.customhoe.upgrades.UpgradeManager;
import me.xiaojibazhanshi.customhoe.upgrades.UpgradeTypeAdapter;
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
    private final Gson gson;
    private Map<UUID, PlayerData> playerDataMap;

    public PlayerDataManager(File dataFolder, UpgradeManager upgradeManager) {
        this.upgradeManager = upgradeManager;
        this.dataFile = new File(dataFolder, "playerdata.json");
        this.playerDataMap = new HashMap<>();

        // Gson with a TypeAdapter for Upgrade
        this.gson = new GsonBuilder()
                .registerTypeAdapter(Upgrade.class, new UpgradeTypeAdapter())
                .create();

        loadPlayerData();
    }

    private void loadPlayerData() {
        if (!dataFile.exists()) {
            return;
        }
        try (FileReader reader = new FileReader(dataFile)) {
            Type type = new TypeToken<Map<UUID, Map<String, Integer>>>() {
            }.getType();
            Map<UUID, Map<String, Integer>> rawData = gson.fromJson(reader, type);
            playerDataMap = new HashMap<>();

            if (rawData != null) {

                for (Map.Entry<UUID, Map<String, Integer>> entry : rawData.entrySet()) {

                    UUID uuid = entry.getKey();
                    Map<String, Integer> upgradeLevels = entry.getValue();
                    Map<Upgrade, Integer> upgradeMap = new HashMap<>();

                    for (Map.Entry<String, Integer> levelEntry : upgradeLevels.entrySet()) {

                        String upgradeKey = levelEntry.getKey();
                        int level = levelEntry.getValue();
                        Upgrade upgrade = upgradeManager.getUpgradeFromKey(upgradeKey);

                        if (upgrade != null) {
                            upgradeMap.put(upgrade, level);
                        }
                    }

                    playerDataMap.put(uuid, new PlayerData(uuid, upgradeMap));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void savePlayerData() {
        try (FileWriter writer = new FileWriter(dataFile)) {
            Map<UUID, Map<String, Integer>> rawData = new HashMap<>();

            for (Map.Entry<UUID, PlayerData> entry : playerDataMap.entrySet()) {

                UUID uuid = entry.getKey();
                PlayerData playerData = entry.getValue();
                Map<String, Integer> upgradeLevels = new HashMap<>();

                for (Map.Entry<Upgrade, Integer> upgradeEntry : playerData.upgradeLevels().entrySet()) {
                    String upgradeKey = upgradeManager.getUpgradeKey(upgradeEntry.getKey());
                    int level = upgradeEntry.getValue();

                    upgradeLevels.put(upgradeKey, level);
                }

                rawData.put(uuid, upgradeLevels);
            }

            gson.toJson(rawData, writer);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public PlayerData getPlayerData(Player player) {
        return playerDataMap.computeIfAbsent(player.getUniqueId(), uuid -> {
            // Initialize with empty upgrade levels
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
