/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lab9;

import lab9.MyMap.*;

/**
 *
 * @author serg
 */
public class Lab9 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        //Тесты для хэш мэп
        /*
         MyHashMap A = new MyHashMap(5);
         A.put("name", "serg");
         A.put("surname", "chornozhuk");
         A.put("nationality", "Ukraine");
         System.out.println(A);
         A.put("age", 20);
         System.out.println(A);
         System.out.println(A.containsKey("ag"));
         System.out.println(A.containsValue("serg"));
         System.out.println(A.isEmpty());
         A.remove("surname");
         System.out.println(A);
         A.put("nationality", "Ukraine");
         System.out.println(A);
         System.out.println(A.get("nationality"));
         A.clear();
         System.out.println(A);
         A.put("name", "David");
         System.out.println(A);
         */
        //Тэсты для три мэп
        
         MyTreeMap B=new MyTreeMap();
         B.put("name", "Serg");
         B.put("surname", "Chornozhuk");
         B.put("age",20);
         B.put("university", "KNU");
         B.put("faculty", "cybernetics");
         B.put("id", 222);
         System.out.println(B);
         System.out.println(B.containsKey("id"));
         System.out.println(B.containsValue("Serg"));
         System.out.println(B.get("age"));
         System.out.println(B);
         System.out.println(B.remove("surname"));
         System.out.println(B);
         System.out.println(B.size());
         System.out.println(B.isEmpty());
         B.clear();
         System.out.println(B);
         System.out.println(B.isEmpty());
         
    }

}
