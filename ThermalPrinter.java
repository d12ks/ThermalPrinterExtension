package com.yourname.thermalprinter;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.google.appinventor.components.annotations.DesignerComponent;
import com.google.appinventor.components.annotations.SimpleFunction;
import com.google.appinventor.components.annotations.SimpleObject;
import com.google.appinventor.components.annotations.SimpleEvent;
import com.google.appinventor.components.annotations.UsesPermissions;
import com.google.appinventor.components.annotations.UsesLibraries;
import com.google.appinventor.components.common.ComponentCategory;
import com.google.appinventor.components.runtime.AndroidNonvisibleComponent;
import com.google.appinventor.components.runtime.ComponentContainer;
import com.google.appinventor.components.runtime.EventDispatcher;

import com.dantsu.escposprinter.EscPosPrinter;
import com.dantsu.escposprinter.connection.bluetooth.BluetoothPrintersConnections;
import com.dantsu.escposprinter.connection.bluetooth.BluetoothConnection;

@DesignerComponent(
        version = 1,
        description = "Extension untuk mencetak ke Thermal Printer via Bluetooth (Text, PNG, QRCode)",
        category = ComponentCategory.EXTENSION,
        nonVisible = true,
        iconName = "aiwebres/icon.png")
@SimpleObject(external = true)
@UsesPermissions(permissionNames = "android.permission.BLUETOOTH, android.permission.BLUETOOTH_ADMIN, android.permission.BLUETOOTH_CONNECT")
@UsesLibraries(libraries = "escpos-thermalprinter-android-3.0.1.aar")
public class ThermalPrinter extends AndroidNonvisibleComponent {

    private BluetoothConnection connection;

    public ThermalPrinter(ComponentContainer container) {
        super(container.$form());
    }

    @SimpleFunction(description = "Hubungkan ke printer bluetooth berdasarkan nama device")
    public void ConnectBluetooth(String deviceName) {
        BluetoothConnection[] bluetoothPrinters = (new BluetoothPrintersConnections()).getList();
        if (bluetoothPrinters != null) {
            for (BluetoothConnection printer : bluetoothPrinters) {
                if (printer.getDevice().getName().equals(deviceName)) {
                    connection = printer;
                    GotMessage("Terhubung ke " + deviceName);
                    return;
                }
            }
        }
        GotMessage("Printer tidak ditemukan: " + deviceName);
    }

    @SimpleFunction(description = "Cetak teks ke printer")
    public void PrintText(String text) {
        try {
            if (connection != null) {
                EscPosPrinter printer = new EscPosPrinter(connection, 203, 48f, 32);
                printer.printText(text + "\n");
                printer.disconnectPrinter();
                GotMessage("Teks berhasil dicetak");
            } else {
                GotMessage("Belum ada koneksi printer");
            }
        } catch (Exception e) {
            GotMessage("Error: " + e.getMessage());
        }
    }

    @SimpleFunction(description = "Cetak QRCode dari teks")
    public void PrintQRCode(String text) {
        try {
            if (connection != null) {
                EscPosPrinter printer = new EscPosPrinter(connection, 203, 48f, 32);
                printer.printText("[C]<qrcode>" + text + "</qrcode>\n");
                printer.disconnectPrinter();
                GotMessage("QR Code berhasil dicetak");
            } else {
                GotMessage("Belum ada koneksi printer");
            }
        } catch (Exception e) {
            GotMessage("Error: " + e.getMessage());
        }
    }

    @SimpleFunction(description = "Cetak gambar dari path file PNG/JPG")
    public void PrintImage(String imagePath) {
        try {
            if (connection != null) {
                Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
                if (bitmap != null) {
                    EscPosPrinter printer = new EscPosPrinter(connection, 203, 48f, 32);
                    printer.printImage(bitmap);
                    printer.disconnectPrinter();
                    GotMessage("Gambar berhasil dicetak");
                } else {
                    GotMessage("Gagal load gambar: " + imagePath);
                }
            } else {
                GotMessage("Belum ada koneksi printer");
            }
        } catch (Exception e) {
            GotMessage("Error: " + e.getMessage());
        }
    }

    @SimpleEvent(description = "Event pesan balik (status/error)")
    public void GotMessage(String message) {
        EventDispatcher.dispatchEvent(this, "GotMessage", message);
    }
}
