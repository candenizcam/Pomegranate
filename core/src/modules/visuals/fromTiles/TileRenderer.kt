package com.pungo.modules.visuals

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import modules.lcsModule.GetLcs
import modules.lcsModule.GetLcsRect
import modules.visuals.OmniVisual
import modules.visuals.ScalingType

class TileRenderer(var rowNo: Int, var colNo: Int, scalingType: ScalingType=ScalingType.FIT_ELEMENT, scaleFactor: Float=1f): OmniVisual() {
    var typesList = mutableListOf<Pair<String,OmniVisual>>()
    var mapData = mutableListOf<Tile>()


    override fun draw(batch: SpriteBatch, alpha: Float) {
        //visualSizeData.updateImageBlock(block)
        val singleWidth = (block.width/colNo)
        val singleHeight = (block.height/rowNo)
        mapData.forEach {
            typesList.first { it2->it2.first==it.type }.second.also {it2->

                it2.resize(singleWidth+GetLcs.byPixel(2f),singleHeight+GetLcs.byPixel(2f))
                it2.relocate(block.wStart + singleWidth*(it.column + 0.5f),block.hStart + singleHeight*(it.row + 0.5f))
                //val r = GetLcsRect.byParameters(singleWidth+GetLcs.byPixel(2f),singleHeight+GetLcs.byPixel(2f))
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
        return TileRenderer(rowNo,colNo)
    }

    override fun dispose() {
        typesList.forEach {
            it.second.dispose()
        }
    }

    override fun setFlip(x: Boolean, y: Boolean) {}

    /*
    override fun updateVisual() {
        val singleWidth = imageBlock.width/colNo
        val singleHeight = imageBlock.height/rowNo
        typesList.forEach {
            it.second.resize(singleWidth, singleHeight)
        }
    }

     */
}