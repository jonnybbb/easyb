
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
      description(maxsize:2048, blank:true, nullable:true)
      setUp(nullable:true)
      tearDown(nullable:true)
      imports(nullable:true)
   }

   static optionals = [ "setUp", "tearDown", "description", "imports"]


   def ordered(String c_type) {

      if (c_type == 'givens') {
         return sort_clauses(givens)
      }

      if (c_type == 'conditions') {
         return sort_clauses(conditions)
      }

      if (c_type == 'conclusions') {
         return sort_clauses(conclusions)
      }

      null

   }


   def sort_clauses( arr ) {

      def tmp = []

      arr.each{ tmp << it}

      Collections.sort(tmp)

      return tmp
      

   }

}
