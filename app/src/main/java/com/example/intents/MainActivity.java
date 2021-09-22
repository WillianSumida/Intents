package com.example.intents;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.intents.databinding.ActivityMainBinding;

class MainActivity extends AppCompatActivity {
    public static final String PARAMETRO = "PARAMETRO";
    private ActivityMainBinding activityMainBinding;
    private ActivityResultLauncher<Intent> outraActivityResultLauncher;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMainBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(activityMainBinding.getRoot());

        getSupportActionBar().setTitle("Tratando Intents");
        getSupportActionBar().setSubtitle("Principais tipos");

        outraActivityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == OutraActivity.RESULT_OK) {
                            activityMainBinding.retornoTv.setText(result.getData().getStringExtra(PARAMETRO));
                        }
                    }
                });
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.outraActivityMi){
            Intent outraActivityIntent = new Intent(this, OutraActivity.class);
            outraActivityIntent.putExtra(PARAMETRO, activityMainBinding.parametroEt.getText().toString());
            outraActivityResultLauncher.launch(outraActivityIntent);
            return true;
        }
        else if (item.getItemId() == R.id.callMi){
            return true;
        }
        else if (item.getItemId() == R.id.dialMi){
            return true;
        }
        else if (item.getItemId() == R.id.pickMi){
            return true;
        }
        else if (item.getItemId() == R.id.chooserMi){
            return true;
        }
        else{
            return false;
        }

    }
}