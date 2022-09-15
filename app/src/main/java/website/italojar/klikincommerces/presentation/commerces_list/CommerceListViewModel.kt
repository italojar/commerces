package website.italojar.klikincommerces.presentation.commerces_list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.maps.model.LatLng
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import website.italojar.klikincommerces.domain.mappers.tovO
import website.italojar.klikincommerces.domain.model.Commerce
import website.italojar.klikincommerces.domain.usecase.GetCategoriesUseCase
import website.italojar.klikincommerces.domain.usecase.GetCommercesByCategoryUseCase
import website.italojar.klikincommerces.domain.usecase.GetCommercesByDistanceUseCase
import website.italojar.klikincommerces.domain.usecase.GetCommercesUseCase
import website.italojar.klikincommerces.presentation.model.CommerceVO
import javax.inject.Inject

@HiltViewModel
class CommerceListViewModel @Inject constructor(
    private val getAllCommercesUseCase: GetCommercesUseCase,
    private val getCategoriesUseCase: GetCategoriesUseCase,
    private val getCommercesByCategoryUseCase: GetCommercesByCategoryUseCase,
    private val getCommercesByDistanceUseCase: GetCommercesByDistanceUseCase
) : ViewModel() {

    private val _commerces = MutableLiveData<List<CommerceVO>>()
    val commerces: LiveData<List<CommerceVO>> = _commerces

    private val _commercesByCategory = MutableLiveData<List<CommerceVO>>()
    val commercesByCategory: LiveData<List<CommerceVO>> = _commercesByCategory

    private val _commercesByDistance = MutableLiveData<List<CommerceVO>>()
    val commercesByDistance: LiveData<List<CommerceVO>> = _commercesByDistance

    private val _categories = MutableLiveData<List<String>?>()
    val categories: LiveData<List<String>?> = _categories

    val isLoading = MutableLiveData<Boolean>()

    val error = MutableLiveData<String>()

    init {
        getInitDataToSetUi()
    }

    private fun getInitDataToSetUi() {
        viewModelScope.launch {
            isLoading.postValue(true)
            val allCommerces = getAllCommercesUseCase()
            val allCategories = getCategoriesUseCase()
            if (!allCommerces.data.isNullOrEmpty()) {
                _commerces.value = allCommerces.data.map { commerce -> commerce.tovO() }
                _categories.value = allCategories.data
                isLoading.postValue(false)
            }else {
                error.postValue(allCommerces.message ?: "Ha ocurrido un error inesperado")
                isLoading.postValue(false)
            }
        }
    }

    fun getCommercesByCategory(category: String) {
        viewModelScope.launch {
            isLoading.postValue(true)
            val allCommercesCategory = getCommercesByCategoryUseCase(category)
            if (!allCommercesCategory.data.isNullOrEmpty()) {
                _commercesByCategory.value = allCommercesCategory.data.map { commerce -> commerce.tovO() }
                isLoading.postValue(false)
            }else {
                error.postValue(allCommercesCategory.message ?: "Ha ocurrido un error inesperado")
                isLoading.postValue(false)
            }
        }
    }

    fun getCommercesByDistance(distanceSelected: Int, latLng: LatLng) {
        viewModelScope.launch {
            isLoading.postValue(true)
            val allCommercesDistance = getCommercesByDistanceUseCase(distanceSelected, latLng)
            if (!allCommercesDistance.data.isNullOrEmpty()) {
                _commercesByDistance.value = allCommercesDistance.data.map { commerce -> commerce.tovO() }
                isLoading.postValue(false)
            }else {
                error.postValue(allCommercesDistance.message ?: "Listado por cercanía vacío")
                isLoading.postValue(false)
            }
        }
    }
}