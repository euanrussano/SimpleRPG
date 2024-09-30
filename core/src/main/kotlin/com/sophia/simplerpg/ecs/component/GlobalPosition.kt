package com.sophia.simplerpg.ecs.component

import com.badlogic.ashley.core.Component
import com.badlogic.gdx.utils.Pool.Poolable
import ktx.ashley.mapperFor

class GlobalPosition: Component, Poolable {
    val coords: Pair<Int, Int>
        get() = Pair(x, y)
    var depth: Int = 0
    var x = 0
    var y = 0

    override fun reset() {
        x = 0
        y = 0
        depth = 0
    }

    companion object{
        val ID = mapperFor<GlobalPosition>()
    }


}
