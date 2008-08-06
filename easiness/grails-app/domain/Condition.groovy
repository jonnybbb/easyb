class Condition  {

   String text
   String code
   int    sortOrder

   Scenario scenario
   
   static belongsTo = Scenario


   static constraints = {
      code(nullable:true)
   }
   
}
