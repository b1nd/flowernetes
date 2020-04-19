package ru.flowernetes.task.domain.usecase

import com.google.common.truth.Truth.assertThat
import org.junit.Before
import org.junit.Test
import ru.flowernetes.entity.task.AndCondition
import ru.flowernetes.entity.task.OrCondition
import ru.flowernetes.entity.task.TaskCondition
import ru.flowernetes.entity.task.TimeCondition
import ru.flowernetes.task.api.domain.usecase.GetTaskIdsFromConditionUseCase

class GetTaskIdsFromConditionUseCaseImplTest {

    private lateinit var useCase: GetTaskIdsFromConditionUseCase

    @Before
    fun setUp() {
        useCase = GetTaskIdsFromConditionUseCaseImpl()
    }

    @Test
    fun `should return all dependent task ids in condition`() {
        val taskId1 = 1L
        val taskId2 = 2L
        val taskId3 = 3L

        val condition = OrCondition(listOf(
          AndCondition(listOf(
            TaskCondition(taskId1),
            OrCondition(listOf(
              TaskCondition(taskId2),
              TaskCondition(taskId3)
            )))),
          TimeCondition("1 * * * * *")
        ))

        assertThat(useCase.exec(condition)).isEqualTo(listOf(taskId1, taskId2, taskId3))
    }
}