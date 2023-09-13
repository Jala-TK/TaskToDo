module com.krodrigues {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.fx.countries;
    requires eu.hansolo.fx.heatmap;
    requires eu.hansolo.toolboxfx;
    requires eu.hansolo.toolbox;
    requires eu.hansolo.tilesfx;
    requires java.sql;
    requires org.xerial.sqlitejdbc;

    opens com.krodrigues.controller to javafx.fxml;

    exports com.krodrigues;
    opens com.krodrigues to javafx.fxml;
    exports com.krodrigues.models.repository;
    opens com.krodrigues.models.repository to javafx.fxml;
    exports com.krodrigues.models.entities;
    opens com.krodrigues.models.entities to javafx.fxml;
    exports com.krodrigues.models.services;
    opens com.krodrigues.models.services to javafx.fxml;

}
