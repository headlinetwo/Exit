package de.headlinetwo.exit.util;

import android.content.Context;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by headlinetwo on 17.11.17.
 */
public class FileUtil {

    /**
     * @param is the input stream that needs to be converted into a string
     * @return the string that has been read from the given input stream
     */
    public static String convertStreamToString(InputStream is) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();
        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line).append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    /**
     * Reads the contents of a file at the given path
     *
     * @param context the application context to access the file
     * @param filePath the path to the file to read
     * @return the data of the given file as a single string
     */
    public static String readFile(Context context, String filePath) {
        StringBuilder stringBuilder = new StringBuilder();

        try {
            FileInputStream fileInputStream = context.openFileInput(filePath);
            byte[] buffer = new byte[1024];

            int n;
            while ((n = fileInputStream.read(buffer)) != -1) {
                stringBuilder.append(new String(buffer, 0, n));
            }

            fileInputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return stringBuilder.toString();
    }

    /**
     * Overrides the contents of the given file with the given content
     *
     * @param context the application context to access the file
     * @param filePath the path to the file to write to
     * @param content the data to write into the file
     */
    public static void writeToFile(Context context, String filePath, String content) {
        try {
            FileOutputStream fileOutputStream = context.openFileOutput(filePath, Context.MODE_PRIVATE);
            fileOutputStream.write(content.getBytes());
            fileOutputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}