package edu.sjsu.cmpe.cache.client;

import java.util.ArrayList;
import java.util.List;

import com.google.common.hash.HashCode;
import com.google.common.hash.Hashing;

public class Client {

    public static void main(String[] args) throws Exception {
        System.out.println("Starting Cache Client...");
        /*CacheServiceInterface cache0 = new DistributedCacheService( "http://localhost:3000");
        CacheServiceInterface cache1 = new DistributedCacheService( "http://localhost:3001");
        CacheServiceInterface cache2 = new DistributedCacheService( "http://localhost:3002");
        
        
        
        
        List<CacheServiceInterface> servers1  = new ArrayList<CacheServiceInterface>();
        servers1.add(cache0);
        servers1.add(cache1);
        servers1.add(cache2);
        
        */
        
        List<String> servers  = new ArrayList<String>();
		servers.add("http://localhost:3000");
		servers.add("http://localhost:3001");
		servers.add("http://localhost:3002");

		ConsistentHash<String> nodes = new ConsistentHash<String>(Hashing.md5(), servers);

		//putting values into servers
		
		System.out.println(" Putting the values to 3 servers");
		
		for(long i = 1  ; i < 10 ; i++){
	
		String servername = nodes.get(i);
		
		CacheServiceInterface cachenode = new DistributedCacheService(servername);
		String value = String.valueOf((char)(i+96));
		//System.out.println(" str "+ str);
		cachenode.put(i, value  );
		System.out.println(" Server name " + servername + " key value pair " + i +"=>"+ value );
		}
		
		//get the values
		System.out.println("Getting values from 3 servers");
		
        ConsistentHash<String> test = new ConsistentHash<String>(Hashing.md5(), servers);
        
        for(long i = 1 ; i <10 ; i++){
        String getNodes = test.get(i);
		CacheServiceInterface cachenode = new DistributedCacheService(getNodes);
		
		System.out.println(" Server name " + getNodes + " get value : " + cachenode.get(i));
		//System.out.println(" hello :" + hello);
	}
        
    }

}
