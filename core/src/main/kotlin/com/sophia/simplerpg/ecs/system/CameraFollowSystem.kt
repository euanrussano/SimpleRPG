package com.sophia.simplerpg.ecs.system

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.systems.IteratingSystem
import com.badlogic.gdx.graphics.OrthographicCamera
import com.sophia.simplerpg.ecs.component.CameraFollow
import com.sophia.simplerpg.ecs.component.Position
import ktx.ashley.allOf
import ktx.ashley.get
import kotlin.math.abs

class CameraFollowSystem(val camera: OrthographicCamera) : IteratingSystem(
    allOf(
        CameraFollow::class,
        Position::class
    ).get()
) {
    override fun processEntity(entity: Entity, delta: Float) {

        val position = entity[Position.ID]?: return

        val dx = position.x - camera.position.x
        val dy =   position.y - camera.position.y
        if (abs(dx) < 1f && abs(dy) < 1f) return
        camera.position.add(dx*delta, dy*delta, 0f)
        camera.update()
    }

}
