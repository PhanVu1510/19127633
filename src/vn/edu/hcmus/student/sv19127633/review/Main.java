package vn.edu.hcmus.student.sv19127633.review;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.Set;

public class Main {

    public static void main(String[] args) throws IOException {
        BufferedReader in=new BufferedReader(new InputStreamReader(System.in));
        ToChucDulieu map=new ToChucDulieu("slang.txt",true);

        long start = System.currentTimeMillis();

/*        ArrayList<String> strings=map.findSlang("Cam for Cam");
        for(int i=0;i<strings.size();i++)
            System.out.println(strings.get(i));*/

        long end = System.currentTimeMillis();
        System.out.println("Execute time: "+ (end-start));
    }
}
