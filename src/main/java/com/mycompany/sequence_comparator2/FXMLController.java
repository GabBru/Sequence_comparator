package com.mycompany.sequence_comparator2;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

public class FXMLController implements Initializable {

    protected static final Logger LOGGER = Logger.getLogger(FXMLController.class.getName());
    public ConnectionDataBase dataAccess;

    @FXML
    protected TextField seq_nom_plante;
    @FXML
    protected TextArea seq;
    @FXML
    protected ComboBox combo_nom_plante;
    @FXML
    protected ComboBox combo_type_prot;
    @FXML
    protected ComboBox combo_nom_prot;
    @FXML
    protected Text text_nom_gene;
    @FXML
    protected Text text_lien_ncbi;
    @FXML
    protected TableView tab_CIS;
    @FXML
    protected TableColumn col_nom_CIS;
    @FXML
    protected TableColumn col_pos_CIS;
    @FXML
    protected TableColumn col_seq_CIS;
    @FXML
    protected TextArea text_seq_ARN;
    @FXML
    protected TextArea text_seq_ADN;
    @FXML
    protected ImageView view_logo;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Image image = new Image("/src/main/resources/images/Logo_geneBVanalytics.png");
        view_logo.setImage(image);
        view_logo.setSmooth(true);
//        try {
//            dataAccess = new ConnectionDataBase();
//            LOGGER.info("dataAccess = " + dataAccess);
//        } catch (ClassNotFoundException | SQLException ex) {
//            Logger.getLogger(FXMLController.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        ObservableList<String> list_plante = FXCollections.observableArrayList();
//        try {
//            list_plante = getNomPlante(dataAccess);
//        } catch (SQLException | ClassNotFoundException ex) {
//            Logger.getLogger(FXMLController.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        combo_nom_plante.setItems(list_plante);
    }

    public ObservableList<String> getNomPlante(ConnectionDataBase dataAccess) throws SQLException, ClassNotFoundException {
        
        Connection con = dataAccess.getCon();
        ObservableList<String> list = FXCollections.observableArrayList();
        // execute query
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery("select * from PLANTE");
        while (rs.next()) {
            list.add(rs.getString(2));
        }
        return list;
    }
    
    public ObservableList<String> getTypeProt(String nom_plante) throws SQLException, ClassNotFoundException {
        Connection con = dataAccess.getCon();
        ObservableList<String> list_type_prot = FXCollections.observableArrayList();
        //execute query
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery("select * from PLANTE natural join TYPE_ENTITE where nom_plante = " + nom_plante);
        while (rs.next()) {
            list_type_prot.add(rs.getString(4));
        }
        return list_type_prot;
    }
    
    public ObservableList<String> getNomProt(String nom_type_plante) throws SQLException, ClassNotFoundException {
        Connection con = dataAccess.getCon();
        ObservableList<String> list_prot = FXCollections.observableArrayList();
        //execute query
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery("select * from PLANTE natural join TYPE_ENTITE where nom_plante = " + nom_type_plante);
        while (rs.next()) {
            list_prot.add(rs.getString(4));
        }
        return list_prot;
    }
        
    @FXML
    void launchBlast(MouseEvent event) throws InterruptedException, IOException {
        Blast blast = new Blast();
        blast.search(getSeq_nom_plante(), getSeq());
        ResultFile file = new ResultFile();
        file.readFile();
        file.deleteFile();
    }

    public String getSeq_nom_plante() {
        return seq_nom_plante.getText();
    }

    public String getSeq() {
        return seq.getText();
    }

}
