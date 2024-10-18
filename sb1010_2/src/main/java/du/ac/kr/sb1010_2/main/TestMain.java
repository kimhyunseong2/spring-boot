package du.ac.kr.sb1010_2.main;

import du.ac.kr.sb1010_2.chap07.Calculator;
import du.ac.kr.sb1010_2.chap07.ImpeCalculator;
import du.ac.kr.sb1010_2.chap07.RecCalculator;

public class TestMain {
    public static void main(String[] args) {
        Calculator cal  = new ImpeCalculator();
        System.out.println("ImpeCalculator() : " + cal.factorial(5));

        Calculator cal2 = new RecCalculator();
        System.out.println("RecCalculator() : " + cal2.factorial(5));


    }
}
