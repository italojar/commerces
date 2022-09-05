package website.italojar.klikincommerces.presentation.commerces_list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import website.italojar.klikincommerces.data.model.dto.CommerceDto
import website.italojar.klikincommerces.domain.usecase.GetCommercesUseCase
import javax.inject.Inject

@HiltViewModel
class CommercesListViewModel @Inject constructor(
    private val getAllCommercesUseCase: GetCommercesUseCase
) : ViewModel() {

    private val _commerces = MutableLiveData<List<CommerceDto>>()
    val commerces: LiveData<List<CommerceDto>> = _commerces
    val isLoading = MutableLiveData<Boolean>()

    init {
        getAllCommerces()
    }

    private fun getAllCommerces() {
        viewModelScope.launch {
            isLoading.postValue(true)
            val allCommerces = getAllCommercesUseCase()
            if (!allCommerces.isNullOrEmpty()){
                _commerces.value = allCommerces
                isLoading.postValue(false)
            }
        }
    }
}