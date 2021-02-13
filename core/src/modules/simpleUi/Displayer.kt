package modules.simpleUi

import com.badlogic.gdx.files.FileHandle
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.Pixmap
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.pungo.modules.basic.geometry.Rectangle
import com.pungo.modules.visuals.PixmapGenerator
import modules.visuals.TextureCache


class Displayer: Building {
    constructor(imageCollection: ImageCollection){
        this.imageCollection = imageCollection
    }

    constructor(ps: PunSprite){
        imageCollection.add(ps)
    }

    constructor(fileHandle: FileHandle){
        imageCollection.add(PunSprite(TextureCache.openTexture(fileHandle)))
    }

    constructor(pixmap: Pixmap, colour: Color = Color.WHITE){
        imageCollection.add(PunSprite(pixmap).also {
            it.color = colour
        })
    }

    constructor(colour: Color){
        imageCollection.add(PunSprite(PixmapGenerator.singleColour()).also {
            it.color = colour
        })
    }

    var imageCollection = ImageCollection()

    override fun update() {
        imageCollection.update()
    }

    override fun draw(batch: SpriteBatch, rectangle: Rectangle, baseWidth: Float, baseHeight: Float, u1: Float, u2: Float, v1: Float, v2: Float) {
        imageCollection.yieldImage()?.also {
            it.setRectangle(rectangle)
            it.u = u1
            it.u2 = u2
            it.v = v1
            it.v2 = v2
            it.draw(batch)
        }
    }

    override fun hoverFunction(hovering: Boolean) {

    }


    fun copy(): Displayer {
        return Displayer(imageCollection.copy())
    }
}