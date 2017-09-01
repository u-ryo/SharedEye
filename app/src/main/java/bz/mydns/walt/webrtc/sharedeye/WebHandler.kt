package bz.mydns.walt.webrtc.sharedeye

/**
 * Created by u-ryo on 16/10/21.
 */
interface WebHandler {
    fun getOffer() : String
    fun setAnswer(sdp: String)
    fun setImage(imageData: String)
    fun getIp() : String
    fun getFile(name: String) : String
    fun startLocalVideo()
    fun stopLocalVideo()
}