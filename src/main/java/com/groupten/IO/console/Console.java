package com.groupten.IO.console;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;

public class Console implements IConsole {

    private InputStream in;
    private PrintStream out;
    private Scanner sc;

    public Console() {
        in = System.in;
        out = System.out;
        sc = new Scanner(in);
    }

    @Override
    public void printLine(Object line) {
        out.println(line);
    }

    @Override
    public String readLine() {
        return sc.nextLine();
    }

    @Override
    public int readInteger() {
        return sc.nextInt();
    }

}
