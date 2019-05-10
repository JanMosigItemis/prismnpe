package de.itemis.jmo.prismnpe;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class PrismNpeApp extends Application {

    private final TableView<TableEntry> itemTable = new TableView<TableEntry>();
    private final ObservableList<TableEntry> items = FXCollections.observableArrayList();

    /**
     * We are not going to use the {@code primaryStage} here, because it may have already got its
     * style set. Styles cannot be changed if a stage has been made visible, so in order to prevent
     * for
     * {@code java.lang.IllegalStateException: Cannot set style once stage has been set visible}, we
     * do create our own stage here.
     */
    @Override
    public void start(Stage primaryStage) {
        TableColumn<TableEntry, String> col1 = new TableColumn<>("Col1");
        col1.setCellValueFactory(
            new PropertyValueFactory<TableEntry, String>("col1"));
        TableColumn<TableEntry, String> col2 = new TableColumn<>("Col2");
        col1.setCellValueFactory(
            new PropertyValueFactory<TableEntry, String>("col2"));

        Stage mainStage = new Stage();
        mainStage.setTitle("PrismNpe");
        mainStage.initStyle(StageStyle.DECORATED);

        itemTable.getColumns().add(col1);
        itemTable.getColumns().add(col2);
        itemTable.setEditable(false);
        itemTable.setId("itemTable");
        itemTable.setItems(items);

        MenuBar mainMenu = new MenuBar();
        Menu prismNpeMenu = new Menu("PrismNpe");
        prismNpeMenu.setId("prismNpeMenu");
        MenuItem addSourceMenuItem = new MenuItem("Nothing..");
        addSourceMenuItem.setId("nothing");
        prismNpeMenu.getItems().add(addSourceMenuItem);
        mainMenu.getMenus().add(prismNpeMenu);

        VBox root = new VBox(mainMenu, itemTable);
        Scene mainScene = new Scene(root, 400, 250);
        mainStage.setScene(mainScene);
        mainStage.show();
    }
}
