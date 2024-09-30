package com.sophia.simplerpg

import com.badlogic.ashley.core.PooledEngine
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.Texture.TextureFilter.Linear
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.sophia.simplerpg.controller.KeyboardController
import com.sophia.simplerpg.screen.level.LevelScreen
import ktx.app.KtxGame
import ktx.app.KtxScreen
import ktx.app.clearScreen
import ktx.assets.disposeSafely
import ktx.assets.toInternalFile
import ktx.async.KtxAsync
import ktx.graphics.use

class SimpleRPG : KtxGame<KtxScreen>() {

    val keyboardController = KeyboardController()
    val engine = PooledEngine()

    override fun create() {
        KtxAsync.initiate()

        addScreen(LevelScreen(this))
        setScreen<LevelScreen>()
    }
}
