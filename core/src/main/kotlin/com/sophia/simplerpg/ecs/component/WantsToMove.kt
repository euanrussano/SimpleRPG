package com.sophia.simplerpg.ecs.component

import com.badlogic.ashley.core.Component
import com.badlogic.gdx.utils.Pool.Poolable
import ktx.ashley.mapperFor

class WantsToMove: Component, Poolable {
    var dx = 0
    var dy = 0

    override fun reset() {
        dx = 0
        dy = 0
    }

    companion object{
        val ID = mapperFor<WantsToMove>()
    }



}
