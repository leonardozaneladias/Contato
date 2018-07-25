package br.com.caelum.contato;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.view.ActionMode;
import android.telecom.Call;
import android.view.Menu;
import android.view.MenuItem;

import java.net.URI;
import java.util.jar.Manifest;

/**
 * Created by android7583 on 20/07/18.
 */

public class ContextActionBar implements ActionMode.Callback {

    private Aluno aluno;
    private ListActivity activity;

    public ContextActionBar(ListActivity activity, Aluno aluno){

        this.activity = activity;
        this.aluno = aluno;
    }

    @Override
    public boolean onCreateActionMode(final ActionMode actionMode, Menu menu) {

        MenuItem ligar = menu.add("Ligar");
        ligar.setIcon(R.drawable.ic_action_call);
        MenuItem sms = menu.add("Enviar SMS");

        MenuItem mapa = menu.add("Achar no Mapa");
        MenuItem site = menu.add("Navegar no Site");

        MenuItem deletar = menu.add("Deletar");
        deletar.setIcon(R.drawable.ic_action_delete);
        deletar.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        deletar.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {

                AlunoDAO dao = new AlunoDAO(activity);
                dao.exclui(aluno);
                dao.close();
                activity.carregaLista();
                return false;
            }
        });

        Intent intentLigar = new Intent(Intent.ACTION_CALL);
        intentLigar.setData(Uri.parse("tel:" + aluno.getTelefone()));

        Intent intentSMS = new Intent(Intent.ACTION_VIEW);
        intentSMS.setData(Uri.parse("sms:" + aluno.getTelefone()));
        intentSMS.putExtra("sms_body", aluno.getNota().toString());

        ligar.setIntent(intentLigar);
        sms.setIntent(intentSMS);

        Intent siteIntent = new Intent(Intent.ACTION_VIEW);
        String siteAluno = aluno.getSite();
        if(!siteAluno.startsWith("http://")){
            siteAluno = "http://" + siteAluno;

        }
        siteIntent.setData(Uri.parse(siteAluno));
        site.setIntent(siteIntent);

        Intent intentMapa = new Intent(Intent.ACTION_VIEW);
        String endereco = aluno.getEndereco();
        intentMapa.setData(Uri.parse("geo:0,0?z=14&q=" + Uri.encode(endereco)));
        mapa.setIntent(intentMapa);


        return true;
    }

    @Override
    public boolean onPrepareActionMode(ActionMode actionMode, Menu menu) {
        return false;
    }

    @Override
    public boolean onActionItemClicked(ActionMode actionMode, MenuItem menuItem) {
        return false;
    }

    @Override
    public void onDestroyActionMode(ActionMode actionMode) {

    }
}
