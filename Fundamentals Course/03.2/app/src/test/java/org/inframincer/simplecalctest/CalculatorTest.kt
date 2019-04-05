package org.inframincer.simplecalctest

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.offset
import org.inframincer.simplecalctest.Calculator.Operator.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class CalculatorTest {

    @Before
    fun setup() {

    }

    @Test
    fun addTwoNumbers() {
        val resultAdd = Calculator.compute(ADD, 1.0, 1.0)
        assertThat(resultAdd).isEqualTo(2.0)
    }

    @Test
    fun addTwoNumbersNegative() {
        val resultAdd = Calculator.compute(ADD, -1.0, 2.0)
        assertThat(resultAdd).isEqualTo(1.0)
    }

    @Test
    fun addTwoNumbersFloats() {
        val resultAdd = Calculator.compute(ADD, 1.111f.toDouble(), 1.111)
        assertThat(resultAdd).isCloseTo(2.222, offset(0.01))
    }

    @Test
    fun subTwoNumbers() {
        val resultSub = Calculator.compute(SUB, 1.0, 1.0)
        assertThat(resultSub).isEqualTo(0.0)
    }

    @Test
    fun subWorksWithNegativeResult() {
        val resultSub = Calculator.compute(SUB, 1.0, 17.0)
        assertThat(resultSub).isEqualTo(-16.0)
    }

    @Test
    fun mulTwoNumbers() {
        val resultMul = Calculator.compute(MUL, 32.0, 2.0)
        assertThat(resultMul).isEqualTo(64.0)
    }

    @Test
    fun divTwoNumbers() {
        val resultDiv = Calculator.compute(DIV, 32.0, 2.0)
        assertThat(resultDiv).isEqualTo(16.0)
    }

    @Test
    fun divTwoNumbersZero() {
        val resultDiv = Calculator.compute(DIV, 32.0, 0.0)
        assertThat(resultDiv).isEqualTo(Double.POSITIVE_INFINITY)
    }
}
