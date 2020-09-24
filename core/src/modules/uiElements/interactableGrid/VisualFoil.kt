package modules.uiElements.interactableGrid

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import modules.lcsModule.GetLcs
import modules.lcsModule.GetLcsRect
import modules.visuals.*
import java.io.File
import kotlin.math.roundToInt

class VisualFoil(var igd: InteractableGridData, var selectedMenu: ImageSelectedLayout, var visualSelector: (Boolean)->Unit) {
    var visualDataList= mutableListOf<VisualData>()
    var dragLocation = Pair(0.1f,0.1f)


    var visualTypeList= mutableListOf<Pair<String,OmniVisual>>()
    private var selectFrame = ColouredBox(colour = Color(0f,0.6f,0f,0.5f),visualSize = VisualSize.FIT_ELEMENT)
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

    fun addVisualByType(selectedFile: File) {
        val name = selectedFile.name
        val visual = when(selectedFile.extension){
            "png","jpg" -> SingleTexture(Gdx.files.internal(selectedFile.absolutePath),visualSize = VisualSize.FIT_ELEMENT)
            "atlas"-> AtlasTexture(Gdx.files.internal(selectedFile.absolutePath),visualSize = VisualSize.FIT_ELEMENT)
            else->{
                return
            }
        }
        if(visualTypeList.none { it.first==name }){
            visualTypeList.add(Pair(name,visual))
        }
        visualDataList.add(VisualData(name,0.5f,0.5f,1,1f,1f))
        updateVisualTypes()
    }

    fun updateVisualTypes(){
        val pxPair = igd.getPxPair()
        visualTypeList.forEach {
            val w = igd.gridBlock.width*(it.second.originalBlock.width.asPixel()/pxPair.first.toFloat())
            val h = igd.gridBlock.height*(it.second.originalBlock.height.asPixel()/pxPair.second.toFloat())
            it.second.resize(w,h)
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
                visualDataList.reversed().forEachIndexed() {index,it->
                    val thisBlock = GetLcsRect.byParameters(igd.gridBlock.width*it.width,igd.gridBlock.height*it.height,igd.gridBlock.wStart + igd.gridBlock.width*it.posX,igd.gridBlock.hStart + igd.gridBlock.height*it.posY)
                    if(thisBlock.contains(GetLcs.ofX(),GetLcs.ofY())){
                        selectedVisual = visualDataList.size-1-index



                        return true
                    }
                }
            } else{
                val it = visualDataList[selectedVisual]
                val thisBlock = GetLcsRect.byParameters(igd.gridBlock.width*it.width,igd.gridBlock.height*it.height,igd.gridBlock.wStart + igd.gridBlock.width*it.posX,igd.gridBlock.hStart + igd.gridBlock.height*it.posY)
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
                visualDataList[selectedVisual].posX = (relativeX*l[0]).roundToInt()/l[0]
                visualDataList[selectedVisual].posY = (relativeY*l[1]).roundToInt()/l[1]
                selectedMenu.rX =(visualDataList[selectedVisual].posX*l[0]).toInt()
                selectedMenu.rY =(visualDataList[selectedVisual].posY*l[1]).toInt()
            }
        }else{
            carrying = false
        }
        return false
    }

    fun menuOpened(){
        carrying = false
    }

    fun draw(batch: SpriteBatch, alpha: Float){
        visualDataList.forEachIndexed() {index, it ->
            visualTypeList.first { it2-> it2.first==it.type }.apply {
                second.relocate(igd.gridBlock.wStart + igd.gridBlock.width*it.posX,igd.gridBlock.hStart + igd.gridBlock.height*it.posY)
                if(selectedVisual==index){
                    selectFrame.reBlock(second.imageBlock.resizeTo(1.05f))
                    selectFrame.draw(batch,alpha)
                }
                second.draw(batch,alpha)
            }

        }
    }

    fun dispose(){
        visualTypeList.forEach {
            it.second.dispose()
        }
    }







}