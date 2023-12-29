package com.example.tarea3.views;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tarea3.R;
import com.example.tarea3.database.Comic;

import java.util.ArrayList;
import java.util.List;

//RecyclerView usado en la tercera activty que muestra el listado de los comics descargador
public class ComicRecyclerViewAdapter  extends RecyclerView.Adapter<ComicRecyclerViewAdapter.ViewHolder> {

    //usamos un arraylist con los comics descargador y un listener para el click en los comics
    private List<Comic> comics;
    private final OnComicClickListener listener;

    //Adaptador de ReciclerView que recibe el listener de los click
    ComicRecyclerViewAdapter(OnComicClickListener listener){

        //devolvemos el arraylist de comics y el listener a la clase principal
        this.comics = new ArrayList<>();
        this.listener = listener;
    }

    //devolvemos el arraylist de comics
    public void updateComics(List<Comic> comics){
        this.comics = comics;
    }

    //creamos un ViewHolder que reutilizamos con el layout comic_list_item para pintar todos los comics en el listado de la vista Lister
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.comic_list_item, viewGroup, false);
        return new ViewHolder(v);
    }

    //con el onBindViewHolder vamos recibiendo las posiciones de cada comic en el arrayList de Comics
    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //rellenamos el obeto comic con el comic que corresponde segun posicion
        Comic comic = comics.get(position);
        //metemos en el Viewholder en el campo titulo y fecha los datos extraidos del comic segun posicion
        holder.num_title.setText(comic.getNum() + " - " + comic.getTitle());
        holder.full_date.setText(comic.getDay() + " - " + comic.getMonth() + " - " + comic.getYear());
        //establecemos en cada comic un listener para los clicks
        holder.cv.setOnClickListener(new View.OnClickListener() {
           //asignamos a cada comic un metodo onclick que luego usaremos para devolver el objeto comic
            @Override
            public void onClick(View v) {
                listener.onComicClick(comic);
            }
        });

    }

    //contador de tamaño de comics del ArrayList
    @Override
    public int getItemCount() {
        return comics.size();
    }

    //establecemos la clase viewHolder para asignar los nombres de los campos titulo, fecha y la cardview del Layout Comic_list_item
    public class ViewHolder extends RecyclerView.ViewHolder {

        public final TextView num_title;
        public final TextView full_date;
        public final CardView cv;

        //recibimos la vista con los datos y los estblecemos en la clase principal del VieHolder
        public ViewHolder(@NonNull View view) {
            super(view);
            num_title = view.findViewById(R.id.num_title_text);
            full_date = view.findViewById(R.id.full_date_text);
            cv = view.findViewById(R.id.cv);
        }
    }

    //interface creada para los clicks en cada comic
    public interface OnComicClickListener{
        void onComicClick(Comic comicClicked);
    }
}
