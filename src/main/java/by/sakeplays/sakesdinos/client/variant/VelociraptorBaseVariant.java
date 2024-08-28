package by.sakeplays.sakesdinos.client.variant;

import java.util.Arrays;
import java.util.Comparator;

public enum VelociraptorBaseVariant {
    GRAY(0),
    LIGHT_GRAY(1),
    BROWN(2);

    private static final VelociraptorBaseVariant[] BY_ID = Arrays.stream(values()).sorted(Comparator.
            comparingInt(VelociraptorBaseVariant::getId)).toArray(VelociraptorBaseVariant[]::new);

    private final int id;

    VelociraptorBaseVariant(int id) {
        this.id = id;
    }

    public int getId() {
        return this.id;
    }

    public static VelociraptorBaseVariant byId(int id) {
        return BY_ID[id % BY_ID.length];
    }
}
