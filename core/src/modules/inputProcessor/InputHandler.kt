package modules.inputProcessor

object InputHandler {
    var typeCache = ""
    var backspacePressed = false
    var deletePressed = false
    fun getTypeCache(erase: Boolean = true):String{
        return typeCache.also {
            if(erase) typeCache = ""
        }
    }

    fun listenBackspace(erase: Boolean): Boolean{
        return backspacePressed.also {
            if(erase) backspacePressed = false
        }
    }

}