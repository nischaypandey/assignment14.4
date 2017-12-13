package com.example.user.myapplication;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity
{
    //Creating reference of Button class.
    Button cameraBtn;

    @Override
    //onCreate method.
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);   //Setting content view.

        //Setting reference with its ID.
        cameraBtn=(Button)findViewById(R.id.camera_btn);

        //Handling onClick event on Button.
        cameraBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                //Checking that button is clicked.
                if(v.getId()==R.id.camera_btn)
                {
                    //Asking and checking for permission by follwing method.
                    askForPermission(Manifest.permission.CAMERA,100);
                }
            }
        });
    }

    //Creting Method to check permission.
    public void askForPermission(String permission,int requestCode)
    {
        //Checking if permission is Granted or not.
        if(ContextCompat.checkSelfPermission(MainActivity.this,permission)!= PackageManager.PERMISSION_GRANTED)
        {
            //Should we show an explanation.
            if(ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this,permission))
            {
                //This is called if user has denied the permission before
                //In this case I am just asking the permission again
                ActivityCompat.requestPermissions(MainActivity.this, new String[]{permission}, requestCode);

            }
            else
            {
                //Asking for permission if did not deny in that case too.
                ActivityCompat.requestPermissions(MainActivity.this,new String[]{permission},requestCode);
            }

            //Displaying Toast.
            Toast.makeText(MainActivity.this,"Permission Requested.",Toast.LENGTH_SHORT).show();

        }
        else
        {
            //Displaying Toast.
            Toast.makeText(MainActivity.this,"Permission Graned",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    //Method after result of request of permission.
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
    {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        //Checking that array is not empty.
        if(grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED)
        {
            if(ActivityCompat.checkSelfPermission(MainActivity.this,permissions[0])==PackageManager.PERMISSION_GRANTED)
            {
                //creating Intent to open Camera.
                Intent takePicture=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                //final checking that Permission is granted.
                if(takePicture.resolveActivity(getPackageManager())!=null)
                {
                    startActivity(takePicture);
                }
                else
                {
                    //Displaying Toast.
                    Toast.makeText(MainActivity.this,"Something went Wrong!! Error Occured",Toast.LENGTH_SHORT).show();
                }
            }
        }
        else
        {
            //Displaying Toast.
            Toast.makeText(MainActivity.this,"Something went Wrong!! Error Occured",Toast.LENGTH_SHORT).show();
        }
    }
}

