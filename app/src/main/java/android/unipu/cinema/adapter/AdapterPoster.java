package android.unipu.cinema.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.unipu.cinema.Adapter;
import android.unipu.cinema.MovieDetail;
import android.unipu.cinema.R;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.List;

public class AdapterPoster extends RecyclerView.Adapter<AdapterPoster.ViewHolder> {

    private LayoutInflater layoutInflater;
    private List<String> naslov;
    private List<String> slikaFilm;
    private List<String> zanrFilm;
    private ImageView pozadinaImage;
    private List<String> trajanje;
    private List<Double> ocjena;
    private List<Integer> id;


    public AdapterPoster(Context context, List<String> slikaFilm, List<String> naslov, List<Integer> id){
        this.layoutInflater = LayoutInflater.from(context);
        this.slikaFilm = slikaFilm;
        this.naslov = naslov;
        this.id = id;
    }

    @NonNull
    @Override
    public AdapterPoster.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = layoutInflater.inflate(R.layout.custom_pozadina,viewGroup,false);
        return new AdapterPoster.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterPoster.ViewHolder viewHolder, int i) {

        String imageUrl = slikaFilm.get(i);
        Picasso.get().load(imageUrl).fit().into(pozadinaImage);

        String title = naslov.get(i);
        viewHolder.textNaslov.setText(title);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return naslov.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView textNaslov;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(v.getContext(), MovieDetail.class);
                    i.putExtra("id",id.get(getAdapterPosition()));
                    v.getContext().startActivity(i);
                }
            });

            textNaslov = itemView.findViewById(R.id.naslovFilm);
            pozadinaImage = itemView.findViewById(R.id.pozadinaImage);
        }
    }
}
