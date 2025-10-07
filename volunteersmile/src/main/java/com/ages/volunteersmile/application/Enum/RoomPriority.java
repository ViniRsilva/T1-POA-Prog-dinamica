package com.ages.volunteersmile.application.Enum;

public enum RoomPriority {
    LOW(3),
    MEDIUM(2),
    HIGH(1);

    private final int level;

    RoomPriority(int level) {
        this.level = level;
    }

    public int getLevel() {
        return level;
    }
}
