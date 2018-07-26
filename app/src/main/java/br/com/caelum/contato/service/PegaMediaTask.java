package br.com.caelum.contato.service;

import android.app.Activity;
import android.os.AsyncTask;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.List;

import br.com.caelum.contato.Aluno;
import br.com.caelum.contato.AlunoDAO;
import br.com.caelum.contato.R;

public class PegaMediaTask extends AsyncTask<Object, Object, String> {

    private Activity context;
    private MenuItem item;

    public PegaMediaTask(Activity context, MenuItem item) {
        this.context = context;
        this.item = item;

    }

    @Override
    protected void onPreExecute() {
        this.item.setIcon(R.drawable.loading);
        this.item.setEnabled(false);
        Toast.makeText(context, "Pegando a média", Toast.LENGTH_LONG).show();
    }

    @Override
    protected String doInBackground(Object[] objects) {

        AlunoDAO dao = new AlunoDAO(context);
        List<Aluno> alunos = dao.getLista();
        dao.close();

        WebClient webClient = new WebClient();
        String retorno = webClient.buscaMedia(alunos);
        return retorno;
    }

    @Override
    protected void onPostExecute(String media) {
        this.item.setIcon(android.R.drawable.arrow_up_float);
        this.item.setEnabled(true);
        Toast.makeText(context, media, Toast.LENGTH_LONG).show();

    }
}
