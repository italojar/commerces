package website.italojar.klikincommerces.presentation.commerces_list

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
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import website.italojar.klikincommerces.databinding.FragmentCommercesListBinding
import website.italojar.klikincommerces.presentation.commerces_list.adapters.categories.CategoriesAdapter

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
            commerces_list.forEach { commerceDto ->
                Log.i(":::", commerceDto.name + " " + commerceDto.category)
            }
            binding.totalCommerces.text = commerces_list.size.toString()
            initRecyclerViewCategories(commerces_list.map { commerceDto -> commerceDto.category })

        })
        viewModel.isLoading.observe(viewLifecycleOwner, Observer { visibility ->
            binding.cardviewCommerces.isVisible = !visibility
            binding.cardviewDistance.isVisible = !visibility
            binding.progressBarApp.root.isVisible = visibility
        })
    }

    private fun initRecyclerViewCategories(listCategories: List<String>) {
        val layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.rvCategories.layoutManager = layoutManager
        val categoriesGroup = listCategories.groupBy { category -> category }
        val categoriesList = categoriesGroup.mapNotNull { name -> name.key }
        val categoriesAdapter = CategoriesAdapter(categoriesList) { category ->
            Toast.makeText(requireContext(), category, Toast.LENGTH_LONG).show()
        }
        binding.rvCategories.adapter = categoriesAdapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}