package com.slinksoft.publixcareeropenings;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Date;
import java.util.Scanner;

public class MainActivity extends AppCompatActivity {

    WebView browser;
    String id, pass;
    TextView version;
    int v, rev;
    boolean autofilled;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final File accPath = new File(getFilesDir() + "/PubCareer/" + File.separator + "info.ss");
        this.setTitle("Publix ACO - By Slink Soft");
        v = 1;
        rev = 0;
        version = findViewById(R.id.vDisplay);
        version.setText("Version: " + v + "." + rev);
        browser = findViewById(R.id.browserWV);
        browser.setWebViewClient(new WebViewClient()
        {
            public void onPageFinished(WebView view, String url) {
                System.out.println("Done Loading");

                if (!accPath.exists())
                {
                    AlertDialog note = new AlertDialog.Builder(MainActivity.this).create();
                    note.setTitle("INFO");
                    note.setMessage("If this is your first time using the app, go to \"Options\" and " +
                            "fill in your login information to make logging in easier and faster!");
                    note.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.dismiss();
                                }
                            });

                    note.show();
                }
                else if (!autofilled)
                {
                    try
                    {
                        Scanner read = new Scanner(new FileReader(accPath));
                        id = read.nextLine();
                        pass = read.nextLine();
                        read.close();
                        autofilled = true;
                        browser.evaluateJavascript("document.getElementById(\"userNameInput\").value =\"" + id + "\"", null);
                        browser.evaluateJavascript("document.getElementById(\"passwordInput\").value =\"" + pass + "\"", null);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        browser.getSettings().setJavaScriptEnabled(true);
        browser.getSettings().setDomStorageEnabled(true);
        browser.getSettings().setLoadWithOverviewMode(true);
        browser.getSettings().setUseWideViewPort(true);
        browser.getSettings().setBuiltInZoomControls(true);
        browser.getSettings().setDisplayZoomControls(false);
        browser.getSettings().setSupportZoom(true);
        browser.getSettings().setDefaultTextEncodingName("utf-8");
        browser.loadUrl("https://ssologin.publix.com/adfs/ls/idpinitiatedsignon?loginToRp=urn:adfs:federation:Kenexa:TG");

    }

    public void options(View v)
    {
        Intent options = new Intent(MainActivity.this, OptionsActivity.class);
        startActivity(options);
        browser.loadUrl("https://ssologin.publix.com/adfs/ls/idpinitiatedsignon?loginToRp=urn:adfs:federation:Kenexa:TG");
    }

    public void backBrowser(View v)
    {
        browser.goBack();
    }

    public void forwardBrowser(View v)
    {
        browser.goForward();
    }

    public void note(View v)
    {
        AlertDialog note = new AlertDialog.Builder(MainActivity.this).create();
        note.setTitle("NOTE");
        note.setMessage("The \"Publix Associate Career Openings\" app is intended to be used by Publix associates/employees only! If you " +
                "are interested in a career job at Publix (this service is not for their retail in-store " +
                "positions), go to: \n\nhttps://corporate.publix.com/careers/support-areas/current-openings");
        note.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
        note.setButton(AlertDialog.BUTTON_POSITIVE, "Visit Current Openings",
                new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://corporate.publix.com/careers/support-areas/current-openings"));
                        startActivity(browserIntent);
                        dialogInterface.dismiss();
                    }
                });

        note.show();
    }

    public void takeScreenshot(View v) {
        Date now = new Date(); // create date object for screenshot file name usage
        android.text.format.DateFormat.format("yyyy-MM-dd_hh:mm:ss", now); // get current date and time

        try {
            // take screenshot
            View v1 = getWindow().getDecorView().getRootView();
            v1.setDrawingCacheEnabled(true);
            // create bitmap to store screen capture
            Bitmap bitmap = Bitmap.createBitmap(v1.getDrawingCache());
            v1.setDrawingCacheEnabled(false);

            // use MediaStore to save screenshot to default Pictures directory
            MediaStore.Images.Media.insertImage(getContentResolver(), bitmap , now + "pubscreenshot", "N/A");

            Toast.makeText(getApplicationContext(), "Screenshot saved. Check your photo gallery!", Toast.LENGTH_SHORT).show();

        } catch (Throwable e) {
            // Several error may come out with file handling or DOM
            Toast.makeText(getApplicationContext(), "An error occurred!", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    public void onCredits(View v)
    {
        AlertDialog credits = new AlertDialog.Builder(MainActivity.this).create();
        credits.setTitle("Credits");
        credits.setMessage("Developed By: Slink (Dan)\nVisit:\nhttps://realslinksoft.wixsite.com/slink-soft-portfolio" +
                "\nand\nhttp://www.YouTube.Com/ReTrOSlink\n\nNOTE: This app is " +
                "not affiliated with Publix Super Markets Inc.! This app is written " +
                "by a college student with intent of practicing, utilizing, and growing his current skills " +
                "to become a prospective software developer at Publix's Information Systems department in Lakeland.\n\n" +
                "Thank you for using this app!\n\n- Slink");
        credits.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
        credits.setButton(AlertDialog.BUTTON_POSITIVE, "Visit SlinkSoft",
                new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialogInterface, int i) {
                        // navigates user to my portfolio upon click of the button
                        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://realslinksoft.wixsite.com/slink-soft-portfolio"));
                        startActivity(browserIntent);
                        dialogInterface.dismiss();
                    }
                });

        credits.show();
    }
}