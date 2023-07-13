package com.example.theallclasses

object SharedData {

    //onlinecourseinfo
    var Boardmap: Map<String, Any>? = null
    var JEEmap: Map<String, Any>? = null
    var NEETmap: Map<String, Any>? = null
    var JEE_Advanced: Map<String, Any>? = null
    var teacherTrainingProgram: Map<String,Any>? = null

    // user courses info
    var Mycourses: HashMap<String, HashMap<String,String>>? = null
    var Mycoursesdata: MutableMap<String, Any>? = mutableMapOf()

    var HomeFragmentData: Map<String, Any>? = null
    var LiveFragmentData: Map<String, Any>? = null
    var MaterialFragmentData: Map<String, Any>? = null

    // mode types
    var HomeTuitionData: Map<String, Any>? = null
    var OfflineModeData: Map<String, Any>? = null

    // userinfo
    var uid: String? = ""

    //customercarenumbers
    var customerCare: Map<String, Any>? = null



}