# ThermalPrinter Extension

Extension untuk **App Inventor / Kodular / Niotron** agar bisa mencetak ke **Thermal Printer Bluetooth** (ESC/POS).

## ✨ Fitur
- Connect ke printer via Bluetooth
- Print Text
- Print Image (PNG/JPG)
- Print QR Code

## 📦 Instalasi
1. Clone repo ini:
   ```bash
   git clone https://github.com/yourname/ThermalPrinterExtension.git
   ```
2. Import project ke **Niotron IDE** atau **AppyBuilder Extension IDE**
3. Compile → hasil: `ThermalPrinter.aix`
4. Import `.aix` ke App Inventor / Kodular / Niotron project

## 📋 Blok Pemakaian
- `ConnectBluetooth(deviceName)` → hubungkan ke printer
- `PrintText(text)` → cetak teks
- `PrintQRCode(text)` → cetak QR Code
- `PrintImage(path)` → cetak gambar dari file path
- `GotMessage(message)` → event status/error

## 📌 Contoh
1. Hubungkan printer:  
   ```
   ConnectBluetooth("Printer_Bluetooth_Name")
   ```
2. Cetak teks:  
   ```
   PrintText("Hello World!")
   ```
3. Cetak QR Code:  
   ```
   PrintQRCode("123456789")
   ```
4. Cetak gambar:  
   ```
   PrintImage("/storage/emulated/0/Download/logo.png")
   ```

---

## ⚠️ Catatan
- Pastikan printer mendukung **ESC/POS Command**  
- Nama device harus sama dengan yang muncul di daftar Bluetooth Android  
- Untuk cetak gambar, gunakan resolusi kecil (max 384px width) agar hasil jelas  

---

## 🙏 Credits
- [DantSu/ESCPOS-ThermalPrinter-Android](https://github.com/DantSu/ESCPOS-ThermalPrinter-Android)
