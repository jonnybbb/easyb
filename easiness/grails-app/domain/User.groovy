class User {

   String name
   String password
   String passwordConfirmation

   String role
   String families


   static hasMany = [ stories: Story]

   //static belongsTo = Role

   static optionals = [ 'passwordConfirmation' ]

   static constraints = {
      name(size:2..64, blank:false, unique:true)
      role(inList:["author","developer","admin"])
   }


   def isAdmin() {
      role == "admin"
   }

   def hasRole( pRole ) {
      return role == pRole
   }


}
