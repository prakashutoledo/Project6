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
        stdin = new Scanner(System.in);
        entryList = readInventory("inventory.txt", entryList);
        int num_entries = getEntryCount(entryList);
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
        for (Entry entry: entryList) {
            if (entry == null) {
                break;
            } else  {
                count++;
            }
        }
        return count;
    }
}
