package ru.flowernetes.orchestration

import io.fabric8.kubernetes.api.model.batch.Job
import ru.flowernetes.orchestration.api.domain.entity.JobLabelKeys
import ru.flowernetes.orchestration.api.domain.entity.NoSuchJobLabelException

fun Job.getLabel(jobLabelKey: JobLabelKeys): String {
    return metadata.labels[jobLabelKey.name] ?: throw NoSuchJobLabelException(toString(), jobLabelKey)
}

fun checkJobHasRequiredLabels(job: Job): Boolean {
    val labels = job.metadata.labels
    return labels.containsKey(JobLabelKeys.WORKLOAD_ID.name)
}