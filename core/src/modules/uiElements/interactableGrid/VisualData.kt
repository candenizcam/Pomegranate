package modules.uiElements.interactableGrid

/** Visual data class has a number of variables
 * type is the type of visual, stored inside
 * posX & posY are floats that are coefficients to the gridBlock describing centres
 * width & height are similar, but for width & height
 */
data class VisualData(val type: String, val path: String, var posX: Float, var posY: Float, var z: Int, var width: Float, var height: Float, val pxWidth: Int, val pxHeight: Int) {
}