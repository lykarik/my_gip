job = Jenkins.instance.getItemByFullName("<project-name>")
job.builds.each() { 
	build ->
	build.delete()
}

job.updateNextBuildNumber(1)

job = Jenkins.instance.getItemByFullName("<project-name>")
job.builds.each() {
	if(it.result == Result.FAILURE) {
		it.delete()
	}
}
