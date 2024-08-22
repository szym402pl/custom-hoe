package me.xiaojibazhanshi.customhoe.data.playerdata;

import me.xiaojibazhanshi.customhoe.upgrades.Upgrade;

import java.util.Map;
import java.util.UUID;

public record PlayerData<T extends Upgrade>(UUID uuid, Map<T, Integer> upgradeLevels) {}
