package yyl.example.basic.io;

import java.util.Scanner;

public class ScannerExample {
    public static void main(String[] args) {
        try (Scanner reader = new Scanner(System.in)) {
            for (;;) {
                System.out.print("Please enter name:");
                String line = reader.nextLine();
                if ("exit".equals(line)) {
                    return;
                }
                System.out.println("hello:" + line);
            }
        }
    }
}
