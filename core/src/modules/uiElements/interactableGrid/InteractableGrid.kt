package modules.uiElements.interactableGrid

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import modules.lcsModule.GetLcs
import modules.lcsModule.GetLcsRect
import modules.lcsModule.LcsRect
import modules.lcsModule.LcsVariable
import modules.uiElements.UiElement
import modules.visuals.OmniVisual
import modules.visuals.PixmapGenerator

class InteractableGrid(id: String, row: Int, col: Int, block: LcsRect = GetLcsRect.ofFullScreen(), var gridPadding: LcsVariable = GetLcs.ofZero(), var adjustToPxRatio: Boolean = false): UiElement(id) {
    var igd = InteractableGridData(row, col, false,Foils.GENERAL)
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
    private var grid = PixmapGenerator.grid(row,col,gridBlock)
    var bf = BlockFoil(igd)
    var tf = BlockFoil(igd)
    var mf = MenuFoil(igd)
    var ff = VisualFoil(igd,mf.frontMenuLayout) { b: Boolean->igd.frontVisualSelected =b}
    val bcf = VisualFoil(igd,mf.backMenuLayout) { b: Boolean->igd.backVisualSelected =b}



    fun takeBrushesFromFile() {

        bf.blockVisualTypes = SetupReaders.linesReader("setupData/types.txt")
        bf.brushType = "eraser"
        //reader of tile brushes here
        tf.brushType = "eraser"
        tf.blockVisualTypes = SetupReaders.atlasReader(Gdx.files.local("tiles/tiles.atlas"))
        mf.blockMenuLayout.updateBrusherButton(bf.blockVisualTypes) { s: String-> bf.brushType= s}
        mf.tilesMenuLayout.updateBrusherButton(tf.blockVisualTypes) {s: String->tf.brushType=s}


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
                    tf.menuOpened()
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
                        Foils.GENERAL ->{
                            false
                        }
                        Foils.BLOCKS -> {
                            false
                            bf.touchHandlerForBlocks()
                        }
                        Foils.FRONT -> {
                            false
                            ff.touchHandler(true)
                        }
                        Foils.BACK -> {
                            false
                            bcf.touchHandler(true)
                        }
                        Foils.TILES ->{
                            false
                            tf.touchHandlerForBlocks()
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
        if((igd.row!=mf.generalMenuLayout.rowNo)||(igd.col!=mf.generalMenuLayout.colNo)||(igd.pxRatio!=mf.generalMenuLayout.pxRatio)){
            igd.row= mf.generalMenuLayout.rowNo
            igd.col= mf.generalMenuLayout.colNo
            igd.pxRatio = mf.generalMenuLayout.pxRatio
            gridBlock = getGridBlock()
            grid = PixmapGenerator.grid(igd.row,igd.col,gridBlock)

            bf.resizeBlockVisuals(gridBlock.width/igd.col,gridBlock.height/igd.row)
            bf.gridColours= bf.gridColours.filter { it.col<igd.col&&it.row<igd.row }.toMutableList()
            ff.updateVisualTypes()
            bcf.updateVisualTypes()
            tf.resizeBlockVisuals(gridBlock.width/igd.col,gridBlock.height/igd.row)
            tf.gridColours = tf.gridColours.filter { it.col<igd.col&&it.row<igd.row }.toMutableList()


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
        grid.relocate(x,y)
        mf.relocate(x,y)
    }

    override fun resize(w: LcsVariable, h: LcsVariable) {
        if((w!=block.width)||(h!=block.height)){
            block = GetLcsRect.byParameters(w,h,block.cX,block.cY)
            grid = PixmapGenerator.grid(igd.row,igd.col,gridBlock)
            bf.resizeBlockVisuals(gridBlock.width/igd.col,gridBlock.height/igd.row)
            tf.resizeBlockVisuals(gridBlock.width/igd.col,gridBlock.height/igd.row)
            mf.resize(w,h)
        }

    }

    private fun drawMenu(batch: SpriteBatch){
        mf.draw(batch)
    }

    override fun draw(batch: SpriteBatch, alpha: Float) {
        when(igd.foil){

            Foils.GENERAL->{

            }
            Foils.BLOCKS->{
                tf.drawBlocks(batch,0.1f)
                bcf.draw(batch, 0.1f)
                bf.drawBlocks(batch,1f)
                //ff.draw(batch,0.1f)
            }
            Foils.FRONT->{
                tf.drawBlocks(batch,0.1f)
                bcf.draw(batch, 0.1f)
                bf.drawBlocks(batch,0.1f)
                ff.draw(batch,1f)
            }
            Foils.BACK->{
                tf.drawBlocks(batch,0.1f)
                bcf.draw(batch, 1f)
                //bf.drawBlocks(batch,0.1f)
                //ff.draw(batch,0.1f)
            }
            Foils.TILES->{
                tf.drawBlocks(batch,1f)
                bf.drawBlocks(batch,0.1f)

            }


        }
        grid.draw(batch,1f)
        if(igd.menuOpen){
            drawMenu(batch)
        }
    }

    override fun dispose() {
        grid.dispose()
        bf.dispose()
        bcf.dispose()
        ff.dispose()
        mf.dispose()
        tf.dispose()
    }
}