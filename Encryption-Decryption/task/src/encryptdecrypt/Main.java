package encryptdecrypt;

import java.io.File;
import java.io.FileWriter;
import java.util.Scanner;
import java.lang.StringBuffer;

interface Encrypt {
    abstract StringBuffer encrypt(String message, int offset);
}

interface Decrypt {
    abstract StringBuffer decrypt(String message, int offset);
}

class EncryptShift implements Encrypt {
    @Override
    public StringBuffer encrypt(String message, int offset) {
        StringBuffer newMessage = new StringBuffer("");
        for (int i = 0; i < message.length(); i++) {
            char x = message.charAt(i);
            if (x == ' ') {
                newMessage.append(" ");
            } else {
                if (Character.isLowerCase(x)) {
                    if (x + offset > 'z') {
                        char rebound = (char) (x + offset - 26);
                        newMessage.append(Character.toString(rebound));
                    } else {
                        newMessage.append(Character.toString(x + offset));
                    }
                } else {
                    if (x + offset > 'Z') {
                        char rebound = (char) (x + offset - 26);
                        newMessage.append(Character.toString(rebound));
                    } else {
                        newMessage.append(Character.toString(x + offset));
                    }
                }

            }
        }

        return newMessage;
    }
}

class EncryptUnicode implements Encrypt {
    @Override
    public StringBuffer encrypt(String message, int offset) {
        StringBuffer newMessage = new StringBuffer("");
        for (int i = 0; i < message.length(); i++) {
            char x = message.charAt(i);
            newMessage.append(Character.toString(x + offset));
        }
        return newMessage;
    }
}

class DecryptShift implements Decrypt {
    @Override
    public StringBuffer decrypt(String message, int offset) {
        StringBuffer newMessage = new StringBuffer("");
        for (int i = 0; i < message.length(); i++) {
            char x = message.charAt(i);
            if (x == ' ') {
                newMessage.append(" ");
            } else {
                if (Character.isLowerCase(x)) {
                    if (x - offset < 'a') {
                        char rebound = (char) (x - offset + 26);
                        newMessage.append(Character.toString(rebound));
                    } else {
                        newMessage.append(Character.toString(x - offset));
                    }
                } else {
                    if (x - offset < 'A') {
                        char rebound = (char) (x - offset + 26);
                        newMessage.append(Character.toString(rebound));
                    } else {
                        newMessage.append(Character.toString(x - offset));
                    }
                }
            }
        }

        return newMessage;
    }
}

class DecryptUnicode implements Decrypt {
    @Override
    public StringBuffer decrypt(String message, int offset) {
        StringBuffer newMessage = new StringBuffer("");
        for (int i = 0; i < message.length(); i++) {
            char x = message.charAt(i);
            newMessage.append(Character.toString(x - offset));
        }
        return newMessage;
    }
}

public class Main {
    public static StringBuffer encrypt(String message, int offset) {
        return null;
    }

    public static StringBuffer decrypt(String message, int offset) {
        return null;
    }

    public static void main(String[] args) {
        String operation = "";
        String message = "";
        int offset = 0;
        String inputFilename = "";
        String outputFilename = "";
        String algorithm = "";
        boolean modeMatch = false;
        boolean dataMatch = false;
        boolean keyMatch = false;
        boolean inputMatch = false;
        boolean outputMatch = false;
        boolean algMatch = false;

        for (int i = 0; i < args.length; i++) {
            if (args[i].equals("-mode")) {
                operation = args[i + 1];
                modeMatch = true;
            }
            else if (args[i].equals("-data")) {
                message = args[i + 1];
                dataMatch = true;
            }
            else if (args[i].equals("-key")) {
                offset = Integer.parseInt(args[i + 1]);
                keyMatch = true;
            }
            else if (args[i].equals("-in")) {
                if (!dataMatch) {
                    inputFilename = args[i + 1];
                    inputMatch = true;
                }
            }
            else if (args[i].equals("-out")) {
                outputFilename = args[i + 1];
                outputMatch = true;
            }
            else if (args[i].equals("-alg")) {
                algorithm = args[i + 1];
                algMatch = true;
            }
        }

        if (!modeMatch) {
            operation = "enc";
        }
        if (!dataMatch && !inputMatch) {
            message = "";
        }
        if (!keyMatch) {
            offset = 0;
        }
        if (inputMatch && !dataMatch) {
            File file = new File (inputFilename);
            try (Scanner scanner = new Scanner(file)) {
                message = scanner.nextLine();
            } catch (Exception e) {
                System.out.println ("Error: Input file not found.");
            }
        }
        if (!algMatch) {
            algorithm = "shift";
        }

        StringBuffer newMessage = new StringBuffer("");

        if (operation.equals("enc") && algorithm.equals("shift")) {
            EncryptShift encryptShift = new EncryptShift();
            newMessage = encryptShift.encrypt(message, offset);
        }
        else if (operation.equals("enc") && algorithm.equals("unicode")) {
            EncryptUnicode encryptUnicode = new EncryptUnicode();
            newMessage = encryptUnicode.encrypt(message, offset);
        }
        else if (operation.equals("dec") && algorithm.equals("shift")) {
            DecryptShift decryptShift = new DecryptShift();
            newMessage = decryptShift.decrypt(message, offset);
        }
        else if (operation.equals("dec") && algorithm.equals("unicode")) {
            DecryptUnicode decryptUnicode = new DecryptUnicode();
            newMessage = decryptUnicode.decrypt(message, offset);
        }

        if (!outputMatch) {
            System.out.println(newMessage);
        }
        else {
            File file = new File (outputFilename);
            try (FileWriter writer = new FileWriter(file)) {
                writer.write(String.valueOf(newMessage));
            } catch (Exception e) {
                System.out.println ("Error: Output file not found.");
            }
        }
    }
}
