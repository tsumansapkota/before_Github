package com.tsuman.rcremote;

public class BluetoothUno {
    BluetoothService bts;
    public static final int HIGH = 1;
    public static final int LOW = 0;
    public static final int A0 = 14;
    public static final int A1 = 15;
    public static final int A2 = 16;
    public static final int A3 = 17;
    public static final int A4 = 18;
    public static final int A5 = 19;

    int[] pinModes = new int[20];
    int[] pinData = new int[20];

    public BluetoothUno(BluetoothService bts) {
        this.bts = bts;
        for (int i = 0; i < 20; i++) {
            pinModes[i] = 0;
            pinData[i] = 0;
        }
    }

    public void digitalWrite(int pin, int data) {
//        System.out.println("Writing digital: pin="+pin+" data="+data);
        int b = pin << 3;
        if (data == 0) {
            b = b | 6;
        } else {
            b = b | 7;
        }
        bts.write(new byte[]{(byte) b});
        pinData[pin] = data;
    }

    public void pinMode(int pin, int mode) {
        int b = pin << 3;
        if (mode == 0) {
            b = b | 5;
        } else {
            b = b | 4;
        }
        bts.write(new byte[]{(byte) b});
        pinData[pin] = mode;
    }

    public void analogWrite(int pin, int data) {
        int b = pin << 3;
        b = b | 3;
        bts.write(new byte[]{(byte) b, (byte) data});
        pinData[pin] = data;
    }

    public int analogRead(int pin){
        int b = pin << 3;
        b = b | 2;
        byte[] data = bts.read(new byte[]{(byte) b}, 2);
        pinData[pin] = (int)data[1]*128 + (int)data[0];
        return pinData[pin];
    }

    public int digitalRead(int pin){
        int b = pin << 3;
        b = b | 1;
        byte[] data = bts.read(new byte[]{(byte) b}, 1);
        pinData[pin] = (int)data[0];
        return pinData[pin];
    }


    int pwm0_LB = 3;
    int pwm1_LF = 5;
    int pwm2_RB = 6;
    int pwm3_RF = 9;

    public void rotateLeftWheel(int speed){
        if (speed > 0){
            speed = Math.min(Math.abs(speed), 255);
            analogWrite(pwm1_LF, speed);
            analogWrite(pwm0_LB, 0);
        }else if (speed < 0){
            speed = Math.min(Math.abs(speed), 255);
            analogWrite(pwm1_LF, 0);
            analogWrite(pwm0_LB, speed);
        }else{
            analogWrite(pwm1_LF, 0);
            analogWrite(pwm0_LB, 0);
        }
    }

    public void rotateRightWheel(int speed){
        if (speed > 50){
            speed = Math.min(Math.abs(speed), 255);
            analogWrite(pwm3_RF, speed);
            analogWrite(pwm2_RB, 0);
        }else if (speed < -50){
            speed = Math.min(Math.abs(speed), 255);
            analogWrite(pwm3_RF, 0);
            analogWrite(pwm2_RB, speed);
        }else{
            analogWrite(pwm3_RF, 0);
            analogWrite(pwm2_RB, 0);
        }
    }


}
