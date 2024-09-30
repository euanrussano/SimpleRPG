package com.sophia.simplerpg.ecs.component

import com.badlogic.ashley.core.Component
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.utils.Pool.Poolable
import ktx.ashley.mapperFor

class Renderable : Component, Poolable {
    var symbol = "?"
    var color = Color.WHITE

    override fun reset() {
        symbol = "?"
        color = Color.WHITE
    }

    companion object{
        val ID = mapperFor<Renderable>()
    }

}
