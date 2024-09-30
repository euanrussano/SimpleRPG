package com.sophia.simplerpg.entityfactory

import com.badlogic.ashley.core.Engine
import com.badlogic.gdx.graphics.Color
import com.sophia.simplerpg.ecs.component.*
import ktx.ashley.entity
import ktx.ashley.with

object EntityFactory {
    fun hero(engine: Engine, globalX: Int, globalY: Int, x: Int, y: Int) {
        engine.entity {
            with<Name>{
                name = "Hero"
            }
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

    // Bat flee from hero
    fun bat(engine: Engine, globalX: Int, globalY: Int, depth: Int, x: Int, y: Int) = animal(engine, globalX, globalY, depth, x, y, "B", Color.BLUE, "Bat")

    // Rat is curious toward the hero
    fun rat(engine: Engine, globalX: Int, globalY: Int, depth: Int, x: Int, y: Int) = animal(engine, globalX, globalY, depth, x, y, "R", Color.RED, "Rat")
    private fun animal(engine: Engine, globalX: Int, globalY: Int, depth: Int, x: Int, y: Int, symbol: String, color: Color, name: String) {
        engine.entity {
            with<Name>{
                this.name = name
            }
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
                this.symbol = symbol
                this.color = color
            }
            with<RandomMovement>()
            with<Collision>()
            with<FieldOfView>{
                range = 4
            }
            with<Animal>{}
        }
    }

}
