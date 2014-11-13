package edu.sjsu.cmpe.cache.client;

import java.util.*;

public class Client {

    public static void main(String[] args) throws Exception {
        
	System.out.println("\nStarting Cache Client...\n");
	        
	CacheServiceInterface[] caches;
	caches = new DistributedCacheService [3];

	caches[0] = new DistributedCacheService("http://localhost:3000");
	caches[1] = new DistributedCacheService("http://localhost:3001");
	caches[2] = new DistributedCacheService("http://localhost:3002");
		
	Map<Long, String> keysAndValues = new HashMap<Long, String>();

	keysAndValues.put(new Long(1), "a");
	keysAndValues.put(new Long(2), "b");
	keysAndValues.put(new Long(3), "c");
	keysAndValues.put(new Long(4), "d");
	keysAndValues.put(new Long(5), "e");
	keysAndValues.put(new Long(6), "f");
	keysAndValues.put(new Long(7), "g");
	keysAndValues.put(new Long(8), "h");
	keysAndValues.put(new Long(9), "i");
	keysAndValues.put(new Long(10), "j");

	Set set = keysAndValues.entrySet();
      
      	Iterator iterator = set.iterator();

	/******************************************************************************************************/
	
	/* PUT */
	
	while(iterator.hasNext()) {
        
	  Map.Entry mapentry = (Map.Entry)iterator.next();  
	  int bucket = ConsistentHash.getHash("MD5", (Long)mapentry.getKey(), caches.length);
          caches[bucket].put((Long) mapentry.getKey(), (String) mapentry.getValue());
	}

	/******************************************************************************************************/

	iterator = set.iterator();	
	
	int bucket_0_counter = 0;
	int bucket_1_counter = 0;
	int bucket_2_counter = 0;

	/******************************************************************************************************/

	/* GET(x) */

        while(iterator.hasNext()) {

        Map.Entry mapentry = (Map.Entry)iterator.next();
	int bucket = ConsistentHash.getHash("MD5", (Long)mapentry.getKey(), caches.length);          
	
	if ( bucket == 0 ) { bucket_0_counter++; }
	else if ( bucket == 1 ) { bucket_1_counter++; }
	else  { bucket_2_counter++; }
		
	String getValue = caches[bucket].get((Long) mapentry.getKey());
       
        if (((Long)mapentry.getKey()) < new Long(10))
	{        
	System.out.println( mapentry.getKey() + " => " + getValue + "     Chached at : " + "http://localhost:300" + bucket);
	}	
	else	 
	{	
	System.out.println( mapentry.getKey() + " => " + getValue + "    Chached at : " + "http://localhost:300" + bucket);
	} 
	 	 
       }

       /******************************************************************************************************/
	
	System.out.println("\n\nBucketwise counts :\n\nBucket 0 : " + bucket_0_counter );
        System.out.println("\nBucket 1 : " + bucket_1_counter );
        System.out.println("\nBucket 2 : " + bucket_2_counter + "\n");
    }


}
       /******************************************************************************************************/

/*	cache.put(1, "foo");
        System.out.println("put(1 => foo)");
        
//	String value = cache.get(1);
 //       System.out.println("get(1) => " + value);

	cache.put(2, "hello");
        System.out.println("put(1 => hello)");

       // String value2 = cache.get(2);
//        System.out.println("get(2) => " + value2);

	
        System.out.println("Existing Cache Client...");
    }
*/

