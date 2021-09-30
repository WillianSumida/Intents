package com.example.intents;

import static android.Manifest.permission.CALL_PHONE;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.MenuItem;

import com.example.intents.databinding.ActivityMainBinding;


public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding    activityMainBinding;
    private ActivityResultLauncher outraActivityResultLauncher;
    private ActivityResultLauncher requisicaoPermissaoActivityResultLauncher;
    private ActivityResultLauncher getImagemActivityResultLauncher;
    private ActivityResultLauncher chooseAppActivityResultLauncher;
    public static final String     PARAMETRO = "PARAMETRO";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMainBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(activityMainBinding.getRoot());

        getSupportActionBar().setTitle("Tratando Intents");
        getSupportActionBar().setSubtitle("Principais tipos de Intents");

        outraActivityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            if (result.getResultCode() == OutraActivity.RESULT_OK)
                activityMainBinding.retornoTv.setText(result.getData().getStringExtra(PARAMETRO));
        });

        requisicaoPermissaoActivityResultLauncher = registerForActivityResult(new ActivityResultContracts.RequestPermission(), result -> {
            if(!result) permissaoLigacao();
            else telefone();
        });

        getImagemActivityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                this::viewImage);

        chooseAppActivityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                this::viewImage);
    }

    private void viewImage(ActivityResult result){
        if(result.getResultCode() == RESULT_OK){
            Intent viewImageIntent = new Intent(Intent.ACTION_VIEW);
            viewImageIntent.setData(result.getData().getData());
            startActivity(viewImageIntent);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.outraActivityMi) {
            Intent outraActivityIntent = new Intent(this, OutraActivity.class);
            outraActivityIntent.putExtra(PARAMETRO, activityMainBinding.parametroEt.getText().toString());

            outraActivityResultLauncher.launch(outraActivityIntent);

            return true;
        }else if (item.getItemId() == R.id.viewMi) {
            String url = activityMainBinding.parametroEt.getText().toString();
            if (!url.toLowerCase().contains("http") && !url.toLowerCase().contains("https"))
                url = "http://" + url;

            Intent siteIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            startActivity(siteIntent);

            return true;
        }else if (item.getItemId() == R.id.callMi) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (checkSelfPermission(CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    permissaoLigacao();
                } else {
                    telefone();
                }
            } else {
                telefone();
            }

            return true;
        }else if (item.getItemId() == R.id.dialMi) {
            Intent discadorIntent = new Intent(Intent.ACTION_DIAL);
            discadorIntent.setData(Uri.parse("tel: " + activityMainBinding.parametroEt.getText()));
            startActivity(discadorIntent);

            return true;
        }else if (item.getItemId() == R.id.pickMi) {
            Intent pegarImagemIntent = new Intent(Intent.ACTION_PICK);
            String diretorio = this.getExternalFilesDir(Environment.DIRECTORY_PICTURES).getPath();
            pegarImagemIntent.setDataAndType(Uri.parse(diretorio), "image/*");
            getImagemActivityResultLauncher.launch(pegarImagemIntent);

            return true;
        }else if (item.getItemId() == R.id.chooserMi) {
            Intent escolherActivityIntent = new Intent(Intent.ACTION_CHOOSER);
            escolherActivityIntent.putExtra(Intent.EXTRA_INTENT, IntentImage());
            escolherActivityIntent.putExtra(Intent.EXTRA_TITLE, "Escolha um aplicativo");
            chooseAppActivityResultLauncher.launch(escolherActivityIntent);

            return true;
        }
        return true;
    }

    private Intent IntentImage(){
        Intent getImageIntent = new Intent(Intent.ACTION_PICK);
        String dir = this.getExternalFilesDir(Environment.DIRECTORY_PICTURES).getPath();
        getImageIntent.setDataAndType(Uri.parse(dir), "image/*");
        return getImageIntent;
    }

    private void permissaoLigacao(){
        requisicaoPermissaoActivityResultLauncher.launch(CALL_PHONE);
    }

    private void telefone(){
        Intent chamarIntent = new Intent();
        chamarIntent.setAction(Intent.ACTION_CALL);
        chamarIntent.setData(Uri.parse("tel: " + activityMainBinding.parametroEt.getText()));
        startActivity(chamarIntent);
    }
}