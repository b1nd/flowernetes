package ru.flowernetes.task.domain.usecase

import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component
import ru.flowernetes.task.api.domain.entity.NoSuchTaskException
import ru.flowernetes.task.api.domain.usecase.CheckThereAreNoDependenciesOnTaskUseCase
import ru.flowernetes.task.api.domain.usecase.DeleteTaskByIdUseCase
import ru.flowernetes.task.data.repo.DependencyMarkerRepository
import ru.flowernetes.task.data.repo.TaskRepository
import ru.flowernetes.workload.api.domain.usecase.DeleteWorkloadsByTaskUseCase

@Component
class DeleteTaskByIdUseCaseImpl(
  private val taskRepository: TaskRepository,
  private val dependencyMarkerRepository: DependencyMarkerRepository,
  private val deleteWorkloadsByTaskUseCase: DeleteWorkloadsByTaskUseCase,
  private val checkThereAreNoDependenciesOnTaskUseCase: CheckThereAreNoDependenciesOnTaskUseCase
) : DeleteTaskByIdUseCase {

    override fun exec(id: Long) {
        val task = taskRepository.findByIdOrNull(id) ?: throw NoSuchTaskException(id)

        checkThereAreNoDependenciesOnTaskUseCase.exec(task)

        dependencyMarkerRepository.deleteAll(dependencyMarkerRepository.findAllByTask(task))

        deleteWorkloadsByTaskUseCase.exec(task)

        taskRepository.deleteById(id)
    }
}