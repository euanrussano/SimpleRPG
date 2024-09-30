package com.sophia.simplerpg.ecs.system

import com.badlogic.ashley.core.EntitySystem
import com.sophia.simplerpg.ecs.component.GlobalPosition
import com.sophia.simplerpg.ecs.component.Player
import com.sophia.simplerpg.ecs.component.Spawn
import com.sophia.simplerpg.entityfactory.EntityFactory
import ktx.ashley.allOf
import ktx.ashley.get
import kotlin.math.E

class HeroSpawnSystem : EntitySystem() {

    override fun update(deltaTime: Float) {
        super.update(deltaTime)
        val hero = engine.getEntitiesFor(allOf(Player::class).get()).firstOrNull()
        if (hero != null) return

        // iterate over the locations and find the first one with a hero spawn point, then
        // spawn and return
        for (entity1 in engine.getEntitiesFor(allOf(Spawn::class).get())){
            val spawn = entity1[Spawn.ID]?: continue
            val globalPosition = entity1[GlobalPosition.ID]?: continue

            val spawnTiles = spawn.tiles
            for (x in spawnTiles.indices) {
                for (y in spawnTiles[0].indices) {
                    if (spawnTiles[x][y] == Spawn.HERO) {
                        EntityFactory.hero(engine, globalPosition.x, globalPosition.y, x, y)
                        return
                    }
                }

            }
        }

    }

}
