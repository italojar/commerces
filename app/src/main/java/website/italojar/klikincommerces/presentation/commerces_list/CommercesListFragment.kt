package website.italojar.klikincommerces.presentation.commerces_list

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import website.italojar.klikincommerces.R
import website.italojar.klikincommerces.databinding.FragmentCommercesListBinding
import website.italojar.klikincommerces.presentation.commerces_list.adapters.categories.CategoriesAdapter
import website.italojar.klikincommerces.data.model.dto.CommerceDto
import website.italojar.klikincommerces.domain.model.Commerce
import website.italojar.klikincommerces.presentation.MainActivity
import website.italojar.klikincommerces.presentation.commerces_list.adapters.commerces.CommerceAdapter
import website.italojar.klikincommerces.presentation.model.CommerceVO
import kotlin.coroutines.suspendCoroutine


@AndroidEntryPoint
class CommercesListFragment : Fragment() {

    private var _binding: FragmentCommercesListBinding? = null
    private val binding get() = _binding!!

    private val viewModel: CommercesListViewModel by viewModels()

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
        viewModel.commerces.observe(viewLifecycleOwner, Observer { commerces_list ->
            binding.totalCommerces.text = commerces_list.size.toString()
            initRecyclerViewCategories(commerces_list.map {
                    commerceDto -> commerceDto.category?: getString(R.string.category_other)
            })
            initRecyclerViewCommerces(commerces_list)
        })
        viewModel.isLoading.observe(viewLifecycleOwner, Observer { visibility ->
            binding.cardviewCommerces.isVisible = !visibility
            binding.cardviewDistance.isVisible = !visibility
            binding.progressBarApp.root.isVisible = visibility
        })
        viewModel.error.observe(viewLifecycleOwner, Observer { error ->
            Toast.makeText(requireContext(), error, Toast.LENGTH_SHORT).show()
        })
    }

    private fun initRecyclerViewCategories(categoriesList: List<String>) {
        val layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.rvCategories.layoutManager = layoutManager
        val categoriesGroup = categoriesList.groupBy { category -> category }
        val listCategoriesGroup = categoriesGroup.mapNotNull { name -> name.key }
        val categoriesAdapter = CategoriesAdapter(listCategoriesGroup) { category ->
            Toast.makeText(requireContext(), category, Toast.LENGTH_LONG).show()
        }
        binding.rvCategories.adapter = categoriesAdapter
    }

    private fun initRecyclerViewCommerces(commercesList: List<CommerceVO>) {
        binding.rvCommerces.layoutManager = LinearLayoutManager(requireContext())
        val commercesAdapter = CommerceAdapter(commercesList) { commerce ->
            findNavController().navigate(CommercesListFragmentDirections.actionCommercesListFragmentToCommerceDetailFragment())
        }
        binding.rvCommerces.adapter = commercesAdapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}