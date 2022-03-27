package com.exercicio.cadastrodemoedas.model.repo;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.exercicio.cadastrodemoedas.model.Moeda;
import com.exercicio.cadastrodemoedas.model.data.MoedaRoomDb;
import com.exercicio.cadastrodemoedas.model.interfaces.MoedaDao;

import java.util.List;

public class MoedaRepository {

    private final MoedaDao moedaDao;
    private final LiveData<List<Moeda>> listarTudo;

    public MoedaRepository(Application application) {
        MoedaRoomDb db = MoedaRoomDb.getDatabase(application);
        moedaDao = db.moedaDao();
        listarTudo = moedaDao.listarTodos();

    }

    public void inserir(Moeda moeda) {
        MoedaRoomDb.databaseWriteExecutor.execute(() -> moedaDao.inserir(moeda));
    }

    public void deletar(Moeda moeda) {
        MoedaRoomDb.databaseWriteExecutor.execute(() -> moedaDao.deletar(moeda));
    }

    public LiveData<List<Moeda>> listarTodos() {
        return listarTudo;
    }

}
