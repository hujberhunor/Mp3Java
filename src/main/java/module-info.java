module com.example {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.example to javafx.fxml, com.google.gson; // Allow Gson access to reflection
    exports com.example;

    // chatgpt
    requires javafx.media;
    requires mp3agic;
    
    // Swing + awt
    requires java.desktop;

    // Gson
    requires com.google.gson; // Ensure Gson is explicitly required
}
