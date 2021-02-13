package modules.simpleUi

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.pungo.modules.basic.geometry.Rectangle


interface Building {
    fun update()
    fun draw(batch: SpriteBatch, rectangle: Rectangle,baseWidth: Float, baseHeight: Float, u1: Float = 0f,u2: Float=1f, v1: Float=0f, v2: Float=1f)
    fun hoverFunction(hovering: Boolean)
}