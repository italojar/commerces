package website.italojar.klikincommerces.presentation.commerce_detail

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import website.italojar.klikincommerces.R
import website.italojar.klikincommerces.databinding.FragmentCommerceDetailBinding
import website.italojar.klikincommerces.presentation.interfaces.IFragmentsListener


class CommerceDetailFragment : Fragment(), OnMapReadyCallback {

    private var _binding: FragmentCommerceDetailBinding? = null
    private val binding get() = _binding!!

    private lateinit var map: GoogleMap
    private var mContext: IFragmentsListener? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentCommerceDetailBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mContext?.setToolbarTitle("Ricardo Jaramillo")
    }

    private fun createMap() {
        val mapFragment: SupportMapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is IFragmentsListener)
            mContext = context
        else
            throw RuntimeException(context.toString() + getString(R.string.implement_listener))
    }

    override fun onDetach() {
        super.onDetach()
        mContext = null
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}