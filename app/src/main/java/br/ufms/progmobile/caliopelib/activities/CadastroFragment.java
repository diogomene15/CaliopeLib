package br.ufms.progmobile.caliopelib.activities;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import java.util.Objects;

import br.ufms.progmobile.caliopelib.R;
import br.ufms.progmobile.caliopelib.dao.UsuarioDAO;
import br.ufms.progmobile.caliopelib.database.AppDatabase;
import br.ufms.progmobile.caliopelib.databinding.CadastroFragmentBinding;
import br.ufms.progmobile.caliopelib.databinding.LoginFragmentBinding;
import br.ufms.progmobile.caliopelib.entities.Usuario;
import br.ufms.progmobile.caliopelib.useCases.CurrentUser;

public class CadastroFragment extends Fragment {

    private CadastroFragmentBinding binding;
    private Usuario currentUser;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = CadastroFragmentBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        CurrentUser cu = CurrentUser.getInstance();
        cu.setUser(null);

        binding.buttonSubmitCadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nome = binding.inputCadastroNome.getText().toString();
                String emailAd = binding.inputCadastroEmail.getText().toString();
                String pwd = binding.inputCadastroPassword.getText().toString();

                if (!emailAd.isEmpty() && !pwd.isEmpty() && !nome.isEmpty()) {
                    new InsertUserTask().execute(new Usuario(nome, emailAd, pwd));
                } else {
                    Toast.makeText(getActivity(), "Preencha todos os campos", Toast.LENGTH_SHORT).show();
                }
            }
        });

        binding.buttonLoginFragmentNav.setOnClickListener(v ->
                NavHostFragment.findNavController(CadastroFragment.this)
                        .navigate(R.id.action_CadastroFragment_to_LoginFragment)
        );
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private class InsertUserTask extends AsyncTask<Usuario, Void, Usuario> {
        @Override
        protected Usuario doInBackground(Usuario... usuarios) {
            currentUser = usuarios[0];
            AppDatabase db = AppDatabase.getDatabase(getContext());
            UsuarioDAO usuarioDAO = db.usuarioDao();
            Usuario usuarioExistente = usuarioDAO.getUserByEmail(usuarios[0].getEmail());
            if (usuarioExistente != null){
                return usuarioExistente;
            }
            int senha = Objects.hash(usuarios[0].getSenha(), "GW3~]P;R&8D:xmEb*e`phk_j/+f#cQU)y$%u=!N?,-v@w2ztVScg(nmS_v7U#PG$){Wu+?!s&*Hq]RyQANY;j9fKZ^':=zV/dD@b");

            usuarios[0].setSenha(String.valueOf(senha));

            long userId = usuarioDAO.insert(usuarios[0]);
            currentUser.setUsuarioId(userId);

            return null;
        }

        @Override
        protected void onPostExecute(Usuario usuarioExistente) {
            if (usuarioExistente == null){
                Toast.makeText(getActivity(), "Usuário cadastrado", Toast.LENGTH_SHORT).show();
                if(currentUser.getUsuarioId() <= 0){
                    return;
                }
                CurrentUser cu = CurrentUser.getInstance();
                cu.setUser(currentUser);

                NavHostFragment.findNavController(CadastroFragment.this)
                        .navigate(R.id.action_CadastroFragment_to_LivrosFragment);
            } else {
                Toast.makeText(getActivity(), "Email já cadastrado", Toast.LENGTH_SHORT).show();
            }
        }
    }

}