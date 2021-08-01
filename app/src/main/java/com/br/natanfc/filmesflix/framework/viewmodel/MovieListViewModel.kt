package com.br.natanfc.filmesflix.framework.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.br.natanfc.filmesflix.framework.viewmodel.api.MovieRestApiTask
import com.br.natanfc.filmesflix.data.MovieRepository
import com.br.natanfc.filmesflix.domain.Movie
import com.br.natanfc.filmesflix.implementation.MovieDataSourceImplementation
import com.br.natanfc.filmesflix.usecase.MovieListUseCase

class MovieListViewModel: ViewModel() {

    private val movieRestApiTask = MovieRestApiTask()
    private val movieDataSource = MovieDataSourceImplementation(movieRestApiTask)
    private val movieRepository = MovieRepository(movieDataSource)
    private val moviesListUseCase = MovieListUseCase(movieRepository)


    companion object {
        const val TAG = "MovieRepository"
    }

    private val listOfMovies = arrayListOf(
        Movie(
            id = 0,
            titulo = "Titanic",
            descricao = null,
            imagem = null,
            dataLancamento = null
        ),
        Movie(
            id = 1,
            titulo = "Central do Brasil",
            descricao = null,
            imagem = null,
            dataLancamento = null
        )
    )

    fun init(){
        getAllMovies()
    }

    private var _moviesList = MutableLiveData<List<Movie>>()
    val moviesList: LiveData<List<Movie>>
    get() = _moviesList

    private fun getAllMovies(){
        Thread {
            try {
                _moviesList.postValue(moviesListUseCase.invoke())
            } catch (exception: Exception){
                Log.d(TAG, exception.message.toString())
            }
        }.start()

    }
}