package modules.LcsModule

/** This data class allows us to capture a spesific rectangle on the screen and it stores all the relevant data so that we don't need to recalculate every time
 */
data class LcsRect(val width: LcsVariable,val height: LcsVariable,val cX: LcsVariable,val cY: LcsVariable,
                   val wStart: LcsVariable,val wEnd: LcsVariable,val hStart: LcsVariable,val hEnd: LcsVariable)