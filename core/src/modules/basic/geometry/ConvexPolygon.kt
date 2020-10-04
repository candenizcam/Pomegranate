package modules.basic.geometry

open class ConvexPolygon {
    var centre = Point(0f,0f) //automatically updated with changing points, and should be left to that, do not change at any other place

    var points = mutableListOf<Point>()
        set(value) {
            field = value
            var p0 = Point(0f,0f)
            value.forEach {
                p0 += it
            }
            centre = p0/points.size.toFloat()
        }

    //constructor(sides: Int, sideLength: Float){}
    constructor(l: MutableList<Point>) {
        points = l
    }
    protected constructor(){}



    fun contains(p: Point): Boolean {
        var res = false
        for(i in 0..(points.size)-2){
            val l = Line(points[i],points[i+1])
            val ar = l.atRight(p)
            if(i==0){
                res = ar
            }else{
                if(ar!=res){
                   return false
                }
            }
        }
        return true
    }

}