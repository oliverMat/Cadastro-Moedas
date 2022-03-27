package com.exercicio.cadastrodemoedas.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.exercicio.cadastrodemoedas.R;

import com.exercicio.cadastrodemoedas.view.adapter.MoedaAdapter;
import com.exercicio.cadastrodemoedas.viewmodel.MoedaViewModel;

public class ListaActivity extends AppCompatActivity {

    private MoedaAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista);

        starRecyclerView();

        MoedaViewModel viewModel = new ViewModelProvider(this).get(MoedaViewModel.class);

        viewModel.getLista().observe(this, adapter::submitList);

        //cria uma nova moeda
        findViewById(R.id.cadastro_fab).setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), CadastroActivity.class);
            startActivity(intent);
        });
    }

    private void starRecyclerView() {

        RecyclerView recyclerListM = findViewById(R.id.recyclerListMoedas);
        adapter = new MoedaAdapter(new MoedaAdapter.MoedaDiff());
        recyclerListM.setAdapter(adapter);
        recyclerListM.setLayoutManager(new LinearLayoutManager(this));

        //divisor de itens da lista
        recyclerListM.addItemDecoration(new DividerItemDecoration(recyclerListM.getContext(), DividerItemDecoration.VERTICAL));

    }
}