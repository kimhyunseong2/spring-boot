package du.ac.kr.sb1010_2;

import du.ac.kr.sb1010_2.chap07.Calculator;

public class ExeTimeCalculator implements Calculator {

    private Calculator delegate;

    public ExeTimeCalculator(Calculator delegate) {
        this.delegate = delegate;
    }

    @Override
    public long factorial(long num) {
        long start = System.nanoTime();
        long result = delegate.factorial(num);
        long end = System.nanoTime();
        System.out.printf("%s.factorial(%d) execution time = %d\n",
                delegate.getClass().getSimpleName(), num, (end - start));
        return result;
    }
}
