package com.openlab.openapi_platform.controller

import com.openlab.openapi_platform.openapi.generated.controller.UsersApi
import com.openlab.openapi_platform.openapi.generated.model.*
import com.openlab.openapi_platform.servcies.UserService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RestController

@RestController
class UserController(
    private val userService: UserService
) : UsersApi{

    override fun createUser(user: User): ResponseEntity<User> {
        val createdUser = userService.create(user)
        return ResponseEntity.ok(createdUser)
    }

    override fun deleteUser(userId: String): ResponseEntity<Boolean> {
        return super.deleteUser(userId)
    }

    override fun getUserInfo(userId: String): ResponseEntity<User> {
        return ResponseEntity.ok(userService.findUserById(userId))
    }

    override fun queryUserList(userQueryParams: UserQuery?): ResponseEntity<UserList> {
        return super.queryUserList(userQueryParams)
    }

    override fun reverifyUser(user: User): ResponseEntity<Boolean> {
        return super.reverifyUser(user)
    }

    override fun updateUserInfo(userId: String, user: User): ResponseEntity<User> {
        return super.updateUserInfo(userId, user)
    }

    override fun userLogin(user: User): ResponseEntity<UserToken> {
        return super.userLogin(user)
    }

    override fun verifyUser(shortUrlPostFix: String): ResponseEntity<Boolean> {
        return super.verifyUser(shortUrlPostFix)
    }
}