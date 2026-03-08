
import java.util.*;

public class Cafeteria {

    static ArrayDeque<String> queue = new ArrayDeque<>();
    static HashMap<String, Integer> arriveTime = new HashMap<>();

    static int time = 0;
    static int served = 0;
    static long totalWait = 0;

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        showHelp();

        while(true){

            System.out.print("[time " + time + "] > ");
            String input = sc.nextLine().trim();

            if(input.equals("")) continue;

            String[] parts = input.split(" ");
            String cmd = parts[0].toUpperCase();

            switch(cmd){

                case "HELP":
                    showHelp();
                    break;

                case "ARRIVE":
                    if(parts.length > 1) addPerson(parts[1], false);
                    break;

                case "VIP_ARRIVE":
                    if(parts.length > 1) addPerson(parts[1], true);
                    break;

                case "SERVE":
                    servePerson();
                    break;

                case "PRINT":
                    System.out.println(queue);
                    break;

                case "EXIT":
                    return;
            }
        }
    }

    static void showHelp(){
        System.out.println("Commands: ARRIVE, VIP_ARRIVE, SERVE, PRINT, EXIT");
    }

    static void addPerson(String name, boolean vip){

        if(arriveTime.containsKey(name)){
            System.out.println("Already in line");
            return;
        }

        arriveTime.put(name, time);

        if(vip){
            queue.addFirst(name);
        }else{
            queue.addLast(name);
        }
    }

    static void servePerson(){

        if(queue.isEmpty()){
            System.out.println("No one in line");
            return;
        }

        String p = queue.removeFirst();

        int wait = time - arriveTime.get(p);
        arriveTime.remove(p);

        served++;
        totalWait += wait;

        System.out.println("Served " + p + " waited " + wait);
    }
}
