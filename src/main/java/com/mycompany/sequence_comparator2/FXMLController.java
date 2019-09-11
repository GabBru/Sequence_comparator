package com.mycompany.sequence_comparator2;

import static com.mycompany.sequence_comparator2.Blast.LOGGER;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collections;
import java.util.List;
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
    protected TableView <CIS> tab_CIS;
    @FXML
    protected TableColumn <CIS, String> col_nom_CIS;
    @FXML
    protected TableColumn <CIS, Integer> col_pos_CIS;
    @FXML
    protected TableColumn <CIS, String> col_seq_CIS;
    @FXML
    protected TextArea text_seq_ARN;
    @FXML
    protected TextArea text_seq_ADN;
    @FXML
    protected ImageView view_logo;
    
    //// Onglet AJOUT REFERENCE ////
    @FXML
    protected TextField seq_nom_plante1;
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
    @FXML
    protected ComboBox combo_analyse_type;
    @FXML
    protected TextField text_new_type;
    @FXML
    protected Button button_new_type;
    

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
                    combo_type_prot.setItems(getTypeProt(combo_nom_plante.getSelectionModel().getSelectedItem().toString()));
                } catch (SQLException | ClassNotFoundException ex) {
                    Logger.getLogger(FXMLController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

        combo_type_prot.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    combo_nom_prot.setItems(getNomProt(combo_nom_plante.getSelectionModel().getSelectedItem().toString(), combo_type_prot.getSelectionModel().getSelectedItem().toString()));
                } catch (SQLException | ClassNotFoundException ex) {
                    Logger.getLogger(FXMLController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        
        try {
            //// Onglet ANALYSE ////
            combo_analyse_type.setItems(getTypeProtAna());
        } catch (SQLException ex) {
            Logger.getLogger(FXMLController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(FXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    public ObservableList<String> getNomPlante(ConnectionDataBase dataAccess) throws SQLException, ClassNotFoundException {
        Connection con = dataAccess.getCon();
        ObservableList<String> list = FXCollections.observableArrayList();
        // execute query
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery("select nom_plante from PLANTE");
        while (rs.next()) {
            list.add(rs.getString(1));
        }
        Collections.sort(list);
        return list;
    }

    public ObservableList<String> getTypeProt(String nom_plante) throws SQLException, ClassNotFoundException {
        Connection con = dataAccess.getCon();
        ObservableList<String> list_type_prot = FXCollections.observableArrayList();
        //execute query
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery("select * from SEQUENCES join TYPE_PROTEINE on nom_type where id-plante in (select id_plante from PLANTE where nom_plante= '"+nom_plante+"')");
        while (rs.next()) {
            list_type_prot.add(rs.getString(5));
        }
        Collections.sort(list_type_prot);
        return list_type_prot;
    }
    
    public ObservableList<String> getTypeProtAna() throws SQLException, ClassNotFoundException {
        Connection con = dataAccess.getCon();
        ObservableList<String> list_type_prot = FXCollections.observableArrayList();
        //execute query
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery("select * from TYPE_PROTEINE");
        while (rs.next()) {
            list_type_prot.add(rs.getString(1));
        }
        Collections.sort(list_type_prot);
        return list_type_prot;
    }

    public ObservableList<String> getNomProt(String nom_plante, String nom_type_plante) throws SQLException, ClassNotFoundException {
        Connection con = dataAccess.getCon();
        ObservableList<String> list_prot = FXCollections.observableArrayList();
        //execute query
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery("select * from PLANTE join TYPE_PROTEINE join SEQUENCES where nom_plante = '" + nom_plante + "' and nom_type = '" + nom_type_plante + "'");
        while (rs.next()) {
            list_prot.add(rs.getString(10));
        }
        Collections.sort(list_prot);
        return list_prot;
    }
    
    public ObservableList<String> GenerateSeqTypePro(ConnectionDataBase dataAccess, String nom_type_plante) throws SQLException, ClassNotFoundException {
        Connection con = dataAccess.getCon();
        ObservableList<String> list = FXCollections.observableArrayList();
        // execute query
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery("select DISTINCT nom_plante from PLANTE where TYPE_PROTEINE.nom_type ='"+nom_type_plante+"'");
        while (rs.next()) {
            list.add(rs.getString(1));
        }
        Collections.sort(list);
        return list;
    }

    @FXML
    void launchBlast(MouseEvent event) throws InterruptedException, IOException, SQLException {
        Connection con = dataAccess.getCon();
        ObservableList<String> refSeqAra = FXCollections.observableArrayList();
        // execute query
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery("Select seq_ADN from SEQUENCES where id_plante = (Select id_plante from PLANTE where nom_plante= 'Arabidopsis thaliana') and nom_type ='"+combo_analyse_type.getSelectionModel().getSelectedItem().toString()+"'");
        while (rs.next()) {
            System.out.println("Dans la boucle while");
            refSeqAra.add(">\n"+rs.getString(1)+"\n");
        }
        Collections.sort(refSeqAra);
        LOGGER.info("list " + refSeqAra.size());
        Blast blast = new Blast();
        List<List<String>> blastResult = blast.search(getSeq_nom_plante(),refSeqAra);

        ResultFile file = new ResultFile();
//        file.readFile();
          Clustal clustal = new Clustal();
          clustal.submit(blastResult,refSeqAra);
        Generate_tree tree = new Generate_tree(clustal.getTree());
        tree.submit();
//          Place place = new Place();
//          place.tBlastN(getSeq_nom_plante());
//          place.place();
//        file.deleteFile();
    }

    public String getSeq_nom_plante() {
        return seq_nom_plante1.getText();
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
