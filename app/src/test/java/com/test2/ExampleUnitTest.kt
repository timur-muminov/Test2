package com.test2

import com.test2.repositories.Mapper
import com.test2.repositories.UserService
import com.test2.repositories.UsersRepository
import com.test2.repositories.UsersRepositoryImpl
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.Assert
import org.junit.Before
import org.junit.Test


class UsersRepositoryTest {

    private lateinit var repository: UsersRepository
    private var service: UserService = mockk()
    private val mapper: Mapper = Mapper()

    @Before
    fun tearDown() {
        repository = UsersRepositoryImpl(service, mapper)
    }

    @Test
    fun `test that repository calls service`() {
        every { service.getUser() } returns MockData.SuccessUserAPI
        repository.getUser()
        verify(exactly = 1) { service.getUser() }
    }

    @Test
    fun `test service returns success and returns data`() {
        every { service.getUser() } returns MockData.SuccessUserAPI
        Assert.assertEquals(MockData.user, repository.getUser())
    }

    @Test(expected = java.lang.NumberFormatException::class)
    fun `test service returns error`() {
        every { service.getUser() } returns MockData.ErrorUserAPI
        repository.getUser()
    }

}

class MapperTest() {

    private val mapper: Mapper = Mapper()

    @Test
    fun `test mapper`() {
        val expected = mapper.mapUser(MockData.SuccessUserAPI)
        val actual = MockData.user
        Assert.assertEquals(expected, actual)
    }

}