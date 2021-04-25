job("Code quality check"){
    startOn {
        gitPush {
            branchFilter {
                +"refs/head/master"
                +"refs/head/main"
            }
        }
    }
    container(displayName = "Run mvn install", image = "maven:latest") {
        shellScript {
            content = """
	            mvn clean package
            """
        }
    }
}