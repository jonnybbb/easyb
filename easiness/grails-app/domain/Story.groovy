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

}
