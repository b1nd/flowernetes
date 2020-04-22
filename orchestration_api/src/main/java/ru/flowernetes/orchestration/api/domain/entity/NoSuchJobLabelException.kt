package ru.flowernetes.orchestration.api.domain.entity

class NoSuchJobLabelException(job: String, jobLabelKey: JobLabelKeys)
    : NoSuchElementException("Job $job has no $jobLabelKey")