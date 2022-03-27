package com.exercicio.cadastrodemoedas.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.exercicio.cadastrodemoedas.model.Moeda;
import com.exercicio.cadastrodemoedas.model.repo.MoedaRepository;

import java.util.List;

public class MoedaViewModel extends AndroidViewModel {

    private final MoedaRepository repository;

    private final LiveData<List<Moeda>> listarTudo;

    public MoedaViewModel(Application application) {
        super(application);
        this.repository = new MoedaRepository(application);
        this.listarTudo = repository.listarTodos();
    }

    public void inserir(Moeda moeda) throws Exception {

        if (!String.valueOf(moeda.getCodigo()).isEmpty() && !moeda.getDescricao().isEmpty()){

            if (limitaString(moeda.getDescricao(),50) && limitaString(moeda.getAbreviatura(), 5)){

                if (formatar(moeda.getCotacao())){
                    repository.inserir(moeda);
                }else {
                    throw new Exception("2 casas antes da vírgula, 4 casas decimais após a vírgula");
                }

            }else {
                throw new Exception("maximo de caracteres atingido");
            }

        }else {
            throw new Exception("Campos obrigatorios");
        }

    }

    public void deletar(Moeda moeda) {
        repository.deletar(moeda);
    }

    public LiveData<List<Moeda>> getLista(){
        return listarTudo;
    }

    //verifica a quantidade de caracter que a string contem
    private boolean limitaString(String texto, int maximo){
        return texto.length() <= maximo;
    }

    //verifica quantas casas decimais possui a cotacao a pos a virgura e antes
    private boolean formatar(float n) {

        String str = Float.toString(n);
        int t1 = str.indexOf('.');
        int t2 = str.length() - t1 - 1;

        return (t1 < 3) && (t2 < 5);
    }

}
