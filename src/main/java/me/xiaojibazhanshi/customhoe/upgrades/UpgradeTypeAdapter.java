package me.xiaojibazhanshi.customhoe.upgrades;

import com.google.gson.*;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class UpgradeTypeAdapter extends TypeAdapter<Map<String, Integer>> {

    @Override
    public void write(JsonWriter out, Map<String, Integer> upgradeLevels) throws IOException {
        out.beginObject();
        for (Map.Entry<String, Integer> entry : upgradeLevels.entrySet()) {
            out.name(entry.getKey());
            out.value(entry.getValue());
        }
        out.endObject();
    }

    @Override
    public Map<String, Integer> read(JsonReader in) throws IOException {
        Map<String, Integer> upgradeLevels = new HashMap<>();
        in.beginObject();
        while (in.hasNext()) {
            String key = in.nextName();
            int value = in.nextInt();
            upgradeLevels.put(key, value);
        }
        in.endObject();
        return upgradeLevels;
    }
}
