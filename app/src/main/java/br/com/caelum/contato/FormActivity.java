package br.com.caelum.contato;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Toast;

import java.io.File;

public class FormActivity extends AppCompatActivity {

    private FormHelper helper;
    private String localDoArquivo;
    private static final int TIRAR_FOTO = 123;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);
        helper = new FormHelper(this);

        Intent intent = getIntent();
        Aluno aluno = (Aluno) intent.getSerializableExtra("aluno");
        if(aluno != null){
            helper.preencheCampos(aluno);
        }

        FloatingActionButton btnPhoto = helper.getButtonPhoto();
        btnPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent irParacamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                localDoArquivo = getExternalFilesDir("fotos") + "/" + System.currentTimeMillis() + ".jpg";
                File file = new File(localDoArquivo);

                String auths = getApplicationContext().getPackageName() + ".fileprovider";
                Uri image = FileProvider.getUriForFile(FormActivity.this, auths, file);
                irParacamera.putExtra(MediaStore.EXTRA_OUTPUT, image);

                //Log.i("cam","Chamou Camera");
                startActivityForResult(irParacamera, TIRAR_FOTO);

            }
        });


        /*
        Button botao = (Button) findViewById(R.id.button);
        botao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String msg = " Nome:" + nome.getText() + "\n Endereço:" + endereco.getText() + "\n Telefone:" + telefone.getText() + "\n Site:" + site.getText() + "\n Nota:" + ratingBar.getRating() ;

                Toast.makeText(FormActivity.this, msg, Toast.LENGTH_SHORT).show();
                finish();
            }
        });
        */
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == TIRAR_FOTO){
            if(resultCode == Activity.RESULT_OK){
                Log.i("file",localDoArquivo);
                helper.carregaImage(localDoArquivo);
            }else{
                Log.i("file","Sem Result");
                this.localDoArquivo = null;
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){

            case R.id.menu_salvar:
                if(!helper.nomaVazio()){
                    Aluno aluno = helper.getAluno();
                    AlunoDAO dao = new AlunoDAO(this);
                    if(aluno.getId() == null){
                        dao.salva(aluno);
                        Toast.makeText(this,"O Aluno " + aluno.getNome() + "foi cadastrado com sucesso!",Toast.LENGTH_SHORT).show();
                    }else{
                        dao.editar(aluno);
                        Toast.makeText(this,"O Aluno " + aluno.getNome() + "foi editado com sucesso!",Toast.LENGTH_SHORT).show();
                    }

                    //Log.i("Aluno: ", aluno.getNome() + aluno.getEndereco() + aluno.getSite() + aluno.getTelefone()  + aluno.getNota());
                    finish();
                    return true;
                }else{
                    helper.escreveErroNoNome();
                    return false;
                }

        }
        return super.onOptionsItemSelected(item);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_fomulario, menu);
        return true;

    }
}
