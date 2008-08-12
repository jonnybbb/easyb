class User {

   String name
   String password
   String passwordConfirmation

   String role

   static hasMany = [ stories: Story, families: Family]

   //static belongsTo = Role

   static optionals = [ 'passwordConfirmation' ]

   static constraints = {
      name(size:2..64, blank:false, unique:true)
      role(inList:Role.roleList)
   }


   def isAdmin() {
      role == "admin"
   }

   def hasRole( pRole ) {
      return role == pRole
   }


}
