package com.viki.flamesrelationshipcalculator;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.MobileAds;

public class MainActivity extends AppCompatActivity {

    EditText yourName, partnerName;
    Button proceed, reset;
    TextView footer;
    ProgressBar mProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       //ad init

        yourName = findViewById(R.id.yourName);
        partnerName = findViewById(R.id.partnerName);
        proceed = findViewById(R.id.calcDetails);
        reset = findViewById(R.id.resetDetails);
        footer = findViewById(R.id.aboutDev);

        mProgress = new ProgressBar(this);

        footer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),AboutDeveloperActivity.class));
            }
        });

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                yourName.setText("");
                partnerName.setText("");
            }
        });

        proceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String uName = yourName.getText().toString();
                String pName = partnerName.getText().toString();
                mProgress.setVisibility(View.VISIBLE);

                if (TextUtils.isEmpty(uName)) {
                    yourName.setError("Enter a valid Input");
                    mProgress.setVisibility(View.INVISIBLE);
                } if (TextUtils.isEmpty(pName)) {
                    partnerName.setError("Enter a valid Input");
                    mProgress.setVisibility(View.INVISIBLE);
                }
                if (!TextUtils.isEmpty(uName) && !TextUtils.isEmpty(pName)){
                    yourName.setText("");
                    partnerName.setText("");
                    calcResult(uName,pName);
                }
            }
        });
    }

    private void calcResult(String uName, String pName) {
        Intent dispResult = new Intent(getApplicationContext(),DisplayResultActivity.class);
        String relation = findRelation(uName,pName);
        Toast.makeText(this, relation+"", Toast.LENGTH_SHORT).show();
        dispResult.putExtra("user",uName);
        dispResult.putExtra("partner",pName);
        dispResult.putExtra("relation",relation);
        mProgress.setVisibility(View.INVISIBLE);
        startActivity(dispResult);
    }

    private String findRelation(String uName, String pName) {
        String Rel;
        uName = uName.replace(" ","");
        pName = pName.replace(" ","");
        uName = uName.trim();
        pName = pName.trim();
        StringBuffer s1 = new StringBuffer(uName);
        StringBuffer s2 = new StringBuffer(pName);

        char res = process(s1,s2);

        switch(res)
        {
            case 'F':Rel = "Friend";
                break;
            case 'L':Rel = "Love";
                break;
            case 'A':Rel = "Affection";
                break;
            case 'M':Rel = "Marriage";
                break;
            case 'E':Rel = "Enemy";
                break;
            case 'S':Rel = "Sibling";
                break;
            default : Rel = "error";
                break;
        }
        return Rel;
    }

    private char process(StringBuffer s1,StringBuffer s2) {
        char c,d,e='\0';
        for(int i=0;i<s1.length();i++)
        {
            c=s1.charAt(i);
            for(int j=0;j<s2.length();j++)
            {
                d=s2.charAt(j);
                if(c==d)
                {
                    s1.deleteCharAt(i);
                    s2.deleteCharAt(j);
                    i--;
                    j--;
                    break;
                }
            }
        }
        s1=s1.append(s2);
        int k=6,l=0;
        String s3="FLAMES";
        while(k>1)
        {
            for(int m=1;m<s1.length();m++)
            {
                if(s3.charAt(l)!='X')
                {
                    l++;
                    if(l==6)
                        l=0;
                }
                else
                {
                    l++;
                    if(l==6)
                        l=0;
                    m--;
                }
            }
            while(s3.charAt(l)=='X')
            {
                l++;
                if(l==6)
                    l=0;
            }
            s3=s3.substring(0,l)+"X"+s3.substring(l+1);
            k--;
        }

        for(int i=0;i<s3.length();i++)
        {
            if(s3.charAt(i)!='X')
                e=s3.charAt(i);
        }
        return e;
    }
}
