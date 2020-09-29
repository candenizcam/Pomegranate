package com.modules.visuals

import modules.visuals.OmniVisual
import java.lang.Exception

object OVL {
    val allVisuals = mutableListOf<OmniVisual>()

    fun dispose(){
        allVisuals.forEach {
            try{
                it.dispose()
            } catch (e: Exception){

            }

        }
    }
}