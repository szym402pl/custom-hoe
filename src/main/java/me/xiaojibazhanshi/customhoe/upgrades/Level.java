package me.xiaojibazhanshi.customhoe.upgrades;

import javax.annotation.Nullable;
import java.util.Map;

public record Level(int level, double chanceToTrigger, int cost, Map<String, Object> extraValues) {

    @SuppressWarnings("unchecked")
    public <T> T getExtraValue(String key, Class<T> type) {
        return (T) extraValues.get(key);
    }
}