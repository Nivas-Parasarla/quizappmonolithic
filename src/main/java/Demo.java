import java.util.*;

public class Demo {
    public static void main(String[] args) {
        Integer num1= Integer.valueOf(-129);
        Integer num2=Integer.valueOf(-129);
        int [] nums={1,2,3,4,5};
        SortedMap<Integer,Integer> map= new TreeMap<>();
        map.put(3,2);
        map.put(2,1);
        map.put(4,4);
        for(Map.Entry<Integer,Integer> e:map.entrySet())
            System.out.println(e.getKey()+" "+e.getValue());

        System.out.println(Arrays.stream(Arrays.copyOfRange(nums,0,4)).sum());
        System.out.println(nums);
        if(num1==num2) {
            System.out.println("equals");
        }
        else {
            System.out.println(num1+"not equals"+num2);
        }
    }
}
