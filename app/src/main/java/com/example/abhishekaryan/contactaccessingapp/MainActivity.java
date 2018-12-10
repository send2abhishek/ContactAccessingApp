package com.example.abhishekaryan.contactaccessingapp;

import android.Manifest;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {


    private TextView textView;

    private Uri uriContent = ContactsContract.Contacts.CONTENT_URI;

    private String[] all_columns = {ContactsContract.Contacts.DISPLAY_NAME,
            ContactsContract.Contacts.HAS_PHONE_NUMBER};
    private Cursor cursor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getLoaderManager().initLoader(0,null,this);



        }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            //ask for authorisation
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_CONTACTS}, 50);
        }


        return new CursorLoader(this,uriContent,all_columns,null,null,null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

        cursor=data;

        StringBuilder stringBuilder = new StringBuilder();
        if(cursor !=null && cursor.getCount() >0) {
            while (cursor.moveToNext()) {

                stringBuilder.append(cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME))

                        + "  " + cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER)) + "\n"

                );
            }


            textView = (TextView) findViewById(R.id.activity_main_contacts);
            //cursor.moveToNext();
            textView.setText(stringBuilder.toString());
        }
        else {
            textView.setText("No Contact Found");
        }


    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }








}

