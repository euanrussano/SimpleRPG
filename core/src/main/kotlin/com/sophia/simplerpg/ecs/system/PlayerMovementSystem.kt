package com.sophia.simplerpg.ecs.system

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.core.EntitySystem
import com.badlogic.ashley.systems.IteratingSystem
import com.badlogic.gdx.Input.Keys
import com.sophia.simplerpg.controller.KeyboardController
import com.sophia.simplerpg.ecs.component.Player
import com.sophia.simplerpg.ecs.component.WantsToMove
import ktx.ashley.allOf
import ktx.ashley.configureEntity
import ktx.ashley.with

class PlayerMovementSystem(val controller: KeyboardController) : IteratingSystem(
    allOf(
        Player::class
    ).get()
) {
    override fun processEntity(entity: Entity, p1: Float) {
        var dx = 0
        var dy = 0
        if (Keys.W in controller.keysPressed) {
            dy = 1
        }
        else if (Keys.S in controller.keysPressed) {
            dy = -1
        }
        else if (Keys.A in controller.keysPressed) {
            dx = -1
        }
        else if (Keys.D in controller.keysPressed) {
            dx = 1
        }

        if (dx to dy != 0 to 0) {
            engine.configureEntity(entity){
                with<WantsToMove>{
                    this.dx = dx
                    this.dy = dy
                }
            }
        }

    }

}
