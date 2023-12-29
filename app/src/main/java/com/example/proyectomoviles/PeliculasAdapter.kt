package com.example.proyectomoviles

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class PeliculasAdapter(private val peliculas: MutableList<Pelicula>, private val onDeleteClickListener: (Int) -> Unit) :
    RecyclerView.Adapter<PeliculasAdapter.PeliculaViewHolder>() {

    class PeliculaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imgPelicula: ImageView = itemView.findViewById(R.id.imgPelicula)
        val txtNombrePelicula: TextView = itemView.findViewById(R.id.txtNombrePelicula)
        val txtDescripcion: TextView = itemView.findViewById(R.id.txtDescripcion)
        val btnEliminar: ImageView = itemView.findViewById(R.id.btnEliminar)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PeliculaViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.item_pelicula, parent, false)
        return PeliculaViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: PeliculaViewHolder, position: Int) {
        val pelicula = peliculas[position]
        holder.imgPelicula.setImageResource(pelicula.imagen)
        holder.txtNombrePelicula.text = pelicula.nombre
        holder.txtDescripcion.text = pelicula.descripcion

        // Configura el clic del botón de eliminación
        holder.btnEliminar.setOnClickListener {
            onDeleteClickListener.invoke(position)
        }
    }

    override fun getItemCount(): Int {
        return peliculas.size
    }

    fun updateData(newData: List<Pelicula>) {
        peliculas.clear()  // Limpiar la lista existente
        peliculas.addAll(newData)
        notifyDataSetChanged()
    }
}
