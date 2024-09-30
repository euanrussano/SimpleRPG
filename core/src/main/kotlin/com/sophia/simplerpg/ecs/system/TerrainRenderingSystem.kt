package com.sophia.simplerpg.ecs.system

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.core.EntitySystem
import com.badlogic.ashley.systems.IteratingSystem
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.sophia.simplerpg.ecs.component.FieldOfView
import com.sophia.simplerpg.ecs.component.GlobalPosition
import com.sophia.simplerpg.ecs.component.Player
import com.sophia.simplerpg.ecs.component.Terrain
import ktx.ashley.allOf
import ktx.ashley.get
import ktx.graphics.use

class TerrainRenderingSystem(
    val batch: SpriteBatch,
    val camera: OrthographicCamera,
    val font: BitmapFont
) : EntitySystem() {

    val terrainSymbolMap = mapOf(
        Terrain.FLOOR to ".",
        Terrain.WALL to "#"
    )

    private var visibleTiles = setOf<Pair<Int, Int>>()
    private var revealedTiles = setOf<Pair<Int, Int>>()

    override fun update(deltaTime: Float) {
        val hero = engine.getEntitiesFor(allOf(Player::class, GlobalPosition::class).get()).first()
        val globalPosition = hero[GlobalPosition.ID]?: return
        val x = globalPosition.x
        val y = globalPosition.y
        val terrainEntity = engine.getEntitiesFor(allOf(Terrain::class, GlobalPosition::class).get()).firstOrNull {
            it[GlobalPosition.ID]?.x == x && it[GlobalPosition.ID]?.y == y
        }?: return
        val fieldOfView = hero[FieldOfView.ID]?: return
        visibleTiles = fieldOfView.visibleTiles
        revealedTiles = fieldOfView.revealedTiles


        camera.update()
        batch.use(camera){
            renderTerrain(terrainEntity)
        }
    }

    private fun renderTerrain(entity: Entity) {
        val terrain = entity[Terrain.ID]?: return


        for (x in terrain.tiles.indices) {
            for (y in terrain.tiles[0].indices) {
                if (x to y !in revealedTiles) continue
                font.color = if (x to y in visibleTiles) Color.GREEN else Color.GRAY
                val tile = terrain.tiles[x][y]
                font.draw(batch, terrainSymbolMap[tile], x.toFloat(), y+1f)
            }
        }
    }

}
