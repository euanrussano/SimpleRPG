package com.sophia.simplerpg.ecs.component

import com.badlogic.ashley.core.Component
import com.badlogic.gdx.utils.Pool.Poolable
import ktx.ashley.mapperFor

class FieldOfView: Component, Poolable {
    var range: Int = 4
    var revealedTiles = setOf<Pair<Int, Int>>()
    var visibleTiles = setOf<Pair<Int, Int>>()

    override fun reset() {
        range = 4
        revealedTiles = setOf()
        visibleTiles = setOf()
    }

    companion object{
        val ID = mapperFor<FieldOfView>()
    }
}
