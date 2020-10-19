package com.pomegranate

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.files.FileHandle
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Sprite
import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.jsonArray
import modules.lcsModule.GetLcsRect
import modules.visuals.subTexture.SubTexture

data class AnimateJson(var path: FileHandle) {
    val meta: Meta
    val texture: Texture
    val frames: Map<String, MutableList<FrameData>>

    init {

        val f = path.readString().drop(3)
        val j: Map<String, JsonElement> = Json.decodeFromString(f)
        meta = Json.decodeFromString(j["meta"].toString())
        val auxFrames: Map<String, JsonElement> = Json.decodeFromString(j["frames"].toString())
        val that = mutableMapOf<String, MutableList<FrameData>>()
        auxFrames.forEach {
            val id = it.key.dropLast(4)
            if (that.containsKey(id)) {
                that[id]!!.add(Json.decodeFromString(it.value.toString()))
            } else {
                that[id] = mutableListOf(Json.decodeFromString(it.value.toString()))
            }

        }
        frames = that.toMap()
        val pathList = path.path().split("/").toMutableList().dropLast(1).joinToString("/")
        texture = Texture(Gdx.files.internal("$pathList/${meta.image}"))

    }

    fun getSubTextures(region: String? = null): MutableList<SubTexture> {

        val rels: MutableList<FrameData>
        if (region == null) {
            rels = mutableListOf()
            frames.forEach {
                rels.addAll(it.value)
            }
        } else {
            rels = frames[region]!!
        }
        return rels.map { SubTexture(Sprite(texture, it.frame.x, it.frame.y, it.frame.w, it.frame.h)) }.toMutableList()
    }

    fun dispose() {
        texture.dispose()
    }


    @Serializable
    data class FrameData(var frame: Coords, var rotated: Boolean, var trimmed: Boolean, var spriteSourceSize: Coords, var sourceSize: Coords)

    @Serializable
    data class Coords(var x: Int = 0, var y: Int = 0, var w: Int = 0, var h: Int = 0)

    @Serializable
    data class Meta(var app: String, var version: String, var image: String, var format: String, var size: Coords, var scale: Int)
}