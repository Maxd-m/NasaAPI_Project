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

    opens com.example.prueba_apod to javafx.fxml;
    opens com.example.prueba_apod.models to com.google.gson;
    exports com.example.prueba_apod;
}