package day2;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class FileRead {


    // 절대 경로로 파일을 읽어오는 예시 코드
    public static void main(String[] args) {


        String filePath = "C:\\Users\\qoqhs\\read.txt"; // 파일 경로

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                System.out.println(line); // 파일의 각 줄을 출력
            }
        } catch (IOException e) {
            e.printStackTrace(); // 예외 처리
        }
    }
}