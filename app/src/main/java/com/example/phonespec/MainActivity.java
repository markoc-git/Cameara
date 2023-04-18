package com.example.phonespec;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    private static final int REQUEST_CAMERA_PERMISSION = 100;
    ImageView imageView;
    Button camera;
    final int REQUEST_IMAGE_CAPTURE = 1;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        camera = findViewById(R.id.cameraOpen);
        imageView = findViewById(R.id.image);


        camera.setOnClickListener(v -> {
            Log.d("click","Rad");
            dispatchTakePictureIntent();
// prvo proverimo da li aplikacija ima dozvolu za pristup kameri

        });
    }
    private void dispatchTakePictureIntent() {
        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            // ako ima dozvolu, pokrećemo kameru
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);
        } else {
            // ako nema dozvolu, tražimo je od korisnika
            ActivityCompat.requestPermissions(MainActivity.this , new String[] { Manifest.permission.CAMERA }, REQUEST_CAMERA_PERMISSION);
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK && data != null) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            imageView.setImageBitmap(imageBitmap);

            String path = saveImageToInternalStorage(imageBitmap);

            String imageUrl = "file://" + path;
            Log.d("Image URL", imageUrl);
        }
    }

    private String saveImageToInternalStorage(Bitmap bitmapImage) {

        // Get the directory for the app's private pictures directory.
        File directory = getApplicationContext().getDir("imageDir", Context.MODE_PRIVATE);

        // Create image file
        File myPath = new File(directory, "myImage.jpg");
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(myPath);
            bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, fos);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                Objects.requireNonNull(fos).close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return myPath.getAbsolutePath();
    }
}