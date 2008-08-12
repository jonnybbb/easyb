class Story {

   String title

   String description

   User user
   Family family

   static belongsTo = [User,Family]

   static hasMany = [ scenarios: Scenario, scripts: Script]

   static constraints = {
      title(maxsize:64, blank:false)
      description(maxsize:2048, blank:true)
   }

}
