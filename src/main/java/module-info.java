module com.example.prueba_apod {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;
    requires javafx.web;

    requires com.dlsc.formsfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires com.google.gson;
    requires java.logging;
    requires kernel;
    requires layout;
    requires java.desktop;
    requires org.slf4j;
    requires org.apache.logging.log4j;
    requires java.sql;
    requires io;
    requires org.apache.httpcomponents.httpclient;
    requires org.apache.httpcomponents.httpcore;
    requires java.annotation;

    opens com.example.prueba_apod to javafx.fxml;
    //opens com.example.prueba_apod.models to com.google.gson;
    exports com.example.prueba_apod;
    exports com.example.prueba_apod.controllers;
    opens com.example.prueba_apod.models;
    opens com.example.prueba_apod.controllers to javafx.fxml;
}