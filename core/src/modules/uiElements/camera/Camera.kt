package com.pungo.modules.uiElements.camera

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import modules.lcsModule.LcsRect
import modules.lcsModule.LcsVariable
import modules.uiElements.UiElement

class Camera(id: String, block: LcsRect, var subject: UiElement, zoomRect: ZoomRect): UiElement(id) {
    var zoomRect = zoomRect
        set(value){
            field = value
            subject.resize(block.width/value.width,block.height/value.height)
            subject.relocate(block.cX + subject.block.width*(0.5f - value.cX),block.cY + subject.block.height*(0.5f - value.cY))
        }


    override var block: LcsRect = block
        set(value) {
            field = value
            subject.resize(value.width/zoomRect.width,value.height/zoomRect.height)
            subject.relocate(value.cX + subject.block.width*(0.5f - zoomRect.cX),value.cY + subject.block.height*(0.5f - zoomRect.cY))
        }



    override fun touchHandler(mayTouch: Boolean): Boolean {
        return subject.touchHandler(mayTouch)
    }

    override fun update() {
        subject.update()
    }

    override fun relocate(x: LcsVariable, y: LcsVariable) {
        block = block.relocateTo(x,y)
    }

    override fun resize(w: LcsVariable, h: LcsVariable) {
        block = block.resizeTo(w,h)
    }

    override fun draw(batch: SpriteBatch, alpha: Float) {
        subject.draw(batch,alpha)
    }

    override fun dispose() {
        subject.dispose()
    }
}