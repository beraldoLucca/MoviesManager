package br.edu.ifsp.scl.ads.pdm.moviesmanager.model

import android.os.Parcelable
import android.widget.EditText
import br.edu.ifsp.scl.ads.pdm.moviesmanager.model.enums.Genres
import kotlinx.parcelize.Parcelize

@Parcelize
data class Movie(

    val id: Int,
    var name: String,
    var yearReleased: String,
    var supplier: String,
    var duration: Double,
    var viewed: Boolean,
    var note: Double,
    var genre: Genres,
): Parcelable