package com.huawei.myself;

import org.junit.jupiter.api.Test;

import java.io.*;

public class InputStreamTest {


    @Test
    public void test() {
        File file = new File("src/main/resources/Abc.txt");

        try {
            InputStream inputStream = new FileInputStream(file);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String firstLine = bufferedReader.readLine();
            System.out.println(firstLine);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
