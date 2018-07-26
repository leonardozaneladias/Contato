package br.com.caelum.contato;

import java.util.ArrayList;
import java.util.List;

public class Provas {

    private String data;
    private String materia;
    private String descricao;
    private List<String> topicos = new ArrayList<>();

    public Provas(String data, String materia){

        this.data = data;
        this.materia = materia;
    }

    @Override
    public String toString() {
        return  materia;
    }


    public List<String> getTopicos() {
        return topicos;
    }

    public void setTopicos(List<String> topicos) {
        this.topicos = topicos;
    }

    public void getTopicos(List<String> strings) {
    }
}
