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

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.io.File;

import br.ufms.progmobile.caliopelib.R;
import br.ufms.progmobile.caliopelib.databinding.FragmentLivroDetalheBinding;
import br.ufms.progmobile.caliopelib.entities.Livro;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LivroDetalheFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LivroDetalheFragment extends Fragment {
    private static final int CAMERA_PERMISSION_CODE = 1;

    private FragmentLivroDetalheBinding binding;
    private ActivityResultLauncher<Uri> tirarFotoLaunchner;

    Uri imageUri;
    public LivroDetalheFragment() {
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
                                binding.newLivroImageView.setImageURI(null);
                                binding.newLivroImageView.setImageURI(imageUri);
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



    public static LivroDetalheFragment newInstance(Livro livro) {
        LivroDetalheFragment fragment = new LivroDetalheFragment();
        Bundle args = new Bundle();
        args.putSerializable("livro", livro);
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

        binding = FragmentLivroDetalheBinding.inflate(inflater, container, false);

        imageUri = createUri();
        registerPictureLauncher();

        if (getArguments() != null) {

            Livro livroParam = (Livro) getArguments().getSerializable("livro");
            if(livroParam != null){

                String tituloLivro = livroParam.getTitulo();
                if(tituloLivro!=null){
                    binding.newTituloEditText.setText(tituloLivro);
                }

                String descricaoLivro = livroParam.getDescricao();
                if(descricaoLivro != null){
                    binding.newDescricaoEditText.setText(descricaoLivro);
                }

                float avaliacaoLivro = livroParam.getAvaliacao();
                if(avaliacaoLivro > 0){
                    binding.newRatingBar.setRating(avaliacaoLivro);
                }

                String imageUriString = livroParam.getFotoPath();

                if (imageUriString != null) {
                    Uri livroUri = Uri.parse(imageUriString);
                    binding.newLivroImageView.setImageURI(null);
                    binding.newLivroImageView.setImageURI(livroUri);
                }
            }

        }

        binding.newAddFotoButton.setOnClickListener(v -> {
            checkAndOpenCamera();
        });
        return binding.getRoot();
    }
}