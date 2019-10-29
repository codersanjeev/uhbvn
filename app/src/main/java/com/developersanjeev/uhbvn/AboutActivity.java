package com.developersanjeev.uhbvn;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.util.List;

public class AboutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
    }

    public void onButtonClick(View view) {
        Intent intent = null;
        switch (view.getId()) {
            case R.id.twitter_iv:
                try {
                    this.getPackageManager().getPackageInfo("com.twitter.android", 0);
                    intent = new Intent(Intent.ACTION_VIEW, Uri.parse("twitter://user?screen_name=codersanjeev"));
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                } catch (PackageManager.NameNotFoundException e) {
                    intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://twitter.com/codersanjeev"));
                }
                startActivity(intent);
                break;
            case R.id.instagram_iv:
                intent = new Intent(Intent.ACTION_VIEW);
                String url = "https://www.instagram.com/coolest.guy.under.the.sun";
                PackageManager packageManager = getApplicationContext().getPackageManager();
                try {
                    if (packageManager.getPackageInfo("com.instagram.android", 0) != null) {
                        intent.setData(Uri.parse("http://instagram.com/_u/coolest.guy.under.the.sun"));
                        intent.setPackage("com.instagram.android");
                        startActivity(intent);
                    }
                } catch (PackageManager.NameNotFoundException ignored) {
                }
                intent.setData(Uri.parse(url));
                startActivity(intent);
                break;
            case R.id.linkedIn_iv:
                intent = new Intent(Intent.ACTION_VIEW, Uri.parse("linkedin://add/%/@developersanjeev"));
                PackageManager pm = getApplicationContext().getPackageManager();
                List<ResolveInfo> list = pm.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
                if (list.isEmpty()) {
                    intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.linkedin.com/profile/view?id=developersanjeev"));
                }
                startActivity(intent);
                break;
            case R.id.facebook_iv:
                String facebookUrl;
                PackageManager packageManagerr = getApplicationContext().getPackageManager();
                try {
                    int versionCode = packageManagerr.getPackageInfo("com.facebook.katana", 0).versionCode;
                    if (versionCode >= 3002850) {
                        facebookUrl = "fb://facewebmodal/f?href=" + "https://www.facebook.com/codersanjeev";
                    } else {
                        facebookUrl = "fb://page/" + "codersanjeev";
                    }

                } catch (Exception e) {
                    facebookUrl = "https://www.facebook.com/codersanjeev";
                }
                intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(facebookUrl));
                startActivity(intent);
                break;
            case R.id.github_iv:
                intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.github.com/thedevelopersanjeev"));
                startActivity(intent);
                break;
            case R.id.medium_iv:
                intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://medium.com/@thedevelopersanjeev"));
                startActivity(intent);
                break;
        }
    }
}
