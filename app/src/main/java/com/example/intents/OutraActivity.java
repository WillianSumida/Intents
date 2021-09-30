package com.example.intents;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.intents.databinding.ActivityMainBinding;
import com.example.intents.databinding.ActivityOutraBinding;

public class OutraActivity extends AppCompatActivity {
    private ActivityOutraBinding activityOutraBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityOutraBinding = ActivityOutraBinding.inflate(getLayoutInflater());
        setContentView(activityOutraBinding.getRoot());

        getSupportActionBar().setTitle("Outra Activity");
        getSupportActionBar().setSubtitle("Recebe e retorna o valor");

        String parametro = getIntent().getStringExtra(MainActivity.PARAMETRO);
        activityOutraBinding.recebidoTv.setText(parametro);

        activityOutraBinding.retornarBt.setOnClickListener(view -> {
            String retorno = activityOutraBinding.retornoEt.getText().toString();
            Intent retornoIntent = new Intent();
            retornoIntent.putExtra(MainActivity.PARAMETRO, activityOutraBinding.retornoEt.getText().toString());
            setResult(RESULT_OK, retornoIntent);

            finish();
        });
    }

}