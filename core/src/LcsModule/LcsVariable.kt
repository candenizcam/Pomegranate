package LcsModule

/** A variable to be used for LCS usage
 * v is a LCS value
 * coeff is one side of the LCS
 */
class LcsVariable(private val v: Float, private val coeff: Float) {
    fun asPixel(): Float {
        return v*coeff;
    }

    fun asLcs(): Float {
        return v;
    }

    fun plus(other: LcsVariable): LcsVariable {
        return LcsVariable(v + other.asLcs(), coeff)
    }

    fun minus(other: LcsVariable): LcsVariable{
        return LcsVariable(v - other.asLcs(), coeff)
    }

    operator fun div(other:LcsVariable): LcsVariable{
        return LcsVariable(v/ other.asLcs(), coeff)
    }
}