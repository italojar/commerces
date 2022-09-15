package website.italojar.klikincommerces.presentation.commerces_list

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.maps.model.LatLng
import dagger.hilt.android.AndroidEntryPoint
import website.italojar.klikincommerces.R
import website.italojar.klikincommerces.databinding.FragmentCommercesListBinding
import website.italojar.klikincommerces.presentation.commerces_list.adapters.categories.CategoriesAdapter
import website.italojar.klikincommerces.presentation.commerces_list.adapters.commerces.CommerceAdapter
import website.italojar.klikincommerces.presentation.components.DistanceDialog
import website.italojar.klikincommerces.presentation.model.CommerceVO
import website.italojar.klikincommerces.presentation.mappers.toDetail
import website.italojar.klikincommerces.presentation.viewmodel.SharedCommerceViewModel


@AndroidEntryPoint
class CommercesListFragment : Fragment() {

    private var _binding: FragmentCommercesListBinding? = null
    private val binding get() = _binding!!

    private val viewModel: CommerceListViewModel by viewModels()
    private val activityViewModel: SharedCommerceViewModel by activityViewModels()
    private lateinit var commercesMutableList: MutableList<CommerceVO>
    private lateinit var commercesAdapter: CommerceAdapter
    private lateinit var categoriesMutableList: MutableList<String>
    private var latitude: Float = 40.4169473f
    private var longitude: Float = -3.7035285f

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentCommercesListBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.tvCommercesDistance.text = getString(R.string.commerces_plus_of)
        getUiData()
    }

    override fun onStart() {
        super.onStart()
        activityViewModel.currentLocation.observe(viewLifecycleOwner, { currentLocation ->
            latitude = currentLocation.latitude.toFloat()
            longitude = currentLocation.longitude.toFloat()
        })
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun getUiData() {
        // success
        viewModel.commerces.observe(viewLifecycleOwner, Observer { commerces_list ->
            commercesMutableList = commerces_list as MutableList<CommerceVO>
            if (this::commercesMutableList.isInitialized) {
                binding.totalCommerces.text = commercesMutableList.size.toString()
                binding.nearestCommerces.text = commercesMutableList.size.toString()
                initRecyclerViewCommerces()
            }
        })
        viewModel.categories.observe(viewLifecycleOwner, Observer { categories ->
            categoriesMutableList = categories as MutableList<String>
            if (this::categoriesMutableList.isInitialized) {
                initRecyclerViewCategories()
            }
        })
        viewModel.commercesByCategory.observe(viewLifecycleOwner, Observer { commerces_list ->
            commercesMutableList = commerces_list as MutableList<CommerceVO>
            if (this::commercesMutableList.isInitialized) {
                binding.totalCommerces.text = commercesMutableList.size.toString()
                initRecyclerViewCommerces()
            }
        })
        activityViewModel.distance.observe(viewLifecycleOwner, Observer { distance ->
            filterCommercesByDistance(distance)
        })
        viewModel.commercesByDistance.observe(viewLifecycleOwner, Observer { commerces_list ->
            commercesMutableList = commerces_list as MutableList<CommerceVO>
            if (this::commercesMutableList.isInitialized) {
                binding.tvErrorDistance.isVisible = false
                binding.totalCommerces.text = commercesMutableList.size.toString()
                binding.nearestCommerces.text = commercesMutableList.size.toString()
                initRecyclerViewCommerces()
            }
        })
        // loading
        viewModel.isLoading.observe(viewLifecycleOwner, Observer { visibility ->
            binding.rvCommerces.isVisible = !visibility
            binding.totalCommerces.isVisible = !visibility
            binding.nearestCommerces.isVisible = !visibility
            binding.progressbarApp.root.isVisible = visibility
            binding.progressBarTotal.root.isVisible = visibility
            binding.progressBarTotal.tvLoading.isVisible = false
            binding.progressBarTotalNearest.root.isVisible = visibility
            binding.progressBarTotalNearest.tvLoading.isVisible = false
        })
        // error
        viewModel.error.observe(viewLifecycleOwner, Observer { error ->
            if (error.equals("Listado por cercanía vacío")) {
                binding.nearestCommerces.text = "0"
                binding.tvErrorDistance.isVisible = true
            } else {
                binding.cardviewCommerces.isVisible = false
                binding.cardviewDistance.isVisible = false
                binding.rvCategories.isVisible = false
                binding.rvCommerces.isVisible = false
                if (this::categoriesMutableList.isInitialized) {
                    commercesMutableList.clear()
                    commercesAdapter.notifyDataSetChanged()
                }
                binding.tvError.isVisible = true
                binding.tvError.text = error
                binding.tvError.setOnClickListener {
                    startActivity(Intent(Settings.ACTION_WIFI_SETTINGS))
                }
            }
        })

        binding.cardviewDistance.setOnClickListener {
            onCardDistanceClick()
        }
    }

    // Recyclers
    private fun initRecyclerViewCategories() {
        val layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.rvCategories.layoutManager = layoutManager
        val categoriesAdapter = CategoriesAdapter(categoriesMutableList) { category ->
            filterCommercesByCategory(category ?: getString(R.string.category_food))
        }
        binding.rvCategories.adapter = categoriesAdapter
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun initRecyclerViewCommerces() {
        binding.tvErrorDistance.isVisible = false
        binding.rvCommerces.layoutManager = LinearLayoutManager(requireContext())
        commercesAdapter = CommerceAdapter(commercesMutableList) { commerce ->
            findNavController().navigate(
                CommercesListFragmentDirections
                    .actionCommercesListFragmentToCommerceDetailFragment(
                        commerce.toDetail(),
                        latitude, longitude
                    )
            )
        }
        binding.rvCommerces.adapter = commercesAdapter
        commercesAdapter.notifyDataSetChanged()
    }

    private fun onCardDistanceClick() {
        val newFragment = DistanceDialog()
        newFragment.show(requireActivity().supportFragmentManager, "distanceDialog")
    }

    private fun filterCommercesByDistance(distanceSelected: Int) {
        if (!this::commercesMutableList.isInitialized) {
            initRecyclerViewCommerces()
        } else {
            binding.tvCommercesDistance.text =
                getString(R.string.commerces_minus_of, (distanceSelected / 1000).toString())
            viewModel.getCommercesByDistance(
                distanceSelected, LatLng(latitude.toDouble(), longitude.toDouble())
            )
        }
    }

    private fun filterCommercesByCategory(category: String) {
        if (!this::commercesMutableList.isInitialized) {
            initRecyclerViewCommerces()
        } else {
            viewModel.getCommercesByCategory(category)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}