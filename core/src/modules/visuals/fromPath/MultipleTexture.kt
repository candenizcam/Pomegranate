package modules.visuals.fromPath

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import modules.visuals.OmniVisual
import modules.visuals.ScalingType
import modules.visuals.SubTexture

/** Unlike the previous iterations this class does not directly concern itself with modifying individual sprites but has handlers for them
 * The macro management approach I believe will yield better results
 */
open class MultipleTexture(subTextures: MutableList<SubTexture> = mutableListOf<SubTexture>(),scalingType: ScalingType = ScalingType.FIT_ELEMENT, scaleFactor: Float = 1f): OmniVisual() {
    protected var activeSubTexture= 0
    private var subTextures = subTextures
    var changeActiveSubTextureFunc = {activeSubTexture: Int->activeSubTexture}


    fun addToSubTextures(st: SingleTexture){
        subTextures.add(SubTexture(st.subTexture))
    }

    fun addToSubTextures(st: SubTexture){
        subTextures.add(st)
    }

    fun addToSubTextures(st: List<SubTexture>){
        subTextures.addAll(st)
    }

    fun setSubTextures(st: List<SubTexture>){
        subTextures = st.toMutableList()
    }

    fun clearSubTextures(){
        subTextures.clear()
    }

    fun getSubTexture(no: Int?): SubTexture {
        return subTextures[no ?: activeSubTexture]
    }


    override fun draw(batch: SpriteBatch, alpha: Float) {
        // visualSizeData.updateImageBlock(block)
        // subTextures[activeSubTexture].visualSizeData.updateImageBlock(block)
        subTextures[activeSubTexture].draw(batch,alpha,block)
    }

    override fun changeActiveSprite(ns: Int) {
        activeSubTexture = ns
    }

    override fun update() {
        activeSubTexture = changeActiveSubTextureFunc(activeSubTexture)

    }

    override fun recolour(c: Color) {
        subTextures.forEach {
            it.color = c
        }
    }

    /** Recolours a spesific item
     * null recolours the active item
     */
    fun recolourItem(c: Color, itemNo: Int? = null){
        subTextures[itemNo ?: activeSubTexture].color = c
    }

    override fun copy(): OmniVisual {
        return MultipleTexture(subTextures)
    }

    override fun dispose() {

    }

    override fun setFlip(x: Boolean, y: Boolean) {
        subTextures.forEach {
            it.flip(x,y)
        }
    }
}