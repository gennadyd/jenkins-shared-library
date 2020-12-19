
// class JenkinsTools {

//     //construction method
//     JenkinsTools(){}

//     /**
//     * The function is getting list of Jenkins jobs.
//     */
    def getJobsList() {
        def jobs_list = []
        Jenkins.instance.getAllItems(AbstractItem.class).each {
            jobs_list.add(it.fullName)
        }
        return jobs_list
    }

// }
