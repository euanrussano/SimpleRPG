package com.sophia.simplerpg.controller

import ktx.app.KtxInputAdapter

class KeyboardController: KtxInputAdapter {
    val keysPressed = mutableSetOf<Int>()

    override fun keyDown(keycode: Int): Boolean {
        keysPressed.add(keycode)
        return true
    }

    override fun keyUp(keycode: Int): Boolean {
        keysPressed.remove(keycode)
        return true
    }

    fun update() {
        keysPressed.clear()
    }

}
