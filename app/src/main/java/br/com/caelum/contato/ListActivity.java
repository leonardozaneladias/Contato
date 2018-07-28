package br.com.caelum.contato;

import android.Manifest;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.BatteryManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import br.com.caelum.contato.converter.AlunoConverter;
import br.com.caelum.contato.service.PegaMediaTask;
import br.com.caelum.contato.service.WebClient;

public class ListActivity extends AppCompatActivity {

    private ListView lista;
    private List<Aluno> listaAlunos;

    private BroadcastReceiver bat = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Integer valor = intent.getIntExtra("level", 0);
            Toast.makeText(context, valor + "%", Toast.LENGTH_LONG).show();
        }
    };

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        this.lista = (ListView) findViewById(R.id.lista);
        Permissoes.fazPermissao(this);

        registerReceiver(bat, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));


        this.lista.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                Aluno aluno = (Aluno) adapterView.getItemAtPosition(i);
                ContextActionBar actionBar = new ContextActionBar(ListActivity.this, aluno);
                ListActivity.this.startSupportActionMode(actionBar);
                return true;
            }
        });

        this.lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Aluno alunoSender = (Aluno) adapterView.getItemAtPosition(i);
                Intent intent = new Intent(ListActivity.this, FormActivity.class);
                intent.putExtra("aluno", alunoSender);
                startActivity(intent);

            }
        });

        /*
        this.lista.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                Aluno aluno = (Aluno) adapterView.getItemAtPosition(i);
                Toast.makeText(ListActivity.this, "Nome: "+ aluno.getNome(), Toast.LENGTH_SHORT).show();
                return false;
            }
        });
        */

        FloatingActionButton botao = (FloatingActionButton) findViewById(R.id.button_new);
        botao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(ListActivity.this, "Voce clicou!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(ListActivity.this, FormActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {


    }

    @Override
    protected void onResume() {
        super.onResume();
        carregaLista();

    }

    public void carregaLista(){

        AlunoDAO dao = new AlunoDAO(this);
        this.listaAlunos = dao.getLista();
        dao.close();
        ListCustomAdapter adapter = new ListCustomAdapter(this, listaAlunos);
        this.lista.setAdapter(adapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_lista_alunos, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.menu_lista_alunos_media:

                new PegaMediaTask(this, item).execute();
                break;

            case R.id.menu_lista_alunos_provas:

                Intent intent = new Intent(this, ProvasActivity.class);
                startActivity(intent);
                break;

            case R.id.menu_lista_alunos_mapa:

                Intent intentMapa = new Intent(this, MostraAlunosActivity.class);
                startActivity(intentMapa);
                break;

        }
        return true;
    }

    /*
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {

        AdapterView.AdapterContextMenuInfo contextMenuInfo = (AdapterView.AdapterContextMenuInfo) menuInfo;
        final Aluno aluno = (Aluno) lista.getItemAtPosition(contextMenuInfo.position);
        MenuItem itemDeletar = menu.add("Deletar");
        itemDeletar.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                AlunoDAO dao = new AlunoDAO(ListActivity.this);
                dao.exclui(aluno);
                carregaLista();
                return false;
            }
        });
//        menu.add("Enviar SMS");
//        menu.add("Achar no Mapa");
//        menu.add("Navegar no site");
    }*/
}
