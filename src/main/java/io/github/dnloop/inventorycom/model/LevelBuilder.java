package io.github.dnloop.inventorycom.model;

public class LevelBuilder {
    private int levelOne = 0;
    private int levelTwo = 0;
    private int levelThree = 0;
    private int levelFour = 0;

    public LevelBuilder() {}

    public Level buildLevel() {
        return new Level(levelOne, levelTwo, levelThree, levelFour);
    }

    public LevelBuilder levelOne(int levelOne) {
        this.levelOne = levelOne;
        return this;
    }

    public LevelBuilder levelTwo(int levelTwo) {
        this.levelTwo = levelTwo;
        return this;
    }

    public LevelBuilder levelThree(int levelThree) {
        this.levelThree = levelThree;
        return this;
    }

    public LevelBuilder levelFour(int levelFour) {
        this.levelFour = levelFour;
        return this;
    }
}
