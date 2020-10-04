package modules.basic.geometry

open class Line{
    var a: Float //line parameter from ax + b = y
    var b: Float //this too
    var p1: Point //first point
    var p2: Point //2nd point
    var x: Float //x component of the line
    var y: Float //y component of the line
    constructor(p1: Point, p2: Point){
        this.a = (p1.y - p2.y)/(p1.x - p2.x)
        this.b = p1.y - this.a * p1.x
        this.p1 = p1
        this.p2 = p2
        this.x = p2.x - p1.x
        this.y = p2.y - p1.y
    }
    constructor(a: Float, b: Float){
        this.a = a
        this.b = b
        this.p1 = Point(0f,a*0f + b)
        this.p2 = Point(1f,a*1f + b)
        this.x = p2.x - p1.x
        this.y = p2.y - p1.y
    }

    fun atRight(p: Point): Boolean {
        val aux = Line(p1,p)
        return (x*aux.y - y*aux.x)>0
        /*
        println("${(p.x*a + b)} && ${p.y}")
        println("${p1.x} ${p1.y} ${p2.x} ${p2.y}")
        println("${a} ${b}")
        return (p.x*a + b)>=p.y

         */
    }



}