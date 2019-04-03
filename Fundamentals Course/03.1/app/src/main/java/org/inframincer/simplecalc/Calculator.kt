package org.inframincer.simplecalc

object Calculator {

    enum class Operator {
        ADD,
        SUB,
        DIV,
        MUL,
    }

    fun compute(operator: Operator, firstOperand: Double, secondOperand: Double) =
        when (operator) {
            Operator.ADD -> add(firstOperand, secondOperand)
            Operator.SUB -> sub(firstOperand, secondOperand)
            Operator.DIV -> div(firstOperand, secondOperand)
            Operator.MUL -> mul(firstOperand, secondOperand)
        }

    private fun add(firstOperand: Double, secondOperand: Double) = firstOperand + secondOperand

    private fun sub(firstOperand: Double, secondOperand: Double) = firstOperand - secondOperand

    private fun div(firstOperand: Double, secondOperand: Double) = firstOperand / secondOperand

    private fun mul(firstOperand: Double, secondOperand: Double) = firstOperand * secondOperand

}
