package com.example.textrecognize

import android.annotation.SuppressLint
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.TextRecognizerOptionsInterface

class ImageAnalyzer(private val listener: listener, textRecognizerOptions: TextRecognizerOptionsInterface) : ImageAnalysis.Analyzer {

    private val recognizer = TextRecognition.getClient(textRecognizerOptions)

    @SuppressLint("UnsafeOptInUsageError")
    override fun analyze(imageProxy: ImageProxy) {
        val mediaImage = imageProxy.image
            mediaImage?.let {
                val inputImage: InputImage = InputImage.fromMediaImage(it, imageProxy.imageInfo.rotationDegrees)
                recognizer.process(inputImage)
                    .addOnSuccessListener { text ->
                        listener(text.text)
                }
                    .addOnFailureListener { e ->
                        listener(e.message!!)
                }
            }
    }

}