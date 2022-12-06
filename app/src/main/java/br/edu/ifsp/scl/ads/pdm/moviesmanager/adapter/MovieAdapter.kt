package com.example.splitthebill.adapter

import android.content.Context
import android.content.Context.LAYOUT_INFLATER_SERVICE
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.CheckBox
import android.widget.TextView
import br.edu.ifsp.scl.ads.pdm.moviesmanager.R
import br.edu.ifsp.scl.ads.pdm.moviesmanager.model.Movie

class MovieAdapter(
    context: Context,
    private val movieList: MutableList<Movie>,
) : ArrayAdapter<Movie>(context, R.layout.tile_movie, movieList) {
    private data class TileMovieHolder(
        val nameTv: TextView, val anoLancamentoTv: TextView, val supplierTv: TextView, val flagCb: CheckBox,val noteTv: TextView,val genreTv: TextView
    )

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val movie = movieList[position]

        var movieTileView = convertView

        if (movieTileView == null) {

            movieTileView =
                (context.getSystemService(LAYOUT_INFLATER_SERVICE) as LayoutInflater).inflate(
                    R.layout.tile_movie,
                    parent,
                    false
                )

            val tileMovieHolder = TileMovieHolder(
                movieTileView.findViewById(R.id.nameTv),
                movieTileView.findViewById(R.id.anoLancamentoTv),
                movieTileView.findViewById(R.id.supplierTv),
                movieTileView.findViewById(R.id.flagCb),
                movieTileView.findViewById(R.id.noteTv),
                movieTileView.findViewById(R.id.genreTv)
            )
            movieTileView.tag = tileMovieHolder
        }

        with(movieTileView?.tag as TileMovieHolder) {
            nameTv.text = movie.name
            anoLancamentoTv.text = String.format("Filme foi lançado em: %s", movie.yearReleased.toString())
            supplierTv.text = String.format("Produtora:  %s", movie.supplier.toString())
            flagCb.isChecked = movie.viewed
            noteTv.text = String.format("Nota: %s", movie.note.toString())
            genreTv.text = String.format("Gênero: %s", movie.genre.toString())
        }

        return movieTileView
    }
}
