job("Code quality check"){
    startOn {
        gitPush {
            branchFilter {
                +"refs/head/master"
                +"refs/head/main"
            }
        }
    }
    container(displayName = "Build bot task", image = "gradle:7.0.0-jdk8") {
        shellScript {
            content = """
	            gradle task buildBotJar
            """
        }
    }
}