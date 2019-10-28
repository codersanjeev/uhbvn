package com.developersanjeev.uhbvn;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.github.gcacace.signaturepad.views.SignaturePad;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;

public class SignatureActivity extends AppCompatActivity {

    private Button submitButton;
    private FormResponse response;
    private SignaturePad signaturePad;
    private StorageReference signaturesRef;
    private DatabaseReference dataRef;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signature);

        Intent intent = getIntent();
        response = (FormResponse) intent.getSerializableExtra(Constants.KEY_EXTRA_DATA);
        submitButton = findViewById(R.id.submit_button);
        signaturePad = findViewById(R.id.signature_pad);
        progressBar = findViewById(R.id.progress_bar);
        signaturesRef = FirebaseStorage.getInstance().getReference("signatures");
        dataRef = FirebaseDatabase.getInstance().getReference().child("data");

        signaturePad.setOnSignedListener(new SignaturePad.OnSignedListener() {
            @Override
            public void onStartSigning() {
            }

            @Override
            public void onSigned() {
                submitButton.setEnabled(true);
            }

            @Override
            public void onClear() {
                submitButton.setEnabled(false);
            }
        });


    }

    public void submitData(View view) {
        if (!submitButton.isEnabled()) {
            Toast.makeText(this, "Please sign in before submitting...", Toast.LENGTH_SHORT).show();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);
        submitButton.setEnabled(false);
        signaturePad.setEnabled(false);

        Bitmap bitmap = signaturePad.getSignatureBitmap();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 50, baos);
        byte[] data = baos.toByteArray();

        // file name, keep it as current time in ms to keep each name as unique
        StorageReference fileRef = signaturesRef.child(String.valueOf(System.currentTimeMillis()));
        UploadTask task = fileRef.putBytes(data);
        task.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
            @Override
            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                return fileRef.getDownloadUrl();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(SignatureActivity.this, "Something went wrong... try submitting the form again.", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(SignatureActivity.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        }).addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                response.setSignUrl(uri.toString());
                dataRef.push().setValue(response);
                showSuccessDialog();
            }
        });

    }

    private void showSuccessDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setTitle("Success")
                .setMessage("Your data has been submitted successfully...")
                .setPositiveButton("OK", (dialogInterface, i) -> {
                    Intent intent = new Intent(SignatureActivity.this, MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                })
                .setCancelable(false);
        builder.create().show();
    }
}
