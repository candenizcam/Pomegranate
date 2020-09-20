package modules.uiElements.interactableGrid

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.Pixmap
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.pungo.engine.modules.uiElements.interactableGrid.VisualFoil
import modules.lcsModule.GetLcs
import modules.lcsModule.GetLcsRect
import modules.lcsModule.LcsRect
import modules.lcsModule.LcsVariable
import modules.uiElements.UiElement
import modules.visuals.OmniVisual
import kotlin.math.ceil

class InteractableGrid(id: String, row: Int, col: Int, block: LcsRect = GetLcsRect.ofFullScreen(), var gridPadding: LcsVariable = GetLcs.ofZero(), var adjustToPxRatio: Boolean = false): UiElement(id) {
    var igd = InteractableGridData(row, col, false,Foils.BLOCKS)
    private var gridBlock: LcsRect = GetLcsRect.ofFullScreen()
        set(value){
            field = value
            igd.gridBlock = value
        }
    override var block = block
        set(value) {
            field = value
            this.gridBlock = getGridBlock()
        }
    lateinit var grid: Sprite

    var bf = BlockFoil(igd)
    var mf = MenuFoil(igd)
    var ff = VisualFoil(igd,igd.frontSelectedMenu) { b: Boolean->igd.frontVisualSelected =b}
    val bcf = VisualFoil(igd,igd.backSelectedMenu) { b: Boolean->igd.backVisualSelected =b}


    init {
        drawGrid()
        grid.setCenter(block.cX.asPixel(), block.cY.asPixel())
    }

    fun takeBrushesFromFile() {
        bf.blockVisualTypes = SetupReaders.linesReader("setupData/types.txt")
        bf.brushType = "eraser"
        mf.blockMenuLayout.updateBrusherButton(bf.blockVisualTypes) { s: String-> bf.brushType= s}
    }

    private fun getGridBlock(): LcsRect{
        val paddedWidth = block.width-gridPadding*2
        val paddedHeight = block.height-gridPadding*2
        return if(adjustToPxRatio){
            val sp = igd.pxRatio.split("x")
            GetLcsRect.byParameters(paddedWidth,paddedHeight,block.cX,block.cY).getFittingRect(sp[0].toFloat(),sp[1].toFloat())
        } else{
            GetLcsRect.byParameters(paddedWidth,paddedHeight,block.cX,block.cY)
        }
    }

    private fun drawGrid(){
        val w = ceil(gridBlock.width.asPixel()).toInt()
        val h = ceil(gridBlock.height.asPixel()).toInt()
        Pixmap(w,h, Pixmap.Format.RGBA8888).also {
            it.setColor(Color.LIGHT_GRAY) //sets colour permanently
            it.drawRectangle(0,0,w,h)
            //it.drawRectangle(1,1,w-1,h-1)
            for (i in (1 until igd.col)) {
                val xVal = w*(i.toFloat()/igd.col)
                it.fillRectangle(ceil(xVal).toInt()-1,0,3,h)
            }
            for (i in (1 until igd.row)) {
                val yVal = h*(i.toFloat()/igd.row)
                it.fillRectangle(0,ceil(yVal).toInt()-1,w,3)
            }
            grid = Sprite(Texture(it)) //sets the pixmap as the sprite
            it.dispose()
        }
    }

    fun addToBlockType(id: String, v: OmniVisual){
        bf.blockVisualTypes.removeIf { it.first==id }
        bf.blockVisualTypes.add(Pair(id,v))
    }

    override fun touchHandler(mayTouch: Boolean): Boolean {
        return if(mayTouch) {
            if (Gdx.input.isButtonJustPressed(1)) {
                if (igd.menuOpen) {
                    igd.menuOpen = false
                    mf.menuClosed()
                } else {
                    bf.menuOpened()
                    ff.menuOpened()
                    bcf.menuOpened()
                    mf.menuOpened()
                    igd.menuOpen = true
                }
                false
            } else {
                if(igd.menuOpen){
                    mf.touchHandlerForMenu(true,block)
                } else{
                    when (igd.foil) {
                        Foils.BLOCKS -> {
                            bf.touchHandlerForBlocks()
                        }
                        Foils.FRONT -> {
                            ff.touchHandler(true)
                        }
                        Foils.BACK -> {
                            bcf.touchHandler(true)
                        }
                    }
                }
            }
        }
        else{
            false
        }
    }

    override fun update() {
        mf.update()
        if((igd.row!=mf.blockMenuLayout.rowNo)||(igd.col!=mf.blockMenuLayout.colNo)||(igd.pxRatio!=mf.blockMenuLayout.pxRatio)){
            igd.row= mf.blockMenuLayout.rowNo
            igd.col= mf.blockMenuLayout.colNo
            igd.pxRatio = mf.blockMenuLayout.pxRatio
            gridBlock = getGridBlock()
            drawGrid()
            grid.setCenter(block.cX.asPixel(),block.cY.asPixel())
            bf.resizeBlockVisuals(gridBlock.width/igd.col,gridBlock.height/igd.row)
            bf.gridColours= bf.gridColours.filter { it.col<igd.col&&it.row<igd.row }.toMutableList()
            ff.updateVisualTypes()
            bcf.updateVisualTypes()
        }
        ff.visualDataList.forEachIndexed(){index,it->
            it.z = index
        }
        bcf.visualDataList.forEachIndexed(){index,it->
            it.z = index
        }
    }

    override fun relocate(x: LcsVariable, y: LcsVariable) {
        block = GetLcsRect.byParameters(block.width,block.height,x,y)
        grid.setCenter(x.asPixel(),y.asPixel())
        mf.relocate(x,y)
    }

    override fun resize(w: LcsVariable, h: LcsVariable) {
        if((w!=block.width)||(h!=block.height)){
            block = GetLcsRect.byParameters(w,h,block.cX,block.cY)
            drawGrid()
            bf.resizeBlockVisuals(gridBlock.width/igd.col,gridBlock.height/igd.row)
            mf.resize(w,h)
        }

    }

    private fun drawMenu(batch: SpriteBatch){
        mf.draw(batch)
    }

    override fun draw(batch: SpriteBatch, alpha: Float) {
        when(igd.foil){
            Foils.BLOCKS->{
                bcf.draw(batch, 0.1f)
                bf.drawBlocks(batch,1f)
                //ff.draw(batch,0.1f)
            }
            Foils.FRONT->{
                bcf.draw(batch, 0.1f)
                bf.drawBlocks(batch,0.1f)
                ff.draw(batch,1f)
            }
            Foils.BACK->{
                bcf.draw(batch, 1f)
                //bf.drawBlocks(batch,0.1f)
                //ff.draw(batch,0.1f)
            }
        }
        grid.draw(batch)
        if(igd.menuOpen){
            drawMenu(batch)
        }
    }

    override fun dispose() {
        grid.texture.dispose()
    }
}