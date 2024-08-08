package com.example.security.auth.controllers

import com.example.security.utilities.constants.AppConstant
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping(AppConstant.MAIN_PATH + "/user")
class UserController {
    @GetMapping
    fun sayHi(): String {
        return "hello"
    }

}