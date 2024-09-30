package com.sophia.simplerpg.ecs.component

import com.badlogic.ashley.core.Component
import com.badlogic.gdx.utils.Pool.Poolable
import ktx.ashley.mapperFor

class Name : Component, Poolable {
    var name = "<unnamed>"

    override fun reset() {
        name = "<unnamed>"
    }

    companion object{
        val ID = mapperFor<Name>()
    }

    override fun toString(): String {
        return name
    }

}
