package com.sophia.simplerpg.mapgenerator

import com.badlogic.ashley.core.Engine
import com.badlogic.gdx.math.MathUtils
import com.sophia.simplerpg.ecs.component.GlobalPosition
import com.sophia.simplerpg.ecs.component.Position
import com.sophia.simplerpg.ecs.component.Spawn
import com.sophia.simplerpg.ecs.component.Terrain
import com.sophia.simplerpg.entityfactory.EntityFactory
import ktx.ashley.entity
import ktx.ashley.with

class SimpleMapGenerator: MapGenerator() {
    override fun generateMap(width: Int, height: Int): Array<Array<Int>> {
        val tiles= Array(width) { Array(height) { Terrain.FLOOR} }

        // surround with walls
        for (x in 0..width-1) {
            tiles[x][0] = Terrain.WALL
            tiles[x][height-1] = Terrain.WALL
        }
        for (y in 0..height-1) {
            tiles[0][y] = Terrain.WALL
            tiles[width-1][y] = Terrain.WALL
        }

        // random walls
        for (x in 1..width-2) {
            for (y in 1..height-2) {
                if (Math.random() < 0.2) {
                    tiles[x][y] = Terrain.WALL
                }
            }
        }
        return tiles
    }

    override fun placeEntities(engine: Engine, tiles: Array<Array<Int>>, globalX: Int, globalY: Int, depth: Int, width: Int, height: Int) {
        (0..5).forEach { i ->
            if (MathUtils.randomBoolean()){
                EntityFactory.bat(engine, globalX, globalY, depth,i*2+1, 4)
            } else {
                EntityFactory.rat(engine, globalX, globalY, depth,i*2+1, 4)
            }

        }
    }

    override fun generateSpawnMap(width: Int, height: Int, depth: Int): Array<Array<Int>> {
        val tiles= Array(width) { Array(height) { Spawn.NOTHING} }
        val heroX = width / 2
        val heroY = height / 2
        tiles[heroX][heroY] = Spawn.HERO
        return tiles
    }

}
