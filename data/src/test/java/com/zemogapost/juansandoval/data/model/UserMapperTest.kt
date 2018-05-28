package com.zemogapost.juansandoval.data.model

import com.zemogapost.juansandoval.data.cache.model.AddressMapper
import com.zemogapost.juansandoval.data.cache.model.CompanyMapper
import com.zemogapost.juansandoval.data.cache.model.GeoMapper
import com.zemogapost.juansandoval.data.cache.model.UserMapper
import com.zemogapost.juansandoval.data.createUser
import com.zemogapost.juansandoval.data.createUserEntity
import org.junit.Assert
import org.junit.Before
import org.junit.Test


class UserMapperTest {

    private lateinit var mapper: UserMapper

    @Before
    fun setUp() {
        mapper = UserMapper(addressMapper = AddressMapper(GeoMapper()), companyMapper = CompanyMapper())
    }

    @Test
    fun `map entity to domain`() {
        // given
        val entity = createUserEntity()

        // when
        val model = mapper.mapToDomain(entity)

        // then
        Assert.assertTrue(model.id == entity.id)
        Assert.assertTrue(model.name == entity.name)
        Assert.assertTrue(model.username == entity.username)
        Assert.assertTrue(model.email == entity.email)
        Assert.assertTrue(model.phone == entity.phone)
        Assert.assertTrue(model.website == entity.website)

        Assert.assertTrue(model.address.street == entity.addressEntity.street)
        Assert.assertTrue(model.address.suite == entity.addressEntity.suite)
        Assert.assertTrue(model.address.city == entity.addressEntity.city)
        Assert.assertTrue(model.address.zipcode == entity.addressEntity.zipcode)

        Assert.assertTrue(model.address.geo.lat == entity.addressEntity.geoEntity.lat)
        Assert.assertTrue(model.address.geo.lng == entity.addressEntity.geoEntity.lng)

        Assert.assertTrue(model.company.name == entity.name)
        Assert.assertTrue(model.company.catchPhrase == entity.companyEntity.catchPhrase)
        Assert.assertTrue(model.company.bs == entity.companyEntity.bs)
    }

    @Test
    fun `map domain to entity`() {
        // given
        val user = createUser()

        // when
        val entity = mapper.mapToEntity(user)

        // then
        Assert.assertTrue(entity.id == user.id)
        Assert.assertTrue(entity.name == user.name)
        Assert.assertTrue(entity.username == user.username)
        Assert.assertTrue(entity.email == user.email)
        Assert.assertTrue(entity.phone == user.phone)
        Assert.assertTrue(entity.website == user.website)

        Assert.assertTrue(entity.addressEntity.street == user.address.street)
        Assert.assertTrue(entity.addressEntity.suite == user.address.suite)
        Assert.assertTrue(entity.addressEntity.city == user.address.city)
        Assert.assertTrue(entity.addressEntity.zipcode == user.address.zipcode)

        Assert.assertTrue(entity.addressEntity.geoEntity.lat == user.address.geo.lat)
        Assert.assertTrue(entity.addressEntity.geoEntity.lng == user.address.geo.lng)

        Assert.assertTrue(entity.companyEntity.name == user.company.name)
        Assert.assertTrue(entity.companyEntity.catchPhrase == user.company.catchPhrase)
        Assert.assertTrue(entity.companyEntity.bs == user.company.bs)
    }
}
