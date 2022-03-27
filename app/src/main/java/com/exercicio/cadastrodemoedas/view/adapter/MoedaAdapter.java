package com.exercicio.cadastrodemoedas.view.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.exercicio.cadastrodemoedas.R;
import com.exercicio.cadastrodemoedas.model.Moeda;
import com.exercicio.cadastrodemoedas.view.CadastroActivity;


public class MoedaAdapter extends ListAdapter<Moeda, MoedaAdapter.MyViewHolder> {

    public static final String CADASTRO_CODE = "1";

    public MoedaAdapter(@NonNull DiffUtil.ItemCallback<Moeda> diffCallback) {
        super(diffCallback);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.moedas_adapter, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        Moeda moeda = getItem(position);
        holder.bind(moeda.getDescricao());

        //evento de click para editar item selecionado
        holder.descricao_tv.setOnClickListener(view -> {
            Intent intent = new Intent(view.getContext(), CadastroActivity.class);
            intent.putExtra(CADASTRO_CODE, moeda);
            view.getContext().startActivity(intent);
        });

    }

    public static class MoedaDiff extends DiffUtil.ItemCallback<Moeda> {

        @Override
        public boolean areItemsTheSame(@NonNull Moeda oldItem, @NonNull Moeda newItem) {
            return oldItem == newItem;
        }

        @Override
        public boolean areContentsTheSame(@NonNull Moeda oldItem, @NonNull Moeda newItem) {
            return oldItem.getDescricao().equals(newItem.getDescricao());
        }
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        private final TextView descricao_tv;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            descricao_tv = itemView.findViewById(R.id.descricao_tv);
        }

        public void bind(String descricao) {
            descricao_tv.setText(descricao);
        }
    }
}
