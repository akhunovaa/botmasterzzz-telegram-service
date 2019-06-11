package com.botmasterzzz.telegram;

import org.apache.catalina.startup.Tomcat;

public class Server {

    private static final int DEFAULT_PORT = 8064;
    private final Tomcat tomcat;

    public static void main(String[] args) throws Exception {
        new Server().run();
    }

    public Server() throws Exception {
        this(getPort());
    }

    public Server(int port) throws Exception {
        tomcat = new Tomcat();
        tomcat.setPort(port);
        tomcat.setBaseDir(System.getProperty("java.io.tmpdir"));
        tomcat.addWebapp("/telegram", System.getProperty("java.io.tmpdir"));
    }


    public void run() throws Exception {
        tomcat.start();
        tomcat.getServer().await();
    }


    public void start() throws Exception {
        tomcat.start();

    }

    public void stop() throws Exception {
        try {
            tomcat.stop();
        } finally {
            tomcat.destroy();
        }
    }

    public static int getPort() {
        return System.getenv("TELEGRAM.TOMCAT.PORT") == null ? DEFAULT_PORT : Integer.parseInt(System.getenv("TELEGRAM.TOMCAT.PORT"));
    }
}