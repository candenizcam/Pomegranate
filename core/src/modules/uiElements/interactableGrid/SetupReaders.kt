package modules.uiElements.interactableGrid

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.files.FileHandle
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.TextureAtlas
import modules.lcsModule.GetLcs
import modules.lcsModule.GetLcsRect
import modules.visuals.*
import modules.visuals.fromPixmap.PixmapGenerator

object SetupReaders {

    /** Reads lines in setupFile and returns brushes
     *
     */
    fun linesReader(s: String): MutableList<Pair<String, OmniVisual>> {
        var lines = Gdx.files.local(s).readString().lines().filterNot {it[0]=='/' && it[1]=='/'}

        var returning = mutableListOf<Pair<String, OmniVisual>>() //this is the list of brushes
        var l = lines
        if (l[0] == "colours:{") {
            val colourLines: List<String>
            l.indexOfFirst { it == "}" }.also {
                colourLines = l.subList(1, it)
                l = l.subList(it + 1, lines.lastIndex + 1)
            }
            returning.addAll(colourLinesReader(colourLines))
        }
        // we will add other types of readers after this
        return returning
    }

    /** Reads colour lines and returns appropriate brushes
     */
    fun colourLinesReader(colourLines: List<String>): MutableList<Pair<String, OmniVisual>> {
        var returning = mutableListOf<Pair<String, OmniVisual>>() //this is the list of brushes
        colourLines.forEach {
            var s = it
            s = s.replace("\\s+".toRegex(),"")
            val type: String
            val colour: Color
            s.split(":").also{it2->
                type = it2[0]
                it2[1].split(",").also{it3->
                    colour = Color(it3[0].toFloat()/255,it3[1].toFloat()/255,it3[2].toFloat()/255,it3[3].toFloat()/255)
                }
            }
            returning.add(Pair(type, PixmapGenerator.singleColour(GetLcsRect.byParameters(GetLcs.byPixel(100f), GetLcs.byPixel(100f)), colour).also {it.visualSize = VisualSize.FIT_ELEMENT}))

        }
        return returning
    }

    fun atlasReader(f: FileHandle): MutableList<Pair<String, OmniVisual>> {
        /*
        var msl = mutableListOf<Sprite>()
        mutableListOf<Sprite>().also {

        }

         */
        var msl = TextureAtlas(f).createSprites().map{ SpriteVisual(it, visualSize = VisualSize.FIT_ELEMENT) }
        return msl.mapIndexed {index,it -> Pair<String,OmniVisual>("tile_$index",it) }.toMutableList()
            /*
            TextureAtlas(f).also { textureAtlas ->

                textureAtlas.createSprites().forEachIndexed { index, sprite ->

                }

                var firstSize =  GetLcsRect.ofCentreSquare()
                (if(region=="") textureAtlas.createSprites() else textureAtlas.createSprites(region)).forEachIndexed{index, sprite->
                    if(index==0){
                        firstSize = GetLcsRect.byParameters(GetLcs.byPixel(sprite.width), GetLcs.byPixel(sprite.height))
                        originalBlock = firstSize
                    }
                    ratioToFirst.add(Pair(sprite.width/firstSize.width.asPixel(),sprite.height/firstSize.height.asPixel()))
                    msl.add(sprite)
                }
            }
            return msl.toList()
        }

             */

    }
}