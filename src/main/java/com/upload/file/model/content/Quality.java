package com.upload.file.model.content;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public enum Quality {

    Q360P(360),
    Q480P(480),
    Q720P(720);

    private static Map<Integer, Quality> map = new HashMap<>();

    static {
        for (Quality value : values()) {
            map.put(value.height, value);
        }
    }

    private int height;

    Quality(int height) {
        this.height = height;
    }

    public static Quality getQualityByHeight(int height) {
        return map.get(height);
    }

    public int getHeight() {
        return height;
    }

    public static Set<Quality> getLowerOrEqualResolutions(int height) {
        return getLowerOrEqualResolutions(getQualityByHeight(height));
    }

    public static Set<Quality> getLowerOrEqualResolutions(Quality quality) {
        return Arrays.stream(values())
                .filter(q -> q.height <= quality.height)
                .collect(Collectors.toSet());
    }
}
