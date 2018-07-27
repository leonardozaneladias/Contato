package br.com.caelum.contato;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class ProvasActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_provas);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        if(isLand()){
            transaction.replace(R.id.provas_view, new ListaProvasFragment());
            transaction.replace(R.id.topicos_view, new DetalhesProvasFragment());
        }else{
            transaction.replace(R.id.provas_view, new ListaProvasFragment());
        }

        transaction.commit();

    }

    private boolean isLand() {

        return  getResources().getBoolean(R.bool.isLand);
    }

    public void lidaComProvas(Provas selecinada) {

        FragmentManager fragmentManager = getSupportFragmentManager();

        if(isLand()){

            DetalhesProvasFragment detalhesProvasFragment = (DetalhesProvasFragment) fragmentManager.findFragmentById(R.id.topicos_view);
            detalhesProvasFragment.populaTable(selecinada);

        }else{

            FragmentTransaction transaction = fragmentManager.beginTransaction();
            DetalhesProvasFragment detalhes = new DetalhesProvasFragment();
            Bundle args = new Bundle();
            args.putSerializable("prova", selecinada);
            detalhes.setArguments(args);

            transaction.replace(R.id.provas_view,detalhes);
            transaction.addToBackStack(null);
            transaction.commit();

        }
    }
}
