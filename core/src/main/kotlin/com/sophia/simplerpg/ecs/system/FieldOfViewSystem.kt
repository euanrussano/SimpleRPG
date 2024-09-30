package com.sophia.simplerpg.ecs.system

import com.badlogic.ashley.core.Engine
import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.core.EntityListener
import com.badlogic.ashley.core.EntitySystem
import com.badlogic.gdx.math.Intersector
import com.badlogic.gdx.math.Vector2
import com.sophia.simplerpg.ecs.component.*
import ktx.ashley.allOf
import ktx.ashley.get
import ktx.ashley.onEntityAdded
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min
import kotlin.math.roundToInt

class FieldOfViewSystem : EntitySystem(), EntityListener {

    val entitiesToUpdate = mutableListOf<Entity>()

    val startVec = Vector2()
    val endVec = Vector2()
    val centerVec = Vector2()

    override fun addedToEngine(engine: Engine) {
        super.addedToEngine(engine)
        engine.addEntityListener(allOf(WantsToMove::class).get(), this)

        // whenever an entity with field of view is added, need to be updated
        engine.onEntityAdded(allOf(FieldOfView::class).get()){
            entitiesToUpdate.add(it)
        }

        // when added to engine, collect all entities with field of view to initial update
        entitiesToUpdate.addAll(engine.getEntitiesFor(allOf(FieldOfView::class).get()))
    }

    override fun update(deltaTime: Float) {
        super.update(deltaTime)

        for (entity in entitiesToUpdate) {
            updateEntity(entity)
        }
        entitiesToUpdate.clear()
    }

    private fun updateEntity(entity: Entity) {
        val fieldOfView = entity[FieldOfView.ID]?: return
        val position = entity[Position.ID]?: return
        val globalPosition = entity[GlobalPosition.ID]?: return
        val terrainEntity = engine.getEntitiesFor(allOf(Terrain::class, GlobalPosition::class).get()).first {
            it[GlobalPosition.ID]?.x == globalPosition.x && it[GlobalPosition.ID]?.y == globalPosition.y
        }
        val tilemap = terrainEntity[Terrain.ID]?.tiles ?: return


        val visibleTiles = mutableSetOf<Pair<Int, Int>>()

        for (x in -fieldOfView.range..fieldOfView.range) {
            for (y in -fieldOfView.range..fieldOfView.range) {
                if (isBlocked(tilemap, position.x, position.y, position.x + x, position.y + y)) continue
                visibleTiles += Pair(position.x + x, position.y + y)
            }
        }

        fieldOfView.visibleTiles = visibleTiles.toSet()

        fieldOfView.revealedTiles += fieldOfView.visibleTiles
    }

    private fun isBlocked(tilemap: Array<Array<Int>>, startX: Int, startY: Int, endX: Int, endY: Int): Boolean {
        // Create vectors for the start and end positions
        startVec.set(startX + 0.5f, startY + 0.5f) // Center of the start tile
        endVec.set(endX + 0.5f, endY + 0.5f)         // Center of the end tile

        // Bresenham's Line Algorithm to walk through all grid tiles along the line
        var x = startX
        var y = startY
        val dx = abs(endX - startX)
        val dy = abs(endY - startY)
        val sx = if (startX < endX) 1 else -1
        val sy = if (startY < endY) 1 else -1
        var err = dx - dy

        while (true) {
            // Skip the check if we're at the end position
            if (x == endX && y == endY) break

            // Check the current tile (x, y) to see if it's blocking
            val tile = tilemap.getOrNull(x)?.getOrNull(y)
            if (tile != null && tile != Terrain.FLOOR) {
                // Perform an intersection test with the circle around the tile
                val centerVec = centerVec.set(x + 0.5f, y + 0.5f)
                if (Intersector.intersectSegmentCircle(startVec, endVec, centerVec, 0.25f)) {
                    return true // Blocked by this tile
                }
            }

            // If we've reached the end tile, break the loop
            if (x == endX && y == endY) break

            // Move to the next tile along the line
            val e2 = 2 * err
            if (e2 > -dy) {
                err -= dy
                x += sx
            }
            if (e2 < dx) {
                err += dx
                y += sy
            }
        }

        return false // No blocking tiles found
    }

    override fun entityAdded(entity: Entity) {
        entitiesToUpdate.add(entity)
    }

    override fun entityRemoved(p0: Entity?) {
    }

}
