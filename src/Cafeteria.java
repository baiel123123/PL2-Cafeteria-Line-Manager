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

                case "LEAVE":
                    if(parts.length > 1) leavePerson(parts[1]);
                    break;

                case "PEEK":
                    peekLine();
                    break;

                case "SIZE":
                    printSize();
                    break;

                case "TICK":
                    if(parts.length > 1) {
                        try {
                            int mins = Integer.parseInt(parts[1]);
                            tickTime(mins);
                        } catch (NumberFormatException e) {
                            System.out.println("Ошибка: введите целое число минут");
                        }
                    }
                    break;

                case "STATS":
                    printStats();
                    break;

                case "PRINT":
                    System.out.println(queue);
                    break;

                case "EXIT":
                    return;

                default:
                    System.out.println("Неизвестная команда. Введите HELP.");
            }
        }
    }

    static void showHelp(){
        System.out.println("Commands: ARRIVE, VIP_ARRIVE, SERVE, LEAVE, PEEK, SIZE, TICK, STATS, PRINT, EXIT");
    }

    static void addPerson(String name, boolean vip){
        if(arriveTime.containsKey(name)){
            System.out.println("Name already in system");
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
            System.out.println("No one to serve.");
            return;
        }

        String p = queue.removeFirst();

        int wait = time - arriveTime.get(p);
        arriveTime.remove(p);

        served++;
        totalWait += wait;

        System.out.println("Served " + p + " (waited " + wait + " min).");
    }

    static void leavePerson(String name) {
        if (queue.remove(name)) {
            arriveTime.remove(name);
            System.out.println(name + " left the line.");
        } else {
            System.out.println("Not found");
        }
    }

    static void peekLine() {
        if (queue.isEmpty()) {
            System.out.println("Line is empty.");
        } else {
            System.out.println("Next: " + queue.peekFirst());
        }
    }

    static void printSize() {
        System.out.println("Size: " + queue.size());
    }

    static void tickTime(int mins) {
        if (mins < 0) {
            System.out.println("Error: minutes cannot be negative.");
            return;
        }
        time += mins;
        System.out.println("Time advanced by " + mins + " minutes. Current time = " + time);
    }

    static void printStats() {
        if (served == 0) {
            System.out.println("Served count = 0, Avg wait = 0.00 min.");
        } else {
            double avgWait = (double) totalWait / served;
            System.out.printf("Served count = %d, Avg wait = %.2f min.\n", served, avgWait);
        }
    }
}