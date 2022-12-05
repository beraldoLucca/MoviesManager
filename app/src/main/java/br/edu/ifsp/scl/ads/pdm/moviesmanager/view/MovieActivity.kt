package br.edu.ifsp.scl.ads.pdm.moviesmanager.view

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import br.edu.ifsp.scl.ads.pdm.moviesmanager.R
import br.edu.ifsp.scl.ads.pdm.moviesmanager.databinding.ActivityMovieBinding
import br.edu.ifsp.scl.ads.pdm.moviesmanager.model.Constant.EXTRA_MOVIE
import br.edu.ifsp.scl.ads.pdm.moviesmanager.model.Movie
import br.edu.ifsp.scl.ads.pdm.moviesmanager.model.enums.Genres
import java.util.*


class MovieActivity : AppCompatActivity() {
    private val amb: ActivityMovieBinding by lazy {
        ActivityMovieBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(amb.root)

        if (intent.getBooleanExtra("VIEW_MOVIE", false)){
            amb.nameEt.isEnabled = false
            amb.anoLancamentoEt.isEnabled = false
            amb.durationEt.isEnabled = false
            amb.genreSp.isEnabled = false
            amb.noteEt.isEnabled = false
            amb.saveBt.text = getString(R.string.close)
        }

        val receivedMovie = intent.getParcelableExtra<Movie>(EXTRA_MOVIE)
        receivedMovie?.let { movie ->
            amb.nameEt.isEnabled = false
            amb.anoLancamentoEt.setText(movie.yearReleased.toString())
            amb.durationEt.setText(movie.duration.toString())
            //amb.genreSp.setSelection(movie.genre)
            amb.noteEt.setText(movie.note.toString())
            amb.supplierEt.setText(movie.supplier.toString())

            for (i in  0 until Genres.values().size) {
                if(amb.genreSp.toString() == Genres.values()[i].toString())
                    amb.genreSp.setSelection(i)
            }
        }


        amb.saveBt.setOnClickListener {
            if (
                amb.nameEt.text.isNotEmpty()
            ) {
                val genrer = amb.genreSp.selectedItem.toString()
                val genrerFromEnum = Genres.valueOf(genrer)
                val movie = Movie(
                    id = receivedMovie?.id ?: Random(System.currentTimeMillis()).nextInt(),
                    name = amb.nameEt.text.toString(),
                    yearReleased = amb.anoLancamentoEt.text.toString(),
                    duration = amb.durationEt.text.toString().toDouble(),
                    genre = genrerFromEnum,
                    note = amb.noteEt.text.toString().toDouble(),
                    supplier = amb.supplierEt.text.toString(),
                    viewed = amb.flagCb.isChecked
                )
                setResult(
                    RESULT_OK,
                    Intent().putExtra(
                        EXTRA_MOVIE,
                        movie
                    )
                )
                finish()
            }else {
                Toast.makeText(
                    this, getString(R.string.ensuring_inputs_movie), Toast.LENGTH_LONG
                ).show()
            }
        }
    }
}