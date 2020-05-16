package ru.flowernetes.task.data.controller

import org.springframework.web.bind.annotation.*
import ru.flowernetes.entity.task.Task
import ru.flowernetes.task.api.domain.dto.TaskDto
import ru.flowernetes.task.api.domain.usecase.*
import ru.flowernetes.task.data.dto.TasksDto

@RestController
@RequestMapping("/tasks")
class TaskController(
  private val addTaskUseCase: AddTaskUseCase,
  private val getTaskByIdUseCase: GetTaskByIdUseCase,
  private val deleteTaskByIdUseCase: DeleteTaskByIdUseCase,
  private val userRunTaskUseCase: UserRunTaskUseCase,
  private val userKillTaskUseCase: UserKillTaskUseCase,
  private val userScheduleTaskUseCase: UserScheduleTaskUseCase,
  private val updateTaskUseCase: UpdateTaskUseCase,
  private val getSessionTasksUseCase: GetSessionTasksUseCase,
  private val userRemoveTaskFromScheduleUseCase: UserRemoveTaskFromScheduleUseCase
) {

    @PutMapping
    fun addTask(@RequestBody taskDto: TaskDto): Task {
        return addTaskUseCase.exec(taskDto)
    }

    @GetMapping("/{id}")
    fun getTask(@PathVariable id: Long): Task {
        return getTaskByIdUseCase.exec(id)
    }

    @PatchMapping("/{id}")
    fun updateTask(@PathVariable id: Long, @RequestBody taskDto: TaskDto): Task {
        return updateTaskUseCase.exec(id, taskDto)
    }

    @GetMapping
    fun getTasks(): TasksDto {
        return TasksDto(getSessionTasksUseCase.exec())
    }

    @DeleteMapping("/{id}")
    fun deleteTask(@PathVariable id: Long) {
        deleteTaskByIdUseCase.exec(id)
    }

    @PostMapping("/{id}/run")
    fun runTask(@PathVariable id: Long) {
        getTaskByIdUseCase.exec(id).also(userRunTaskUseCase::exec)
    }

    @PostMapping("/{id}/kill")
    fun killTask(@PathVariable id: Long) {
        getTaskByIdUseCase.exec(id).also(userKillTaskUseCase::exec)
    }

    @PostMapping("/{id}/schedule")
    fun scheduleTask(@PathVariable id: Long) {
        getTaskByIdUseCase.exec(id).also(userScheduleTaskUseCase::exec)
    }

    @PostMapping("/{id}/unschedule")
    fun unscheduleTask(@PathVariable id: Long) {
        getTaskByIdUseCase.exec(id).also(userRemoveTaskFromScheduleUseCase::exec)
    }
}