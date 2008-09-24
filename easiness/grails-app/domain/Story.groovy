class Story {

   String title

   String description


   String packageText

   String setUp
   String tearDown

   String imports



   User user
   Family family

   static belongsTo = [User,Family]

   static hasMany = [ scenarios: Scenario, scripts: Script, reports: RunReport ]

   static constraints = {
      title(maxsize:256, blank:false)
      description(maxsize:2048, blank:true)
      packageText(nullable:true)
      setUp(nullable:true)
      tearDown(nullable:true)
      imports(nullable:true)
   }


   def lastReport() {

      def tmp = RunReport.findAll("from RunReport as r where r.story = ? order by r.create_dt DESC", [this], [max:1])

      if (tmp.size > 0) {
         return tmp[0]
      }


      return null



   }

}
