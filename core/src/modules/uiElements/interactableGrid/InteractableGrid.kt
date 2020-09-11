package modules.uiElements.interactableGrid

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.Pixmap
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import modules.lcsModule.GetLcs
import modules.lcsModule.GetLcsRect
import modules.lcsModule.LcsRect
import modules.lcsModule.LcsVariable
import modules.uiElements.UiElement
import modules.visuals.OmniVisual
import kotlin.math.ceil
import kotlin.math.floor

class InteractableGrid(id: String, var row: Int, var col: Int, block: LcsRect = GetLcsRect.ofFullScreen(), var gridPadding: LcsVariable = GetLcs.ofZero(), var adjustToSquares: Boolean = false): UiElement(id) {
    private var gridBlock: LcsRect = GetLcsRect.ofFullScreen()
    override var block = block
    set(value) {
        field = value
        this.gridBlock = getGridBlock()
    }
    lateinit var grid: Sprite
    private var bf = BlockFoil()
    private var mf = MenuFoil()

    var foil = Foils.BLOCKS
    init {
        drawGrid()
        grid.setCenter(block.cX.asPixel(), block.cY.asPixel())
    }

    fun takeBrushesFromFile(){
        bf.blockVisualTypes = SetupReaders.linesReader("setupData/types.txt")
        bf.brushType = "eraser"
        mf.updateBrusherButton(bf.blockVisualTypes) { s: String-> bf.brushType= s}
    }

    private fun getGridBlock(): LcsRect{
        val paddedWidth = block.width-gridPadding*2
        val paddedHeight = block.height-gridPadding*2
        return if(adjustToSquares){
            val paddedSingleWidth = paddedWidth.asLcs()/col
            val paddedSingleHeight = paddedHeight.asLcs()/row
            if(paddedSingleHeight<paddedSingleWidth){
                GetLcsRect.byParameters(GetLcs.byLcs(paddedSingleHeight*col),GetLcs.byLcs(paddedSingleHeight*row),block.cX,block.cY)
            }else{
                GetLcsRect.byParameters(GetLcs.byLcs(paddedSingleWidth*col),GetLcs.byLcs(paddedSingleWidth*row),block.cX,block.cY)
            }
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
            for (i in (1 until col)) {
                val xVal = w*(i.toFloat()/col)
                it.fillRectangle(ceil(xVal).toInt()-1,0,3,h)
            }
            for (i in (1 until row)) {
                val yVal = h*(i.toFloat()/row)
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



    private fun rowAsY(r: Int): LcsVariable{
        return gridBlock.hStart + gridBlock.height - gridBlock.height/row*(r.toFloat()+0.5f)
    }

    private fun colAsX(c: Int): LcsVariable{
        return gridBlock.wStart + gridBlock.width/col*(c.toFloat()+0.5f)
    }

    private fun xAsCol(x: LcsVariable): Int {
        val w = gridBlock.width/col
        return floor((x-gridBlock.wStart).asLcs()/w.asLcs()).toInt()
    }

    private fun yAsRow(y: LcsVariable): Int {
        val h = gridBlock.height/row
        return floor((GetLcs.ofHeight(1f)-(y+gridBlock.hStart)).asLcs()/h.asLcs()).toInt()
    }



    override fun touchHandler(mayTouch: Boolean): Boolean {
        if(mayTouch){
            when(foil){
                Foils.MENU->{
                    return mf.touchHandlerForMenu(mayTouch,block) { f: Foils->foil=f}
                }
                Foils.BLOCKS->{
                    mf.touchHandlerForMenu(false,block) { f: Foils->foil=f}
                    return bf.touchHandlerForBlocks(gridBlock,row,col,{x: LcsVariable->xAsCol(x)},{x: LcsVariable->yAsRow(x)},{f: Foils->foil=f})
                }
            }
        }
        return false
    }

    override fun update() {
        if((row!=mf.rowNo)||(col!=mf.colNo)){
            row= mf.rowNo
            col= mf.colNo
            gridBlock = getGridBlock()
            drawGrid()
            grid.setCenter(block.cX.asPixel(),block.cY.asPixel())
            bf.resizeBlockVisuals(gridBlock.width/col,gridBlock.height/row)
            bf.gridColours= bf.gridColours.filter { it.col<col&&it.row<row }.toMutableList()
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
            bf.resizeBlockVisuals(gridBlock.width/col,gridBlock.height/row)
            mf.resize(w,h)
        }

    }



    private fun drawMenu(batch: SpriteBatch){
        mf.draw(batch)
    }

    override fun draw(batch: SpriteBatch) {
        when(foil){
            Foils.BLOCKS->{
                bf.drawBlocks(batch,{x: Int->colAsX(x)},{y: Int->rowAsY(y)})
                grid.draw(batch)
            }
            Foils.MENU->{
                bf.drawBlocks(batch,{x: Int->colAsX(x)},{y: Int->rowAsY(y)})
                grid.draw(batch)
                drawMenu(batch)
            }
        }

    }

    override fun dispose() {
        grid.texture.dispose()
    }
}