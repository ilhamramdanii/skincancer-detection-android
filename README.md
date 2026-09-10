# skincancer-detection-android

Android app for early skin cancer detection (malignant vs benign) using TensorFlow Lite Image Classifier — Tugas Akhir.

## Fitur
- Pilih gambar dari galeri
- Klasifikasi malignant vs benign via `model_7725_metadata(2).tflite` (Task Vision ImageClassifier)
- Dibangun dengan Kotlin + TensorFlow Lite

## Cara run
1. Clone repo ini
2. Buka folder `SkinCancerDetection-1/` di Android Studio (bukan folder parent)
3. Sync Gradle, Run ke emulator / HP (minSdk 24)

## Struktur model
- Model aktif: `app/src/main/ml/model_7725_metadata(2).tflite`
- Label: `app/src/main/assets/labels.txt` (malignant, benign)

## Dataset
Dataset tidak ikut di-push (ukuran besar). Link Drive: <isi-link-drive-kamu>
