package com.dicoding.aplikasiku.allboom

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dicoding.aplikasiku.allboom.data.AlbumRepository
import com.dicoding.aplikasiku.allboom.model.Album
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class AllBoomViewModel(private val repository: AlbumRepository) : ViewModel() {
    private val _groupedAlbums = MutableStateFlow(
        repository.getAlbums()
            .sortedBy { it.name }
            .groupBy { it.name[0] }
    )
    val groupedAlbums: StateFlow<Map<Char, List<Album>>> get() = _groupedAlbums

    private val _query = mutableStateOf("")
    val query: State<String> get() = _query
    fun search(newQuery: String) {
        _query.value = newQuery
        _groupedAlbums.value = repository.searchAlbums(_query.value)
            .sortedBy { it.name }
            .groupBy { it.name[0] }
    }
}

class ViewModelFactory(private val repository: AlbumRepository) :
    ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AllBoomViewModel::class.java)) {
            return AllBoomViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }
}