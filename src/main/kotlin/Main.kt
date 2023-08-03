fun main(args: Array<String>) {
    println("Hello World!")

    val speak = StudentClass()
    speak.say()

    // Try adding program arguments via Run/Debug configuration.
    // Learn more about running applications: https://www.jetbrains.com/help/idea/running-applications.html.
    println("Program arguments: ${args.joinToString()}")
}