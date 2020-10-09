package modules.uiElements.interactableGrid

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import modules.lcsModule.GetLcs
import modules.lcsModule.GetLcsRect
import modules.lcsModule.LcsRect
import modules.visuals.*
import modules.visuals.fromPath.SingleTexture
import modules.visuals.fromPath.AtlasTexture
import modules.visuals.fromPixmap.PixmapGenerator
import java.io.File

class VisualFoil(var igd: InteractableGridData, var selectedMenu: ImageSelectedLayout, var visualSelector: (Boolean)->Unit) {
    var visualDataList= mutableListOf<VisualData>()
    var dragLocation = Pair(0.1f,0.1f)


    var visualTypeList= mutableListOf<Pair<String,OmniVisual>>()
    private var selectFrame = PixmapGenerator.singleColour(c = Color(0f,0.6f,0f,0.5f))
    private var selectedVisual = -1
    set(value){
        field = value
        visualSelector(value!=-1)
        if(value!=-1){
            val  l = igd.pxRatio.split("x").map{it.toFloat() }
            selectedMenu.cXIncrementer.setValue((visualDataList[selectedVisual].posX*l[0]).toInt())
            selectedMenu.cYIncrementer.setValue((visualDataList[selectedVisual].posY*l[1]).toInt())
        }
    }
    private var carrying = false


    init{
        selectedMenu.delButton.clicked ={
            if(selectedVisual!=-1){
                visualDataList.removeAt(selectedVisual)
                selectedVisual = -1
            }
        }
        selectedMenu.copyButton.clicked = {
            if(selectedVisual!=-1){
                visualDataList.add(visualDataList[selectedVisual].copy())
                selectedVisual = visualDataList.lastIndex
            }
        }
        selectedMenu.cXIncrementer.numChangeFunc = {
            val  l = igd.pxRatio.split("x").map{it.toFloat() }
            visualDataList[selectedVisual].posX = (selectedMenu.cXIncrementer.theValue as Int)/l[0]
        }
        selectedMenu.cYIncrementer.numChangeFunc = {
            val  l = igd.pxRatio.split("x").map{it.toFloat() }
            visualDataList[selectedVisual].posY = (selectedMenu.cYIncrementer.theValue as Int)/l[1]
        }
        selectedMenu.oneUp.clicked = {
            swapSelectedVisual(selectedVisual+1)
        }
        selectedMenu.oneDown.clicked = {
            swapSelectedVisual(selectedVisual-1)
        }
        selectedMenu.toBottom.clicked = {
            swapSelectedVisual(0)
        }
        selectedMenu.toTop.clicked = {
            swapSelectedVisual(visualDataList.lastIndex)
        }
    }

    //fun addVisualByType(selectedFile: File,)

    fun addVisualByType(selectedFile: File,posX: Float=0.5f,posY: Float=0.5f,z:Int=1, width: Float=1f,height: Float=1f) {
        val name = selectedFile.name
        val visual = when(selectedFile.extension){
            "png","jpg" -> SingleTexture(Gdx.files.internal(selectedFile.absolutePath))
            "atlas"-> AtlasTexture(Gdx.files.internal(selectedFile.absolutePath))
            else->{
                return
            }
        }
        if(visualTypeList.none { it.first==name }){
            visualTypeList.add(Pair(name,visual))
        }
        val o= visual.getOriginalRect()
        visualDataList.add(VisualData(name,selectedFile.path,posX,posY,z,width,height,o.width.asPixel().toInt(),o.height.asPixel().toInt()))
        updateVisualTypes()
    }

    fun updateVisualTypes(){
        val pxPair = igd.getPxPair()
        visualTypeList.forEach {
            it.second.getOriginalRect().also{originalRect->
                val w = igd.gridBlock.width*(originalRect.width.asPixel()/pxPair.first.toFloat())
                val h = igd.gridBlock.height*(originalRect.height.asPixel()/pxPair.second.toFloat())
                it.second.resize(w,h)
            }



        }
    }


    fun swapSelectedVisual(ind1: Int){
        if(ind1 in visualDataList.indices){
            visualDataList[selectedVisual] = visualDataList[ind1].also {
                visualDataList[ind1] = visualDataList[selectedVisual]
            }
            selectedVisual = ind1
        }
    }

    fun touchHandler(mayTouch: Boolean): Boolean {
        if(Gdx.input.justTouched()){
            if(selectedVisual == -1){
                visualDataList.reversed().forEachIndexed() { index, it ->
                    //println("${}")
                    val relBlock : LcsRect
                    visualTypeList.first { it2-> it2.first==it.type }.apply {
                        relBlock = second.getImageRect(second.block)
                    }
                    val p = igd.getPxPair()

                    val thisBlock = GetLcsRect.byParameters(relBlock.width, relBlock.height, igd.gridBlock.wStart+ igd.gridBlock.width *it.posX, igd.gridBlock.hStart + igd.gridBlock.height *it.posY)
                    //val thisBlock = GetLcsRect.byParameters(igd.gridBlock.width * relBlock.width, igd.gridBlock.height * relBlock.height, igd.gridBlock.wStart + igd.gridBlock.width * relBlock.cX, igd.gridBlock.hStart + igd.gridBlock.height * relBlock.cY)
                    if (thisBlock.contains(GetLcs.ofX(), GetLcs.ofY())) {
                        selectedVisual = visualDataList.size - 1 - index



                        return true
                    }
                }
            } else{
                val it = visualDataList[selectedVisual]
                val relBlock = visualTypeList.first { it2-> it2.first==it.type }.second.getImageRect()
                val thisBlock = GetLcsRect.byParameters(relBlock.width, relBlock.height, igd.gridBlock.wStart+ igd.gridBlock.width *it.posX, igd.gridBlock.hStart + igd.gridBlock.height *it.posY)
                //val thisBlock = GetLcsRect.byParameters(igd.gridBlock.width*relBlock.width,igd.gridBlock.height*relBlock.height,igd.gridBlock.wStart + igd.gridBlock.width*relBlock.cX,igd.gridBlock.hStart + igd.gridBlock.height*relBlock.cY)

                if(!thisBlock.contains(GetLcs.ofX(),GetLcs.ofY())){
                    selectedVisual = -1
                    return false
                }else{
                    carrying = true
                    val a = ((GetLcs.ofX()-thisBlock.wStart)/thisBlock.width).asLcs()
                    val b = ((GetLcs.ofY()-thisBlock.hStart)/thisBlock.height).asLcs()
                    dragLocation = Pair(a,b)
                    return true
                }
            }
        }
        if(Gdx.input.isTouched){
            if(carrying){
                val relativeX = ((GetLcs.ofX()-igd.gridBlock.wStart)/(igd.gridBlock.width)).asLcs()-dragLocation.first+0.5f
                val relativeY = ((GetLcs.ofY()-igd.gridBlock.hStart)/(igd.gridBlock.height)).asLcs()-dragLocation.second+0.5f
                val  l = igd.pxRatio.split("x").map{it.toFloat() }
                //visualDataList[selectedVisual].posX = (relativeX*l[0]).roundToInt()/l[0]
                //visualDataList[selectedVisual].posY = (relativeY*l[1]).roundToInt()/l[1]
                //selectedMenu.rX =(visualDataList[selectedVisual].posX*l[0]).toInt()
                //selectedMenu.rY =(visualDataList[selectedVisual].posY*l[1]).toInt()
            }
        }else{
            carrying = false
        }
        return false
    }

    fun menuOpened(){
        carrying = false
    }

    fun draw(batch: SpriteBatch, alpha: Float) {
        visualDataList.forEachIndexed() { index, it ->
            visualTypeList.first { it2 -> it2.first == it.type }.apply {
                second.relocate(igd.gridBlock.wStart + igd.gridBlock.width * it.posX, igd.gridBlock.hStart + igd.gridBlock.height * it.posY)
                if (selectedVisual == index) {
                    //selectFrame.visualSizeData.updateImageBlock(second.visualSizeData.imageBlock.resizeTo(1.05f))
                    //selectFrame.reBlock(second.imageBlock.resizeTo(1.05f))
                    selectFrame.reBlock(second.getImageRect().resizeTo(1.05f))
                    selectFrame.draw(batch, alpha)
                }
                second.draw(batch, alpha)
            }

        }
    }

    fun dispose(){
        visualTypeList.forEach {
            it.second.dispose()
        }
    }







}