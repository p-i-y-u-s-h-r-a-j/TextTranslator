package com.example.texttranslator

import android.app.ProgressDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.mlkit.nl.translate.TranslateLanguage
import com.google.mlkit.nl.translate.Translation
import com.google.mlkit.nl.translate.Translator
import com.google.mlkit.nl.translate.TranslatorOptions
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    var originalText:String = ""
    lateinit var englishHindiTranslator:Translator


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        translateBtn.setOnClickListener {
            originalText = original_edtv.text.toString()

            TranslateModel()
        }
    }

    private fun TranslateModel() {
        val options = TranslatorOptions.Builder()
            .setSourceLanguage(TranslateLanguage.ENGLISH)
            .setTargetLanguage(TranslateLanguage.HINDI)
            .build()
        englishHindiTranslator = Translation.getClient(options)

        val progress = ProgressDialog(this)
        progress.setTitle("Model Downloading...!")
        progress.setMessage("Please Wait While Language Model is being downloaded")
        progress.show()

        englishHindiTranslator.downloadModelIfNeeded().addOnCompleteListener {
            progress.dismiss()
            translateText()
        }.addOnFailureListener {
            resultTv.text = "Error ${it.message}"
        }
    }

    private fun translateText() {
        val progress = ProgressDialog(this)
        progress.setTitle("Translating...!")
        progress.setMessage("Please Wait While text is being translated")
        progress.show()
        englishHindiTranslator.translate(originalText).addOnSuccessListener {
            progress.dismiss()
            resultTv.text = it
        }.addOnFailureListener {
            resultTv.text = "Error !! ${it.message}"
        }
    }
}