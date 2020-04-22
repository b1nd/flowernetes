package ru.flowernetes.orchestration.data.service

import io.fabric8.kubernetes.api.model.batch.Job
import io.fabric8.kubernetes.client.KubernetesClient
import io.fabric8.kubernetes.client.KubernetesClientException
import io.fabric8.kubernetes.client.Watcher
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class JobWatcherService(
  private val kubernetesClient: KubernetesClient,
  private val jobReceiverService: JobReceiverService
) {
    private val log: Logger = LoggerFactory.getLogger(this.javaClass)

    init {
        runWatcher()
    }

    private fun runWatcher() {
        kubernetesClient.batch().jobs().watch(object : Watcher<Job> {
            override fun onClose(cause: KubernetesClientException) {
                log.error("Cannot connect to kubernetes, reconnecting in 10 seconds...", cause)
                Thread.sleep(10000)
                runWatcher()
            }

            override fun eventReceived(action: Watcher.Action, resource: Job) {
                jobReceiverService.receive(action, resource)
            }
        })
    }
}