package modules.uiElements.Layouts

import modules.LcsModule.LcsRect

abstract class SegmentedLayout(id: String, rect: LcsRect): OmniLayout(id,rect) {


    override fun adjustSubBlocks(newBlock: LcsRect) {

        divideBlocksToBiased(stepsList)
    }


    abstract fun divideBlocksToBiased(n: List<Float>) //divides the layout to a list of bias numbers
    /** Creates an equally divided layout with n members
     * This class works with isDividedToBiased but it has simpler usage
     */
    fun isEquallyDividedTo(n: Int){
        val b = mutableListOf<Float>()
        for(i in 0 until n){
            b.add(1f)
        }
        divideBlocksToBiased(b)
    }

    protected var stepsList = listOf(1f)
    init{
        isEquallyDividedTo(1)
    }

    override fun adjustElements(){
        for(i in elements.indices){
            elements[i] = adjustElementTo(elements[i],subBlocks[i])
        }
    }

}