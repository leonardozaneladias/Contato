package br.com.caelum.contato;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;

import java.io.File;

/**
 * Created by android7583 on 18/07/18.
 */

public class FormHelper {

    private EditText nome;
    private EditText telefone;
    private EditText endereco;
    private EditText site;
    private RatingBar ratingBar;
    private ImageView photo;
    private FloatingActionButton buttonPhoto;
    private Aluno aluno;
    private FormActivity activity;

    public FormHelper(FormActivity activity){

        aluno = new Aluno();
        this.activity = activity;


        nome = (EditText) this.activity.findViewById(R.id.editText);
        telefone = (EditText) this.activity.findViewById(R.id.editText2);
        endereco = (EditText) this.activity.findViewById(R.id.editText3);
        site = (EditText) this.activity.findViewById(R.id.editText4);
        ratingBar = (RatingBar) this.activity.findViewById(R.id.ratingBar);
        photo = (ImageView) this.activity.findViewById(R.id.foto);
        buttonPhoto = (FloatingActionButton) this.activity.findViewById(R.id.button_photo);

    }

    public Aluno getAluno(){

        aluno.setNome(nome.getText().toString());
        aluno.setTelefone(telefone.getText().toString());
        aluno.setEndereco(endereco.getText().toString());
        aluno.setSite(site.getText().toString());
        aluno.setNota((double) ratingBar.getRating());
        aluno.setPhoto((String) photo.getTag());
//        aluno.setNota(Double.valueOf(ratingBar.getRating()));
        return  aluno;

    }

    public void preencheCampos(Aluno aluno){
        nome.setText(aluno.getNome());
        telefone.setText(aluno.getTelefone());
        endereco.setText(aluno.getEndereco());
        site.setText(aluno.getSite());
        ratingBar.setRating(aluno.getNota().floatValue());
        if(aluno.getPhoto() != null){
            carregaImage(aluno.getPhoto());
        }

        this.aluno = aluno;


    }

    public void escreveErroNoNome(){
        nome.setError("O campo nome e obrigatorio");
    }

    public boolean nomaVazio(){
        return nome.getText().toString().isEmpty();
    }

    public FloatingActionButton getButtonPhoto() {
        return buttonPhoto;
    }

    public void carregaImage(String localDoArquivo) {

            Bitmap imagemFoto = BitmapFactory.decodeFile(localDoArquivo);
            imagemFoto.createScaledBitmap(imagemFoto, 300, 300, true);
            photo.setImageBitmap(imagemFoto);
            photo.setMaxWidth(300);
            photo.setTag(localDoArquivo);
    }
}
