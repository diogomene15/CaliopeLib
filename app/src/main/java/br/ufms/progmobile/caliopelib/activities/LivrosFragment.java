package br.ufms.progmobile.caliopelib.activities;
import br.ufms.progmobile.caliopelib.adapters.LivroListAdapter;
import br.ufms.progmobile.caliopelib.database.AppDatabase;
import br.ufms.progmobile.caliopelib.databinding.FragmentLivrosBinding;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import java.util.List;

import br.ufms.progmobile.caliopelib.R;
import br.ufms.progmobile.caliopelib.entities.Livro;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LivrosFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LivrosFragment extends Fragment {

    private FragmentLivrosBinding binding;
    public LivrosFragment() {
        // Required empty public constructor
    }


    private List<Livro> getAllLivros(){
        AppDatabase db = AppDatabase.getDatabase(getContext());
        List<Livro> livros = db.livroDao().getAll();
        return livros;
    }
    public static LivrosFragment newInstance(String param1, String param2) {
        LivrosFragment fragment = new LivrosFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentLivrosBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        LivroListAdapter livrosAdapter = new LivroListAdapter(view.getContext(), this.getAllLivros());

        binding.livrosListView.setAdapter(livrosAdapter);
        binding.livrosListView.setOnItemClickListener((parent, view1, position, id) -> {
            Livro livroClicado = livrosAdapter.getItem(position);

        });

    }
}