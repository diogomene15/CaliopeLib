package br.ufms.progmobile.caliopelib.activities;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import java.util.List;

import br.ufms.progmobile.caliopelib.R;
import br.ufms.progmobile.caliopelib.database.AppDatabase;
import br.ufms.progmobile.caliopelib.databinding.AlarmesFragmentBinding;
import br.ufms.progmobile.caliopelib.entities.Alarme;
import br.ufms.progmobile.caliopelib.entities.Livro;
import br.ufms.progmobile.caliopelib.useCases.AlarmeNotificacao;
import br.ufms.progmobile.caliopelib.useCases.CurrentAlarme;

public class AlarmeFragment extends Fragment {
    private AlarmesFragmentBinding binding;
    private ArrayAdapter<Alarme> alarmesAdapter;

    private List<Alarme> allAlarmes;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = AlarmesFragmentBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        allAlarmes = this.getAllAlarmes();

        setAdapter(allAlarmes, view);

        binding.buttonAddAlarme.setOnClickListener(v -> navToCadastroAlarme());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private List<Alarme> getAllAlarmes(){
        AppDatabase db = AppDatabase.getDatabase(getContext());
        List<Alarme> alarmes = db.alarmeDao().getAll();
        return alarmes;
    }

    public void deleteAlarme(Alarme alarme){
        AlarmeNotificacao.cancelarAlarme(getContext(), (int)alarme.getAlarmeId());
    }

    public void navToCadastroAlarme(){
        NavHostFragment.findNavController(AlarmeFragment.this)
                .navigate(R.id.action_AlarmesFragment_to_CadastroAlarme);
    }

    public void setAdapter(List<Alarme> allAlarmes, View view){
        alarmesAdapter = new ArrayAdapter<Alarme>(view.getContext(), R.layout.item_alarme_list, R.id.item_horario, allAlarmes){
            @NonNull
            public View getView(int position, View convertView, @NonNull ViewGroup parent) {
                if (convertView == null) {
                    convertView = LayoutInflater.from(view.getContext()).inflate(R.layout.item_alarme_list, parent, false);
                }

                View itemView = super.getView(position, convertView, parent);
                Button buttonExcluir = itemView.findViewById(R.id.buttonExcluirAlarme);
                Button buttonEditar = itemView.findViewById(R.id.buttonEditarAlarme);

                TextView item_horario = itemView.findViewById(R.id.item_horario);
                TextView item_livro = itemView.findViewById(R.id.item_livro);

                item_horario.setText(allAlarmes.get(position).getHorarioString());
                item_livro.setText(allAlarmes.get(position).getLivro());

                buttonExcluir.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        deleteAlarme(allAlarmes.get(position));
                        allAlarmes.remove(position);
                        notifyDataSetChanged();
                    }
                });

                buttonEditar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        CurrentAlarme.getInstance().setAlarme(allAlarmes.get(position));
                        navToCadastroAlarme();
                    }
                });

                return itemView;
            }
        };

        binding.listAlarmes.setAdapter(alarmesAdapter);
    }


    public void onResume() {
        super.onResume();
        allAlarmes = this.getAllAlarmes();

        setAdapter(allAlarmes, this.getView());
    }
}
