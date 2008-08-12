class Family {

   String name   // the user-friendly name
   String code   // the url-friendly execution code url.                          

   static hasMany = [ stories: Story ]

   static constraints = {
      name(size:2..64, blank:false, unique:true)
      code(size:2..32, blank:false, unique:true)
   }

}
