package com.sophia.simplerpg.screen.level

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.math.Vector2
import com.sophia.simplerpg.SimpleRPG
import com.sophia.simplerpg.ecs.component.*
import com.sophia.simplerpg.ecs.system.*
import com.sophia.simplerpg.mapgenerator.RoomsAndCorridorsGenerator
import com.sophia.simplerpg.mapgenerator.SimpleMapGenerator
import ktx.app.KtxScreen
import ktx.ashley.add
import ktx.ashley.entity
import ktx.ashley.with

class LevelScreen(val game: SimpleRPG): KtxScreen {

    val engine = game.engine
    val batch = SpriteBatch()
    val camera = OrthographicCamera()
    private val viewportSize = 30f
    val font = BitmapFont().apply {
        setUseIntegerPositions(false)
        data.setScale(1f/data.lineHeight)
    }

    val keyboardController = game.keyboardController


    override fun show() {
        super.show()
        camera.setToOrtho(false, viewportSize*camera.viewportWidth/camera.viewportHeight, viewportSize)
        camera.position.set(camera.viewportWidth/2f, camera.viewportHeight/2f, 0f)
        Gdx.input.inputProcessor = keyboardController


        engine.add {
            addSystem(HeroSpawnSystem())
            addSystem(PlayerMovementSystem(keyboardController))
            addSystem(RandomMovementSystem())
            addSystem(AnimalAISystem())

            addSystem(MovementSystem())
            addSystem(FieldOfViewSystem())

            addSystem(CameraFollowSystem(camera))
            addSystem(TerrainRenderingSystem(batch, camera, font))
            addSystem(RenderingSystem(batch, camera, font))
        }

        RoomsAndCorridorsGenerator().apply(engine, 0, 0, 0, 80, 50)
        SimpleMapGenerator().apply(engine, 1, 0, 0, 80, 50)


    }

    override fun render(delta: Float) {
        super.render(delta)
        engine.update(delta)
        keyboardController.update()
    }

    override fun resize(width: Int, height: Int) {
        super.resize(width, height)
        camera.setToOrtho(false, viewportSize*width/height, viewportSize)
    }


}
