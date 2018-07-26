package br.com.caelum.contato.service;

import java.io.IOException;
import java.util.List;

import br.com.caelum.contato.Aluno;
import br.com.caelum.contato.converter.AlunoConverter;
import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class WebClient {

    private OkHttpClient client = new OkHttpClient();

    public String buscaMedia(List<Aluno> alunos){

        try {

            String json = new AlunoConverter().toJSON(alunos);

            MediaType tipo = MediaType.parse("application/json; charset=UTF-8");

            RequestBody body = RequestBody.create(tipo, json);

            Request request = new Request.Builder()
                    .url("https://www.caelum.com.br/mobile")
                    .post(body)
                    .build();

            Call call = client.newCall(request);

            Response response = call.execute();

            ResponseBody responseBody = response.body();

            String media = responseBody.string();

            return  media;

        } catch (IOException e) {

            return "Deu ruim";

        }

    }

}
