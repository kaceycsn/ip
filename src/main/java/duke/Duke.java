package duke;

import duke.exceptions.EmptyTaskException;
import duke.exceptions.InvalidCommandException;
import duke.tasks.Deadline;
import duke.tasks.Event;
import duke.tasks.Task;
import duke.tasks.Todo;

import java.util.ArrayList;
import java.util.Scanner;
public class Duke {

    public static void printBorder() {
        String border = "____________________________________________________________ \n";
        System.out.println(border);
    }

    public static void printGreeting(){
        String logo = " ____        _        \n"
                + "|  _ \\ _   _| | _____ \n"
                + "| | | | | | | |/ / _ \\\n"
                + "| |_| | |_| |   <  __/\n"
                + "|____/ \\__,_|_|\\_\\___|\n";
        String border = "____________________________________________________________ \n";
        String greeting = "Hello! I'm duke.Duke\n" + "What can I do for you?\n";
        System.out.println(logo + border+ greeting);
    }

    //prints exit statement
    public static void printExit(){
        String exit = "Bye. Hope to see you again soon! \n";
        System.out.println(exit);
    }


    //add a new to do
    public static void addTodo(ArrayList<Task> tasks, int listCount, String name) throws EmptyTaskException {
        if(name.equals(" ")){
            throw new EmptyTaskException();
        }
        tasks.add(new Todo(name));
//        tasks[listCount] = new Todo(name);
        printBorder();
        System.out.println("added: " + name + "\n");
        printBorder();
    }

    //add a new deadline task
    public static void addDeadline(ArrayList<Task> tasks, int listCount, String name, String deadline){
//        tasks[listCount] = new Deadline(name, deadline);
        tasks.add(new Deadline(name, deadline));
        printBorder();
        System.out.println("added: " + name + "\n");
        printBorder();
    }

    //add a new event
    public static void addEvent(ArrayList<Task> tasks, int listCount, String name, String start, String end){
//        tasks[listCount] = new Event(name, start, end);
        tasks.add(new Event(name, start, end));
        printBorder();
        System.out.println("added: " + name + "\n");
        printBorder();
    }

    //print list of duke.tasks
    public static void printList(ArrayList<Task> tasks){
        printBorder();

        int counter = 1;
        for (Task t : tasks) {
            if (t == null) {
                break;
            } else {
                System.out.println(counter + ". " + t);
                counter++;
            }

        }

        System.out.println("You have " + tasks.size() + " tasks in your list.");
        printBorder();
    }

    //mark task as done
    public static void markTask(ArrayList<Task> tasks, int taskIndex){
//        tasks[taskIndex].setStatus("mark");
        tasks.get(taskIndex).setStatus("mark");
        System.out.println("Nice! I've marked this task as done: \n");
        System.out.println(tasks.get(taskIndex) + "\n");
        printBorder();
    }

    //mark task as undone
    public static void unmarkTask(ArrayList<Task> tasks, int taskIndex){
//        tasks[taskIndex].setStatus("unmark");
        tasks.get(taskIndex).setStatus("unmark");
        System.out.println("OK, I've marked this task as not done yet: \n");
        System.out.println(tasks.get(taskIndex) + "\n");
        printBorder();
    }

//    delete task
    public static void deleteTask(ArrayList<Task> tasks, int taskIndex){
        Task temp = tasks.get(taskIndex);
        tasks.remove(taskIndex);
        System.out.println("Noted. I've removed this task: \n");
        System.out.println(temp);
        System.out.println("Now you have " + tasks.size() + " tasks in the list." );
        printBorder();
    }

    public static void main(String[] args) {

//        Task[] tasks = new Task[100];
        ArrayList<Task> tasks = new ArrayList<>();
        Integer listCount = 0;


        printGreeting();
        printBorder();


        Scanner input = new Scanner(System.in);
        String entry = input.nextLine();



        do {

            String[] userInput = entry.split(" ", 2);

            try {


                switch (userInput[0]) {
                    case "list":
                        printList(tasks);
                        entry = input.nextLine();
                        break;


                    case "mark":
                        Integer index = Integer.parseInt(userInput[1]);
                        try{
                            markTask(tasks, index - 1);
                        } catch(NullPointerException e){
                            System.out.println("Error! Specify a valid task index!");
                            printBorder();
                        }
                        entry = input.nextLine();
                        break;


                    case "unmark":
                        Integer index_um = Integer.parseInt(userInput[1]);
                        try{
                            unmarkTask(tasks, index_um - 1);
                        } catch(NullPointerException e){
                            System.out.println("Error! Specify a valid task index!");
                            printBorder();
                        }
                        entry = input.nextLine();
                        break;

                    case "deadline":
                        try {
                            String[] info = userInput[1].split("/by", 2);
                            addDeadline(tasks, listCount, info[0], info[1]);
                        } catch (ArrayIndexOutOfBoundsException e) {
                            System.out.println("OOPS! The description of task cannot be empty");
                            printBorder();
                        }
                        listCount++;
                        entry = input.nextLine();
                        break;


                    case "todo":
                        try {
                            addTodo(tasks, listCount, userInput[1]);
                        } catch (ArrayIndexOutOfBoundsException e) {
                            System.out.println("OOPS! The description of task cannot be empty");
                            printBorder();
                        } catch (EmptyTaskException e) {
                            e.printErrorMessage();
                            printBorder();
                        }
                        listCount++;
                        entry = input.nextLine();
                        break;

                    case "event":
                        try {
                            String[] info_e = userInput[1].split("/", 3);
                            addEvent(tasks, listCount, info_e[0], info_e[1], info_e[2]);
                        } catch (ArrayIndexOutOfBoundsException e) {
                            System.out.println("OOPS! The description of task cannot be empty");
                            printBorder();
                        }
                        listCount++;
                        entry = input.nextLine();
                        break;

                    case "delete":
                        Integer index_dl = Integer.parseInt(userInput[1]);
                        try {
                            deleteTask(tasks, index_dl - 1);
                        } catch(IndexOutOfBoundsException e){
                            System.out.println("Error! Specify a valid task index!");
                            printBorder();
                        }
                        entry = input.nextLine();
                        break;


                    default:
                        throw new InvalidCommandException();

                }
            } catch(InvalidCommandException e){
                e.printErrorMessage();
                printBorder();
                entry = input.nextLine();
            }
        }while(!entry.equals("bye"));


        printBorder();
        printExit();
        printBorder();
    }
}
