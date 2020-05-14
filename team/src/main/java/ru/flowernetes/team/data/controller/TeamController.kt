package ru.flowernetes.team.data.controller

import org.springframework.web.bind.annotation.*
import ru.flowernetes.entity.orchestration.Namespace
import ru.flowernetes.entity.team.Team
import ru.flowernetes.orchestration.api.domain.usecase.GetNamespaceByNameOrEmptyUseCase
import ru.flowernetes.team.api.domain.dto.TeamDto
import ru.flowernetes.team.api.domain.dto.TeamInfoDto
import ru.flowernetes.team.api.domain.dto.UpdateTeamDto
import ru.flowernetes.team.api.domain.usecase.*
import ru.flowernetes.team.data.dto.AllTeamsDto
import ru.flowernetes.team.data.dto.AllTeamsInfoDto
import ru.flowernetes.team.data.dto.TeamCpuUsageDto
import ru.flowernetes.team.data.dto.TeamRamUsageDto
import ru.flowernetes.workflow.api.domain.usecase.GetTeamWorkflowsCpuUsageUseCase
import ru.flowernetes.workflow.api.domain.usecase.GetTeamWorkflowsRamUsageUseCase
import java.time.LocalDate

@RestController
@RequestMapping("/teams")
class TeamController(
  private val addTeamUseCase: AddTeamUseCase,
  private val getAllTeamsInfoUseCase: GetAllTeamsInfoUseCase,
  private val deleteTeamUseCase: DeleteTeamUseCase,
  private val updateTeamUseCase: UpdateTeamUseCase,
  private val getTeamByIdUseCase: GetTeamByIdUseCase,
  private val getNamespaceByNameOrEmptyUseCase: GetNamespaceByNameOrEmptyUseCase,
  private val getTeamWorkflowsRamUsageUseCase: GetTeamWorkflowsRamUsageUseCase,
  private val getTeamWorkflowsCpuUsageUseCase: GetTeamWorkflowsCpuUsageUseCase,
  private val getAllTeamsUseCase: GetAllTeamsUseCase,
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

    @GetMapping("/info")
    fun listTeamInfo(): AllTeamsInfoDto {
        return AllTeamsInfoDto(getAllTeamsInfoUseCase.exec())
    }

    @GetMapping
    fun getTeams(): AllTeamsDto {
        return AllTeamsDto(getAllTeamsUseCase.exec())
    }

    @GetMapping("/{id}/namespace")
    fun getTeamNamespace(
      @PathVariable id: Long
    ): Namespace {
        return getTeamByIdUseCase.exec(id).let {
            getNamespaceByNameOrEmptyUseCase.exec(it.namespace)
        }
    }

    @GetMapping("/{id}/cpu")
    fun getTeamCpuUsage(
      @PathVariable id: Long,
      @RequestParam from: LocalDate = LocalDate.now(),
      @RequestParam to: LocalDate = LocalDate.now()
    ): TeamCpuUsageDto = TeamCpuUsageDto(
      getTeamByIdUseCase.exec(id).let {
          getTeamWorkflowsCpuUsageUseCase.exec(it, from, to)
      }
    )

    @GetMapping("/{id}/ram")
    fun getTeamRamUsage(
      @PathVariable id: Long,
      @RequestParam from: LocalDate = LocalDate.now(),
      @RequestParam to: LocalDate = LocalDate.now()
    ): TeamRamUsageDto = TeamRamUsageDto(
      getTeamByIdUseCase.exec(id).let {
          getTeamWorkflowsRamUsageUseCase.exec(it, from, to)
      }
    )
}