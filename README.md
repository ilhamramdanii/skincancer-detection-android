# skincancer-detection-android

Aplikasi Android untuk deteksi dini kanker kulit dari citra (foto lesi kulit) menggunakan model TensorFlow Lite on-device. Aplikasi mengklasifikasikan gambar menjadi malignant (ganas) atau benign (jinak) beserta skor keyakinannya. Dibuat sebagai Tugas Akhir.

Catatan: aplikasi ini hanya untuk tujuan edukasi dan referensi. Selalu konsultasikan dengan dokter untuk diagnosis medis yang akurat.

## Fitur

- Pilih gambar lesi kulit dari galeri
- Klasifikasi on-device dengan TFLite Task Vision ImageClassifier
- Output probabilitas per kelas (contoh: malignant: 92.40%, benign: 7.60%)
- 100 persen offline, tidak ada data yang dikirim ke server

## Tech Stack

| Komponen | Teknologi / Versi |
|---|---|
| Bahasa | Kotlin (JVM target 17) |
| Platform | Android (compileSdk 34, minSdk 24, targetSdk 34) |
| ML on-device | TensorFlow Lite 2.13.0, tensorflow-lite-support 0.4.3, tensorflow-lite-task-vision 0.4.3, tensorflow-lite-gpu 2.3.0 |
| Model | model_7725_metadata(2).tflite + labels.txt (malignant, benign) |
| UI | AndroidX AppCompat 1.6.1, Material 1.11.0, ConstraintLayout 2.1.4, ViewBinding + DataBinding |
| Lifecycle | lifecycle-runtime-ktx + lifecycle-viewmodel-ktx 2.7.0, core-ktx 1.13.1 |
| Build | Gradle (Kotlin DSL), mlModelBinding = true |
| Test | JUnit 4.13.2, Espresso 3.5.1 |

## Struktur Proyek

```
SkinCancerDetection-1/
├── app/src/main/
│   ├── java/.../MainActivity.kt                  # entry point, minta permission
│   ├── java/.../imagedetection/ImageDetectionActivity.kt  # pilih gambar + tampilkan hasil
│   ├── java/.../classifier/ImageClassifierHelper.kt       # wrapper TFLite ImageClassifier
│   ├── ml/model_7725_metadata(2).tflite          # model aktif (satu-satunya yang dipakai)
│   ├── assets/labels.txt                         # malignant, benign
│   └── res/                                      # layout, drawable, values
├── build.gradle.kts / settings.gradle.kts / gradle/
└── README.md
```

Alur kerja: MainActivity -> ImageDetectionActivity (pilih gambar galeri) -> ImageClassifierHelper.classify(bitmap) -> ImageClassifier.createFromFileAndOptions(...) -> tampilkan skor di resultText.

## Cara Menjalankan (Developer)

Prasyarat: Android Studio (Ladybug ke atas), JDK 17, Android SDK 34, HP/emulator Android 7.0+ (API 24).

1. Clone repo:
   ```bash
   git clone https://github.com/ilhamramdanii/skincancer-detection-android.git
   ```
2. Buka folder SkinCancerDetection-1/ di Android Studio (bukan folder parent), tunggu Gradle Sync.
3. Run ke emulator atau HP via USB debugging (minSdk 24).
4. Jika build gagal karena cache lama: Build > Clean Project lalu Rebuild.

## Cara Menggunakan Aplikasi (User)

1. Buka aplikasi SkinCancerDetection, izinkan akses Kamera/Galeri saat diminta.
2. Di halaman utama, tekan tombol Pilih Gambar.
3. Pilih foto lesi kulit dari galeri. Gambar akan tampil di ImageView.
4. Hasil analisis muncul otomatis di kolom Hasil Deteksi, contoh:
   ```
   malignant: 92.40%
   benign: 7.60%
   ```
   Hasil dengan keyakinan di bawah 1 persen per kelas disaring oleh setScoreThreshold(0.001f) dan maksimal 2 hasil (setMaxResults(2)).
5. Ulangi dengan foto lain bila perlu. Ingat: ini bukan diagnosis medis.

## Info Model dan Dataset

- Model aktif: app/src/main/ml/model_7725_metadata(2).tflite (sekitar 14 MB, dengan metadata preprocessing + label).
- Dataset training tidak ikut di-push ke GitHub karena ukuran besar. Simpan di Google Drive dan cantumkan link di sini: <isi-link-drive-kamu>.

## Lisensi

Proyek akademik (Tugas Akhir). Bebas digunakan dan dimodifikasi untuk keperluan edukasi.
