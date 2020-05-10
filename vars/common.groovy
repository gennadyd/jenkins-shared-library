def setDisplayName(displayText){
    println "setDisplayName"
    addShortText(text: displayText, borderColor: '#FF0000')
}



def get_results(param1) {

    if (param1 == "fruit") {
        return ["apple","orange","mango"]
    } else {
        return ["grape","peach","banana"]

    }
}

