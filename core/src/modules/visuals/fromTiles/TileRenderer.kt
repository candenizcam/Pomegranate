package com.pungo.modules.visuals

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import modules.lcsModule.GetLcs
import modules.lcsModule.GetLcsRect
import modules.lcsModule.LcsRect
import modules.lcsModule.LcsVariable
import modules.visuals.OmniVisual
import modules.visuals.VisualSize
import kotlin.math.asinh

class TileRenderer(var rowNo: Int, var colNo: Int, block: LcsRect = GetLcsRect.ofFullScreen(), visualSize: VisualSize=VisualSize.FIT_ELEMENT, scaleFactor: Float=1f): OmniVisual(visualSize = visualSize,scaleFactor = scaleFactor) {
    var typesList = mutableListOf<Pair<String,OmniVisual>>()
    var mapData = mutableListOf<Tile>()


    override fun draw(batch: SpriteBatch, alpha: Float) {
        val singleWidth = (imageBlock.width/colNo)
        val singleHeight = (imageBlock.height/rowNo)
        mapData.forEach {
            typesList.first { it2->it2.first==it.type }.second.also {it2->
                it2.resize(singleWidth+GetLcs.byPixel(2f),singleHeight+GetLcs.byPixel(2f))
                it2.relocate(imageBlock.wStart + singleWidth*(it.column + 0.5f),imageBlock.hStart + singleHeight*(it.row + 0.5f))
                it2.draw(batch,alpha)
            }
        }
    }

    override fun changeActiveSprite(ns: Int) {}

    override fun update() {
        typesList.forEach {
            it.second.update()
        }
    }

    override fun recolour(c: Color) {
        typesList.forEach {
            it.second.recolour(c)
        }
    }

    override fun copy(): OmniVisual {
        return TileRenderer(rowNo,colNo,block,visualSize, scaleFactor)
    }

    override fun dispose() {
        typesList.forEach {
            it.second.dispose()
        }
    }

    override fun setFlip(x: Boolean, y: Boolean) {}

    override fun updateVisual() {
        val singleWidth = imageBlock.width/colNo
        val singleHeight = imageBlock.height/rowNo
        typesList.forEach {
            it.second.resize(singleWidth, singleHeight)
        }
    }
}