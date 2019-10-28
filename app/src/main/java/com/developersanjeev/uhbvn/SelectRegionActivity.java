package com.developersanjeev.uhbvn;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.jaredrummler.materialspinner.MaterialSpinner;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SelectRegionActivity extends AppCompatActivity {

    private MaterialSpinner circleSpinner, subdivisonSpinner;
    private String[] circles = {"Rohtak", "Ambala", "Yamuna Nagar", "Panipat", "Sonipat", "Jhajjar", "Karnal", "Hisar"};
    private Map<String, List<String>> regions;
    private static final int RC_SIGN_IN = 123;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_region);

        SharedPreferences preferences = this.getSharedPreferences(Constants.UHBVN_PREFERENCES, Context.MODE_PRIVATE);
        boolean hasSignedIn = preferences.getBoolean(Constants.KEY_LOGGED_IN, false);
        if (!hasSignedIn) {
            List<AuthUI.IdpConfig> providers = Arrays.asList(
                    new AuthUI.IdpConfig.GoogleBuilder().build());
            startActivityForResult(
                    AuthUI.getInstance()
                            .createSignInIntentBuilder()
                            .setAvailableProviders(providers)
                            .setIsSmartLockEnabled(false)
                            .build(),
                    RC_SIGN_IN);
        }

        populateRegions();

        circleSpinner = findViewById(R.id.circle_spinner);
        subdivisonSpinner = findViewById(R.id.subdivison_spinner);
        circleSpinner.setItems(circles);
        subdivisonSpinner.setItems(regions.get(circles[0]));
        circleSpinner.setOnItemSelectedListener((view, position, id, item) -> subdivisonSpinner.setItems(regions.get(circles[position])));

    }

    private void populateRegions() {
        regions = new HashMap<>();
        regions.put("Rohtak", Arrays.asList("No. 1 Rohtak", "No. 2 Rohtak", "No. 3 Rohtak"));
        regions.put("Ambala", Arrays.asList("City Panchkula", "S/U Panchkula + Madanpur", "No. 2 Ambala Cant", "Model Town Ambala City"));
        regions.put("Yamuna Nagar", Arrays.asList("M/T Yamunanagar", "Industrial Area Ynagar"));
        regions.put("Panipat", Arrays.asList("M/T Panipat", "City Panipat", "Sanoli Road Panipat"));
        regions.put("Sonipat", Arrays.asList("City Sonipat", "Industrial Area", "Model Town Sonepat"));
        regions.put("Jhajjar", Arrays.asList("No. 1 Bahadurgarh", "No. 2 Bahadurgarh", "City Jhajjar"));
        regions.put("Karnal", Arrays.asList("City Karnal", "Model Town Karnal"));
        regions.put("Hisar", Arrays.asList("City Line Hisar", "Civil Line Hisar", "City Hansi"));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            if (resultCode == RESULT_OK) {
                // Successfully signed in
                SharedPreferences preferences = this.getSharedPreferences(Constants.UHBVN_PREFERENCES, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putBoolean(Constants.KEY_LOGGED_IN, true);
                editor.apply();
            } else {
                Toast.makeText(this, "Sign In Failed", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void submitRegion(View view) {
        String selectedCircle = circles[circleSpinner.getSelectedIndex()];
        String selectedSubdivison = regions.get(circles[circleSpinner.getSelectedIndex()]).get(subdivisonSpinner.getSelectedIndex());
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference ref = db.getReference("users");
        DatabaseReference regionsRef = db.getReference("regions");
        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setTitle("Are you sure?")
                .setMessage("Circle : " + selectedCircle + "\nSub Divison : " + selectedSubdivison)
                .setPositiveButton("Yes", (dialogInterface, i) -> {
                    User user = new User(firebaseUser.getDisplayName(), firebaseUser.getEmail(), firebaseUser.getUid());
                    // selectedCircle
                    // selectedSubdivison
                    Map<String, String> value = new HashMap<>();
                    value.put(selectedCircle, selectedSubdivison);
                    SharedPreferences preferences = this.getSharedPreferences(Constants.UHBVN_PREFERENCES, Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString(Constants.CURRENT_CIRCLE, selectedCircle);
                    editor.putString(Constants.CURRENT_SUBDIVISON, selectedSubdivison);
                    editor.apply();
                    regionsRef.child(firebaseUser.getUid()).push().setValue(value);
                    ref.child(firebaseUser.getUid()).setValue(user);
                    Intent intent = new Intent(this, MainActivity.class);
                    startActivity(intent);
                })
                .setNegativeButton("No", (dialogInterface, i) -> dialogInterface.dismiss())
                .setCancelable(false);
        builder.create().show();
    }
}
