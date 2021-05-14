package se.umu.yarn.model

import android.webkit.JavascriptInterface
import se.umu.yarn.CallActivity

/**
 * Interface for exposing Android functions to JavaScript
 */
class JavascriptInterface(private val callActivity: CallActivity) {

    /**
     * This function is accessible in JavaScript and
     * called in call.js for handling peer connection event.
     */
    @JavascriptInterface
    public fun onPeerConnected() {
        callActivity.onPeerConnected()
    }

}