package by.sakeplays.sakesdinos.client.variant;

import java.util.Arrays;
import java.util.Comparator;

public enum VelociraptorEyesVariant {

    YELLOW(0),
    RED(1),
    BLUE(2),
    GREEN(3);

    private static final VelociraptorEyesVariant[] BY_ID = Arrays.stream(values()).sorted(Comparator.
            comparingInt(VelociraptorEyesVariant::getId)).toArray(VelociraptorEyesVariant[]::new);

    private final int id;

    VelociraptorEyesVariant(int id) {
        this.id = id;
    }

    public int getId() {
        return this.id;
    }

    public static VelociraptorEyesVariant byId(int id) {
        return BY_ID[id % BY_ID.length];
    }
}
