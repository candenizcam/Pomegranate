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
        width = left-right
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




}