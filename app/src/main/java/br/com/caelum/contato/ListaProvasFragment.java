package br.com.caelum.contato;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.Arrays;
import java.util.List;

public class ListaProvasFragment extends Fragment {

    private ListView listViewProvas;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View viewProvas = inflater.inflate(R.layout.fragment_lista_provas, container, false);

        Provas prova1 = new Provas("20/06/2018", "Matematica");
        prova1.setTopicos(Arrays.asList("Algebra", "Calculo", "Estatiticas"));

        Provas prova2 = new Provas("20/07/2018", "Portugues");
        prova2.setTopicos(Arrays.asList("Oracoes", "Complemento Nominal", "Analise Sintatica"));

        List<Provas> provasAdapter = Arrays.asList(prova1, prova2);

        this.listViewProvas = (ListView) viewProvas.findViewById(R.id.fragment_lista_provas);
        this.listViewProvas.setAdapter(new ArrayAdapter<Provas>(getContext(), android.R.layout.simple_list_item_1, provasAdapter));

        this.listViewProvas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Provas selecinada = (Provas) adapterView.getItemAtPosition(i);
                ProvasActivity activity = (ProvasActivity) getActivity();
                activity.lidaComProvas(selecinada);
            }
        });

        return viewProvas;
    }
}
