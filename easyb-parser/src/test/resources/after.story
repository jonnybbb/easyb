def foo

before "do this before each story", {

}

scenario "scenario 1", {
    given "context 1"
    when "event 1"
    then "outcome 1"
}

scenario "scenario 2", {
    given "context 2", {

    }
    when "event 2", {

    }
    then "outcome 2", {

    }
}

after "do this after each story", {

}