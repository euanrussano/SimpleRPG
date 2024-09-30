package com.sophia.simplerpg.ecs.system

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.systems.IntervalIteratingSystem
import com.badlogic.ashley.systems.IteratingSystem
import com.sophia.simplerpg.ecs.component.*
import ktx.ashley.allOf
import ktx.ashley.get

class AnimalAISystem : IntervalIteratingSystem(
    allOf(
        Animal::class,
        Position::class,
        FieldOfView::class
    ).get(),
    1f) {

    override fun processEntity(entity: Entity) {
        val fieldOfView = entity[FieldOfView.ID]?: return
        val position = entity[Position.ID]?: return
        val globalPosition = entity[GlobalPosition.ID]?: return
        val name = entity[Name.ID]?: return

        val hero = engine.getEntitiesFor(allOf(Player::class).get()).first()
        val heroPosition = hero[Position.ID]?: return
        val heroGlobalPosition = hero[GlobalPosition.ID]?: return

        if (heroGlobalPosition.coords != globalPosition.coords || heroGlobalPosition.depth != globalPosition.depth) return

        if (heroPosition.coords in fieldOfView.visibleTiles){
            println("$name sees hero")
        }

    }

}
