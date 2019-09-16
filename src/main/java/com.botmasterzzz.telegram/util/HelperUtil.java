package com.botmasterzzz.telegram.util;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.net.URL;

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
        File file = new File(filePath);
        if(!file.exists()){
            ImageIO.write(ImageIO.read(new URL(imageUrl)), "jpg", new File(filePath));
        }
        return filePath;
    }
}
