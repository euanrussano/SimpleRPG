package com.sophia.simplerpg.entityfactory

import com.badlogic.ashley.core.Engine
import com.badlogic.gdx.graphics.Color
import com.sophia.simplerpg.ecs.component.*
import ktx.ashley.entity
import ktx.ashley.with

object EntityFactory {
    fun hero(engine: Engine, globalX: Int, globalY: Int, x: Int, y: Int) {
        engine.entity {
            with<Position> {
                this.x = x
                this.y = y
            }
            with<GlobalPosition> {
                this.x = globalX
                this.y = globalY
                depth = 0
            }
            with<Renderable> {
                symbol = "@"
                color = Color.YELLOW
            }
            with<Player>()
            with<Collision>()
            with<CameraFollow>()
            with<FieldOfView>{
                range = 8
            }
        }
    }

    fun animal(engine: Engine, globalX: Int, globalY: Int, depth: Int, x: Int, y: Int) {
        engine.entity {
            with<Position>{
                this.x = x
                this.y = y
            }
            with<GlobalPosition>{
                this.x = globalX
                this.y = globalY
                this.depth = depth
            }
            with<Renderable>{
                symbol = "R"
                color = Color.RED
            }
            with<RandomMovement>()
            with<Collision>()
            with<FieldOfView>{
                range = 4
            }
        }
    }

}
