package modules.scenes

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import modules.LcsModule.GetLcsRect
import modules.uiElements.Layouts.OmniLayout
import modules.uiElements.Layouts.PinboardLayout


open class Scene(val id: String, var zOrder: Float, protected var layout: OmniLayout = PinboardLayout(id,GetLcsRect.ofFullScreen())) {
    var visible = true

    fun draw(batch: SpriteBatch){
        if(visible){
            layout.draw(batch)
        }

    }

    fun update(){
        if(visible){
            layout.update()
        }
    }

    fun getMainLayout(): OmniLayout {
        return layout
    }


}

