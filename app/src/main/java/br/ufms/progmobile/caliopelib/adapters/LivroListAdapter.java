package br.ufms.progmobile.caliopelib.adapters;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.List;

import br.ufms.progmobile.caliopelib.R;
import br.ufms.progmobile.caliopelib.entities.Livro;

public class LivroListAdapter extends ArrayAdapter<Livro> {
    private Context context;
    private List<Livro> items;
    public LivroListAdapter(Context context, List<Livro> items){
        super(context, R.layout.item_livro_list, items);
        this.context = context;
        this.items = items;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_livro_list, parent, false);
        }

        TextView tituloLivro= convertView.findViewById(R.id.livroListTituloTextView);
        RatingBar ratingBar = convertView.findViewById(R.id.livroListRatingBar);
        ImageView imageView = convertView.findViewById(R.id.livroListImageView);


        tituloLivro.setText(items.get(position).getTitulo());
        ratingBar.setRating(items.get(position).getAvaliacao());
        ratingBar.setEnabled(false);

        String filePath = items.get(position).getFotoPath();
        if(filePath!=null && !filePath.isEmpty()){
            imageView.setImageURI(null);
            imageView.setImageURI(Uri.parse(items.get(position).getFotoPath()));
        }
        return convertView;
    }


}
