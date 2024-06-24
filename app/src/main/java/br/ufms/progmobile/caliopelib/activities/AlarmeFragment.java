package br.ufms.progmobile.caliopelib.activities;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import java.util.List;

import br.ufms.progmobile.caliopelib.R;
import br.ufms.progmobile.caliopelib.database.AppDatabase;
import br.ufms.progmobile.caliopelib.databinding.AlarmesFragmentBinding;
import br.ufms.progmobile.caliopelib.entities.Alarme;
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

        alarmesAdapter = new ArrayAdapter(view.getContext(), R.layout.item_alarme_list, allAlarmes){
            public View getView(int position, View convertView, ViewGroup parent) {
                View itemView = super.getView(position, convertView, parent);
                Button buttonExcluir = convertView.findViewById(R.id.buttonExcluirAlarme);
                Button buttonEditar = convertView.findViewById(R.id.buttonEditarAlarme);

                buttonExcluir.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        deleteAlarme(allAlarmes.get(position));
                        allAlarmes.remove(position);
                        notifyDataSetChanged();
                    }
                });

                buttonEditar.setOnClickListener(v -> navToCadastroAlarme());

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
        AppDatabase db = AppDatabase.getDatabase(getContext());
        db.alarmeDao().delete(alarme);
    }

    public void navToCadastroAlarme(){
        NavHostFragment.findNavController(AlarmeFragment.this)
                .navigate(R.id.action_AlarmesFragment_to_CadastroAlarme);
    }
}
