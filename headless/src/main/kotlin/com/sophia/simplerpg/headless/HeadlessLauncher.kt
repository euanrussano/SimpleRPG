@file:JvmName("HeadlessLauncher")

package com.sophia.simplerpg.headless

import com.badlogic.gdx.backends.headless.HeadlessApplication
import com.badlogic.gdx.backends.headless.HeadlessApplicationConfiguration
import com.sophia.simplerpg.SimpleRPG

/** Launches the headless application. Can be converted into a server application or a scripting utility. */
fun main() {
    HeadlessApplication(SimpleRPG(), HeadlessApplicationConfiguration().apply {
        // When this value is negative, SimpleRPG#render() is never called:
        updatesPerSecond = -1
    })
}
