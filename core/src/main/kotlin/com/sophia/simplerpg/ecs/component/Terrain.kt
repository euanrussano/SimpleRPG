package com.sophia.simplerpg.ecs.component

import com.badlogic.ashley.core.Component
import ktx.ashley.mapperFor

class Terrain : Component {
    var tiles = Array(0){Array(0){ FLOOR  }}

    companion object{
        val ID = mapperFor<Terrain>()
        val FLOOR = 0
        val WALL = 1
    }

}
