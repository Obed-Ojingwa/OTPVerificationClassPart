fun main(args:Array<String>){

    // class object or instance of the class
    val myHouse =  Person("Obed", 22)

    myHouse.openDoor()
    println(myHouse.message)

}

class Person (name:String, age:Int) {


    // class properties
    val message = "The house is  owned by me and built by experts in the field"


    // class method
    fun openDoor(){
        println("The door is opened ")

    }
}