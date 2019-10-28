package MobileRechargeStore;

import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.util.Callback;

import java.sql.ResultSet;

public class DrawTable {
    private ObservableList<ObservableList<String>> data;

    DrawTable(){

        data= FXCollections.observableArrayList();
    }

    public void setTableData(ResultSet dbReturn, TableView tableToDraw) throws Exception{
        if(tableToDraw.getColumns() != null){
            tableToDraw.getColumns().clear();
        };
        for(int i=0 ; i<dbReturn.getMetaData().getColumnCount(); i++) {
            //We are using non property style for making dynamic table
            final int j = i;
            TableColumn col = new TableColumn(dbReturn.getMetaData().getColumnLabel(i + 1));
            col.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ObservableList, String>, ObservableValue<String>>() {
                public ObservableValue<String> call(TableColumn.CellDataFeatures<ObservableList, String> param) {
                    return new SimpleStringProperty(param.getValue().get(j).toString());
                }
            });
            tableToDraw.getColumns().add(col);
            System.out.println("Column [" + i + "] ");
        }
        while(dbReturn.next()){
            //Iterate Row
            ObservableList<String> row = FXCollections.observableArrayList();
            for(int i=1 ; i<=dbReturn.getMetaData().getColumnCount(); i++){
                //Iterate Column
                if(dbReturn.getString(i) != null) {
                    row.add(dbReturn.getString(i));
                }else{
                    row.add("N/A");
                }
            }
            System.out.println("Row [1] added "+row );
            data.add(row);
        }
                tableToDraw.setItems(data);
    }
}
