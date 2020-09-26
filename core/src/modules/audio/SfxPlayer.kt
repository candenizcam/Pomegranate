package engine.audio

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.audio.Sound

class SfxPlayer (var volume: Float = 1.0f){
    private val sfxList = mutableListOf<Pair<String, Sound>>()
    var muted = false

    fun addSFX(name: String, fileName: String){
        var sfx = Gdx.audio.newSound(Gdx.files.internal(fileName))
        sfxList.add(Pair(name, sfx))
    }

    fun play(name: String){
        sfxList.filter{ it.first == name }[0].second.play(volume)
    }

    fun changeVolume(vol: Float){
        if(!muted){
            volume = vol
        }
        else{
            volume = 0.0f
        }
    }
}