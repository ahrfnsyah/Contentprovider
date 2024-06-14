package com.example.contentprovidieerrr;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.example.contentprovidieerrr.receiver.MessageReceiver;

public class Halamansms extends AppCompatActivity implements View.OnClickListener, MessageReceiver.MessageListener {

    public static final int REQUEST_CODE = 100;
    Button actionBySmsIntent, actionSmsManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_halamansms);

        actionBySmsIntent = findViewById(R.id.actionSmsByIntent);
        actionSmsManager = findViewById(R.id.actionSmsManager);
        actionSmsManager.setOnClickListener(this);
        actionBySmsIntent.setOnClickListener(this);

        if (periksaIzinPenyimpanan()) {
            MessageReceiver.bindListener(this);
        }
    }

    boolean periksaIzinPenyimpanan() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(Manifest.permission.RECEIVE_SMS) == PackageManager.PERMISSION_GRANTED &&
                    checkSelfPermission(Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED) {
                return true;
            } else {
                ActivityCompat.requestPermissions(this, new String[]{
                        Manifest.permission.RECEIVE_SMS,
                        Manifest.permission.SEND_SMS
                }, REQUEST_CODE);
                return false;
            }
        } else {
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "Izin Berhasil", Toast.LENGTH_SHORT).show();
                    MessageReceiver.bindListener(this);
                } else {
                    Toast.makeText(this, "Izin Ditolak", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
                break;
        }
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.actionSmsByIntent) {
            sendSmsByIntent();
        } else if (id == R.id.actionSmsManager) {
            sendSmsByManager();
        }
    }

    public void sendSmsByManager() {
        String nomorTujuan = "0089613614003";
        String message = "Halo Apa Kabar";
        try {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(nomorTujuan, null, message, null, null);
            Toast.makeText(getApplicationContext(), "SMS Berhasil Dikirim!", Toast.LENGTH_LONG).show();
        } catch (Exception ex) {
            Toast.makeText(getApplicationContext(), "Pengiriman SMS Gagal...", Toast.LENGTH_LONG).show();
            ex.printStackTrace();
        }
    }

    public void sendSmsByIntent() {
        String nomorTujuan = "0089613614003";
        String message = "Halo Apa Kabar";
        Uri uri = Uri.parse("smsto:" + nomorTujuan);
        Intent smsIntent = new Intent(Intent.ACTION_SENDTO, uri);
        smsIntent.putExtra("sms_body", message);
        try {
            startActivity(smsIntent);
        } catch (Exception ex) {
            Toast.makeText(Halamansms.this, "Pengiriman SMS Gagal...", Toast.LENGTH_LONG).show();
            ex.printStackTrace();
        }
    }

    @Override
    public void messageReceived(String message) {
        Toast.makeText(this, "New Message Received: " + message, Toast.LENGTH_SHORT).show();
    }
}
