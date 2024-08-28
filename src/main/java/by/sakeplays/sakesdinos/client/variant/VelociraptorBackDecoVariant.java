package by.sakeplays.sakesdinos.client.variant;

import java.util.Arrays;
import java.util.Comparator;

public enum VelociraptorBackDecoVariant {
    SPOTS_WHITE(0),
    SPOTS_BLACK(1),
    SPOTS_BLUE(2),
    SPOTS_RED(3),
    SPOTS_GREEN(4),
    SPOTS_BROWN(5),
    STRIPES_WHITE(6),
    STRIPES_BLACK(7),
    STRIPES_BLUE(8),
    STRIPES_RED(9),
    STRIPES_GREEN(10),
    STRIPES_BROWN(11);



    private static final VelociraptorBackDecoVariant[] BY_ID = Arrays.stream(values()).sorted(Comparator.
            comparingInt(VelociraptorBackDecoVariant::getId)).toArray(VelociraptorBackDecoVariant[]::new);

    private final int id;

    VelociraptorBackDecoVariant(int id) {
        this.id = id;
    }

    public int getId() {
        return this.id;
    }

    public static VelociraptorBackDecoVariant byId(int id) {
        return BY_ID[id % BY_ID.length];
    }
}
