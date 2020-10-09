package modules.basic.geometry

class Rectangle: ConvexPolygon {
    var top: Float
    var bottom: Float
    var left: Float
    var right: Float
    var width: Float
    var height: Float

    constructor(bottomLeft: Point, topRight: Point) : super(){
        points = mutableListOf(bottomLeft,Point(topRight.x,bottomLeft.y),topRight,Point(bottomLeft.x,topRight.y))
        top = topRight.y
        bottom = bottomLeft.y
        right = topRight.x
        left = bottomLeft.x
        width = right-left
        height= top-bottom
    }

    constructor(left: Float, right: Float,bottom: Float, top: Float){
        points = mutableListOf(Point(bottom,left),Point(bottom,right),Point(top,right),Point(top,left))
        this.top = top
        this.bottom = bottom
        this.right = right
        this.left = left
        width = right-left
        height= top-bottom
    }

    operator fun plus(other: Rectangle): Rectangle {
        val t = (top).coerceAtLeast(other.top)
        val b = (bottom).coerceAtMost(other.bottom)
        val l = (left).coerceAtMost(other.left)
        val r = (right).coerceAtLeast(other.right)
        return Rectangle(l,r,b,t)
    }

    /** This function takes itself as unit rectangle and input as ratios for it, and returns the adjusted rectangle
     * ex: this = (left: 0,bottom: 0,right: 2,top: 1), other = (0.25,0.25,0.75,0.75) ->  (0.5,0.25,1.5,0.75)
     */
    fun getSubRectangle(other: Rectangle): Rectangle {
        val l = left + width*other.left
        val r = left + width*other.right
        val b = bottom + height*other.bottom
        val t = bottom + height*other.top
        return Rectangle(l,r,b,t)

    }

    fun dataString(): String {
        return "left: $left right: $right bottom: $bottom top: $top"
    }



}