package com.sophia.simplerpg.ecs.component

import com.badlogic.ashley.core.Component
import com.badlogic.gdx.utils.Pool.Poolable
import ktx.ashley.mapperFor

class Position: Component, Poolable {
    val coords: Pair<Int, Int>
        get() = Pair(x, y)
    var x = 0
    var y = 0

    override fun reset() {
        x = 0
        y = 0
    }

    companion object{
        val ID = mapperFor<Position>()
    }


}
