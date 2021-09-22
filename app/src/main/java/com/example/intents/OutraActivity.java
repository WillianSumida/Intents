package com.example.intents;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.intents.databinding.ActivityOutraBinding;

class OutraActivity extends AppCompatActivity {
    ActivityOutraBinding activityOutraBinding = ActivityOutraBinding.inflate(getLayoutInflater());

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(activityOutraBinding.getRoot());

        getSupportActionBar().setTitle("Outra Activity");
        getSupportActionBar().setSubtitle("Recebe e retorna um valor");

        activityOutraBinding.recebidoTv.setText(getIntent().getStringExtra(MainActivity.PARAMETRO));

        activityOutraBinding.retornarBt.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent retorno = new Intent();

                retorno.putExtra(MainActivity.PARAMETRO, activityOutraBinding.retornoEt.getText().toString());
                setResult(RESULT_OK, retorno);
            }
        });
    }
}
