package com.sophia.simplerpg.ecs.component

import com.badlogic.ashley.core.Component
import ktx.ashley.mapperFor

class Player : Component {
    companion object {
        val ID = mapperFor<Player>()
    }

}
