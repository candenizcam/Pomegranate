package modules.visuals

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.Pixmap
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Sprite
import modules.lcsModule.GetLcs
import modules.lcsModule.GetLcsRect
import modules.lcsModule.LcsRect
import kotlin.math.ceil

/** Used to create custom pixmaps which are returned as CustomPixmap visual elements
 *
 */
object PixmapGenerator {
    fun singleColour(b: LcsRect=GetLcsRect.ofFullScreen(), c: Color =Color.WHITE,visualSize: VisualSize=VisualSize.FIT_ELEMENT, scaleFactor: Float = 1f): CustomPixmap {
        Pixmap(b.width.asPixel().toInt() + 1, b.height.asPixel().toInt() + 1, Pixmap.Format.RGBA8888).also {
            it.setColor(1f,1f,1f,1f)
            it.fill()
            return CustomPixmap(it,c,visualSize,scaleFactor)
        }
    }


    /** Creates a grid with col and row
     */
    fun grid(col: Int, row: Int, b: LcsRect=GetLcsRect.ofFullScreen(), c: Color = Color.WHITE,visualSize: VisualSize=VisualSize.FIT_ELEMENT, scaleFactor: Float = 1f): CustomPixmap {
        val w = ceil(b.width.asPixel()).toInt()
        val h = ceil(b.height.asPixel()).toInt()
        Pixmap(w,h, Pixmap.Format.RGBA8888).also {
            it.setColor(Color.LIGHT_GRAY) //sets colour permanently
            it.drawRectangle(0,0,w,h)
            for (i in (1 until col)) {
                val xVal = w*(i.toFloat()/col)
                it.fillRectangle(ceil(xVal).toInt()-1,0,3,h)
            }
            for (i in (1 until row)) {
                val yVal = h*(i.toFloat()/row)
                it.fillRectangle(0, ceil(yVal).toInt()-1,w,3)
            }
            return CustomPixmap(it,c,visualSize,scaleFactor)
        }
    }

    fun circle(b: LcsRect=GetLcsRect.ofCentreSquare(), c: Color = Color.WHITE,visualSize: VisualSize=VisualSize.FIT_ELEMENT, scaleFactor: Float = 1f): CustomPixmap {
        Pixmap(101, 101, Pixmap.Format.RGBA8888).also {
            it.setColor(1f,1f,1f,1f)
            it.fillCircle(50,50,50)
            return CustomPixmap(it,c,visualSize,scaleFactor)
        }
    }
}