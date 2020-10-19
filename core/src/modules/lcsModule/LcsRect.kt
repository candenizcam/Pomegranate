package modules.lcsModule

import modules.basic.geometry.Point
import modules.basic.geometry.Rectangle

/** This data class allows us to capture a spesific rectangle on the screen and it stores all the relevant data so that we don't need to recalculate every time
 */
data class LcsRect(val width: LcsVariable, val height: LcsVariable, val cX: LcsVariable, val cY: LcsVariable,
                   val wStart: LcsVariable, val wEnd: LcsVariable, val hStart: LcsVariable, val hEnd: LcsVariable) {


    fun contains(x: LcsVariable, y: LcsVariable): Boolean {
        return (wStart.asLcs() < x.asLcs()) && (wEnd.asLcs() > x.asLcs()) && (hStart.asLcs() < y.asLcs()) && (hEnd.asLcs() > y.asLcs())
    }

    /** This function returns a rectangle with the given ratio that fits in the rect and has the same centre
     */
    fun getFittingRect(w: Float, h: Float): LcsRect {
        val u = width / w * h
        return if (u.asLcs() < height.asLcs()) {
            GetLcsRect.byParameters(width, u, cX, cY)
        } else {
            GetLcsRect.byParameters(height / h * w, height, cX, cY)
        }

    }


    /** Returns a rectangle relocated to a spesific position
     */
    fun relocateTo(x: LcsVariable, y: LcsVariable): LcsRect {
        return GetLcsRect.byParameters(width,height,x,y)
    }

    /** Returns a rectangle resized by given LCS variables
     */
    fun resizeTo(w: LcsVariable, h: LcsVariable): LcsRect {
        return GetLcsRect.byParameters(w,h,cX,cY)
    }

    /** Returns a rectangle multiplied by a single variable
     */
    fun resizeTo(multiply: Float): LcsRect {
        return GetLcsRect.byParameters(width*multiply,height*multiply,cX,cY)
    }

    /** Returns a rectangle multiplied by width and height coefficients
     */
    fun resizeTo(cW: Float, cH: Float): LcsRect {
        return GetLcsRect.byParameters(width*cW,height*cH,cX,cY)
    }

    /** Returns true if width and height are zero
     */
    fun isZero(): Boolean {
        return (width.asLcs() == 0f) && (height.asLcs() == 0f)
    }


    fun getWidthRatio(w: LcsVariable): Float {
        return ((w-wStart)/width).asLcs()
    }

    fun getHeightRatio(h: LcsVariable): Float {
        return ((h-hStart)/height).asLcs()
    }

    fun getXFromRatio(w: Float): LcsVariable {
        return wStart + width*w
    }

    fun getYFromRatio(h: Float): LcsVariable {
        return hStart + height*h
    }

    fun getPointFromRatio(w: Float, h:Float): Pair<LcsVariable, LcsVariable> {
        return Pair(getXFromRatio(w),getYFromRatio(h))
    }

    fun getRatiosFromPoint(w: LcsVariable,h:LcsVariable): Pair<Float, Float> {
        return Pair(getWidthRatio(w),getHeightRatio(h))
    }

    fun getGeoRect(other: LcsRect):Rectangle{
        //return Rectangle(Point((other.wStart-wStart).ratio(width),(other.hStart-hStart).ratio(height)))
        return Rectangle((other.wStart-wStart).ratio(width),(other.wEnd-wStart).ratio(width),(other.hStart-hStart).ratio(height),(other.hEnd-hStart).ratio(height))
    }

    fun getLcsRectFromGeo(other: Rectangle): LcsRect{
        return GetLcsRect.byBorders(wStart + width*other.left,wStart + width*other.right,hStart + height*(other.top), hStart + height*other.bottom)
    }





}