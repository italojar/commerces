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
import website.italojar.klikincommerces.domain.usecase.GetCommercesByCategoryUseCase
import website.italojar.klikincommerces.domain.usecase.GetCommercesUseCase
import website.italojar.klikincommerces.presentation.model.CommerceVO
import javax.inject.Inject

@HiltViewModel
class SharedCommerceViewModel @Inject constructor() : ViewModel() {

    private val _currentLocation = MutableLiveData<LatLng>()
    val currentLocation: LiveData<LatLng> = _currentLocation
    private val _distance = MutableLiveData<Int>()
    val distance: LiveData<Int> = _distance

    fun getCurrentLocation(location: LatLng) {
        _currentLocation.value = location
    }

    fun getDistance(distance: Int) {
        _distance.value = distance
    }
}