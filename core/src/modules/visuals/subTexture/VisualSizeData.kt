package modules.visuals.subTexture

import modules.lcsModule.LcsRect

data class VisualSizeData(val originalRect: LcsRect, val scalingType: ScalingType = ScalingType.FIT_ELEMENT, val scaleFactor: Float = 1f) {
    var imageBlock: LcsRect = originalRect


    private fun fitElement(block: LcsRect): LcsRect {
        return imageBlock.resizeTo(block.width * scaleFactor, block.height * scaleFactor)
    }

    private fun fitWithRatio(block: LcsRect): LcsRect {
        block.getFittingRect(originalRect.width.asLcs(), originalRect.height.asLcs()).also {
            return imageBlock.resizeTo(it.width * scaleFactor, it.height * scaleFactor)
        }
    }

    private fun fitStatic(): LcsRect {
        return imageBlock.resizeTo(originalRect.width * scaleFactor, originalRect.height * scaleFactor)
    }

    fun updateImageBlock(block: LcsRect): Boolean{
        val ib = imageBlock.copy()
        imageBlock = when (scalingType) {
            ScalingType.STATIC -> {
                fitStatic()
            }
            ScalingType.FIT_ELEMENT -> {
                fitElement(block)
            }
            ScalingType.FIT_WITH_RATIO -> {
                fitWithRatio(block)
            }
        }.relocateTo(block.cX, block.cY)
        return ib!=imageBlock
    }
}