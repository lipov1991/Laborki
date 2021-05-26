package pl.lipov.laborki.common.utils

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Intent
import android.speech.RecognizerIntent
import android.util.Log
import android.widget.Toast
import java.util.*

class STTUtils {

    fun listenForSpeechRecognize(
        activity: Activity,
        requestCode: Int
    ) {
        val recognizeSpeechIntent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
            putExtra(
                RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
            )
            putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault())
            putExtra(RecognizerIntent.EXTRA_PROMPT, "Podaj typ lokalu")
        }
        try {
            activity.startActivityForResult(recognizeSpeechIntent, requestCode)
        } catch (exception: ActivityNotFoundException) {
            Toast.makeText(activity, "Twoje urządzenie nie wspiera STT", Toast.LENGTH_SHORT).show()
        }
    }

    fun handleSpeechRecognizeResult(
        data: Intent,
        mapUtils: MapUtils
    ) {
        data.getStringArrayExtra(RecognizerIntent.EXTRA_RESULTS)
            ?.firstOrNull()?.let { recognizeResult ->
                Log.d("STT_git", recognizeResult)

                mapUtils.recognizeResult = recognizeResult
                mapUtils.checkRecognizeResult()

            }
    }
}