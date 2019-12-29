/**
 * This library create for connect your Java application with Arduino through Serial connection.
 */

package com.arduinoSerialJ;

import java.awt.*;
import java.io.PrintWriter;
import java.util.Scanner;
import com.fazecast.jSerialComm.*;

public class Arduino<T> {

    private SerialPort comPort;
    private String portDescription;
    private int baudRate;

    /**
     * Empty constructor to be used only if port is unknown and it is imperative to initialise object.
     * If this constructor is called, then calling setPortDescription(String portDescription) is essential to set the serial port.
     */
    public Arduino() {
    }

    /**
     * Parameterised constructor that initialises the arduino object and sets the communication port to that which has been specified.
     *
     * @param portDescription your serial port through Arduino is connected with your computer
     *                        It may write how:
     *                        For Windows  "COM52";
     *                        For Linux and MAC  /dev/cu.usbmodem1411
     */
    public Arduino(final String portDescription) {
        this.portDescription = portDescription;
        comPort = SerialPort.getCommPort(this.portDescription);
    }

    /**
     * Parameterised constructor that initialises the arduino object and sets the communication port to that which has been specified.
     * It also sets the baud rate for the serial communication.
     * This is the recommended constructor.
     *
     * @param portDescription your serial port through Arduino is connected with your computer
     *                        It may write how:
     *                        For Windows -  "COM52";
     *                        For Linux and MAC -  /dev/cu.usbmodem1411
     * @param baudRate sets the baud rate for the serial communication.
     *                  For Example 9600. You must use baud rate similar of your arduino sketch.
     */
    public Arduino(final String portDescription, final int baudRate) {
        this.portDescription = portDescription;
        comPort = SerialPort.getCommPort(this.portDescription);
        this.baudRate = baudRate;
        comPort.setBaudRate(this.baudRate);
    }

    /**
     * Opens the connection if portDescription has been initialised.
     * Also displays an error message to the user when connection was unsuccessful.
     * Make sure to call this before anything else or exceptions will be thrown.
     *
     * @return boolean depending on whether the connection was successful.
     */
    public boolean openConnection() {
        if (comPort.openPort()) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return true;
        } else {
            final AlertBox alert = new AlertBox(
                    new Dimension(400,100),
                    "Error Connecting",
                    "Try Another port");
            alert.display();
            return false;
        }
    }

    /**
     * Closes connection to serial port.
     */
    public void closeConnection() {
        comPort.closePort();
    }

    /**
     * Setter method to change serial port to which the object is attached.
     *
     * @param portDescription port description.
     */
    public void setPortDescription(final String portDescription) {
        this.portDescription = portDescription;
        comPort = SerialPort.getCommPort(this.portDescription);
    }

    /**
     * Sets the baud rate for serial
     *
     * @param baudRate baud of rate. You must use baud rate similar of your arduino sketch.
     */
    public void setBaudRate(final int baudRate) {
        this.baudRate = baudRate;
        comPort.setBaudRate(this.baudRate);
    }

    /**
     * Getter method.
     * @return returning the String containng the port description.
     */
    public String getPortDescription() {
        return portDescription;
    }

    /**
     *
     * @return returns an object of type SerialPort with the current Serial Port.
     */
    public SerialPort getSerialPort() {
        return comPort;
    }

    /**
     * Runs until there is no more data available in the serial to be read.
     * This may be an infinite loop depending on availability of data.
     *
     * @return all of the data as a string.
     */
    public String serialRead() {
        comPort.setComPortTimeouts(SerialPort.TIMEOUT_READ_SEMI_BLOCKING, 0, 0);
        final StringBuilder input = new StringBuilder();
        final Scanner scanner = new Scanner(comPort.getInputStream());

        while (scanner.hasNext()) {
            input.append(scanner.next()).append("\n");
        }
        scanner.close();
        return input.toString();
    }

    /**
     * Returns a string containing as many readings as the value of limit.
     * Recommended for reading
     *
     * @param limit limits of bytes witch do you wont to read from serial port
     * @return input bytes
     */
    public String serialRead(final int limit) {
        comPort.setComPortTimeouts(SerialPort.TIMEOUT_READ_SEMI_BLOCKING, 0, 0);
        final StringBuilder input = new StringBuilder();

        int count = 0;
        final Scanner scanner = new Scanner(comPort.getInputStream());

        while (count <= limit && scanner.hasNext()) {
            input.append(scanner.next()).append("\n");
            count++;
        }
        scanner.close();
        return input.toString();
    }

    /**
     * Returns a string array containing as many readings as the value of limit.
     * This method can be use, wen you wont to have data package with split data.
     *
     * @param limit limits of bytes witch do you wont to read from serial port
     * @return input bytes in String array
     */
    public String[] arraySerialRead(final int limit) {
        comPort.setComPortTimeouts(SerialPort.TIMEOUT_READ_SEMI_BLOCKING, 0, 0);
        final String[] input = new String[limit + 1];

        int count = 0;
        final Scanner scanner = new Scanner(comPort.getInputStream());

        while (count <= limit && scanner.hasNext()) {
            input[count] += (scanner.next());
            count++;
        }
        scanner.close();
        return input;
    }

    /**
     * Returns a string array containing as many readings as the value of limit.
     * This method can be use, wen you wont to have data package with split data.
     *
     * @param limit limits of bytes witch do you wont to read from serial port
     * @return input bytes in byte array
     */
    public byte[] byteSerialRead(final int limit) {
        comPort.setComPortTimeouts(SerialPort.TIMEOUT_READ_SEMI_BLOCKING, 0, 0);
        final byte[] input = new byte[limit + 1];

        int count = 0;
        Scanner scanner = new Scanner(comPort.getInputStream());

        while (count <= limit && scanner.hasNext()) {
            input[count] = scanner.nextByte();
            count++;
        }
        scanner.close();
        return input;
    }

    /**
     * Writes the contents of the entire string to the serial at once. Written as string to serial.
     *
     * @param s output String value
     */
    public void serialWrite(final T s) {
        comPort.setComPortTimeouts(SerialPort.TIMEOUT_SCANNER, 0, 0);

        try {
            Thread.sleep(5);
        } catch(InterruptedException e) {
            e.printStackTrace();
        }

        final PrintWriter output = new PrintWriter(comPort.getOutputStream());
        output.print(s);
        output.flush();
    }

    /**
     * Writes the contents of the strings to the serial gradually.
     * It writes the string in incremental steps with 'numberOfChars' characters each time, with a pause of 'delay' milliseconds between each write.
     * Written as string to serial.
     * Recommended to write String.
     *
     * @param data output String value.
     * @param numberOfChars incremental steps.
     * @param delay pause in milliseconds between each write.
     */
    public void stringSerialWrite(final String data, final int numberOfChars, final int delay) {
        comPort.setComPortTimeouts(SerialPort.TIMEOUT_SCANNER, 0, 0);

        try {
            Thread.sleep(5);
        } catch(InterruptedException e) {
            e.printStackTrace();
        }

        final PrintWriter output = new PrintWriter(comPort.getOutputStream());

        for (int i = 0; i < data.length(); i += numberOfChars) {
            final String writingData = data.substring(i, i + numberOfChars);
            output.write(writingData);
            output.flush();
            System.out.println(writingData);

            try {
                Thread.sleep(delay);
            } catch(InterruptedException e) {
                e.printStackTrace();
            }
        }
        output.write(numberOfChars);
        output.flush();
    }

    /**
     * Writes the individual char to the serial in data type char.
     *
     * @param data output char value
     */
    public void charSerialWrite(final char data) {
        comPort.setComPortTimeouts(SerialPort.TIMEOUT_SCANNER, 0, 0);

        try {
            Thread.sleep(5);
        } catch(InterruptedException e) {
            e.printStackTrace();
        }

        final PrintWriter output = new PrintWriter(comPort.getOutputStream());
        output.write(data);
        output.flush();
    }

    /**
     * Writes the individual char to the serial in datatype char and pauses the thread for delay milliseconds after.
     * Recommended to write char.
     *
     * @param data output char value.
     * @param delay pause in milliseconds between each write.
     */
    public void charSerialWrite(final char data, final int delay) {
        comPort.setComPortTimeouts(SerialPort.TIMEOUT_SCANNER, 0, 0);

        try {
            Thread.sleep(5);
        } catch(InterruptedException e) {
            e.printStackTrace();
        }

        final PrintWriter output = new PrintWriter(comPort.getOutputStream());
        output.write(data);
        output.flush();

        try {
            Thread.sleep(delay);
        } catch(InterruptedException e) {
            e.printStackTrace();
        }
    }

}
