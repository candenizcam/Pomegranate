package modules.Layout

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import modules.LcsModule.GetLcsRect
import modules.LcsModule.LcsRect
import modules.LcsModule.LcsVariable

class ColLayout(id: String, rect: LcsRect) : OmniLayout(id, rect){


    override fun isDividedToBiased(n: List<Float>){
        stepsList = n
        val total = n.sum()
        val pe = mutableListOf<UiElement>()
        var start = block.wStart

        for(i in n.indices){
            val step = block.width*(n[i]/total)
            val rect = GetLcsRect.byBorders(start,start+step,block.hStart,block.hEnd)
            start += step
            pe.add(PlaceholderElement("${id}n",rect = rect))
        }
        elements = pe

    }

    override fun replaceElement(n: Int, e: UiElement, stretch: Boolean){
        e.stretch=stretch
        if(stretch){
            e.resize(elements[n].block.width,elements[n].block.height)
        }
        e.relocate(elements[n].block.cX,elements[n].block.cY)
        elements[n] = e
    }



}