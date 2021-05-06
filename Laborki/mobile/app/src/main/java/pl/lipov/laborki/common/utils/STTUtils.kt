package pl.lipov.laborki.common.utils

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.speech.RecognizerIntent
import android.widget.Toast
import pl.lipov.laborki.R
import java.util.*

class STTUtils {

    fun listenForSpeechRecognise(
            activity: Activity,
            requestCode: Int
    ){
        val recogniseSpeechIntent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
            putExtra(
                    RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                    RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
            )

            putExtra(
                    RecognizerIntent.EXTRA_LANGUAGE,
                    Locale.getDefault()
            )
            putExtra(RecognizerIntent.EXTRA_PROMPT, activity.getString(R.string.stt_prompt))

        }

        try {
            activity.startActivityForResult(recogniseSpeechIntent,requestCode)
        }catch (exception: ActivityNotFoundException){
            Toast.makeText(activity,"Twoje urzadzenie nie wspiera STT",Toast.LENGTH_SHORT).show()
        }
    }

    fun handleSpeechRecognizeResult(
            data: Intent,
            context: Context
    ){
        data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
                ?.firstOrNull()?.let {
                    Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
                }
    }


}