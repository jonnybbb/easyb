class Story {

   String title

   String description

   User user

   static belongsTo = User

   static hasMany = [ scenarios: Scenario, scripts: Script]

   static constraints = {
      title(maxsize:64, blank:false)
      description(maxsize:2048, blank:true)
   }

}
