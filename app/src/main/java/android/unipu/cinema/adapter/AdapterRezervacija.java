package android.unipu.cinema.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.unipu.cinema.R;
import android.unipu.cinema.RezervacijaDetalji;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import java.util.List;

public class AdapterRezervacija extends RecyclerView.Adapter<AdapterRezervacija.ViewHolder> {

    private LayoutInflater layoutInflater ;
    private List<String> naslov;
    private List<String> slikaFilm;
    private List<String> datum;
    private ImageView imageView;
    private List<String> vrijeme;
    private List<Integer> id;


    public AdapterRezervacija(Context context, List<String> slikaFilm, List<String> naslov, List<String> datum, List<String> vrijeme,  List<Integer> id){
        this.layoutInflater = LayoutInflater.from(context);
        this.slikaFilm = slikaFilm;
        this.naslov = naslov;
        this.datum = datum;
        this.vrijeme = vrijeme;
        this.id = id;
    }

    @NonNull
    @Override
    public AdapterRezervacija.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = layoutInflater.inflate(R.layout.custom_view,viewGroup,false);
        return new AdapterRezervacija.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterRezervacija.ViewHolder viewHolder, int i) {

        String imageUrl = slikaFilm.get(i);
        Picasso.get().load(imageUrl).fit().into(imageView);

        String title = naslov.get(i);
        viewHolder.textTitle.setText(title);

        String datum_prikazivanja = datum.get(i);
        viewHolder.textDatum.setText("Datum: "+datum_prikazivanja);

        String vrijeme_prikazivanja = vrijeme.get(i);
        viewHolder.textVrijeme.setText("Vrijeme: "+vrijeme_prikazivanja);
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

        TextView textTitle,textDatum,textVrijeme;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(v.getContext(), RezervacijaDetalji.class);
                    i.putExtra("id",id.get(getAdapterPosition()));
                    v.getContext().startActivity(i);
                }
            });

            textTitle = itemView.findViewById(R.id.textTitle);
            textDatum= itemView.findViewById(R.id.textDesc);
            imageView = itemView.findViewById(R.id.imageView);
            textVrijeme = itemView.findViewById(R.id.textDesc3);
        }
    }
}
