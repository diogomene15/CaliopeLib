package br.ufms.progmobile.caliopelib.activities;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import java.util.List;

import br.ufms.progmobile.caliopelib.R;
import br.ufms.progmobile.caliopelib.database.AppDatabase;
import br.ufms.progmobile.caliopelib.databinding.CadastroAlarmeFragmentBinding;
import br.ufms.progmobile.caliopelib.entities.Alarme;
import br.ufms.progmobile.caliopelib.entities.Livro;
import br.ufms.progmobile.caliopelib.entities.Usuario;
import br.ufms.progmobile.caliopelib.useCases.AlarmeNotificacao;
import br.ufms.progmobile.caliopelib.useCases.CurrentAlarme;
import br.ufms.progmobile.caliopelib.useCases.CurrentUser;

public class CadastroAlarmeFragment extends Fragment {

    private TimePicker timePicker;
    private CadastroAlarmeFragmentBinding binding;
    private Livro livro = new Livro("","", "",0);
    private int hora = 0;
    private int minuto = 0;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = CadastroAlarmeFragmentBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.spinnerLivros.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                livro = ((Livro) parent.getItemAtPosition(position));
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        ArrayAdapter<Livro> adapt = new ArrayAdapter<Livro>(view.getContext(), android.R.layout.simple_spinner_item, getLivros());
        adapt.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
        binding.spinnerLivros.setAdapter(adapt);

        binding.buttonSubmitCadastroAlarme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nomeLivro = livro.getTitulo();
                int hora = binding.inputTime.getHour();
                int minuto = binding.inputTime.getMinute();
                Usuario currentUsuario = CurrentUser.getInstance().getUser();

                if (!nomeLivro.isEmpty() & (currentUsuario != null)) {
                    Alarme a = new Alarme(currentUsuario.getUsuarioId(), nomeLivro, hora, minuto);
                    insertAlarme(a);
                } else if (currentUsuario == null) {
                    Alarme a = new Alarme("(sem informação)", hora, minuto);
                    insertAlarme(a);
                } else {
                    Toast.makeText(getActivity(), "Preencha todos os campos.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public void insertAlarme(Alarme alarme){
        AppDatabase db = AppDatabase.getDatabase(getContext());
        List<Alarme> alarmesExistentes = db.alarmeDao().getAlarmesByUsuario(alarme.getUsuarioId());

        if (alarmesExistentes.isEmpty()){
            AlarmeNotificacao.agendarAlarme(getContext(), alarme);
        }
        else {
            for (Alarme a : alarmesExistentes) {
                if (CurrentAlarme.getInstance().getAlarme() != null) {
                    AlarmeNotificacao.atualizarAlarme(getContext(), alarme, CurrentAlarme.getInstance().getAlarme().getAlarmeId());
                    CurrentAlarme.getInstance().setAlarme(null);

                    break;
                } else if (a.equals(alarme)) {
                    Toast.makeText(getActivity(), "Alarme já cadastrado", Toast.LENGTH_SHORT).show();
                    break;
                } else {
                    AlarmeNotificacao.agendarAlarme(getContext(), alarme);
                }
            }
        }
        navToAlarmesFragment();
    }

    public List<Livro> getLivros(){
        Usuario cUser = CurrentUser.getInstance().getUser();
        AppDatabase db = AppDatabase.getDatabase(getContext());

        if(cUser != null){
            return db.livroDao().getLivrosByUsuario(cUser.getUsuarioId());
        } else {
            return db.livroDao().getLivrosSemUsuario();
        }
    }

    public void navToAlarmesFragment(){
        NavHostFragment.findNavController(CadastroAlarmeFragment.this)
                .navigate(R.id.action_CadastroAlarme_to_AlarmesFragment);
    }

}
