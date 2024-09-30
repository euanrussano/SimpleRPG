package com.sophia.simplerpg.mapgenerator

import com.badlogic.ashley.core.Engine
import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.math.Vector2
import com.sophia.simplerpg.ecs.component.Spawn
import com.sophia.simplerpg.ecs.component.Terrain
import com.sophia.simplerpg.entityfactory.EntityFactory

class RoomsAndCorridorsGenerator : MapGenerator() {

    var rooms = listOf<Rectangle>()

    override fun generateMap(width: Int, height: Int): Array<Array<Int>> {
        rooms = generateRooms(width, height)
        return roomsAndCorridors(width, height, rooms)
    }

    override fun placeEntities(
        engine: Engine,
        tiles: Array<Array<Int>>,
        globalX: Int,
        globalY: Int,
        depth: Int,
        width: Int,
        height: Int
    ) {

        // Place additional entities in the other rooms
        rooms.drop(1).withIndex().forEach { (i, rec) ->
            val (x, y) = rec.center()
            if (MathUtils.randomBoolean()) {
                EntityFactory.rat(engine, globalX, globalY, depth, x, y)
            } else{
                EntityFactory.bat(engine, globalX, globalY, depth, x, y)
            }

        }
    }

    override fun generateSpawnMap(width: Int, height: Int, depth: Int): Array<Array<Int>> {
        val tiles = Array(width) { Array(height) { Spawn.NOTHING} }

        val (heroX, heroY) = rooms.first().center()
        tiles[heroX][heroY] = Spawn.HERO
        return tiles
    }

    private fun generateRooms(width: Int, height: Int): List<Rectangle> {
        val MAX_ROOMS = 30
        val MIN_SIZE = 6
        val MAX_SIZE = 10
        val rooms = mutableListOf<Rectangle>()


        for (i in 0..MAX_ROOMS) {
            val w = MathUtils.random(MIN_SIZE, MAX_SIZE)
            val h = MathUtils.random(MIN_SIZE, MAX_SIZE)
            val x = MathUtils.random(0, width - w)
            val y = MathUtils.random(0, height - h)
            val newRoom = Rectangle(x, y, w, h)
            var ok = rooms.none { it.overlaps(newRoom) }
            if (ok) {
                rooms.add(newRoom)
            }
        }
        return rooms.toList()
    }

    private fun roomsAndCorridors(width: Int, height: Int, rooms: List<Rectangle>): Array<Array<Int>> {
        val tiles = Array(width) { Array(height) { Terrain.WALL} }

        for ((idx, room) in rooms.withIndex()) {
            applyRoomToMap(room, tiles)
            val prevRoom = rooms.getOrNull(idx - 1)?: continue
            val (newX, newY) = room.center()
            val (prevX, prevY) = prevRoom.center()
            if (MathUtils.randomBoolean()) {
                applyHorizontalTunnel(tiles, newX, prevX, prevY)
                applyVerticalTunnel(tiles, newY, prevY, newX)
            } else{
                applyHorizontalTunnel(tiles, newX, prevX, newY)
                applyVerticalTunnel(tiles, newY, prevY, prevX)
            }

        }
        return tiles


    }

    private fun Rectangle(x: Int, y: Int, w: Int, h: Int) = Rectangle(x.toFloat(), y.toFloat(), w.toFloat(), h.toFloat())

    private fun applyRoomToMap(room: Rectangle, map: Array<Array<Int>>) {
        for (y in room.y.toInt() until (room.y + room.height).toInt()) {
            for (x in room.x.toInt() until (room.x + room.width).toInt()) {
                map[x][y] = Terrain.FLOOR
            }
        }
    }

    fun applyHorizontalTunnel(map: Array<Array<Int>>, x1: Int, x2: Int, y: Int) {
        val startX = minOf(x1, x2)
        val endX = maxOf(x1, x2)

        for (x in startX..endX) {
            map[x][y] = Terrain.FLOOR
        }
    }

    fun applyVerticalTunnel(map: Array<Array<Int>>, y1: Int, y2: Int, x: Int) {
        val startY = minOf(y1, y2)
        val endY = maxOf(y1, y2)

        for (y in startY..endY) {
            map[x][y] = Terrain.FLOOR
        }
    }

}

private fun Rectangle.center(): Pair<Int, Int>{
    val vec2 = Vector2()
    this.getCenter(vec2)
    return vec2.x.toInt() to vec2.y.toInt()
}
