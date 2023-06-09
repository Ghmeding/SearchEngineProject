package searchengine;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class CalculatorTest{
    private static Calculator calculator;

    @BeforeAll static void setUp(){
        calculator = new Calculator();


    }

    @Test void termFrequency_resultCorrespondsToFormula(){
        assertEquals((double) 4/10, calculator.termFrequency(4, 10));
    }

    @Test void inverseFrequency_resultCorrespondsToFormula(){
        assertEquals(Math.log((double) 3 / 5), calculator.inverseFrequency(3, 5));
    }

    @Test void finalFrequency_resultCorrespondsToFormula(){
        assertEquals(((double) 4/10) * Math.log((double) 3 / 5),
            calculator.finalFrequency(4, 10, 3, 5)
        );
    }



}