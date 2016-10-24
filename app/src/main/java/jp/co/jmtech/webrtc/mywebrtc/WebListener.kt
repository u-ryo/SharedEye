package jp.co.jmtech.webrtc.mywebrtc

/**
 * Created by u-ryo on 16/10/21.
 */
interface WebListener {
    fun getOffer() : String
    fun setAnswer(sdp: String)
    fun getIp() : String
    fun getFile(name: String) : String
}