package com.openlab.openapi_platform.service

import com.openlab.openapi_platform.openapi.generated.model.APISpec
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest()
class ApiSpecSVCTest(@Autowired private val apiSpecSVC: ApiSpecSVC) {

    @Test
    fun createApiSpec() {
        val entity = APISpec()
        val result = apiSpecSVC.createApiSpec(entity)
        assertEquals(entity, result)
    }
}