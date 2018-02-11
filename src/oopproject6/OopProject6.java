package oopproject6;

import entry.Entry;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
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
                case "d":
                    index = getIndex(entryList, command);
                    if (index >= 0) {
                        Entry[] temp = ArrayUtils.remove(entryList, index);
                        for (Entry entry : entryList) {
                            entry = null;
                        }
                        num_entries = num_entries - 1;
                        storeInventory("inventory.txt", temp);
                        entryList = readInventory("inventory.txt", entryList);
                        break;
                    }
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

    public static void storeInventory(String fileName, Entry[] entryList) throws FileNotFoundException {
        PrintStream printFormat = new PrintStream(new File(fileName));
        for (int i = 0; i < num_entries; i++) {
            printFormat.println(entryList[i].name + " " + entryList[i].quantity + " " + entryList[i].notes);
        }

        System.out.println("Inventory stored.");
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
        try {
            String productName = command.substring(2).trim().toLowerCase();
            String option;
            for (int i = 0; i < num_entries; i++) {
                if (entryList[i].name.equalsIgnoreCase(productName)) {
                    System.err.println("Product already exists.");
                    return;
                }
            }
            System.out.print("Enter quantity : ");
            String quantity = stdin.next();
            System.out.println();
            System.out.print("Enter notes : ");
            String notes = stdin.next();
            entryList[num_entries] = new Entry(productName, quantity, notes);
            System.out.println();
            num_entries++;
        } catch (ArrayIndexOutOfBoundsException ex) {
            System.err.println("Entry is full");
            System.exit(0);
        } catch (StringIndexOutOfBoundsException ex) {
            System.err.println("Please enter a valid command");
        }
    }
    private static int getIndex(Entry[] entryList, String command) {
        String productName = command.substring(2).trim();
        if (ArrayUtils.isEmpty(entryList)) {
            return -1;
        } else {
            int index = 0;
            for (int i = 0; i < num_entries; i++) {
                if (entryList[i].name.equalsIgnoreCase(productName) && entryList[i] != null) {
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
        while (command.length() > 8 || command.isEmpty()) {
            System.out.println("Sorry, command must not exceed 8 characters.");
            System.out.print("Enter command :");
            command = stdin.nextLine();
            command = command.trim();
        }
        return command;
    }
}
