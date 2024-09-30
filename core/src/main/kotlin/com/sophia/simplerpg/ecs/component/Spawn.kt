package com.sophia.simplerpg.ecs.component

import com.badlogic.ashley.core.Component
import ktx.ashley.mapperFor

class Spawn : Component {
    var tiles = Array(0) { Array(0) { 0 } }

    companion object{
        val ID = mapperFor<Spawn>()
        val NOTHING = 0
        val HERO = 1
    }

}
