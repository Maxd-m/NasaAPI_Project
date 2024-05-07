package com.example.prueba_apod;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TextField;

public class LimitarTF {
    public static void addTextLimiter(final TextField tf, final int maxLength) {
        tf.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(final ObservableValue<? extends String> ov, final String oldValue, final String newValue) {
                if (newValue != null && !newValue.matches("\\d*")) {
                    tf.setText(oldValue); // Vuelve al valor anterior si la nueva entrada no es un dígito
                } else if (tf.getText().length() > maxLength) {
                    String s = tf.getText().substring(0, maxLength);
                    tf.setText(s); // Recorta el texto si excede la longitud máxima
                }
            }
        });
    }

}
