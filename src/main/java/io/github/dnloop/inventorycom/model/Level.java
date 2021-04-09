package io.github.dnloop.inventorycom.model;

public class Level {
    private int levelOne;
    private int levelTwo;
    private int levelThree;
    private int levelFour;

    public Level(int levelOne, int levelTwo, int levelThree, int levelFour) {
        this.levelOne = levelOne;
        this.levelTwo = levelTwo;
        this.levelThree = levelThree;
        this.levelFour = levelFour;
    }

    public int getLevelOne() {
        return levelOne;
    }

    public void setLevelOne(int levelOne) {
        this.levelOne = levelOne;
    }

    public int getLevelTwo() {
        return levelTwo;
    }

    public void setLevelTwo(int levelTwo) {
        this.levelTwo = levelTwo;
    }

    public int getLevelThree() {
        return levelThree;
    }

    public void setLevelThree(int levelThree) {
        this.levelThree = levelThree;
    }

    public int getLevelFour() {
        return levelFour;
    }

    public void setLevelFour(int levelFour) {
        this.levelFour = levelFour;
    }
}

