package Encryption;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class encryption {
    /* Declaring objects and datatypes */
    private Scanner sc;
    private ArrayList<Character> list;
    private ArrayList<Character> shuffle;
    private char[] letters;
    FileWriter write;
    FileReader read;
    String Line;

    /* Constructor */
    encryption() {
        sc = new Scanner(System.in);
        list = new ArrayList<>();
        shuffle = new ArrayList<>();

        /* Calling the selection method */
        selection();
        GenKey();
    }

    private void selection() {
        while (true) {
            System.out.println(
                    "Press: N to generate New key, G for Get key, E for Encrypt, D for Decrypt, Q for Quit, L to load a key, S to save the shuffled key.");
            char response = Character.toUpperCase(sc.nextLine().charAt(0));

            /* Switch case that takes response as input */
            switch (response) {
                case 'N':
                    GenKey();
                    break;
                case 'G':
                    getKey();
                    break;
                case 'E':
                    encrypt();
                    break;
                case 'D':
                    decrypt();
                    break;
                case 'L':
                    LoadKey();
                    break;
                case 'S':
                    SaveKey();
                    break;
                case 'Q':
                    exit();
                    return;

                default:
                    System.out.println("Invalid option");
                    break;
            }
        }
    }

    /* GenKey method -> Genereates new key */
    private void GenKey() {
        list.clear();
        shuffle.clear();

        /*
         * The for loop below starts at ASCII value 32 i.e, "SPACE" and ends at 126 i.e,
         * "~"
         */
        for (int i = 32; i < 127; i++) {
            list.add((char) i); /* Typecasting the value of int i to char and adding it to the ArrayList list */
        }

        shuffle.addAll(list); /*
                               * Adds all the values stored in the ArrayList "list" to another ArrayList
                               * "shuffle".
                               */
        Collections.shuffle(shuffle); /*
                                       * Collections -> Pre-defined class in java library where shuffle is a method in
                                       * the collection that randomly sets up the character in the ArrayList shuffle.
                                       */
        System.out.println("New key generated");

    }

    private void SaveKey() {
        if (shuffle.isEmpty()) {
            System.out.println("There is no shufled key to save");
            return;
        }
        try {
            FileWriter file = new FileWriter("Saved_Shuffled_Key.txt"); /* Creating the file */
            for (Character x : shuffle) {
                file.write(x); /* Saving the shuffled key from shuffle to the file created above */
            }
            file.close();
            System.out.println("Key is saved");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error saving the key " + e.getMessage());
        }

    }

    private void LoadKey() {
        /*
         * Scanner provides a built-in mechanism to read the file line by line, we
         * basically input filereader object into the scanner object so that we can
         * read
         * the file line by line.
         */
        try (Scanner scan = new Scanner(new FileReader("Saved_Shuffled_Key.txt"))) {
            shuffle.clear();
            System.out.println("Loading key from file...");

            /*
             * scan.hasNextLine is self explanatory in itself, it's basically saying while
             * the scanner has another/next line into the created file do the following.
             */
            while (scan.hasNextLine()) {
                String line = scan.nextLine();
                for (char ch : line.toCharArray()) {
                    shuffle.add(ch);
                }
            }

            /*
             * The code below first clears the list thar contains the ASCII values of 32
             * to
             * 126, then a for loop is initialized that again sort of rebuilds the list
             * and
             * adds the ASCII code to the list again.
             */
            list.clear();
            for (int i = 32; i < 127; i++) {
                list.add((char) i);
            }
            System.out.println("Key loaded successfully.");

        } catch (FileNotFoundException e) {
            System.out.println("Error loading the file: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /* getKey method -> gets the generated key and displays it to the user */
    private void getKey() {
        if (shuffle.isEmpty()) {
            System.out.println("No keys to get");
            System.out.println("Shuffle size: " + shuffle.size());
            return;
        }

        /* prints out the shuffled character */
        System.out.println();
        for (Character x : shuffle) {
            System.out.print(x);
        }
        System.out.println();
    }

    private void encrypt() {
        /*
         * Encryption mechanism -> First the list that contains all the ASCII code is
         * Shuffled, then if the index of characters of the message to be encrypted
         * matches the index of the list that contains ASCII codes the character of
         * that particular index is replaced with the same index of the shuffled
         * character and then the encrypted message is formed.
         */
        if (list.isEmpty() || shuffle.isEmpty()) {
            System.out.println("No keys generated");
            return;
        }

        System.out.println("Enter a message to be encrypted: ");
        String message = sc.nextLine();

        letters = message.toCharArray();

        for (int i = 0; i < letters.length; i++) {
            for (int j = 0; j < list.size(); j++) {
                if (letters[i] == list.get(j)) {
                    letters[i] = shuffle.get(j);
                    break;
                }
            }
        }

        StringBuilder sb = new StringBuilder();
        for (char c : letters) {
            sb.append(c);
        }
        String x = sb.toString();
        System.out.println("Encrypted: " + x);
    }

    private void decrypt() {
        /*
         * Decryption mechanism -> First, the encrypted message is converted into an
         * array of characters from the string. Then, a for loop is initialized which
         * compares each character of the encrypted message with the characters in the
         * shuffled key. The index of the shuffled key is then used to find the
         * corresponding character in the original list that contains the ASCII codes.
         * This mapping is used to replace the characters in the encrypted message with
         * the original ones, thereby decrypting the message.
         */
        if (list.isEmpty() || shuffle.isEmpty()) {
            System.out.println("Key not loaded. Cannot decrypt.");
            return;
        }

        System.out.println("Enter a message to be decrypted: ");
        String message = sc.nextLine();

        letters = message.toCharArray();

        for (int i = 0; i < letters.length; i++) {

            for (int j = 0; j < shuffle.size(); j++) {
                if (letters[i] == shuffle.get(j)) {
                    letters[i] = list.get(j);
                    break;
                }
            }
        }
        System.out.println("Decrypted: ");
        for (char x : letters) {
            System.out.print(x);
        }
        System.out.println();
    }

    private void exit() {
        System.out.println("Exitting the program: ");
        return;
    }

}