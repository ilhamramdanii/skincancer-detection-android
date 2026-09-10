package com.tugasakhir.skincancerdetection.classifier

import android.content.Context
import android.graphics.Bitmap
import org.tensorflow.lite.support.image.TensorImage
import android.util.Log
import org.tensorflow.lite.task.vision.classifier.ImageClassifier
import java.io.IOException


class ImageClassifierHelper(private val context: Context) {

    // imageClassifier akan diinisialisasi saat pertama kali dibutuhkan.
    private val imageClassifier: ImageClassifier? by lazy {
        setupImageClassifier()
    }

    private fun setupImageClassifier(): ImageClassifier? {
        // Opsi untuk mengatur model, seperti threshold score.
        val options = ImageClassifier.ImageClassifierOptions.builder()
            .setScoreThreshold(0.001f) // Hanya ambil hasil dengan keyakinan > 1%
            .setMaxResults(2) // Ambil 2 hasil teratas
            .build()

        try {
            // ✅ Buat classifier. Library akan otomatis membaca metadata (termasuk
            //    info pre-processing dan daftar label) dari file model.
            val classifier = ImageClassifier.createFromFileAndOptions(
                context,
                "model_7725_metadata(2).tflite", // Nama model di folder assets
                options
            )
            Log.d("ImageClassifierHelper", "✅ Model TFLite dengan metadata berhasil dimuat.")
            return classifier
        } catch (e: IOException) {
            Log.e("ImageClassifierHelper", "❌ Gagal memuat model TFLite: ${e.message}")
            return null
        }
    }

    fun classify(bitmap: Bitmap): String {
        val classifier = imageClassifier ?: run {
            Log.e("ImageClassifierHelper", "❌ Classifier belum diinisialisasi.")
            return "Error: Model tidak tersedia."
        }

        return try {
            val tensorImage = TensorImage.fromBitmap(bitmap)
            val results = classifier.classify(tensorImage)

            // ✅ Perubahan dimulai di sini
            if (results.isNotEmpty() && results[0].categories.isNotEmpty()) {
                // Siapkan StringBuilder untuk menampung semua hasil
                val resultBuilder = StringBuilder()

                // Loop melalui setiap kategori (kelas) yang terdeteksi
                for (category in results[0].categories) {
                    val label = category.label
                    val score = category.score
                    val formattedScore = String.format("%.2f", score * 100)

                    // Tambahkan setiap hasil ke dalam string dengan baris baru
                    resultBuilder.append("$label: $formattedScore%\n")
                }

                // Kembalikan string yang sudah berisi semua hasil
                resultBuilder.toString()

            } else {
                "⚠️ Tidak ada hasil deteksi."
            }
        } catch (e: Exception) {
            Log.e("ImageClassifierHelper", "❌ Error saat klasifikasi: ${e.message}")
            "❌ Error: Terjadi kesalahan saat memproses gambar."
        }
    }
}