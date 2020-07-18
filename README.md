# flowernetes
Python Scripts Workflows on Kubernetes

Manage your workflows for Python and Jupyter Notebook scripts with autodeployment on Kubernetes

## Features
1. Manage teams and their resource quotas
1. Upload scripts and control their versions
1. Create and manage tasks by setting
   * base Docker image
   * running script
   * time deadline
   * cpu and ram usage
   * maximum retries on failure
   * dependencies on other tasks
   * cron scheduling
1. Control workflow execution on DAG with real-time status updates
1. Get tasks workloads and download their logs and artifacts
1. Analyze cpu and ram usage by workflow or team namespace on time interval
1. Analyze tasks duration and average time on specified time interval
1. Analyze tasks overlap and identify bottlenecks on Gantt chart

### Screenshots 
For UI screenshots visit https://github.com/b1nd/flowernetes_web

### Deployment
For system ci/cd deployment visit https://github.com/b1nd/flowernetes_devops
