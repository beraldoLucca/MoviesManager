package br.edu.ifsp.scl.ads.pdm.moviesmanager.model.dao

import br.edu.ifsp.scl.ads.pdm.moviesmanager.model.Movie

interface MovieDao {
    fun createMovie(movie: Movie): Int
    fun retrieveMovie(movieId: Int): Movie?
    fun retrieveMovies(): MutableList<Movie>
    fun updateMovie(movie: Movie): Int
    fun deleteMovie(idMovie: Int): Int
}