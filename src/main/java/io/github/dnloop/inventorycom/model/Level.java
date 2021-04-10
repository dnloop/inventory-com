/*
 *     Inventory-Com: Inventory and Commerce Management Application.
 *     Copyright (C) 2021. dnloop.
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <https://www.gnu.org/licenses/>.
 *
 */

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

    @Override
    public String toString() {
        return "Level { " +
               "levelOne=" + levelOne +
               ", levelTwo=" + levelTwo +
               ", levelThree=" + levelThree +
               ", levelFour=" + levelFour +
               " }";
    }

    public void incrementLevelOne() {
        ++this.levelOne;
    }

    public void incrementLevelTwo() {
        ++this.levelTwo;
    }

    public void incrementLevelThree() {
        ++this.levelThree;
    }

    public void incrementLevelFour() {
        ++this.levelFour;
    }

}

