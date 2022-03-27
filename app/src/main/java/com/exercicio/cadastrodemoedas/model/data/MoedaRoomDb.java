package com.exercicio.cadastrodemoedas.model.data;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.exercicio.cadastrodemoedas.model.Moeda;
import com.exercicio.cadastrodemoedas.model.interfaces.MoedaDao;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Moeda.class}, version = 1, exportSchema = false)
public abstract class MoedaRoomDb extends RoomDatabase {

    public abstract MoedaDao moedaDao();

    private static volatile MoedaRoomDb INSTANCE;
    private static final int NUMBER_OF_THREADS = 5;
    public static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public static MoedaRoomDb getDatabase(final Context context) {

        if (INSTANCE == null) {
            synchronized (MoedaRoomDb.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            MoedaRoomDb.class, "moeda_db")
                            .addCallback(roomDbCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    private static final RoomDatabase.Callback roomDbCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);

            databaseWriteExecutor.execute(() -> {

                MoedaDao dao = INSTANCE.moedaDao();
                Moeda moeda = new Moeda(1,"Dirham", "DHS", 1.31f);
                dao.inserir(moeda);
                moeda = new Moeda(2,"Dólar Americano", "USD", 4.83f);
                dao.inserir(moeda);
                moeda = new Moeda(3,"Euro", "EUR", 5.31f);
                dao.inserir(moeda);
                moeda = new Moeda(4,"Real", "BRL", 0.21f);
                dao.inserir(moeda);

            });
        }


    };

}
