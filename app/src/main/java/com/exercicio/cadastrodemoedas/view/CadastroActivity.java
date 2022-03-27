package com.exercicio.cadastrodemoedas.view;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;

import com.exercicio.cadastrodemoedas.R;
import com.exercicio.cadastrodemoedas.model.Moeda;
import com.exercicio.cadastrodemoedas.viewmodel.MoedaViewModel;
import com.google.android.material.textfield.TextInputEditText;

import java.util.List;

public class CadastroActivity extends AppCompatActivity {

    public static final String CADASTRO_CODE = "1";

    private MoedaViewModel viewModel;
    private Moeda moeda;
    private Menu menu;
    private TextInputEditText codigo_et, desc_et, abrev_et, cot_et;
    private List<Moeda> listarTudo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        //removendo nome da toolbar
        Toolbar toolbar = findViewById(R.id.cadastro_toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);

        //inicializando componentes
        codigo_et = findViewById(R.id.codigo_et);
        desc_et = findViewById(R.id.desc_et);
        abrev_et = findViewById(R.id.abrev_et);
        cot_et = findViewById(R.id.cot_et);

        viewModel = new ViewModelProvider(this).get(MoedaViewModel.class);

        viewModel.getLista().observe(this, mo -> listarTudo = mo);

        //recebe dados para edicao
        moeda = (Moeda) getIntent().getSerializableExtra(CADASTRO_CODE);
        if (moeda!=null) {
            try {
                codigo_et.setText(String.valueOf(moeda.getCodigo()));
                desc_et.setText(moeda.getDescricao());
                abrev_et.setText(moeda.getAbreviatura());
                cot_et.setText(String.valueOf(moeda.getCotacao()));
                codigo_et.setEnabled(false);
            }catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    //salva ou atualiza a moeda
    private void salvarUpdate(int codigo) {

        for (Moeda moeda : listarTudo) {
            if (moeda.getCodigo() == codigo && this.moeda == null) {
                editarDados(moeda);
                return; // achou o item, pode sair do método
            }
        }

        try {
            viewModel.inserir(new Moeda(codigo, desc_et.getText().toString(), abrev_et.getText().toString(), verifiCotacaoNull()));

            finish();
        }catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }

    }

    private void editarDados(Moeda moeda){

        AlertDialog.Builder dialogInterno = new AlertDialog.Builder(CadastroActivity.this);
        dialogInterno.setMessage(R.string.alerta_existente);

        dialogInterno.setPositiveButton(R.string.sim, (dialog, which) -> {

            codigo_et.setText(String.valueOf(moeda.getCodigo()));
            desc_et.setText(moeda.getDescricao());
            abrev_et.setText(moeda.getAbreviatura());
            cot_et.setText(String.valueOf(moeda.getCotacao()));
            codigo_et.setEnabled(false);
            menu.getItem(0).setVisible(true);
            this.moeda = moeda;
        });

        dialogInterno.setNegativeButton(R.string.nao, null);
        dialogInterno.create();
        dialogInterno.show();
    }

    //exibe confirmacao para deletar moeda
    private void deletar() {

        AlertDialog.Builder dialogInterno = new AlertDialog.Builder(CadastroActivity.this);
        dialogInterno.setMessage(R.string.alerta_excluir);

        dialogInterno.setPositiveButton(R.string.sim, (dialog, which) -> {

            try {
                viewModel.deletar(moeda);
                finish();
            }catch (Exception e) {
                e.printStackTrace();
            }

        });

        dialogInterno.setNegativeButton(R.string.nao, null);
        dialogInterno.create();
        dialogInterno.show();

    }

    //verifica se o usuario deixou o campo da cotacao vazio
    private float verifiCotacaoNull() {
        float valor;

        if (!cot_et.getText().toString().isEmpty()){
            valor = Float.parseFloat(cot_et.getText().toString());

        }else {
            valor = 0.0f;
        }
        return valor;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_cadastro, menu);

        //se for inserir uma nova moeda a lixeira nao sera exibida, somente na edicao
        if (moeda != null){
            menu.getItem(0).setVisible(true);
        }

        this.menu = menu;
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.salvar) {

            salvarUpdate(Integer.parseInt(codigo_et.getText().toString()));

            return true;

        } else if (id == R.id.deletar) {

            deletar();

            return true;

        }

        return super.onOptionsItemSelected(item);
    }
}