package com.developersanjeev.uhbvn;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.text.InputFilter;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.firebase.auth.FirebaseAuth;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import locationprovider.davidserrano.com.LocationProvider;

public class MainActivity extends AppCompatActivity {

    private static final int SETTINGS_ACTION = 124;
    private int bothLocationsRequestCode = 1;
    private LocationProvider locationProvider;
    private double latitude, longitude;
    private SharedPreferences preferences;

    private EditText nameEditText, meterNumberEditText, mobileNumberEditText;

    private RadioGroup[] questions = new RadioGroup[30];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        toolbar.getOverflowIcon().setTint(Color.WHITE);

        nameEditText = findViewById(R.id.name_edit_text);
        meterNumberEditText = findViewById(R.id.meter_number_edit_text);
        mobileNumberEditText = findViewById(R.id.mobile_number_edit_text);

        InputFilter[] nameEditTextFilters = nameEditText.getFilters();
        InputFilter[] meterNumberEditTextFilters = meterNumberEditText.getFilters();
        InputFilter[] nameEditTextNewFilters = new InputFilter[nameEditTextFilters.length + 1];
        InputFilter[] meterNumberEditTextNewFilters = new InputFilter[meterNumberEditTextFilters.length + 1];
        System.arraycopy(nameEditTextFilters, 0, nameEditTextNewFilters, 0, nameEditTextFilters.length);
        System.arraycopy(meterNumberEditTextFilters, 0, meterNumberEditTextNewFilters, 0, meterNumberEditTextFilters.length);
        nameEditTextNewFilters[nameEditTextFilters.length] = new InputFilter.AllCaps();
        meterNumberEditTextNewFilters[meterNumberEditTextFilters.length] = new InputFilter.AllCaps();
        nameEditText.setFilters(nameEditTextNewFilters);
        meterNumberEditText.setFilters(meterNumberEditTextNewFilters);

        questions[0] = findViewById(R.id.q1_radio_group);
        questions[1] = findViewById(R.id.q2_radio_group);
        questions[2] = findViewById(R.id.q3_radio_group);
        questions[3] = findViewById(R.id.q4_radio_group);
        questions[4] = findViewById(R.id.q5_radio_group);
        questions[5] = findViewById(R.id.q6_radio_group);
        questions[6] = findViewById(R.id.q7_radio_group);
        questions[7] = findViewById(R.id.q8_radio_group);
        questions[8] = findViewById(R.id.q9_radio_group);
        questions[9] = findViewById(R.id.q10_radio_group);
        questions[10] = findViewById(R.id.q11_radio_group);
        questions[11] = findViewById(R.id.q12_radio_group);
        questions[12] = findViewById(R.id.q13_radio_group);
        questions[13] = findViewById(R.id.q14_radio_group);
        questions[14] = findViewById(R.id.q15_radio_group);
        questions[15] = findViewById(R.id.q16_radio_group);
        questions[16] = findViewById(R.id.q17_radio_group);
        questions[17] = findViewById(R.id.q18_radio_group);
        questions[18] = findViewById(R.id.q19_radio_group);
        questions[19] = findViewById(R.id.q20_radio_group);
        questions[20] = findViewById(R.id.q21_radio_group);
        questions[21] = findViewById(R.id.q22_radio_group);
        questions[22] = findViewById(R.id.q23_radio_group);
        questions[23] = findViewById(R.id.q24_radio_group);
        questions[24] = findViewById(R.id.q25_radio_group);
        questions[25] = findViewById(R.id.q26_radio_group);
        questions[26] = findViewById(R.id.q27_radio_group);
        questions[27] = findViewById(R.id.q28_radio_group);
        questions[28] = findViewById(R.id.q29_radio_group);
        questions[29] = findViewById(R.id.q30_radio_group);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkLocationPermission()) {
                startLocationUpdates();
            }
        } else {
            startLocationUpdates();
        }

        preferences = this.getSharedPreferences(Constants.UHBVN_PREFERENCES, Context.MODE_PRIVATE);
        String region = preferences.getString(Constants.CURRENT_CIRCLE, "NO_REGION");
        if (region.equals("Rohtak")) {
            View view = findViewById(R.id.optional_layout);
            view.setVisibility(View.VISIBLE);
        }

    }

    private boolean checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)) {
                showExplanation();
            } else {
                String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION};
                ActivityCompat.requestPermissions(this, permissions, bothLocationsRequestCode);
            }
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == bothLocationsRequestCode) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    startLocationUpdates();
                }
            } else {
                showExplanation();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SETTINGS_ACTION && resultCode == Activity.RESULT_OK) {
            startLocationUpdates();
        }
    }

    private void startLocationUpdates() {
        LocationProvider.LocationCallback callback = new LocationProvider.LocationCallback() {
            @Override
            public void onNewLocationAvailable(float lat, float lon) {
                Log.v("LOCATION", "New Location Available : " + String.valueOf(lat) + " : " + String.valueOf(lon));
                latitude = lat;
                longitude = lon;
            }

            @Override
            public void locationServicesNotEnabled() {
                Log.v("LOCATION", "Location Services not Enabled");
            }

            @Override
            public void updateLocationInBackground(float lat, float lon) {
                Log.v("LOCATION", "Location Updated in Background : " + String.valueOf(lat) + " : " + String.valueOf(lon));
                latitude = lat;
                longitude = lon;
            }

            @Override
            public void networkListenerInitialised() {
                Log.v("LOCATION", "Network Listener Initialised...");
            }

            @Override
            public void locationRequestStopped() {
                Log.v("LOCATION", "Stopped Requesting Location...");
            }
        };
        locationProvider = new LocationProvider.Builder()
                .setContext(this)
                .setListener(callback)
                .create();

        Log.v("LOCATION", "Starting Location Updates : ");
        getLifecycle().addObserver(locationProvider);
        locationProvider.requestLocation();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        getLifecycle().removeObserver(locationProvider);
    }

    private void showExplanation() {
        new AlertDialog.Builder(this)
                .setTitle("Location Permissions Required")
                .setMessage("You need to give location permissions to be able to submit data")
                .setPositiveButton("OK", (dialogInterface, i) -> {
                    dialogInterface.dismiss();
                    Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                    Uri uri = Uri.fromParts("package", getPackageName(), null);
                    intent.setData(uri);
                    startActivityForResult(intent, SETTINGS_ACTION);
                })
                .setNegativeButton("Cancel", (dialogInterface, i) -> dialogInterface.dismiss())
                .show();
    }

    public void submitRegion(View view) {
        if (TextUtils.isEmpty(nameEditText.getText())) {
            Toast.makeText(this, "Please enter the name", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(meterNumberEditText.getText())) {
            Toast.makeText(this, "Please enter the meter number", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(mobileNumberEditText.getText())) {
            Toast.makeText(this, "Please enter the mobile number", Toast.LENGTH_SHORT).show();
            return;
        }
        Pattern CORRECT_PHONE_NUMBER_PATTERN =
                Pattern.compile("^[6789][0-9]{9}$");
        Matcher matcher = CORRECT_PHONE_NUMBER_PATTERN.matcher(mobileNumberEditText.getText().toString());
        if (!matcher.find()) {
            Toast.makeText(this, "Please enter a valid 10 digit mobile number", Toast.LENGTH_SHORT).show();
            return;
        }
        String region = preferences.getString(Constants.CURRENT_CIRCLE, "NO_CIRCLE");
        String subdivison = preferences.getString(Constants.CURRENT_SUBDIVISON, "NO_SUBDIVISON");
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy-hh-mm-ss", Locale.US);
        String format = dateFormat.format(new Date());
        if (region.equals("Rohtak")) {
            for (int i = 0; i < 30; i++) {
                if (questions[i].getCheckedRadioButtonId() == -1) {
                    Toast.makeText(this, "Please answer all the question before submitting the form", Toast.LENGTH_SHORT).show();
                    return;
                }
            }
            RadioButton[] radioButtons = new RadioButton[30];
            for (int i = 0; i < 30; i++)
                radioButtons[i] = findViewById(questions[i].getCheckedRadioButtonId());

            FormResponse response = new FormResponse(FirebaseAuth.getInstance().getCurrentUser().getDisplayName(),
                    FirebaseAuth.getInstance().getCurrentUser().getEmail(),
                    region,
                    subdivison,
                    latitude,
                    longitude,
                    nameEditText.getText().toString(),
                    meterNumberEditText.getText().toString(),
                    mobileNumberEditText.getText().toString(),
                    format,
                    radioButtons[0].getText().toString(),
                    radioButtons[1].getText().toString(),
                    radioButtons[2].getText().toString(),
                    radioButtons[3].getText().toString(),
                    radioButtons[4].getText().toString(),
                    radioButtons[5].getText().toString(),
                    radioButtons[6].getText().toString(),
                    radioButtons[7].getText().toString(),
                    radioButtons[8].getText().toString(),
                    radioButtons[9].getText().toString(),
                    radioButtons[10].getText().toString(),
                    radioButtons[11].getText().toString(),
                    radioButtons[12].getText().toString(),
                    radioButtons[13].getText().toString(),
                    radioButtons[14].getText().toString(),
                    radioButtons[15].getText().toString(),
                    radioButtons[16].getText().toString(),
                    radioButtons[17].getText().toString(),
                    radioButtons[18].getText().toString(),
                    radioButtons[19].getText().toString(),
                    radioButtons[20].getText().toString(),
                    radioButtons[21].getText().toString(),
                    radioButtons[22].getText().toString(),
                    radioButtons[23].getText().toString(),
                    radioButtons[24].getText().toString(),
                    radioButtons[25].getText().toString(),
                    radioButtons[26].getText().toString(),
                    radioButtons[27].getText().toString(),
                    radioButtons[28].getText().toString(),
                    radioButtons[29].getText().toString()
            );

            Intent intent = new Intent(this, SignatureActivity.class);
            intent.putExtra(Constants.KEY_EXTRA_DATA, response);
            startActivity(intent);

        } else {
            for (int i = 0; i < 14; i++) {
                if (questions[i].getCheckedRadioButtonId() == -1) {
                    Toast.makeText(this, "Please answer all the questions before submitting the form", Toast.LENGTH_SHORT).show();
                    return;
                }
            }
            RadioButton[] radioButtons = new RadioButton[14];
            for (int i = 0; i < 14; i++)
                radioButtons[i] = findViewById(questions[i].getCheckedRadioButtonId());
            FormResponse response = new FormResponse(FirebaseAuth.getInstance().getCurrentUser().getDisplayName(),
                    FirebaseAuth.getInstance().getCurrentUser().getEmail(),
                    region,
                    subdivison,
                    latitude,
                    longitude,
                    nameEditText.getText().toString(),
                    meterNumberEditText.getText().toString(),
                    mobileNumberEditText.getText().toString(),
                    format,
                    radioButtons[0].getText().toString(),
                    radioButtons[1].getText().toString(),
                    radioButtons[2].getText().toString(),
                    radioButtons[3].getText().toString(),
                    radioButtons[4].getText().toString(),
                    radioButtons[5].getText().toString(),
                    radioButtons[6].getText().toString(),
                    radioButtons[7].getText().toString(),
                    radioButtons[8].getText().toString(),
                    radioButtons[9].getText().toString(),
                    radioButtons[10].getText().toString(),
                    radioButtons[11].getText().toString(),
                    radioButtons[12].getText().toString(),
                    radioButtons[13].getText().toString(),
                    "N/A",
                    "N/A",
                    "N/A",
                    "N/A",
                    "N/A",
                    "N/A",
                    "N/A",
                    "N/A",
                    "N/A",
                    "N/A",
                    "N/A",
                    "N/A",
                    "N/A",
                    "N/A",
                    "N/A",
                    "N/A"
            );
            // DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
            // reference.child("data").push().setValue(response);

            Intent intent = new Intent(this, SignatureActivity.class);
            intent.putExtra(Constants.KEY_EXTRA_DATA, response);
            startActivity(intent);

        }

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.menu_about_developer) {
            launchAboutDeveloperActivity();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void launchAboutDeveloperActivity() {
        Intent intent = new Intent(this, AboutActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.about_menu, menu);
        return true;
    }
}
