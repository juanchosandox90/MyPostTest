package com.zemogaposttest.juansandoval.zemogaposttest.userdetails

import android.arch.lifecycle.ViewModelProvider
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.android.gms.maps.model.MarkerOptions
import com.zemogaposttest.juansandoval.zemogaposttest.R
import com.zemogaposttest.juansandoval.zemogaposttest.getAppInjector
import com.zemogaposttest.juansandoval.zemogaposttest.model.USER_ID_KEY
import com.zemogaposttest.juansandoval.zemogaposttest.model.UserItem
import com.zemogaposttest.juansandoval.zemogaposttest.observe
import com.zemogaposttest.juansandoval.zemogaposttest.withViewModel
import kotlinx.android.synthetic.main.activity_user_details.*
import javax.inject.Inject


class UserDetailsActivity : AppCompatActivity(), OnMapReadyCallback {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var map: GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_details)
        getAppInjector().inject(this)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap
        map.setMapStyle(MapStyleOptions.loadRawResourceStyle(this, R.raw.style_json))

        withViewModel<UserDetailsViewModel>(viewModelFactory) {
            userId = intent.getStringExtra(USER_ID_KEY)
            observe(user, ::updateUser)
        }
    }

    private fun updateUser(user: UserItem?) {
        user?.let {
            userName.text = getString(R.string.user_name, it.name)
            userUsername.text = getString(R.string.user_username, it.username)
            userEmail.text = getString(R.string.user_email, it.email)
            userPhone.text = getString(R.string.user_phone, it.phone)
            val address = it.addressItem
            userAddress.text = getString(R.string.user_address, address.street, address.city, address.zipcode)
            userWebsite.text = getString(R.string.user_website, it.website)
            userCompany.text = getString(R.string.user_company, it.companyItem.name)

            val position = it.addressItem.latLng
            map.addMarker(MarkerOptions().position(position).title("@${it.username}"))
            map.moveCamera(CameraUpdateFactory.newLatLng(position))
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}