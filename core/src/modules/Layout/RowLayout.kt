package modules.Layout

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import modules.LcsModule.GetLcsRect
import modules.LcsModule.LcsRect
import modules.LcsModule.LcsVariable

class RowLayout(id: String, rect: LcsRect): OmniLayout(id, rect) {


    override fun isDividedToBiased(n: List<Float>){
        stepsList = n
        val total = n.sum()
        val pe = mutableListOf<UiElement>()
        var start = block.hStart
        for(i in n.indices){
            val step = block.height*(n[i]/total)
            val rect = GetLcsRect.byBorders(block.wStart,block.wEnd,start,start+step)
            start += step
            pe.add(PlaceholderElement("${id}n",rect = rect))
        }
        elements = pe
    }

    override fun replaceElement(n: Int, e: UiElement, stretch: Boolean){
        e.stretch = stretch
        if(stretch){
            e.resize(elements[n].block.width,elements[n].block.height)
        }
        e.relocate(elements[n].block.cX,elements[n].block.cY)
        elements[n] = e
    }
}