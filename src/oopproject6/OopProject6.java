package oopproject6;

import entry.Entry;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import org.apache.commons.lang3.ArrayUtils;

public class OopProject6 {

    public static Scanner stdin, reader;
    private static int num_entries;

    public static void main(String[] args) throws FileNotFoundException {
        Entry[] entryList = new Entry[200];
        String command = null;
        String commandCode = null;
        
        stdin = new Scanner(System.in);
        entryList = readInventory("inventory.txt", entryList);
        num_entries = getEntryCount(entryList);
        
        System.out.println("Codes are entered as 1 to 8 characters. ");
        System.out.println("Use \"e\" for enter, \"f\" for find, \"l\" for list, \"q\" for quit. ");
        System.out.println();
        command = getCommand();
        commandCode = command.charAt(0) + "";
        
        while (!command.equalsIgnoreCase("q")) {
            switch (commandCode) {
                case "e":
                    getEntries(command, entryList);
                    break;
                case "f":
                    int index = getIndex(entryList, command);
                    if (index >= 0) {
                        System.out.println("-- " + entryList[index].name);
                        System.out.println("-- " + entryList[index].quantity);
                        System.out.println("-- " + entryList[index].notes);
                    } else if (index == -1) {
                        System.out.println("Entry not found");
                    }
                    break;
                default:
                    System.err.println("Not a valid input");
            }
            command = getCommand();
            commandCode = command.charAt(0) + "";
        }
    }

    private static Entry[] readInventory(String fileName, Entry[] entryList) throws FileNotFoundException {
        File file = new File(fileName);
        reader = new Scanner(file);
        int lineCount = 0;
        while (reader.hasNextLine()) {
            String line = reader.nextLine();
            String[] arr = line.split(" ");
            Entry entry = new Entry(arr[0], arr[1], arr[2]);
            entryList[lineCount] = entry;
            lineCount++;
        }
        return entryList;
    }

    private static int getEntryCount(Entry[] entryList) {
        int count = 0;
        for (Entry entry : entryList) {
            if (entry == null) {
                break;
            } else {
                count++;
            }
        }
        return count;
    }

    private static void getEntries(String command, Entry[] entryList) {
        String productName = command.substring(2).trim().toLowerCase();
        String option;
        try {
            for (int i = 0; i < num_entries; i++) {
                if (entryList[i].name.equalsIgnoreCase(productName)) {
                    System.err.println("Product already exists.");
                    System.out.println();
                    return;
                }
            }
            System.out.println("Enter quantity : ");
            String quantity = stdin.next();
            System.out.println("Enter notes : ");
            String notes = stdin.next();
            entryList[num_entries] = new Entry(productName, quantity, notes);
            num_entries++;
        } catch (ArrayIndexOutOfBoundsException ex) {
            System.err.println("Entry is full");
        }
    }

    private static int getIndex(Entry[] entryList, String command) {
        String productName = command.substring(2).trim();
        if (ArrayUtils.isEmpty(entryList)) {
            return -1;
        } else {
            int index = 0;
            for (int i = 0; i < 200; i++) {
                if (entryList[i].name.equalsIgnoreCase(productName)) {
                    index = i;
                    break;
                } else {
                    index = -1;
                }
            }
            return index;
        }
    }

    private static String getCommand() {
        String command;
        System.out.print("Enter command: ");
        command = stdin.nextLine().toLowerCase();
        command = command.trim();
        System.out.println();
        while (command.length() > 8 || command.isEmpty()) {
            System.out.println("Sorry, command must not exceed 8 characters.");
            System.out.print("Enter command :");
            command = stdin.nextLine();
            command = command.trim();
        }
        return command;
    }
}
