package com.cft.cfttask

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.cft.cfttask.api.data.ContactItem
import com.squareup.picasso.Picasso

class ListContactsFragment : Fragment() {

    private lateinit var swipe: SwipeRefreshLayout
    private lateinit var contactRecyclerView: RecyclerView
    private lateinit var viewModel: ContactsViewModel
    private lateinit var navController: NavController
    private var contactRecyclerViewAdapter: ContactAdapter = ContactAdapter(arrayListOf())

    companion object {
        fun newInstance() = ListContactsFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this)[ContactsViewModel::class.java]
        viewModel.loadDataFromDB()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = layoutInflater.inflate(R.layout.fragment_list_contact, container, false)

        swipe = view.findViewById(R.id.swipeRefreshContacts)
        contactRecyclerView = view.findViewById(R.id.contact_recycler_view)
        contactRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = contactRecyclerViewAdapter
        }

        swipe.setColorSchemeColors(
            ContextCompat.getColor(requireContext(), android.R.color.holo_blue_bright),
            ContextCompat.getColor(requireContext(), android.R.color.holo_green_light),
            ContextCompat.getColor(requireContext(), android.R.color.holo_orange_light),
            ContextCompat.getColor(requireContext(), android.R.color.holo_red_light)
        )

        viewModel.contacts.observe(this) { countries ->
            countries?.let {
                contactRecyclerViewAdapter.updateContacts(it)
            }
        }
        viewModel.contactsLoadError.observe(this) { error ->
            if (!error.isNullOrEmpty()) {
                Toast.makeText(requireContext(), "Error: $error", Toast.LENGTH_SHORT).show()
            }
        }

        viewModel.loading.observe(this) {
            swipe.isRefreshing = it
        }

        swipe.setOnRefreshListener {
            viewModel.refresh()
        }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
    }

    private inner class ContactHolder(view: View): RecyclerView.ViewHolder(view), View.OnClickListener {

        private lateinit var contact: ContactItem

        private var photoContactImageView: ImageView = view.findViewById(R.id.photoContact)
        private val nameTextView: TextView = view.findViewById(R.id.nameContact)
        private val addressTextView: TextView = view.findViewById(R.id.addressContact)
        private val phoneTextView: TextView = view.findViewById(R.id.phoneContact)

        init {
            itemView.setOnClickListener(this)
        }

        fun bind(contact: ContactItem) {
            this.contact = contact
            Picasso
                .get()
                .load(this.contact.picture.thumbnail)
                .into(photoContactImageView)
            nameTextView.text = this.contact.fullName
            addressTextView.text = this.contact.address
            phoneTextView.text = this.contact.phone

        }

        override fun onClick(v: View?) {
            val action = ListContactsFragmentDirections.actionListContactFragmentToContactDetailFragment(contact)
            navController.navigate(action)
        }
    }

    private inner class ContactAdapter(private val contactItems: ArrayList<ContactItem>): RecyclerView.Adapter<ContactHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactHolder {
            val inflater = LayoutInflater.from(context)
            val view = inflater.inflate(R.layout.list_contact_item, parent, false)
            return ContactHolder(view)
        }

        override fun onBindViewHolder(holder: ContactHolder, position: Int) {
            holder.bind(contactItems[position])
        }

        override fun getItemCount(): Int {
            return contactItems.size
        }

        fun updateContacts(newContacts: List<ContactItem>) {
            contactItems.clear()
            contactItems.addAll(newContacts)
            notifyDataSetChanged()
        }
    }
}