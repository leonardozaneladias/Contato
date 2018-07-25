package br.com.caelum.contato;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ListCustomAdapter extends BaseAdapter {

    private final Activity activity;
    private final List<Aluno> alunos;

    public ListCustomAdapter(Activity activity, List<Aluno> alunos){


        this.activity = activity;
        this.alunos = alunos;
    }

    @Override
    public int getCount() {
        return alunos.size();
    }

    @Override
    public Object getItem(int i) {
        return alunos.get(i);
    }

    @Override
    public long getItemId(int i) {
        return alunos.get(i).getId();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        View viewCustom = activity.getLayoutInflater().inflate(R.layout.list_item_custom, viewGroup, false);
        Aluno aluno = alunos.get(i);

        TextView name = (TextView) viewCustom.findViewById(R.id.item_text1);
        TextView tel = (TextView) viewCustom.findViewById(R.id.item_text2);
        TextView note = (TextView) viewCustom.findViewById(R.id.item_text3);
        CircleImageView imageView = (CircleImageView) viewCustom.findViewById(R.id.list_item_custom_photo);

        name.setText(aluno.getNome());
        tel.setText(aluno.getTelefone());
        note.setText(aluno.getNota().toString());

        Bitmap img;
        if(aluno.getPhoto() != null){
            img = BitmapFactory.decodeFile(aluno.getPhoto());
        }else{
            img = BitmapFactory.decodeResource(activity.getResources(), R.drawable.person);
        }

        img = Bitmap.createScaledBitmap(img, 80, 80, true);
        imageView.setImageBitmap(img);

        return viewCustom;
    }
}
