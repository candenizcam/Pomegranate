package modules.LcsModule

/** This is the getter for the data class, lcs rect
 *
 */
object GetLcsRect {
    /** calls a rectangle by borders
     * wStart and wEnd are width edges
     * hStart and hEnd are height edges
     */
    fun byBorders(wStart: LcsVariable, wEnd: LcsVariable, hStart: LcsVariable, hEnd: LcsVariable): LcsRect {
        val width = wEnd-wStart
        val height = hEnd-hStart
        val cX = (wEnd+wStart)/2f
        val cY = (hEnd+hStart)/2f
        return LcsRect(width, height, cX,cY,wStart,wEnd,hStart,hEnd )
    }

    /** calls a rectangle by parameters
     * width and height are width and height
     * cX and cY are centre x & y
     */
    fun byParameters(w: LcsVariable, h: LcsVariable, cX: LcsVariable, cY: LcsVariable): LcsRect {
        val wStart = cX-(w/2f)
        val wEnd = cX+(w/2f)
        val hStart = cY-(h/2f)
        val hEnd = cY+(h/2f)
        return LcsRect(w, h, cX,cY,wStart,wEnd,hStart,hEnd )
    }

    /** creates a 0 0 0 0 rectangle
     */
    fun getZero(): LcsRect {
        val l = GetLcs.ofZero()
        return LcsRect(l,l,l,l,l,l,l,l)
    }

    fun ofFullScreen():LcsRect{
        return GetLcsRect.byBorders(GetLcs.ofWidth(0f), GetLcs.ofWidth(1f), GetLcs.ofHeight(0f), GetLcs.ofHeight(1f))
    }
}