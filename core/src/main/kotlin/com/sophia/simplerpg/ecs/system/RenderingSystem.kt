package com.sophia.simplerpg.ecs.system

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.systems.IteratingSystem
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.sophia.simplerpg.ecs.component.*
import ktx.ashley.allOf
import ktx.ashley.get
import ktx.graphics.use

class RenderingSystem(
    val batch: SpriteBatch,
    val camera: OrthographicCamera,
    val font: BitmapFont) : IteratingSystem(
    allOf(
        Position::class,
        GlobalPosition::class,
        Renderable::class
    ).get()
) {
    private var globalX = 0
    private var globalY = 0
    private var visibleTiles = setOf<Pair<Int, Int>>()
    private var revealedTiles = setOf<Pair<Int, Int>>()


    override fun update(deltaTime: Float) {
        val hero = engine.getEntitiesFor(allOf(Player::class, GlobalPosition::class).get()).first()
        val globalPosition = hero[GlobalPosition.ID]?: return
        globalX = globalPosition.x
        globalY = globalPosition.y
        val fieldOfView = hero[FieldOfView.ID]?: return
        visibleTiles = fieldOfView.visibleTiles
        revealedTiles = fieldOfView.revealedTiles

        camera.update()
        batch.use(camera){
            super.update(deltaTime)
        }
    }

    override fun processEntity(entity: Entity, p1: Float) {
        val globalPosition = entity[GlobalPosition.ID]?: return
        if (globalPosition.x != globalX || globalPosition.y != globalY) return

        val position = entity[Position.ID]?: return
        val renderable = entity[Renderable.ID]?: return

        if (position.coords !in visibleTiles) return

        font.color = renderable.color
        font.draw(batch, renderable.symbol, position.x.toFloat(), position.y+1f)
    }

}

