package ru.flowernetes.auth.data.controller

import org.springframework.web.bind.annotation.*
import ru.flowernetes.auth.api.domain.dto.PasswordDto
import ru.flowernetes.auth.api.domain.usecase.ChangePasswordUseCase

@RestController
@RequestMapping("/users")
class UserController(
  private val changePasswordUseCase: ChangePasswordUseCase
) {

    @PatchMapping("/{username}")
    fun changePassword(@PathVariable username: String, @RequestBody passwordDto: PasswordDto) {
        changePasswordUseCase.execute(username, passwordDto.password)
    }
}