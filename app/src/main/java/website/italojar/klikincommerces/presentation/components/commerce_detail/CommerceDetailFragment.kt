package website.italojar.klikincommerces.presentation.components.commerce_detail

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.navigation.fragment.navArgs
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import dagger.hilt.android.AndroidEntryPoint
import website.italojar.klikincommerces.R
import website.italojar.klikincommerces.databinding.FragmentCommerceDetailBinding
import website.italojar.klikincommerces.presentation.interfaces.IFragmentsListener
import website.italojar.klikincommerces.utils.loadImage

@AndroidEntryPoint
class CommerceDetailFragment : Fragment(), OnMapReadyCallback {

    private var _binding: FragmentCommerceDetailBinding? = null
    private val binding get() = _binding!!

    val args: CommerceDetailFragmentArgs by navArgs()
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
        createMapFragment()
        setDataToCommerceDetail()
        navigateToMaps()
    }

    /* Maps */
    private fun createMapFragment() {
        val mapFragment: SupportMapFragment =
            childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap
        createMarker(args.commerceDetail.latitude, args.commerceDetail.longitude)
    }

    private fun createMarker(latitude: Double, longitude: Double) {
        val cordinates = LatLng(latitude, longitude)
        val marker = MarkerOptions().position(cordinates).title(args.commerceDetail.name)
        map.addMarker(marker)
        map.animateCamera(
            CameraUpdateFactory.newLatLngZoom(cordinates, 15f),
            400, null
        )
    }

    private fun navigateToMaps() {
        binding.tvTakeMeHere.setOnClickListener {
            takeMeToMap(Uri
                    .parse("http://maps.google.com/maps?saddr=${args.latitude}, ${args.longitude}" +
                                "&daddr=${args.commerceDetail.latitude}, ${args.commerceDetail.longitude}")
            )
        }
    }

    /* Set Data */
    private fun setDataToCommerceDetail() {
        with(binding) {
            mContext?.setToolbarTitle(args.commerceDetail.name)
            imCommercerImage.loadImage(
                args.commerceDetail.logo ?: getString(R.string.detail_fragment_default_image)
            )
            tvTelephoneDetail.setOnClickListener { dialPhoneNumber(args.commerceDetail.contact) }
            tvStreetDetail.text = args.commerceDetail.address.capitalize()
            tvTelephoneDetail.text = args.commerceDetail.contact
            tvTimetableDetail.text = args.commerceDetail.openingHours
            tvDescriptionDetail.text = args.commerceDetail.description ?: getString(R.string.detail_fragment_come_to_visit_us)
            setCategory(args.commerceDetail.category, binding.imCategoryDetail)
        }
    }

    private fun setCategory(category: String?, img: ImageView) {
        when (category) {
            getString(R.string.category_shopping) -> {
                img.setImageResource(R.drawable.cart_colour)
            }
            getString(R.string.category_food) -> {
                img.setImageResource(R.drawable.catering_colour)
            }
            getString(R.string.category_beauty) -> {
                img.setImageResource(R.drawable.beauty_colour)
            }
            getString(R.string.category_leisure) -> {
                img.setImageResource(R.drawable.leisure_colour)
            }
            getString(R.string.category_other) -> {
                img.setImageResource(R.drawable.truck_colour)
            }
        }
    }

    /* Intents */
    private fun takeMeToMap(geoLocation: Uri) {
        val intent = Intent(Intent.ACTION_VIEW).apply {
            data = geoLocation
        }
        if (intent.resolveActivity(requireActivity().packageManager) != null) {
            startActivity(intent)
        }
    }

    private fun dialPhoneNumber(phoneNumber: String) {
        val intent = Intent(Intent.ACTION_DIAL).apply {
            data = Uri.parse("tel:$phoneNumber")
        }
        if (intent.resolveActivity(requireActivity().packageManager) != null) {
            startActivity(intent)
        }
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