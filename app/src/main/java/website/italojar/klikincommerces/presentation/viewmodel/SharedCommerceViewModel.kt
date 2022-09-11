package website.italojar.klikincommerces.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.maps.model.LatLng
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import website.italojar.klikincommerces.domain.mappers.tovO
import website.italojar.klikincommerces.domain.usecase.GetCategoriesUseCase
import website.italojar.klikincommerces.domain.usecase.GetCommercesUseCase
import website.italojar.klikincommerces.presentation.model.CommerceVO
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class SharedCommerceViewModel @Inject constructor(
    private val getAllCommercesUseCase: GetCommercesUseCase,
    private val getCategoriesUseCase: GetCategoriesUseCase
) : ViewModel() {

    private val _commerces = MutableLiveData<List<CommerceVO>>()
    val commerces: LiveData<List<CommerceVO>> = _commerces
    private val _categories = MutableLiveData<List<String>>()
    val categories: LiveData<List<String>> = _categories
    val isLoading = MutableLiveData<Boolean>()
    val error = MutableLiveData<String>()
    private val _currentLocation = MutableLiveData<LatLng>()
    val currentLocation: LiveData<LatLng> = _currentLocation

    init {
        getAllCommerces()
        getCategories()
    }

    private fun getAllCommerces() {
        viewModelScope.launch {
            try {
                isLoading.postValue(true)
                val allCommerces = getAllCommercesUseCase()
                if (!allCommerces.isNullOrEmpty()){
                    _commerces.value = allCommerces.map { commerce -> commerce.tovO() }
                    isLoading.postValue(false)
                }
            }catch (exception: Exception){
                isLoading.postValue(false)
                error.postValue("Ha ocurrido un error inesperado")
            }
        }
    }

    private fun getCategories() {
        viewModelScope.launch {
            val allCategories = getCategoriesUseCase()
            if (!allCategories.isNullOrEmpty()){
                _categories.value = allCategories
            }
        }
    }

    fun getCurrentLocation(location: LatLng) {
        _currentLocation.value = location
    }

    fun updateCommerces(commercesList: List<CommerceVO>, category: String) {
        _commerces.postValue(commercesList.filter { it.category == category })
    }
}