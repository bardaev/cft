package com.cft.cfttask

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import com.squareup.picasso.Picasso

class ContactDetailFragment : Fragment() {

    private lateinit var photoDetail: ImageView
    private lateinit var nameDetail: TextView
    private lateinit var phoneDetail: TextView
    private lateinit var emailDetail: TextView
    private lateinit var locationDetail: TextView

    private lateinit var phoneCard: CardView
    private lateinit var emailCard: CardView
    private lateinit var locationCard: CardView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_contact_detail, container, false)
        photoDetail = view.findViewById(R.id.photoDetailContact)
        nameDetail = view.findViewById(R.id.nameDetailContact)
        phoneDetail = view.findViewById(R.id.phoneDetail)
        emailDetail = view.findViewById(R.id.emailDetail)
        locationDetail = view.findViewById(R.id.locationDetail)
        val message = ContactDetailFragmentArgs.fromBundle(requireArguments()).contactItem
        Picasso.get().load(message.picture.large).into(photoDetail)
        nameDetail.text = message.fullName
        phoneDetail.text = message.phone
        emailDetail.text = message.email
        locationDetail.text = "${message.location.country} ${message.location.city} ${message.location.street.name} ${message.location.street.number}"

        phoneCard = view.findViewById(R.id.phoneDetailCard)
        emailCard = view.findViewById(R.id.emailDetailCard)
        locationCard = view.findViewById(R.id.locationDetailCard)

        phoneCard.setOnClickListener {
            startActivity(Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", message.phone, null)))
        }

        emailCard.setOnClickListener {
            startActivity(Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto", message.email, null)))
        }

        locationDetail.setOnClickListener {
            val geoUriString = "geo:${message.location.coordinates.latitude},${message.location.coordinates.longitude}?z=20"
            val geoUri: Uri = Uri.parse(geoUriString)
            val mapIntent = Intent(Intent.ACTION_VIEW, geoUri)
            startActivity(mapIntent)
        }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val menuHost: MenuHost = requireActivity()
        menuHost.invalidateMenu()
        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menu.clear()
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return true
            }
        })
    }

    companion object {

        @JvmStatic
        fun newInstance() = ContactDetailFragment()
    }
}