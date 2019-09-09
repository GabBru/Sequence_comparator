package com.mycompany.sequence_comparator2;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

public class FXMLController implements Initializable {

    protected static final Logger LOGGER = Logger.getLogger(FXMLController.class.getName());
    public ConnectionDataBase dataAccess;

    @FXML
    private Label label;
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

    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            dataAccess = new ConnectionDataBase();
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(FXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }
        ObservableList<String> list_plante = FXCollections.observableArrayList();
        try {
            list_plante = getNomPlante(dataAccess);
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(FXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }
        combo_nom_plante.setItems(list_plante);                             
    }

    public ObservableList<String> getNomPlante(ConnectionDataBase dataAccess) throws SQLException, ClassNotFoundException {
        System.out.println("Getnomplante");
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

//    combo_type_prot.setOnAction(new EventHandler<MouseEvent>(){
//            @Override
//            public void handle(MouseEvent event){
//                ObservableList<Experiences> ligneSelectionnee;
//                ligneSelectionnee = table_chercheur.getSelectionModel().getSelectedItems();
//                txt_nom_exp.setText(ligneSelectionnee.get(0).getNom_exp());
//                txt_type_exp.setText(ligneSelectionnee.get(0).getType_exp());
//                txt_AgBio.setText(ligneSelectionnee.get(0).getAgBio());
//                txt_duree.setText(ligneSelectionnee.get(0).getDuree());
//                txt_nomlab.setText(ligneSelectionnee.get(0).getLaborantin().getPrenom()+" "+ligneSelectionnee.get(0).getLaborantin().getNom());
//                txt_replicat.setText(ligneSelectionnee.get(0).getReplicats());
//                txt_seuils.setText("[" + ligneSelectionnee.get(0).getSeuil1() + "-" + ligneSelectionnee.get(0).getSeuil2() + "]");
//                txt_frequence.setText(ligneSelectionnee.get(0).getFrequence() + " minute(s)");
//                txt_commande.setText(ligneSelectionnee.get(0).getDate());
//            
//                if (ligneSelectionnee.get(0).getEtat().equals("Finalisée")){
//                    txt_statut.setText("Résultats acceptés"); txt_statut.setStyle("-fx-text-inner-color: green;");
//                    txt_statut.setVisible(true);
//                    button_download.setVisible(true);
//                }
//                else if (ligneSelectionnee.get(0).getEtat().equals("Refusée")){
//                    txt_statut.setText("Résultats refusés"); txt_statut.setStyle("-fx-text-color: red;");
//                    txt_statut.setVisible(true);
//                    button_download.setVisible(true);
//                }
//                else {
//                    txt_statut.setVisible(false);
//                    button_download.setVisible(false);
//                }
//                exp_nuplet.setCellValueFactory(
//            new Callback<TableColumn.CellDataFeatures<DataEchantillon, String>, ObservableValue<String>>() {
//                @Override public ObservableValue<String> call(TableColumn.CellDataFeatures<DataEchantillon, String> p) {
//                return new ReadOnlyObjectWrapper(table_result.getItems().indexOf(p.getValue()) + "");
//                }
//            });
//            exp_qtA.setCellValueFactory(
//            new PropertyValueFactory<>("qA"));
//            exp_qtC.setCellValueFactory(
//            new PropertyValueFactory<>("qC"));
//            exp_qtN.setCellValueFactory(
//            new PropertyValueFactory<>("qN"));
//            exp_res.setCellValueFactory(
//            new PropertyValueFactory<>("res"));
//            exp_res.setSortable(false);
//            exp_qtA.setSortable(false);
//            exp_qtN.setSortable(false);
//            exp_qtC.setSortable(false);
//            exp_nuplet.setSortable(false);
//            table_result.setItems(ligneSelectionnee.get(0).getListeEchantillon());
//            }
//        });
        
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
