package modules.LcsModule

/** This data class allows us to capture a spesific rectangle on the screen and it stores all the relevant data so that we don't need to recalculate every time
 */
data class LcsRect(val width: LcsVariable,val height: LcsVariable,val cX: LcsVariable,val cY: LcsVariable,
                   val wStart: LcsVariable,val wEnd: LcsVariable,val hStart: LcsVariable,val hEnd: LcsVariable){

    fun contains(x: LcsVariable, y: LcsVariable): Boolean {
        return (wStart.asLcs()< x.asLcs())&& (wEnd.asLcs()>x.asLcs())&&(hStart.asLcs()<y.asLcs())&& (hEnd.asLcs()>y.asLcs())
    }

    /** This function returns a rectangle with the given ratio that fits in the rect and has the same centre
     */
    fun getFittingRect(w: Float,h: Float): LcsRect {
        val u = width/w*h
        return if(u.asLcs()<height.asLcs()){
            GetLcsRect.byParameters(width,u,cX,cY)
        }else{
            GetLcsRect.byParameters(height/h*w,height,cX,cY)
        }

    }
}