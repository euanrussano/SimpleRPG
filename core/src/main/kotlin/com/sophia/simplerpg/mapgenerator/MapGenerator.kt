package com.sophia.simplerpg.mapgenerator

import com.badlogic.ashley.core.Engine
import com.sophia.simplerpg.ecs.component.GlobalPosition
import com.sophia.simplerpg.ecs.component.Spawn
import com.sophia.simplerpg.ecs.component.Terrain
import ktx.ashley.entity
import ktx.ashley.with

abstract class MapGenerator {

    abstract fun generateMap(width: Int, height: Int): Array<Array<Int>>

    abstract fun placeEntities(engine: Engine, tiles: Array<Array<Int>>, globalX: Int, globalY: Int, depth: Int, width: Int, height: Int)

    fun apply(engine: Engine, globalX: Int, globalY: Int, depth: Int, width: Int, height: Int) {
        // Generate the map
        val tiles = generateMap(width, height)
        val spawnTiles = generateSpawnMap(width, height, depth)

        // Add the terrain to the engine
        engine.entity {
            with<Terrain> {
                this.tiles = tiles
            }
            with<Spawn>{
                this.tiles = spawnTiles
            }
            with<GlobalPosition> {
                this.x = globalX
                this.y = globalY
                this.depth = depth
            }
        }

        // Place the entities on the map
        placeEntities(engine, tiles, globalX, globalY, depth, width, height)
    }

    abstract fun generateSpawnMap(width: Int, height: Int, depth: Int): Array<Array<Int>>

}
