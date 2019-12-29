# Arduino [![](https://jitpack.io/v/Kirill26-07/Arduino.svg)](https://jitpack.io/#Kirill26-07/Arduino)
This library provide communication between your Java application and Arduino controller thought serial port.

#### Maven dependences
If you use Maven or Gradle, you can add dependences through [Jitpack](https://jitpack.io/#Kirill26-07-92/arduino/master-SNAPSHOT),
Or add .jar in your project.

### How to use?

```java
public class TestSerial {

private static final String SERIAL_PORT = "/dev/cu.usbmodem1411"; // Create constant, which include you serial port you use to connect your Arduino controller
private static final int SERIAL_SPEED = 9600; // Create constant, which include max connection speed

private static Arduino arduinoTest = new Arduino(SERIAL_PORT, SERIAL_SPEED); // Create object 

 public static void main(String[] args) throws InterruptedException {
 
  boolean connected = arduinoTest.openConnection();
  System.out.println("Connection success: " + connected);
  Thread.sleep(1000);
  byte[] inputData = arduinoTest.bytesSerialRead(2);
  }
}
```
## License

MIT License

Copyright (c) 2018 Kirill Shiruaev

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
