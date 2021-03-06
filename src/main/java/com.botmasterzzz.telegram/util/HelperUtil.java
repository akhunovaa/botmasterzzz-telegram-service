package com.botmasterzzz.telegram.util;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Random;

public class HelperUtil {

    public static String getClientIp(HttpServletRequest request) {

        String remoteAddr = "";

        if (request != null) {
            remoteAddr = request.getHeader("X-FORWARDED-FOR");
            if (remoteAddr == null || "".equals(remoteAddr)) {
                remoteAddr = request.getRemoteAddr();
            }
        }

        return remoteAddr;
    }

    public static String saveImage(String imageUrl, String destinationFile) throws IOException {
        String filePath = System.getProperty("java.io.tmpdir") + "/" + destinationFile;
        ImageIO.write(ImageIO.read(new URL(imageUrl)), "jpg", new File(filePath));
        return filePath;
    }

    public static String saveVideo(String imageUrl, String destinationFile) throws IOException {
        String filePath = System.getProperty("java.io.tmpdir") + "/" + destinationFile;

        ImageIO.write(ImageIO.read(new URL(imageUrl)), "jpg", new File(filePath));
        return filePath;
    }

    public static String stringBeautyFormat(String text){
        if (null == text){
            return "неизвестно";
        }
        return text;
    }

    public static String stringBeautyMeausreFormat(String text){
        if (null == text){
            return "неизвестно";
        }
        double value = Double.valueOf(text) * 1000;
        return value + " мм.";
    }

    public static int[] randomNumbersWithoutRepetition(int start, int end, int count) {
        Random rng = new Random();

        int[] result = new int[count];
        int cur = 0;
        int remaining = end - start;
        for (int i = start; i < end && count > 0; i++) {
            double probability = rng.nextDouble();
            if (probability < ((double) count) / (double) remaining) {
                count--;
                result[cur++] = i;
            }
            remaining--;
        }
        return result;
    }

}
