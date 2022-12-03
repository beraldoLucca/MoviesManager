package br.edu.ifsp.scl.ads.pdm.moviesmanager.model

import android.os.Parcelable
import android.widget.EditText
import kotlinx.parcelize.Parcelize

@Parcelize
data class Movie(

    val id: Int,
    var name: String,
    var yearReleased: EditText,
    var supplier: String,
    var duration: Double,
    var viewed: Boolean,
    var note: Double,
    var genre: String,
): Parcelable