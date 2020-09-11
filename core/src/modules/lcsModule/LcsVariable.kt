package modules.lcsModule

import kotlin.math.abs

/** A variable to be used for LCS usage
 * v is a LCS value
 * coeff is one side of the LCS
 */
class LcsVariable(private val v: Float, private val coeff: Float) {
    fun asPixel(): Float {
        return v * coeff;
    }

    fun asLcs(): Float {
        return v;
    }

    operator fun plus(other: LcsVariable): LcsVariable {
        return LcsVariable(v + other.asLcs(), coeff)
    }

    operator fun minus(other: LcsVariable): LcsVariable {
        return LcsVariable(v - other.asLcs(), coeff)
    }

    operator fun times(other: LcsVariable): LcsVariable {
        return LcsVariable(v * other.asLcs(), coeff)
    }

    operator fun times(other: Int): LcsVariable {
        return LcsVariable(v * other.toFloat(), coeff)
    }

    operator fun times(other: Float): LcsVariable {
        return LcsVariable(v * other, coeff)
    }

    operator fun div(other: LcsVariable): LcsVariable {
        return LcsVariable(v / other.asLcs(), coeff)
    }

    operator fun div(other: Int): LcsVariable {
        return LcsVariable(v / other.toFloat(), coeff)
    }

    operator fun div(other: Float): LcsVariable {
        return LcsVariable(v / other, coeff)
    }

    override operator fun equals(other: Any?): Boolean {
        return if (other is LcsVariable) {
            other.v == v
        } else {
            false
        }
    }

    fun limitBelow(other: LcsVariable): LcsVariable {
        return LcsVariable(v.coerceAtLeast(other.v), coeff)
    }

    fun limitAbove(other: LcsVariable): LcsVariable {
        return LcsVariable(v.coerceAtMost(other.v), coeff)
    }

    fun abs(): LcsVariable {
        return LcsVariable(abs(v), coeff)
    }
}