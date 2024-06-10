package com.openlab.openapi_platform.repository

import com.openlab.openapi_platform.entity.ApiSpecEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ApiSpecRepository : JpaRepository<ApiSpecEntity, Long>