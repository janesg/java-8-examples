package com.devxpress;

import java.util.Arrays;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class GS {
    
    static String[] getTopIP(String[] lines) {

        Map<String, Integer> map = new HashMap<>();
        
        for (String s: lines) {
            String ip = s.substring(0,8);
            
            if (map.containsKey(ip)) {
                map.put(ip, map.get(ip) + 1);
            } else {
                map.put(ip, 1);
            }
        }
        
        int max = 0;
        List<String> results = new ArrayList<>();
        
        for (String s : map.keySet()) {
            int count = map.get(s);
            
            if (count > max) {
                max = count;
                results.clear();
                results.add(s);
            } else if (count == max) {
                results.add(s);
            }
        }
        
        return results.toArray(new String[results.size()]);
    }
    
    static String[] getTopIPUsingStream(String[] lines) {
        
        // Create a map of counts
        Map<String,Long> ipCountMap = 
                Arrays.stream(lines)
                    .map(s -> s.substring(0, 8))
                    .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
                    
        // Sort the count map:
        //      - first, in descending order of value (count)
        //      - second, in ascending order of key (ip)
        Map<String,Long> sortedDescCountMap =
                ipCountMap
                    .entrySet()
                    .stream()
                    .sorted(Map.Entry.<String,Long> 
                                comparingByValue(Comparator.reverseOrder())
                                    .thenComparing(Map.Entry.comparingByKey()))
                    .collect(Collectors.toMap(Map.Entry::getKey, 
                                              Map.Entry::getValue, 
                                              (e1, e2) -> e2,
                                              LinkedHashMap::new));
        
        long max = 0;
        List<String> results = new ArrayList<>();
        
        for (String s : sortedDescCountMap.keySet()) {
            long count = sortedDescCountMap.get(s);
            
            if (results.size() == 0) {
                max = count;
                results.add(s);
            } else if (count == max) {
                results.add(s);
            } else {
                break;
            }
        }
        
        System.out.println("sortedDescCountMap : " + sortedDescCountMap.toString());
        
        return results.toArray(new String[results.size()]);
    }
    
    class MyHashMap<K,V> {
        private static final int NUM_BUCKETS = 16;
        private List<List<MyEntry<K,V>>> buckets;
        
        MyHashMap() {
            buckets = new ArrayList<List<MyEntry<K,V>>>(NUM_BUCKETS);
            
            for (int i = 0; i < NUM_BUCKETS; i++) {
                buckets.add(i, new LinkedList<MyEntry<K,V>>());
            }
        }
        
        void put(K key, V value) {
            int idx = key.hashCode() % NUM_BUCKETS;
            
            List<MyEntry<K,V>> l = buckets.get(idx);
            l.add(new MyEntry<K,V>(key, value));
        }
        
        V get(K key) {
            int idx = key.hashCode() % NUM_BUCKETS;
            
            for (MyEntry<K,V> e : buckets.get(idx)) {
                if (key.equals(e.getKey())) {
                    return e.getValue();
                }
            }
            
            return null;
        }
        
        void printMap() {
            for (int i = 0; i < buckets.size(); i++) {
                List<MyEntry<K,V>> l = buckets.get(i);
                if (l.size() > 0) {
                    System.out.println("Bucket[" + i + "] ->");
                    for (MyEntry<K,V> e : l) {
                        System.out.println("\tKey : " + e.getKey() + " / Value : " + e.getValue());
                    }
                } else {
                    System.out.println("Bucket[" + i + "] is empty !");
                }
            }
        }
        
        
        class MyEntry<K,V> {
            private K key;
            private V value;
            
            MyEntry(K key, V value) {
                this.key = key;
                this.value = value;
            }
            
            K getKey() {
                return this.key;
            }
            
            V getValue() {
                return this.value;
            }
        }
    }
    
}