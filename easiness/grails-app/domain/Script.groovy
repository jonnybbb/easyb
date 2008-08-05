class Script {

   String title
   String type
   String code


   static belongsTo = Story


   static constraints = {
      type(inList:[".bat",".sh",".groovy"])
   }

}
