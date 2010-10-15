import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Query;



using "GAE"


scenario "Creating a simple entity easily", {
  given "a ds instance", {
   ds = DatastoreServiceFactory.getDatastoreService();  
  }
  then "it should be found and contain all relevant datapoints", {
     ds.prepare(new Query("yam")).countEntities().shouldBe 0
     ds.put(new Entity("yam"));
     ds.put(new Entity("yam"));
     ds.prepare(new Query("yam")).countEntities().shouldBe 2
  }
}
