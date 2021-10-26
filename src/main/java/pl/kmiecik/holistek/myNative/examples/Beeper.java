package pl.kmiecik.holistek.myNative.examples;


import com.sun.jna.Library;
import com.sun.jna.Native;

public class Beeper {


    public interface Kernel32 extends Library {
        public boolean beep(int frequency, int duration);

        public void sleep(int duration);
    }

    public static void main(String[] args) {
        Kernel32 lib = Native.loadLibrary("kernel32", Kernel32.class);
        lib.beep(698, 500);
        lib.sleep(500);
        lib.beep(698, 500);

    }
}
