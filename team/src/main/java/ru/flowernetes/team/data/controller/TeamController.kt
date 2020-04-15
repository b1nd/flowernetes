package ru.flowernetes.team.data.controller

import org.springframework.web.bind.annotation.*
import ru.flowernetes.entity.team.Team
import ru.flowernetes.team.api.domain.dto.TeamDto
import ru.flowernetes.team.api.domain.dto.TeamInfoDto
import ru.flowernetes.team.api.domain.dto.UpdateTeamDto
import ru.flowernetes.team.api.domain.usecase.*
import ru.flowernetes.team.data.dto.AllTeamsInfoDto

@RestController
@RequestMapping("/teams")
class TeamController(
  private val addTeamUseCase: AddTeamUseCase,
  private val getAllTeamsInfoUseCase: GetAllTeamsInfoUseCase,
  private val deleteTeamUseCase: DeleteTeamUseCase,
  private val updateTeamUseCase: UpdateTeamUseCase,
  private val getCallingUserTeamUseCase: GetCallingUserTeamUseCase
) {

    @GetMapping("/session")
    fun callingUserTeam(): Team {
        return getCallingUserTeamUseCase.exec()
    }

    @PutMapping
    fun addTeam(@RequestBody teamDto: TeamDto): TeamInfoDto {
        return addTeamUseCase.exec(teamDto)
    }

    @PatchMapping("/{id}")
    fun updateTeam(@PathVariable id: Long, @RequestBody updateTeamDto: UpdateTeamDto): TeamInfoDto {
        return updateTeamUseCase.exec(id, updateTeamDto)
    }

    @DeleteMapping("/{id}")
    fun deleteTeam(@PathVariable id: Long) {
        deleteTeamUseCase.exec(id)
    }

    @GetMapping
    fun listTeamInfo(): AllTeamsInfoDto {
        return AllTeamsInfoDto(getAllTeamsInfoUseCase.exec())
    }
}