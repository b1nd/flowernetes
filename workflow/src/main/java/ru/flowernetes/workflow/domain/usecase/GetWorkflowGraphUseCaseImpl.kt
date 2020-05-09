package ru.flowernetes.workflow.domain.usecase

import org.springframework.stereotype.Component
import ru.flowernetes.entity.task.Task
import ru.flowernetes.entity.workflow.Graph
import ru.flowernetes.entity.workflow.Node
import ru.flowernetes.entity.workflow.NodeType
import ru.flowernetes.entity.workflow.Workflow
import ru.flowernetes.monitoring.api.domain.usecase.GetTaskStatusInfoUseCase
import ru.flowernetes.task.api.domain.usecase.GetAllTasksByWorkflowUseCase
import ru.flowernetes.task.api.domain.usecase.GetTaskByIdUseCase
import ru.flowernetes.task.api.domain.usecase.GetTaskDependencyEdgesUseCase
import ru.flowernetes.workflow.api.domain.usecase.GetWorkflowGraphUseCase
import java.util.*


@Component
class GetWorkflowGraphUseCaseImpl(
  private val getAllTasksByWorkflowUseCase: GetAllTasksByWorkflowUseCase,
  private val getTaskDependencyEdgesUseCase: GetTaskDependencyEdgesUseCase,
  private val getTaskByIdUseCase: GetTaskByIdUseCase,
  private val getTaskStatusInfoUseCase: GetTaskStatusInfoUseCase
) : GetWorkflowGraphUseCase {

    override fun exec(workflow: Workflow): Graph<Task, Long> {
        val workflowTasks = getAllTasksByWorkflowUseCase.exec(workflow).associateBy { it.id }
        val edges = workflowTasks.values.flatMap(getTaskDependencyEdgesUseCase::exec)
        val uniqueIds = edges.flatMap { listOf(it.from, it.to) }.distinct()
        val idToIndex = uniqueIds.withIndex().associate { it.value to it.index }
        val indexToId = idToIndex.entries.associate { (k, v) -> v to k }

        val graph = DirectedTopologicalGraph(uniqueIds.size)
        edges.forEach { graph.addEdge(idToIndex.getValue(it.from), idToIndex.getValue(it.to)) }

        val orderedIndexes = graph.topologicalSort()
        val nodes = orderedIndexes.map(indexToId::getValue).map {
            if (workflowTasks.containsKey(it)) {
                Node(workflowTasks.getValue(it), NodeType.INNER)
            } else {
                Node(getTaskByIdUseCase.exec(it), NodeType.OUTER)
            }
        }
        val statuses = nodes.map { getTaskStatusInfoUseCase.exec(it.value) }

        return Graph(
          nodes = nodes,
          edges = edges,
          statuses = statuses
        )
    }

    private class DirectedTopologicalGraph(private val n: Int) {

        private val adjacency: ArrayList<ArrayList<Int>> = ArrayList(n)

        fun addEdge(from: Int, to: Int) {
            adjacency[from].add(to)
        }

        fun topologicalSortUtil(v: Int, visited: BooleanArray, deque: Deque<Int>) {
            visited[v] = true

            adjacency[v].forEach {
                if (!visited[it]) {
                    topologicalSortUtil(it, visited, deque)
                }
            }
            deque.push(v)
        }

        fun topologicalSort(): Iterable<Int> {
            val deque = ArrayDeque<Int>(n)
            val visited = BooleanArray(n) { false }

            repeat(n) {
                if (!visited[it]) {
                    topologicalSortUtil(it, visited, deque)
                }
            }
            return deque
        }

        init {
            repeat(n) { adjacency.add(ArrayList()) }
        }
    }
}