package br.com.caelum.contato;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class DetalhesProvasFragment extends Fragment {

    private TextView materia;
    private TextView data;
    private ListView topicos;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_layout_prova, container, false);

        materia = (TextView) view.findViewById(R.id.detalhes_prova_materia);
        data = (TextView) view.findViewById(R.id.detalhes_prova_data);
        topicos = (ListView) view.findViewById(R.id.detalhes_prova_topicos);


        if(getArguments() != null){
            Provas prova = (Provas) getArguments().getSerializable("prova");
            populaTable(prova);
        }

        return view;

    }

    public void populaTable(Provas prova) {

        materia.setText(prova.getMateria());
        data.setText(prova.getData());

        topicos.setAdapter(new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, prova.getTopicos()));

    }
}
