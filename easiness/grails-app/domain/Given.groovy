class Given implements Comparable   {

   String text
   String code
   int    sortOrder

   Scenario scenario
   

   static belongsTo = Scenario


   static constraints = {
      code(nullable:true)
   }

   public int compareTo(other) {
      return sortOrder - other.sortOrder

   }
   
   
}
