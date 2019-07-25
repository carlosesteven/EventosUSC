package usc.app.eventosusc.adaptador

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import usc.app.eventosusc.R
import usc.app.eventosusc.objetos.Eventos

class RvEvento(private var listaMangas: ArrayList<Eventos>, private val listener: INTERFACE_click) : RecyclerView.Adapter<RvEvento.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    {
        val foto: ImageView = itemView.findViewById(R.id.eventoFoto)
        val nombre : TextView = itemView.findViewById(R.id.eventoTitulo)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ViewHolder {
        val v = LayoutInflater.from(viewGroup.context).inflate(R.layout.item_evento, viewGroup, false)
        val pvh = ViewHolder(v)
        v.setOnClickListener { v1 -> listener.onItemClick(v1, pvh.layoutPosition) }
        return pvh
    }

    override fun onBindViewHolder(view: ViewHolder, i: Int)
    {
        val actual : Eventos = listaMangas[ i ]
        view.nombre.text = actual.titulo
        Picasso
            .get()
            .load( actual.foto )
            .resize(0, 500)
            .into( view.foto )
    }

    override fun getItemCount(): Int {
        return listaMangas.size
    }

}