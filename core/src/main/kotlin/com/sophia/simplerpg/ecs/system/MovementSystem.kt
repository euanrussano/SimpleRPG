package com.sophia.simplerpg.ecs.system

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.systems.IteratingSystem
import com.sophia.simplerpg.ecs.component.*
import ktx.ashley.allOf
import ktx.ashley.get
import ktx.ashley.remove

class MovementSystem : IteratingSystem(
    allOf(
        Position::class,
        WantsToMove::class
    ).get()
) {
    override fun processEntity(entity: Entity, delta: Float) {
        val position = entity[Position.ID]?: return
        val globalPosition = entity[GlobalPosition.ID]?: return
        val wantsToMove = entity[WantsToMove.ID]?: return

        val newX = position.x + wantsToMove.dx
        val newY = position.y + wantsToMove.dy

        val globalX = globalPosition.x
        val globalY = globalPosition.y

        if (!isBlocked(globalX, globalY, newX, newY)){
            position.x = newX
            position.y = newY
        }


        entity.remove<WantsToMove>()
    }

    private fun isBlocked(globalX: Int, globalY: Int, newX: Int, newY: Int): Boolean {
        if (isBlockedByTile(globalX, globalY,newX, newY)) return true
        else if (isBlockedByEntity(globalX, globalY,newX, newY)) return true
        return false
    }

    private fun isBlockedByEntity(globalX: Int, globalY: Int, newX: Int, newY: Int): Boolean {
        for (otherEntity in engine.getEntitiesFor(allOf(Position::class, Collision::class).get())) {
            val otherPosition = otherEntity[Position.ID]?: continue
            val otherGlobalPosition = otherEntity[GlobalPosition.ID]?: continue
            if (otherGlobalPosition.x != globalX || otherGlobalPosition.y != globalY) continue
            if (newX == otherPosition.x && newY == otherPosition.y) {
                return true
            }
        }
        return false
    }

    private fun isBlockedByTile(globalX: Int, globalY: Int,newX: Int, newY: Int): Boolean {
        val terrainEntity = engine.getEntitiesFor(allOf(Terrain::class).get()).first {
            it[GlobalPosition.ID]?.x == globalX && it[GlobalPosition.ID]?.y == globalY
        }?: return true
        val terrain = terrainEntity[Terrain.ID]?: return true
        val currentTileMap = terrain.tiles

        val tile = currentTileMap.getOrNull(newX)?.getOrNull(newY)?:return true
        return tile == Terrain.WALL
    }

}
