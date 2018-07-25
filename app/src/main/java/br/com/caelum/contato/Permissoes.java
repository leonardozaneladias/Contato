package br.com.caelum.contato;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;

import java.util.ArrayList;

/**
 * Created by android7583 on 23/07/18.
 */

public class Permissoes {

    private static ArrayList<String> listaPermissoes = new ArrayList<>();
    private static final int CODE = 123;

    //requestPermissions(permissoes, 123);

    public static void fazPermissao(Activity activity) {

        String[] permissoes = {Manifest.permission.CALL_PHONE,
                Manifest.permission.RECEIVE_SMS,
                Manifest.permission.INTERNET,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.CAMERA};
        for (String permissao : permissoes) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

                if (activity.checkSelfPermission(permissao) != PackageManager.PERMISSION_GRANTED) {
                    listaPermissoes.add(permissao);
                }
            }


        }
        request(activity);

    }

    public static void request(Activity activity) {
        String[] array = listaPermissoes.toArray(new String[]{});
        if (listaPermissoes.size() > 0) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                activity.requestPermissions(array, CODE);
            }
        }

    }
}
