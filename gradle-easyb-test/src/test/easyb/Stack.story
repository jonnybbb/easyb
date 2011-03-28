import foo.Foo

scenario "easyb can and should support expressions", {

 given "a value of 3", {
  value = 3
   new Foo()
 }

 then "an expression should evaluate to true", {
  (value >=0 && value <=6).shouldBe true
 }
}