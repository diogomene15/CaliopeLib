package br.ufms.progmobile.caliopelib.activities;

import android.Manifest;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.io.File;

import br.ufms.progmobile.caliopelib.R;
import br.ufms.progmobile.caliopelib.database.AppDatabase;
import br.ufms.progmobile.caliopelib.databinding.FragmentCadastroLivroBinding;
import br.ufms.progmobile.caliopelib.entities.Livro;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CadastroLivroFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CadastroLivroFragment extends Fragment {
    private static final int CAMERA_PERMISSION_CODE = 1;

    private FragmentCadastroLivroBinding binding;
    private ActivityResultLauncher<Uri> tirarFotoLaunchner;

    Uri imageUri;
    public CadastroLivroFragment() {
        // Required empty public constructor
    }

    private Uri createUri(){
        String fileName = "image" + System.currentTimeMillis() + ".jpg";
        File imagemLivro = new File(getContext().getFilesDir(), fileName);
        return FileProvider.getUriForFile(
                getContext(),
                "br.ufms.progmobile.caliopelib.fileprovider",
                imagemLivro
        );
    }

    private void registerPictureLauncher(){
        tirarFotoLaunchner = registerForActivityResult(
                new ActivityResultContracts.TakePicture(),
                new ActivityResultCallback<Boolean>() {
                    @Override
                    public void onActivityResult(Boolean result) {
                        try {
                            if(result){
                                System.out.println("Sucesso "+ imageUri.toString());
                                binding.livroImageView.setImageURI(null);
                                binding.livroImageView.setImageURI(imageUri);
                            }
                        }catch (Exception e){
                            e.getStackTrace();
                        }
                    }
                }
        );
    }

    private void checkAndOpenCamera(){
        if(ActivityCompat.checkSelfPermission(getActivity().getApplicationContext(), Manifest.permission.CAMERA)
        != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CAMERA}, CAMERA_PERMISSION_CODE);
        }else{
            tirarFotoLaunchner.launch(imageUri);
        }
    }
    private void closeFragment() {
        NavController navController = NavHostFragment.findNavController(CadastroLivroFragment.this);
        navController.popBackStack();
    }



    public static CadastroLivroFragment newInstance() {
        CadastroLivroFragment fragment = new CadastroLivroFragment();
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

        binding = FragmentCadastroLivroBinding.inflate(inflater, container, false);

        imageUri = createUri();
        registerPictureLauncher();

        binding.addFotoButton.setOnClickListener(v -> {
            checkAndOpenCamera();
        });

        binding.cadastrarLivroButton.setOnClickListener(v -> {
            Livro novoLivro = new Livro();

            String tituloLivro = binding.tituloEditText.getText().toString();
            String descricaoLivro = binding.descricaoEditText.getText().toString();
            float avaliacaoLivro = binding.ratingBar.getRating();

            novoLivro.setTitulo(tituloLivro);
            novoLivro.setDescricao(descricaoLivro);
            novoLivro.setAvaliacao(avaliacaoLivro);
            novoLivro.setFotoPath(imageUri.toString());
            try {
                AppDatabase db = AppDatabase.getDatabase(getActivity().getApplicationContext());
                db.livroDao().insert(novoLivro);
                Toast.makeText(getActivity(), "Livro cadastrado com sucesso", Toast.LENGTH_SHORT).show();
                closeFragment();
            }catch (Exception e){
                Toast.makeText(getActivity(), "Erro ao cadastrar livro", Toast.LENGTH_SHORT).show();
            }
        });
        return binding.getRoot();
    }
}