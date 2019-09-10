package com.mycompany.sequence_comparator2;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collections;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

public class FXMLController implements Initializable {

    protected static final Logger LOGGER = Logger.getLogger(FXMLController.class.getName());
    public ConnectionDataBase dataAccess;

    //// Onglet CONSULTATION ////
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
    
    //// Onglet AJOUT REFERENCE ////
    @FXML
    protected TextField ref_nom_plante;
    @FXML
    protected TextField ref_type_prot;
    @FXML
    protected TextField ref_nom_prot;
    @FXML
    protected TextArea ref_seq;
    @FXML
    protected Button button_add;
    @FXML
    protected Tooltip info_nom_plante;
    @FXML
    protected Tooltip info_type_prot;
    @FXML
    protected Tooltip info_nom_prot;
    @FXML
    protected Label pop_nom_plante;
    @FXML
    protected Label pop_type_prot;
    @FXML
    protected Label pop_nom_prot;
    
    //// Onglet ANALYSE ////
    
    

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // ---- Logo GENEBV analytics ---- //
        Image image = new Image("/images/Logo_geneBVanalytics.png");
        view_logo.setImage(image);
        view_logo.setSmooth(true);

        // --- Connexion BDD --- //
        try {
            dataAccess = new ConnectionDataBase();
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(FXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        //// Onglet CONSULTATION ////
        ObservableList<String> list_plante = FXCollections.observableArrayList();
        try {
            list_plante = getNomPlante(dataAccess);
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(FXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }
        combo_nom_plante.setItems(list_plante);

        combo_nom_plante.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    combo_type_prot.setItems(getNomProt(combo_nom_plante.getSelectionModel().getSelectedItem().toString(), combo_type_prot.getSelectionModel().getSelectedItem().toString()));
                } catch (SQLException | ClassNotFoundException ex) {
                    Logger.getLogger(FXMLController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

        combo_type_prot.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    combo_nom_prot.setItems(getTypeProt(combo_nom_plante.getSelectionModel().getSelectedItem().toString()));
                } catch (SQLException | ClassNotFoundException ex) {
                    Logger.getLogger(FXMLController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
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
        Collections.sort(list);
        return list;
    }

    public ObservableList<String> getTypeProt(String nom_plante) throws SQLException, ClassNotFoundException {
        Connection con = dataAccess.getCon();
        ObservableList<String> list_type_prot = FXCollections.observableArrayList();
        //execute query
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery("select * from PLANTE natural join TYPE_ENTITE where nom_plante = '" + nom_plante + "'");
        while (rs.next()) {
            list_type_prot.add(rs.getString(5));
        }
        Collections.sort(list_type_prot);
        return list_type_prot;
    }

    public ObservableList<String> getNomProt(String nom_plante, String nom_type_plante) throws SQLException, ClassNotFoundException {
        Connection con = dataAccess.getCon();
        ObservableList<String> list_prot = FXCollections.observableArrayList();
        //execute query
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery("select * from PLANTE natural join TYPE_ENTITE natural join SEQ_ARN NATURAL JOIN PROTEINE where nom_plante = '" + nom_plante + "' and nom_type = '" + nom_type_plante + "'");
        while (rs.next()) {
            list_prot.add(rs.getString(10));
        }
        Collections.sort(list_prot);
        return list_prot;
    }

    @FXML
    void launchBlast(MouseEvent event) throws InterruptedException, IOException {
//        Blast blast = new Blast();
//        blast.search(getSeq_nom_plante(),getSeq());

        ResultFile file = new ResultFile();
//        file.readFile();
          Clustal clustal = new Clustal();
          clustal.submit();
        Generate_tree tree = new Generate_tree(clustal.getTree());
        tree.submit();
//          Place place = new Place();
//          place.tBlastN(getSeq_nom_plante());
//          place.place();
//        file.deleteFile();
    }

    public String getSeq_nom_plante() {
        return ref_nom_plante.getText();
    }

    public String getSeq() {
        return ref_seq.getText();
    }

    /// POP HELP ///
    public void help_nom_plante(MouseEvent event) throws IOException{
        info_nom_plante = new Tooltip();
        info_nom_plante.setText("Renseignez le nom complet de la \n "
                + "plante en utilisant de préférence son nom dans \n"
                + "la nomenclature officiel (latin).");
        pop_nom_plante.setTooltip(info_nom_plante);
        }
}
