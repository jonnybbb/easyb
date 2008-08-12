class Family {

   String name   // the user-friendly name
   String code   // the url-friendly execution code url.                          

   static belongsTo = [ User ]

   static hasMany = [ stories: Story ]

   static constraints = {
      name(size:2..64, blank:false, unique:true)
      code(size:2..32, blank:false, unique:true)
   }


   static Family getDefault() {

      def fam = Family.findByName("Default")

      if (fam == null) {
         fam = new Family()
         fam.name = "Default"
         fam.code = "default"

         fam.save()
      }

      return fam

   }

}
