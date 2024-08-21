package me.xiaojibazhanshi.customhoe.data.playerdata;

import me.xiaojibazhanshi.customhoe.upgrades.Level;
import me.xiaojibazhanshi.customhoe.upgrades.Upgrade;

import java.util.Map;
import java.util.UUID;

public record PlayerData(UUID uuid, Map<Upgrade, Level> upgrades) {}
