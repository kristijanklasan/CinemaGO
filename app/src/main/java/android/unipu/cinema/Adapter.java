package android.unipu.cinema;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder>{

    private LayoutInflater layoutInflater;
    private List<String> naslov;
    private List<String> slikaFilm;
    private List<String> zanrFilm;
    private ImageView imageView;
    private List<String> trajanje;
    private List<Double> ocjena;
    private List<Integer> id;


    public Adapter(Context context,List<String> slikaFilm, List<String> naslov,List<String> zanrFilm,List<String> trajanje, List<Double> ocjena,List<Integer> id){
        this.layoutInflater = LayoutInflater.from(context);
        this.naslov = naslov;
        this.zanrFilm = zanrFilm;
        this.slikaFilm = slikaFilm;
        this.trajanje = trajanje;
        this.ocjena = ocjena;
        this.id = id;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = layoutInflater.inflate(R.layout.custom_view,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

        String imageUrl = slikaFilm.get(i);
        Picasso.get().load(imageUrl).fit().into(imageView);

        String title = naslov.get(i);
        viewHolder.textTitle.setText(title);

        String zanr = zanrFilm.get(i);
        viewHolder.textZanr.setText("Žanr: "+zanr);

        String trajanjeFilm = trajanje.get(i);
        viewHolder.textTrajanje.setText("Trajanje: "+trajanjeFilm);

        double ocjenaFilm = ocjena.get(i);
        viewHolder.textOcjena.setText(String.valueOf(ocjenaFilm));
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

        TextView textTitle,textZanr,textTrajanje,textOcjena;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(v.getContext(),MovieDetail.class);
                    i.putExtra("id",id.get(getAdapterPosition()));
                    v.getContext().startActivity(i);
                }
            });

            textTitle = itemView.findViewById(R.id.textTitle);
            textZanr= itemView.findViewById(R.id.textDesc);
            imageView = itemView.findViewById(R.id.imageView);
            textTrajanje = itemView.findViewById(R.id.textDesc3);
            textOcjena = itemView.findViewById(R.id.textDesc4);
        }
    }
}