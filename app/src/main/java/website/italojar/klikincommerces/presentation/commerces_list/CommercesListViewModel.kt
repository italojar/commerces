package website.italojar.klikincommerces.presentation.commerces_list

import android.content.Context
import android.content.res.Resources
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.maps.model.LatLng
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import website.italojar.klikincommerces.R
import website.italojar.klikincommerces.domain.mappers.tovO
import website.italojar.klikincommerces.domain.model.Commerce
import website.italojar.klikincommerces.domain.usecase.GetCommercesUseCase
import website.italojar.klikincommerces.presentation.model.CommerceVO
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class CommercesListViewModel @Inject constructor(
    private val getAllCommercesUseCase: GetCommercesUseCase
) : ViewModel() {

    private val _commerces = MutableLiveData<List<CommerceVO>>()
    val commerces: LiveData<List<CommerceVO>> = _commerces
    val isLoading = MutableLiveData<Boolean>()
    val error = MutableLiveData<String>()
    private val mutableSelectedItem = MutableLiveData<LatLng>()
    val selectedItem: LiveData<LatLng> = mutableSelectedItem

    fun selectItem(location: LatLng) {
        mutableSelectedItem.value = location
    }

    init {
        getAllCommerces()
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

    fun updatePokemon(commerces: List<CommerceVO>) {
        _commerces.postValue(commerces)
    }
}