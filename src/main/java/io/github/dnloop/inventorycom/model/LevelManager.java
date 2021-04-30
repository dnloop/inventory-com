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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Class used to implement validation and control the insertion of records into a hierarchy.
 */
public class LevelManager {

    private static final Log log = LogFactory.getLog(LevelManager.class);

    /**
     * According to the state of the node perform increments
     * in the corresponding level leaving the rest untouched.
     * The result will be used to insert into a category level.
     */
    public static void insertLevel(Level level) {
        if (level.getLevelOne() == 0) {
            // insert into level1
            log.debug("L1: " + level);
            level.incrementLevelOne();
        } else if (level.getLevelTwo() == 0 && level.getLevelThree() == 0) {
            // insert into level2
            log.debug("L2: " + level);
            level.incrementLevelTwo();
        } else if (level.getLevelTwo() != 0 && level.getLevelThree() == 0) {
            // insert into level3
            log.debug("L3: " + level);
            level.incrementLevelThree();
        } else if (level.getLevelThree() != 0) {
            // insert into level4
            log.debug("L4: " + level);
            level.incrementLevelFour();
        }
    }

    /**
     * Similar to {@link  #insertLevel(Level)} but it increases the first
     * value of the level found given that the other level values are 0.
     * Control is performed backwards, that is looking at the values of from
     * the last level column.
     * This is required in case an adjacent node is needed.
     * The result will be used to insert into a category level.
     */
    public static void incrementLevel(Level level) {
        if (level.getLevelOne() == 0
            && level.getLevelTwo() == 0
            && level.getLevelThree() == 0
            && level.getLevelFour() == 0) {
            // insert into level1
            log.debug("L1: " + level);
            level.incrementLevelOne();
        } else if (level.getLevelOne() != 0 && level.getLevelTwo() == 0) {
            // insert into level1
            log.debug("L1: " + level);
            level.incrementLevelOne();
        } else if (level.getLevelTwo() != 0 && level.getLevelThree() == 0) {
            // insert into level2
            log.debug("L2: " + level);
            level.incrementLevelTwo();
        } else if (level.getLevelThree() != 0 && level.getLevelFour() == 0) {
            // insert into level3
            log.debug("L3: " + level);
            level.incrementLevelThree();
        } else {
            // insert into level4
            log.debug("L4: " + level);
            level.incrementLevelFour();
        }
    }

    /**
     * Simple test method.
     */
    public static void main(String[] args) {
        LevelBuilder builder = new LevelBuilder();
        LevelManager levelManager = new LevelManager();
        levelManager.insertIntoHierarchy(builder);
        levelManager.insertAdjacent(builder);
    }

    private void insertIntoHierarchy(LevelBuilder builder) {
        System.out.println("[ Beginning of Hierarchy Insertion Test. ]");
        Level emptyRoot = builder.levelOne(0)
                                 .levelTwo(0)
                                 .levelThree(0)
                                 .levelFour(0)
                                 .buildLevel(); // L1
        Level expectedEmptyRoot = builder.levelOne(1)
                                         .levelTwo(0)
                                         .levelThree(0)
                                         .levelFour(0)
                                         .buildLevel(); // L1
        Level root = builder.levelOne(1).buildLevel(); // L2
        Level expectedRoot = builder.levelOne(1).levelTwo(1).buildLevel(); // L2
        Level targetNode = builder.levelOne(2).levelTwo(1).buildLevel(); // L3
        Level expectedTargetNode = builder.levelOne(2).levelTwo(1).levelThree(1).buildLevel(); // L3
        Level fullNode = builder
                .levelOne(2)
                .levelTwo(1)
                .levelThree(1)
                .levelFour(1)
                .buildLevel(); // L4
        Level expectedFullNode = builder
                .levelOne(2)
                .levelTwo(1)
                .levelThree(1)
                .levelFour(2)
                .buildLevel(); // L4

        LevelManager.insertLevel(emptyRoot);
        LevelManager.insertLevel(root);
        LevelManager.insertLevel(targetNode);
        LevelManager.insertLevel(fullNode);

        processAssertion(
                emptyRoot, expectedEmptyRoot,
                root, expectedRoot,
                targetNode, expectedTargetNode,
                fullNode, expectedFullNode
        );
        log.info("[ End of Hierarchy Insertion Test. ]");
    }

    private void processAssertion(
            Level emptyRoot,
            Level expectedEmptyRoot,
            Level root,
            Level expectedRoot,
            Level targetNode,
            Level expectedTargetNode,
            Level fullNode,
            Level expectedFullNode
    ) {
        System.out.println("[ State of the objects after operation. ]");
        assert emptyRoot.equals(expectedEmptyRoot);
        log.info("emptyRoot ---> " + emptyRoot);
        assert root.equals(expectedRoot);
        log.info("root ---> " + root);
        assert targetNode.equals(expectedTargetNode);
        log.info("targetNode ---> " + targetNode);
        assert fullNode.equals(expectedFullNode);
        log.info("fullNode ---> " + fullNode);
    }

    private void insertAdjacent(LevelBuilder builder) {
        System.out.println("[ Beginning of Adjacency Insertion Test. ]");
        Level emptyRoot = builder
                .levelOne(0)
                .levelTwo(0)
                .levelThree(0)
                .levelFour(0)
                .buildLevel(); // L1
        Level expectedEmptyRoot = builder
                .levelOne(0)
                .levelTwo(0)
                .levelThree(0)
                .levelFour(0)
                .buildLevel(); // L1
        Level root = builder.levelOne(1).buildLevel(); // L2
        Level expectedRoot = builder.levelOne(1).buildLevel(); // L2
        Level targetNode = builder.levelOne(2).levelTwo(1).buildLevel(); // L3
        Level expectedTargetNode = builder.levelOne(2).levelTwo(1).buildLevel(); // L3
        Level fullNode = builder
                .levelOne(2)
                .levelTwo(1)
                .levelThree(1)
                .levelFour(1)
                .buildLevel(); // L4
        Level expectedFullNode = builder
                .levelOne(2)
                .levelTwo(1)
                .levelThree(1)
                .levelFour(1)
                .buildLevel(); // L4
        LevelManager.incrementLevel(emptyRoot);
        LevelManager.incrementLevel(root);
        LevelManager.incrementLevel(targetNode);
        LevelManager.incrementLevel(fullNode);

        processAssertion(
                emptyRoot, expectedEmptyRoot,
                root, expectedRoot,
                targetNode, expectedTargetNode,
                fullNode, expectedFullNode
        );
        System.out.println("[ End of Adjacency Insertion Test. ]");
    }
}

