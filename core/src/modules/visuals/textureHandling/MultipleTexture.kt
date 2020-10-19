package modules.visuals.textureHandling

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import modules.visuals.OmniVisual
import modules.visuals.subTexture.ScalingType
import modules.visuals.subTexture.SubTexture

/** Unlike the previous iterations this class does not directly concern itself with modifying individual sprites but has handlers for them
 * The macro management approach I believe will yield better results
 */
open class MultipleTexture(subTextures: MutableList<SubTexture> = mutableListOf()): OmniVisual() {
    protected var activeSubTexture= 0
    private var subTextures = subTextures
    var frameChanger = FrameChanger()


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



    override fun setScalingType(scalingType: ScalingType?, widthScaleFactor: Float?,heightScaleFactor: Float?){
        subTextures.forEach {
            it.setScaling(scalingType,widthScaleFactor,heightScaleFactor)
        }
    }


    override fun draw(batch: SpriteBatch, alpha: Float) {
        subTextures[activeSubTexture].draw(batch,alpha,block)
    }

    override fun changeActiveSprite(ns: Int) {
        activeSubTexture = ns
    }

    override fun update() {
        frameChanger.update()
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

    fun changeVisualTypes(){
        subTextures[0].v
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

    fun rollActiveFrame(n: Int){
        activeSubTexture = (activeSubTexture + n).rem(subTextures.size)
    }


    /** This is the base class for frame changers
     * for any custom design, one can generate a frame changer class here
     * or inherit this from outside and make its own class, then inject to frameChanger of this object
     */
    open inner class FrameChanger(){
        open fun update(){

        }
    }

    /** This generates a frame changer class with fps and changes frame with time
     *
     */
    inner class FpsFrameChanger(var fps: Float) : FrameChanger(){
        var time = 0f
        override fun update(){
            time += Gdx.graphics.deltaTime
            rollActiveFrame((time*fps).toInt())
            time = time.rem(1/fps)
        }

    }
}