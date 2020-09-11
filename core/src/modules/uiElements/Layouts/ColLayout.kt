package modules.uiElements.Layouts

import modules.LcsModule.GetLcsRect
import modules.LcsModule.LcsRect
import modules.uiElements.PlaceholderElement
import modules.uiElements.UiElement

class ColLayout(id: String, rect: LcsRect) : SegmentedLayout(id, rect) {


    /** Makes a biased partition of rows based on a bias array
     */
    override fun divideBlocksToBiased(n: List<Float>) {
        stepsList = n
        val total = n.sum()
        val pb = mutableListOf<LcsRect>()
        var start = block.wStart
        for (i in n.indices) {
            val step = block.width * (n[i] / total)
            val rect = GetLcsRect.byBorders(start, start + step, block.hStart, block.hEnd)
            start += step
            pb.add(rect)
        }
        subBlocks = pb
    }





}