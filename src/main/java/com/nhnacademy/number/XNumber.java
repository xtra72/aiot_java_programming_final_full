package com.nhnacademy.number;

/**
 * 기본 연산(+, -, *, /)가 지원되는 수를 정의합니다.
 */
public abstract class XNumber {
    /**
     * 두 수를 더 합니다.
     * add(op1, op2) => op1 + op2
     *
     * @param operand1 피연산자
     * @param operand2 피연산자
     * @exception NullPointerException 인수로 null이 주어졌습니다.
     * @return
     */
    public static XNumber add(XNumber operand1, XNumber operand2) {
        if ((operand1 == null) || (operand2 == null)) {
            throw new NullPointerException();
        }

        return operand1.add(operand2);
    }

    /**
     * operand1에서 operand2를 뺍니다.
     * subtract(op1, op2) => op1 - op2
     *
     * @param operand1 피연산자
     * @param operand2 피연산자
     * @exception NullPointerException 인수로 null이 주어졌습니다.
     * @return
     */
    public static XNumber subtract(XNumber operand1, XNumber operand2) {
        return operand1.subtract(operand2);
    }

    /**
     * operand1와 operand2를 곱합니다.
     * multiply(op1, op2) => op1 * op2
     *
     * @param operand1 피연산자
     * @param operand2 피연산자
     * @exception NullPointerException 인수로 null이 주어졌습니다.
     * @return
     */
    public static XNumber multiply(XNumber operand1, XNumber operand2) {
        return operand1.multiply(operand2);
    }

    /**
     * operand1을 operand2로 나눕니다.
     * divide(op1, op2) => op1 / op2
     *
     * @param operand1 피연산자
     * @param operand2 피연산자
     * @exception NullPointerException 인수로 null이 주어졌습니다.
     * @return
     */
    public static XNumber divide(XNumber operand1, XNumber operand2) {
        return operand1.divide(operand2);
    }

    /**
     * 현재 값에 주어진 값을 더합니다.
     * 현재 값은 변경되지 않고, 계산된 값은 새로운 오브젝트로 생성됩니다.
     * number.add(other) => number + other
     *
     * @param other 피연산자
     * @exception NullPointerException 인수로 null이 주어졌습니다.
     * @return
     */
    public abstract XNumber add(XNumber other);

    /**
     * 현재 값에서 주어진 값을 뺍니다.
     * 현재 값은 변경되지 않고, 계산된 값은 새로운 오브젝트로 생성됩니다.
     * number.subtract(other) => number - other
     *
     * @param other 피연산자
     * @exception NullPointerException 인수로 null이 주어졌습니다.
     * @return
     */
    public abstract XNumber subtract(XNumber other);

    /**
     * 현재 값에 주어진 값을 곱합니다.
     * 현재 값은 변경되지 않고, 계산된 값은 새로운 오브젝트로 생성됩니다.
     * number.multiply(other) => number * other
     *
     * @param other 피연산자
     * @exception NullPointerException 인수로 null이 주어졌습니다.
     * @return
     */
    public abstract XNumber multiply(XNumber other);

    /**
     * 현재 값을 주어진 값으로 나눕니다.
     * 현재 값은 변경되지 않고, 계산된 값은 새로운 오브젝트로 생성됩니다.
     * number.divide(other) => number / other
     *
     * @param other 피연산자
     * @exception NullPointerException  인수로 null이 주어졌습니다.
     * @exception DivideByZeroException 인수가 0으로 나눗셈이 되지 않습니다.
     * @return
     */
    public abstract XNumber divide(XNumber other);
}
