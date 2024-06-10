package com.openlab.openapi_platform.service

import com.openlab.openapi_platform.entity.ApiSpecEntity
import com.openlab.openapi_platform.exception.ApiSpecErrorCode
import com.openlab.openapi_platform.exception.ApiSpecException
import com.openlab.openapi_platform.openapi.generated.model.APISpec
import com.openlab.openapi_platform.repository.ApiSpecRepository
import org.springframework.transaction.annotation.Transactional

import io.swagger.v3.parser.OpenAPIV3Parser
import io.swagger.v3.parser.core.models.SwaggerParseResult
import org.springframework.stereotype.Service

import java.io.FileInputStream
import java.io.InputStream

import org.springframework.cache.annotation.CacheEvict
import org.springframework.cache.annotation.Cacheable
/* api 생성 로직 -> cashing 된 유저 정보 획득 -> 벨리데이션 -> api spec invaild 검증 -> api spec 메타 정보 db 저장 + cashing -> kafka에 event 던짐 (to file manager -삭제/동기화) */

@Service
class ApiSpecSVC(
    private val repository: ApiSpecRepository
) {
    @Transactional
    public fun createApiSpecMeta(apiSpec: APISpec): APISpec {
            return convertEntityToResponse(repository.save(convertRequestToEntity(apiSpec)))
            /* Check Get File*/

            /* Validation Spec */
            if (!isValidateOpenAPISpec(getTextFromFile(apiSpec.apiSpecFile.fileName))) throw ApiSpecException(ApiSpecErrorCode.INVALID_SPEC, "")

    }

    @Cacheable(value = ["openAPISpec"], key = "#id")
    public fun getSpecById(id: Long): OpenAPISpec? {
        return repository.findById(id).orElse(null)
    }

    @Transactional
    @CacheEvict(value = ["openAPISpec"], key = "#id")
    fun updateSpec(id: Long, name: String, spec: String): OpenAPISpec? {
        val openAPISpec = repository.findById(id).orElse(null) ?: return null
        openAPISpec.name = name
        openAPISpec.spec = spec
        return repository.save(openAPISpec)
    }

    @Transactional
    @CacheEvict(value = ["openAPISpec"], key = "#id")
    fun deleteSpec(id: Long) {
        repository.deleteById(id)
    }

    private fun isValidateOpenAPISpec(spec: String): Boolean {
        val result: SwaggerParseResult = OpenAPIV3Parser().readContents(spec, null, null)
        return result.messages.isEmpty()
    }

    private fun getTextFromFile(filePath: String): String {
        val inputStream: InputStream = FileInputStream(filePath)
        return inputStream.readBytes().toString(Charsets.UTF_8)
    }

    private fun convertEntityToResponse(entity: ApiSpecEntity): APISpec {
        return APISpec(specname = entity.specname)
    }

    private fun convertRequestToEntity(apiSpec: APISpec): ApiSpecEntity {
        return ApiSpecEntity(specname = apiSpec.specname)
    }
}