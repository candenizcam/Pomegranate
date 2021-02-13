package modules.uiPlots

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.pungo.modules.basic.geometry.Point
import com.pungo.modules.basic.geometry.Rectangle
import com.pungo.modules.uiPlots.District
import com.pungo.modules.uiPlots.Plot
import modules.application.PuniversalValues

class SceneDistrict(id: String, private var w: Float, private var h: Float, var resizeReaction: ResizeReaction = ResizeReaction.STRETCH): District(id) {

    override fun draw(batch: SpriteBatch, alpha: Float, faceOther: Boolean) {

        plots.sortedBy { it.z }.forEach {
            if(it.visible){
                val rec2go = getPlayingField().getSubRectangle(it.estate)
                val pf = getPlayingField()

                if(rec2go.overlaps(getPlayingField())){
                    val u1 = (pf.left-rec2go.left).coerceAtLeast(0f)/rec2go.width
                    val u2 = 1 + (pf.right-rec2go.right).coerceAtMost(0f)/rec2go.width
                    val v1 = - (pf.top-rec2go.top).coerceAtMost(0f)/rec2go.height
                    val v2 = 1 - (pf.bottom-rec2go.bottom).coerceAtLeast(0f)/rec2go.height
                    val rec3go = Rectangle(rec2go.left.coerceAtLeast(pf.left),rec2go.right.coerceAtMost(pf.right),rec2go.bottom.coerceAtLeast(pf.bottom),rec2go.top.coerceAtMost(pf.top))
                    it.element?.draw(batch,rec3go,w*rec2go.width/pf.width,h*rec2go.height/pf.height,u1,u2,v1,v2)
                }

            }
        }


        //super.draw(batch, alpha, faceOther)
    }

    override fun update() {
        // following handles clicks
        var holder = false
        for (i in plots.sortedBy { it.z }.reversed()){ // in reverse z order
            if(holder||i.inactive){ //if touch was consumed or i is inactive
                i.hovering = false // hovering is false
            }else{ // hover is checked
                val h = getPlayingField().getSubRectangle(i.estate).contains(PuniversalValues.cursorPoint)
                i.hovering = h
                holder = (h&&i.touchStopper)
            }
        }

        super.update()
    }


    private fun getPlayingField(): Rectangle {
        return when(resizeReaction){
            ResizeReaction.STATIC-> Rectangle(w,h, PuniversalValues.appCentre)
            ResizeReaction.STRETCH -> Rectangle(PuniversalValues.appWidth,PuniversalValues.appHeight,PuniversalValues.appCentre)
            ResizeReaction.RATED -> Rectangle(PuniversalValues.appWidth,PuniversalValues.appHeight,PuniversalValues.appCentre).getSubRectangle(w,h)
        }
    }

    fun setSizeReference(w: Float, h: Float){
        this.w = w
        this.h = h
    }

    enum class ResizeReaction{
        STATIC, //retains given pixels
        STRETCH, //stretches to the app
        RATED //retains ratio while stretching up to the app
    }
}