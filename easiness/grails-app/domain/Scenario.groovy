class Scenario {

   String title
   String description

   String setUp
   String tearDown

   String imports

   
   Story  story


   static belongsTo = Story

   static hasMany = [ givens: Given, conditions: Condition, conclusions: Conclusion]

   static constraints = {
      title(unique:true, blank:false, maxsize:128)
      description(maxsize:2048, blank:true)
   }

   static optionals = [ 'setUp', 'tearDown', 'imports', 'description']


   def ordered(String c_type) {

      if (c_type == 'givens') {
         return givens
      }

      if (c_type == 'conditions') {
         return conditions
      }

      if (c_type == 'conclusions') {
         return conclusions
      }

      null

   }

}
