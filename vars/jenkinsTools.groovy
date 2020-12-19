
//class JenkinsTools {

    //construction method
    //JenkinsTools(){}

    /**
    * The function is getting list of Jenkins jobs.
    */
    def getJobsList() {
        echo "DEBUG - getJobsList params:[]"
        def jobs_list = []
        Jenkins.instance.getAllItems(AbstractItem.class).each {
            jobs_list.add(it.fullName)
        }
        return jobs_list
    }

    /**
    * The function is getting rootPath of Jenkins slave.
    */
    def getRootPath(node) {
        return Jenkins.instance.getNode(node).getRootPath()
    }

    /**
    * The function is getting list of nodes from particular label.
    */
    def getNodesByLabel(label) {
        def nodes = []
        jenkins.model.Jenkins.instance.computers.each { c ->
            if (c.node.labelString.contains(label)) {
                nodes.add(c.node.selfLabel.name)
            }
        }
        return nodes
    }

   /**
    * The function is deleting Keep Forever builds older than keepDays params
    * @param String keepDays - days to keep "keep forever" builds
    */
    @NonCPS
    def getKeepForeverBuilds(String keepDays="60"){
        echo "DEBUG - getKeepForeverBuilds params: keepDays = ${keepDays}"

        def result_success = []
        def result_failed = []
        Jenkins.instance.getAllItems().findAll { item -> (item instanceof Job) && !(((Job)item).builds.isEmpty()) } .each {
            it.builds.findAll { build -> build.isKeepLog() } .each { keepForeverBuild ->
                def absoluteURL = keepForeverBuild.getAbsoluteUrl()

                use(groovy.time.TimeCategory) {
                    def runningSince = groovy.time.TimeCategory.minus( new Date(), keepForeverBuild.getTime() )

                    if(runningSince.getDays().toInteger() > keepDays.toInteger()) {
                        try {
                            keepForeverBuild.delete()
                            println "Deleting ${absoluteURL} runningSince: ${runningSince} ago"
                            result_success.add("${absoluteURL}")
                        }
                        catch (err) {
                            println "Failed to delete ${absoluteURL} runningSince: ${runningSince} ago"
                            result_failed.add("${absoluteURL}")
                            throw err
                        }
                    }
                }
            }
        }
        return [result_success, result_failed]
    }

//}
