package br.edu.ifsp.scl.ads.pdm.moviesmanager.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.Date

@Parcelize
data class Movie (

    val id: Int,
    var name: String,
    var yearReleased: Date,
    var supplier: String,
    var duration: Double,
    var viewed: Boolean,
    var note: Double,
    var genre: String,
): Parcelable