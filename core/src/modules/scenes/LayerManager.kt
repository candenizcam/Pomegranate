package modules.scenes

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.modules.visuals.FontGenerator
import com.modules.visuals.OVL
import modules.visuals.textureHandling.TextureCache
import modules.visuals.fromTiles.TileMapOpener

object LayerManager {
    val layers = mutableListOf<Scene>()
    val scenesToAdd = mutableListOf<Pair<Scene, Boolean>>()

    fun add(scene: Scene, visible: Boolean) {
        if (layers.any { it.id == scene.id }) {
            throw Exception("A scene with that id already exists")
        } else {
            scene.visible = visible
            layers.add(scene)
        }
    }

    fun findScene(id: String): Scene {
        val found = layers.filter { it.id == id }
        when {
            found.isEmpty() -> {
                throw Exception("No scene with that name")
            }
            found.size == 1 -> {
                return found[0]
            }
            else -> {
                throw Exception("There are multiple scenes by that name")
            }
        }
    }

    fun update() {
        layers.sortWith(compareBy({ it.visible }, { it.zOrder }))
        layers.forEach { it.update() }
        scenesToAdd.forEach {
            add(it.first, it.second)
        }
        scenesToAdd.clear()
    }

    fun draw(batch: SpriteBatch) {
        layers.forEach { if (it.visible) it.draw(batch) }
    }

    fun dispose(){
        layers.forEach {
            it.dispose()
        }
        FontGenerator.dispose()
        OVL.dispose()
        TextureCache.dispose()
        TileMapOpener.dispose()
    }
}