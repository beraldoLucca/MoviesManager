package br.edu.ifsp.scl.ads.pdm.moviesmanager.model.sqlite

import android.content.ContentValues
import android.content.Context.MODE_PRIVATE
import android.database.sqlite.SQLiteDatabase
import android.database.Cursor
import java.sql.SQLException
import android.content.Context
import android.util.Log
import br.edu.ifsp.scl.ads.pdm.moviesmanager.model.Movie
import br.edu.ifsp.scl.ads.pdm.moviesmanager.model.dao.MovieDao
import br.edu.ifsp.scl.ads.pdm.moviesmanager.model.enums.Genres

class SqliteImpl(context: Context): MovieDao {
    companion object Constant {
        private const val MOVIES_DATABASE_FILE = "movies_database"
        private const val MOVIE_TABLE = "movie"
        private const val ID_COLUMN = "id"
        private const val NAME_COLUMN = "name"
        private const val YEAR_RELEASED_COLUMN = "year"
        private const val SUPPLIER_COLUMN = "studio"
        private const val DURATION_COLUMN = "timeofduration"
        private const val VIEWED_COLUMN = "watched"
        private const val NOTE_COLUMN = "note"
        private const val GENRE_COLUMN = "genre"

        private const val CREATE_MOVIE_TABLE_STATEMENT =
            "CREATE TABLE IF NOT EXISTS $MOVIE_TABLE ( " +
                    "$ID_COLUMN INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "$NAME_COLUMN TEXT NOT NULL, " +
                    "$YEAR_RELEASED_COLUMN TEXT NOT NULL, " +
                    "$SUPPLIER_COLUMN TEXT NOT NULL, " +
                    "$DURATION_COLUMN TEXT NOT NULL," +
                    "$VIEWED_COLUMN BOOLEAN NOT NULL," +
                    "$NOTE_COLUMN TEXT NOT NULL," +
                    "$GENRE_COLUMN TEXT NOT NULL );"
    }

    private val movieSqliteDatabase: SQLiteDatabase

    init {
        movieSqliteDatabase = context.openOrCreateDatabase(
            MOVIES_DATABASE_FILE,
            MODE_PRIVATE,
            null
        )
        try {
            movieSqliteDatabase.execSQL(CREATE_MOVIE_TABLE_STATEMENT)
        } catch (se: SQLException) {
            Log.e("SQL_Error", "Erro na criacao do banco de dados")
        }
    }

    private fun Movie.toContentValues() = with(ContentValues()) {
        put(NAME_COLUMN, name)
        put(YEAR_RELEASED_COLUMN, yearReleased)
        put(SUPPLIER_COLUMN, supplier)
        put(DURATION_COLUMN, duration)
        put(VIEWED_COLUMN, viewed )
        put(NOTE_COLUMN, note )
        put(GENRE_COLUMN, genre.toString())


        this
    }

    private fun movieToContentValues(movie: Movie) = with(ContentValues()) {
        put(NAME_COLUMN, movie.name)
        put(YEAR_RELEASED_COLUMN, movie.yearReleased)
        put(SUPPLIER_COLUMN, movie.supplier)
        put(DURATION_COLUMN, movie.duration)
        put(VIEWED_COLUMN, movie.viewed)
        put(NOTE_COLUMN, movie.note)
        put(GENRE_COLUMN, movie.genre.toString())
        this
    }

    private fun Cursor.rowToMovie() = Movie(
        getInt(getColumnIndexOrThrow(ID_COLUMN)),
        getString(getColumnIndexOrThrow(NAME_COLUMN)),
        getString(getColumnIndexOrThrow(YEAR_RELEASED_COLUMN)),
        getString(getColumnIndexOrThrow(SUPPLIER_COLUMN)),
        getDouble(getColumnIndexOrThrow(DURATION_COLUMN)),
        getColumnIndexOrThrow(VIEWED_COLUMN) == 0,
        getDouble(getColumnIndexOrThrow(NOTE_COLUMN)),
        Genres.valueOf(getString(getColumnIndexOrThrow(GENRE_COLUMN)))
    )

    override fun createMovie(movie: Movie) = movieSqliteDatabase.insert(
        MOVIE_TABLE,
        null,
        movieToContentValues(movie)
    ).toInt()


    override fun retrieveMovie(id: Int): Movie? {
        val cursor = movieSqliteDatabase.rawQuery(
            "SELECT * FROM $MOVIE_TABLE WHERE $ID_COLUMN = ?",
            arrayOf(id.toString())
        )
        val movie = if (cursor.moveToFirst()) cursor.rowToMovie() else null

        cursor.close()
        return movie
    }

    override fun retrieveMovies(): MutableList<Movie> {
        val movieList = mutableListOf<Movie>()
        val cursor = movieSqliteDatabase.rawQuery(
            "SELECT * FROM $MOVIE_TABLE ORDER BY $NAME_COLUMN",
            null
        )
        while (cursor.moveToNext()) {
            movieList.add(cursor.rowToMovie())
        }
        cursor.close()
        return movieList
    }

    override fun updateMovie(movie: Movie) = movieSqliteDatabase.update(
        MOVIE_TABLE,
        movie.toContentValues(),
        "$ID_COLUMN = ?",
        arrayOf(movie.id.toString())
    )

    override fun deleteMovie(id: Int) =
        movieSqliteDatabase.delete(
            MOVIE_TABLE,
            "$ID_COLUMN = ?",
            arrayOf(id.toString())
        )
}