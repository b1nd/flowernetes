package ru.flowernetes.task.domain.usecase

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.stereotype.Component
import ru.flowernetes.entity.task.Conditions
import ru.flowernetes.entity.task.Task
import ru.flowernetes.task.api.domain.entity.TaskHasCyclicDependenciesException
import ru.flowernetes.task.api.domain.usecase.CheckTaskDependenciesHasNoCyclesUseCase
import ru.flowernetes.task.api.domain.usecase.GetTaskByIdUseCase
import ru.flowernetes.task.api.domain.usecase.GetTaskDependencyEdgesUseCase
import ru.flowernetes.task.api.domain.usecase.GetTaskIdsFromConditionUseCase
import java.util.*


@Component
class CheckTaskDependenciesHasNoCyclesUseCaseImpl(
  private val getTaskByIdUseCase: GetTaskByIdUseCase,
  private val getTaskIdsFromConditionUseCase: GetTaskIdsFromConditionUseCase,
  private val getTaskDependencyEdgesUseCase: GetTaskDependencyEdgesUseCase,
  private val objectMapper: ObjectMapper
) : CheckTaskDependenciesHasNoCyclesUseCase {

    override fun exec(task: Task) {
        val dependencies = mutableSetOf(task)
        getAllDependencies(task, dependencies)
        val edges = dependencies.flatMap(getTaskDependencyEdgesUseCase::exec)
        val uniqueIds = dependencies.map { it.id }
        val idToIndex = uniqueIds.withIndex().associate { it.value to it.index }

        val graph = DirectedGraph(uniqueIds.size)
        edges.forEach { graph.addEdge(idToIndex.getValue(it.from), idToIndex.getValue(it.to)) }

        if (graph.isCyclic()) {
            throw TaskHasCyclicDependenciesException(task)
        }
    }

    private fun getAllDependencies(task: Task, dependencies: MutableSet<Task>) {
        val conditions = objectMapper.readValue(task.conditionsJson, Conditions::class.java)
        val logicCondition = conditions.logicCondition ?: return
        val taskDependencies = getTaskIdsFromConditionUseCase.exec(logicCondition)
        val tasks = taskDependencies.map(getTaskByIdUseCase::exec)
        tasks.forEach {
            if (dependencies.add(it)) {
                getAllDependencies(it, dependencies)
            }
        }
    }

    private class DirectedGraph(private val n: Int) {
        private val adjacency: ArrayList<ArrayList<Int>> = ArrayList(n)

        fun addEdge(from: Int, to: Int) {
            adjacency[from].add(to)
        }

        private fun isCyclicUtil(
          i: Int,
          visited: BooleanArray,
          recStack: BooleanArray
        ): Boolean {
            if (recStack[i]) return true
            if (visited[i]) return false

            visited[i] = true
            recStack[i] = true

            val children: List<Int> = adjacency[i]

            for (c in children) {
                if (isCyclicUtil(c, visited, recStack)) {
                    return true
                }
            }
            recStack[i] = false
            return false
        }

        fun isCyclic(): Boolean {
            val visited = BooleanArray(n)
            val recStack = BooleanArray(n)

            repeat(n) {
                if (isCyclicUtil(it, visited, recStack)) {
                    return true
                }
            }
            return false
        }

        init {
            repeat(n) { adjacency.add(ArrayList()) }
        }
    }
}