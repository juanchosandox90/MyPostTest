package com.zemogaposttest.juansandoval.zemogaposttest.model

import org.junit.Assert
import org.junit.Before
import org.junit.Test


class UserItemMapperTest {

    private lateinit var mapper: UserItemMapper

    @Before
    fun setUp() {
        mapper = UserItemMapper(addressItemMapper = AddressItemMapper(LatLngMapper()), companyItemMapper = CompanyItemMapper())
    }

    @Test
    fun `map domain to presentation`() {
        // given
        val user = com.zemogaposttest.juansandoval.zemogaposttest.createUser()

        // when
        val userItem = mapper.mapToPresentation(user)

        // then
        Assert.assertTrue(userItem.id == user.id)
        Assert.assertTrue(userItem.name == user.name)
        Assert.assertTrue(userItem.username == user.username)
        Assert.assertTrue(userItem.email == user.email)
        Assert.assertTrue(userItem.phone == user.phone)
        Assert.assertTrue(userItem.website == user.website)

        Assert.assertTrue(userItem.addressItem.street == user.address.street)
        Assert.assertTrue(userItem.addressItem.suite == user.address.suite)
        Assert.assertTrue(userItem.addressItem.city == user.address.city)
        Assert.assertTrue(userItem.addressItem.zipcode == user.address.zipcode)

        Assert.assertTrue(userItem.addressItem.latLng.latitude.toString() == user.address.geo.lat)
        Assert.assertTrue(userItem.addressItem.latLng.longitude.toString() == user.address.geo.lng)

        Assert.assertTrue(userItem.companyItem.name == user.name)
        Assert.assertTrue(userItem.companyItem.catchPhrase == user.company.catchPhrase)
        Assert.assertTrue(userItem.companyItem.bs == user.company.bs)
    }
}