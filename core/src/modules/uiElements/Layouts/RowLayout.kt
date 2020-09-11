package modules.uiElements.Layouts

import modules.LcsModule.GetLcsRect
import modules.LcsModule.LcsRect
import modules.uiElements.PlaceholderElement
import modules.uiElements.UiElement

class RowLayout(id: String, rect: LcsRect) : SegmentedLayout(id, rect) {


    /** Makes a biased partition of rows based on a bias array
     */
    override fun divideBlocksToBiased(n: List<Float>) {
        stepsList = n
        val total = n.sum()
        val pb = mutableListOf<LcsRect>()
        var end = block.hEnd
        for (i in n.indices) {
            val step = block.height * (n[i] / total)
            val rect = GetLcsRect.byBorders(block.wStart, block.wEnd, end-step, end)
            end -= step
            pb.add(rect)
        }
        subBlocks = pb
    }


}