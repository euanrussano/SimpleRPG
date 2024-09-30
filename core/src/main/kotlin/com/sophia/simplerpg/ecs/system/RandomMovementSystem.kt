package com.sophia.simplerpg.ecs.system

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.systems.IntervalIteratingSystem
import com.badlogic.ashley.systems.IteratingSystem
import com.badlogic.gdx.math.MathUtils
import com.sophia.simplerpg.ecs.component.RandomMovement
import com.sophia.simplerpg.ecs.component.WantsToMove
import ktx.ashley.allOf
import ktx.ashley.configureEntity
import ktx.ashley.with

class RandomMovementSystem : IntervalIteratingSystem(
    allOf(
        RandomMovement::class
    ).get(),
     1f
) {
    override fun processEntity(entity: Entity) {
        if (MathUtils.randomBoolean()) return

        val dx = MathUtils.random(-1, 1)
        val dy = if (dx == 0) MathUtils.random(-1, 1) else 0

        engine.configureEntity(entity){
            with<WantsToMove>{
                this.dx = dx
                this.dy = dy
            }
        }
    }

}
