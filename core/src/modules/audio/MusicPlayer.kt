package modules.audio

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.audio.Music

class MusicPlayer(initialVolume: Float = 1.0f) {
    private lateinit var bgm : Music
    private var volume = 1.0f
    private var muted = false

    fun open(fileName: String) {
        val file = Gdx.files.internal(fileName)
        bgm = Gdx.audio.newMusic(file)
        bgm.volume = volume
    }

    fun setLooping(loop: Boolean) {
        bgm.isLooping = loop
    }

    fun play() {
        bgm.play()
    }

    fun pause() {
        bgm.pause()
    }

    fun stop() {
        bgm.stop()
    }

    fun release(){
        bgm.dispose()
    }

    fun changeVolume(vol: Float){
        if(!muted){
            volume = vol
            bgm.volume = vol
        }
        else{
            volume = 0.0f
            bgm.volume = 0.0f
        }
    }
}