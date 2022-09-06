package website.italojar.klikincommerces.presentation.commerces_list

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import website.italojar.klikincommerces.R
import website.italojar.klikincommerces.databinding.FragmentCommercesListBinding

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
                Log.i(":::", commerceDto.name)
            }
            binding.textViewA.text = commerces_list.size.toString() + "Comercios"
            binding.textViewA.setOnClickListener {
                findNavController().navigate(CommercesListFragmentDirections.actionCommercesListFragmentToCommerceDetailFragment())
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}