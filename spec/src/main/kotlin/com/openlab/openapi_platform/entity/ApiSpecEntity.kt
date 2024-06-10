package com.openlab.openapi_platform.entity

import jakarta.persistence.Entity
import jakarta.persistence.*

@Entity
data class ApiSpecEntity(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,
    val specname: String,
    @ManyToOne
    @JoinColumn(name = "project_id")
    val project: Project,
    val specTag: String,
    val stableTag: String,
    val storageType: String,
    @OneToOne(cascade = [CascadeType.ALL])
    @JoinColumn(name = "api_spec_file_id", referencedColumnName = "id")
    val apiSpecFile: APISpecFile,
    @OneToOne(cascade = [CascadeType.ALL])
    @JoinColumn(name = "s3_location_id", referencedColumnName = "id")
    val s3Location: S3?,
    @OneToOne(cascade = [CascadeType.ALL])
    @JoinColumn(name = "github_info_id", referencedColumnName = "id")
    val githubInfo: GitHub?,
    @Embedded
    val audit: Audit
)

@Entity
data class Project(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,
    val projectname: String,
    val projectdesc: String,
    val isActive: Boolean,
    val isDeleted: Boolean,
    @Embedded
    val audit: Audit
)

@Entity
data class APISpecFile(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,
    val filePath: String,
    val fileName: String,
    val fileSize: Long,
    @Embedded
    val audit: Audit
)

@Entity
data class S3(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,
    val name: String,
    val dir: String,
    val filename: String,
    @Embedded
    val audit: Audit
)

@Entity
data class GitHub(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,
    val reponame: String,
    val branchname: String,
    val filename: String,
    @Embedded
    val audit: Audit
)

@Embeddable
data class Audit(
    val createdDate: String,
    val createdBy: String,
    val modifiedDate: String,
    val modifiedBy: String
)