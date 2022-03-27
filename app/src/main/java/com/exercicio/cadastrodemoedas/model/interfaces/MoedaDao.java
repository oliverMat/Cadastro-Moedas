package com.exercicio.cadastrodemoedas.model.interfaces;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.exercicio.cadastrodemoedas.model.Moeda;

import java.util.List;

@Dao
public interface MoedaDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void inserir(Moeda moeda);

    @Delete
    void deletar(Moeda moeda);

    @Query("select * from DB_MOEDAS ORDER BY descricao")
    LiveData<List<Moeda>> listarTodos();

}
