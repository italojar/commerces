package website.italojar.klikincommerces.presentation.commerces_list

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import website.italojar.klikincommerces.R
import website.italojar.klikincommerces.databinding.FragmentCommercesListBinding
import website.italojar.klikincommerces.presentation.commerces_list.adapters.categories.CategoriesAdapter
import website.italojar.klikincommerces.presentation.commerces_list.adapters.commerces.CommerceAdapter
import website.italojar.klikincommerces.presentation.model.CommerceVO
import website.italojar.klikincommerces.presentation.mappers.toDetail
import website.italojar.klikincommerces.presentation.viewmodel.SharedCommerceViewModel


@AndroidEntryPoint
class CommercesListFragment : Fragment() {

    private var _binding: FragmentCommercesListBinding? = null
    private val binding get() = _binding!!

    private val viewModel: SharedCommerceViewModel by viewModels()
    private val activityViewModel: SharedCommerceViewModel by activityViewModels()
    private lateinit var commercesMutableList: MutableList<CommerceVO>
    private lateinit var commercesAdapter: CommerceAdapter
    private lateinit var categoriesMutableList: MutableList<String>
    private  var latitude: Float = 0.0f
    private  var longitude: Float = 0.0f


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentCommercesListBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    @SuppressLint("MissingPermission")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // success
        viewModel.commerces.observe(viewLifecycleOwner, Observer { commerces_list ->
            commercesMutableList = commerces_list as MutableList<CommerceVO>
            binding.totalCommerces.text = commercesMutableList.size.toString()
            if (this::commercesMutableList.isInitialized){
                initRecyclerViewCommerces()
            }
        })
        viewModel.categories.observe(viewLifecycleOwner, Observer { categories ->
            categoriesMutableList = categories as MutableList<String>
            if (this::categoriesMutableList.isInitialized){
                initRecyclerViewCategories()
            }
        })
        // loading
        viewModel.isLoading.observe(viewLifecycleOwner, Observer { visibility ->
            binding.cardviewCommerces.isVisible = !visibility
            binding.cardviewDistance.isVisible = !visibility
            binding.progressBarApp.root.isVisible = visibility
        })
        // error
        viewModel.error.observe(viewLifecycleOwner, Observer { error ->
            activity?.finish()
            Toast.makeText(requireContext(), error, Toast.LENGTH_SHORT).show()
        })
    }

    override fun onStart() {
        super.onStart()
        activityViewModel.currentLocation.observe(viewLifecycleOwner, { currentLocation ->
            latitude = currentLocation.latitude.toFloat()
            longitude = currentLocation.longitude.toFloat()
        })
    }

    // Recyclers
    private fun initRecyclerViewCategories() {
        val layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.rvCategories.layoutManager = layoutManager
        val categoriesAdapter = CategoriesAdapter(categoriesMutableList) { category ->
            updateCommerces(category ?: getString(R.string.category_food))
        }
        binding.rvCategories.adapter = categoriesAdapter
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun initRecyclerViewCommerces() {
        binding.rvCommerces.layoutManager = LinearLayoutManager(requireContext())
        commercesAdapter = CommerceAdapter(commercesMutableList) { commerce ->
            findNavController().navigate(CommercesListFragmentDirections
                .actionCommercesListFragmentToCommerceDetailFragment(commerce.toDetail(),
                    latitude, longitude
                ))
        }
        binding.rvCommerces.adapter = commercesAdapter
        commercesAdapter.notifyDataSetChanged()
    }

    private fun updateCommerces(category: String) {
        if (!this::commercesMutableList.isInitialized){
            initRecyclerViewCommerces()
        }else{
            viewModel.updateCommerces(commercesMutableList, category)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}