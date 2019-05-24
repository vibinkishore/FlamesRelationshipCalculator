package com.viki.flamesrelationshipcalculator;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;

import static com.viki.flamesrelationshipcalculator.R.string.*;

public class DisplayResultActivity extends AppCompatActivity {

    String relation,user,partner;
    ImageView resImage;
    TextView resTitle, resDesc;
    ProgressDialog progress;
    TextView footer;
    AlertDialog.Builder exitDialog;/*
    private InterstitialAd mInterstitialAd;*/


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_result);

        // add loader
        relation = getIntent().getStringExtra("relation");
        user = toTitleCase(getIntent().getStringExtra("user"));
        partner = toTitleCase(getIntent().getStringExtra("partner"));

        resImage = findViewById(R.id.resImage);
        resTitle = findViewById(R.id.resTitle);
        resDesc = findViewById(R.id.resDesc);
        footer = findViewById(R.id.aboutDev);

        footer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),AboutDeveloperActivity.class));
            }
        });

        exitDialog = new AlertDialog.Builder(this);
        exitDialog.setTitle("Thank you!");
        exitDialog.setMessage("Do you really want to Exit? Why not Try for some other name pairs?");
        exitDialog.setCancelable(false);
        exitDialog.setPositiveButton("Stay", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
                finish();
            }
        });

        exitDialog.setNegativeButton("Exit", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent homeIntent = new Intent(Intent.ACTION_MAIN);
                homeIntent.addCategory( Intent.CATEGORY_HOME );
                homeIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(homeIntent);
                finish();
            }
        });

        progress = new ProgressDialog(DisplayResultActivity.this);
        progress.setTitle("Please Wait");
        progress.setMessage("Loading Content");
        progress.show();

        if (relation.equals("error")) {
            Toast.makeText(this, "Please Try Again..", Toast.LENGTH_SHORT).show();
            progress.dismiss();
            onBackPressed();
        }
        if (relation.equals("Friend")) {
            resImage.setImageResource(R.drawable.f_result);
            resTitle.setText(String.format("%s & %s are Friends", user, partner));
            resDesc.setText(desc_f);
            progress.dismiss();
        } if (relation.equals("Love")) {
            resImage.setImageResource(R.drawable.l_result);
            resTitle.setText(String.format("%s & %s love each other", user, partner));
            resDesc.setText(desc_l);
            progress.dismiss();
        } if (relation.equals("Affection")) {
            resImage.setImageResource(R.drawable.a_result);
            resTitle.setText(String.format("%s & %s are Affectionate", user, partner));
            resDesc.setText(desc_a);
            progress.dismiss();
        } if (relation.equals("Marriage")) {
            resImage.setImageResource(R.drawable.m_result);
            resTitle.setText(String.format("%s & %s are Life Partners", user, partner));
            resDesc.setText(desc_m);
            progress.dismiss();
        } if (relation.equals("Enemy")) {
            resImage.setImageResource(R.drawable.e_result);
            resTitle.setText(String.format("%s & %s hate each other", user, partner));
            resDesc.setText(desc_e);
            progress.dismiss();
        } if (relation.equals("Sibling")) {
            resImage.setImageResource(R.drawable.s_result);
            resTitle.setText(String.format("%s & %s are Siblings", user, partner));
            resDesc.setText(desc_s);
            progress.dismiss();
        }
    }

    private String toTitleCase(String user) {
        String s1 = String.valueOf(user.charAt(0));
        s1 = s1.toUpperCase();
        String s2 = user.substring(1);
        return s1+s2;
    }

    @Override
    public void onBackPressed() {
        exitDialog.show();
    }
}
