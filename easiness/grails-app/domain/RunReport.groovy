class RunReport {

   int success
   int failures
   int pending
   int total

   String[] failed_scenarios
   HashMap  failure_reasons

   
   Date create_dt = new Date()


   Story story


   static belongsTo = Story





}
