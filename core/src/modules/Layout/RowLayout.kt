package modules.Layout

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import modules.LcsModule.GetLcsRect
import modules.LcsModule.LcsRect
import modules.LcsModule.LcsVariable

class RowLayout(id: String, rect: LcsRect): OmniLayout(id, rect) {


    /** Makes a biased partition of rows based on a bias array
     */
    override fun isDividedToBiased(n: List<Float>){
        stepsList = n
        val total = n.sum()
        val pe = mutableListOf<UiElement>()
        val pb = mutableListOf<LcsRect>()
        var start = block.hStart
        for(i in n.indices){
            val step = block.height*(n[i]/total)
            val rect = GetLcsRect.byBorders(block.wStart,block.wEnd,start,start+step)
            start += step
            pe.add(PlaceholderElement("${id}n"))
            pb.add(rect)
        }
        elements = pe
        blocks = pb
    }


}