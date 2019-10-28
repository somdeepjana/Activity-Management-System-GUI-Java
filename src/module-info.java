module JavaActivityManagement {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens MobileRechargeStore;
    opens MobileRechargeStore.ModifingMenu;
}