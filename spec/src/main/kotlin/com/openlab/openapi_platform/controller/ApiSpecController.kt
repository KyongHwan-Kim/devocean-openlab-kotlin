package com.openlab.openapi_platform.controller

import com.openlab.openapi_platform.openapi.generated.controller.ApisApi
import com.openlab.openapi_platform.openapi.generated.model.APISpec
import com.openlab.openapi_platform.openapi.generated.model.APISpecFile
import com.openlab.openapi_platform.openapi.generated.model.APISpecList
import com.openlab.openapi_platform.openapi.generated.model.APISpecQuery
import com.openlab.openapi_platform.service.ApiSpecSVC
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RestController

@RestController
class ApiSpecController(
    private val apiSpecSVC: ApiSpecSVC
) : ApisApi {
    override fun createApiSpec(apISpec: APISpec): ResponseEntity<APISpec> {
        val result = apiSpecSVC.createApiSpecMeta(apISpec)
        return super.createApiSpec(apISpec)
    }

    override fun queryAPISpecList(apiQueryParams: APISpecQuery?): ResponseEntity<APISpecList> {
        return super.queryAPISpecList(apiQueryParams)
    }

    override fun tagAPISpec(apiId: String, apISpec: APISpec): ResponseEntity<APISpec> {
        return super.tagAPISpec(apiId, apISpec)
    }

    override fun deleteAPISpec(apiId : kotlin.String): ResponseEntity<kotlin.Boolean> {
        return super.deleteAPISpec(apiId)
    }

    override fun uploadAPISpecFile(apiId: kotlin.String, apISpecFile: APISpecFile): ResponseEntity<APISpecFile> {
        return super.uploadAPISpecFile(apiId, apISpecFile)
    }

}