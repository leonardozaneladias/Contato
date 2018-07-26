package br.com.caelum.contato;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class ProvasActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_provas);


        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        transaction.replace(R.id.provas_view, new ListaProvasFragment());

        transaction.commit();

    }
}
