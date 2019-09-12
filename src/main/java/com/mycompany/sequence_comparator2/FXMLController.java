package com.mycompany.sequence_comparator2;

import static com.mycompany.sequence_comparator2.Blast.LOGGER;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
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
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import static javax.swing.UIManager.getInt;

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
    protected Hyperlink text_lien_ncbi;
    @FXML
    protected TableView<CIS> tab_CIS;
    @FXML
    protected TableColumn<CIS, String> col_nom_CIS;
    @FXML
    protected TableColumn<CIS, Integer> col_pos1_CIS;
    @FXML
    protected TableColumn<CIS, Integer> col_pos2_CIS;
    @FXML
    protected TableColumn<CIS, String> col_seq_CIS;
    @FXML
    protected TextArea text_seq_ARN;
    @FXML
    protected TextArea text_seq_ADN;
    @FXML
    protected ImageView view_logo;
    @FXML
    protected HBox hbox_web_view;
    @FXML
    protected WebView web_zone;

    //// Onglet AJOUT REFERENCE ////
    @FXML
    protected TextField seq_nom_plante1;
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
    @FXML
    protected HBox hbox_arbre;
    @FXML
    protected WebView webview_arbre;
    @FXML
    protected ComboBox combo_analyse_type;
    @FXML
    protected TextField text_new_type;
    @FXML
    protected Button button_new_type;
    @FXML
    private TextArea ref_prot;
    @FXML
    protected Button button_search_silent;
    @FXML
    protected Button button_search;
    @FXML
    protected ProgressIndicator progress_indicator;
    @FXML
    protected TableView<Sequence> tab_arbre;
    @FXML
    private TableColumn<Sequence, CheckBox> col_check_arbre;
    @FXML
    protected ComboBox combo_ref_nom_plante;
    @FXML
    protected ComboBox combo_ref_type_prot;
    @FXML
    protected Button add_plante;
    @FXML
    protected Button add_type_prot;
    @FXML
    private TableColumn<Sequence, String> col_nom_arbre;
    @FXML
    private TableColumn<Sequence, String> col_details_arbre;
    @FXML
    protected Text text_arbre;
    @FXML
    protected Button button_arbre;
    @FXML
    protected Text text_info_arbre;
    @FXML
    protected Button button_soumettre;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // ---- Logo GENEBV analytics ---- //
        Image image = new Image("/images/Logo_geneBVanalytics.png");
        view_logo.setImage(image);
        view_logo.setSmooth(true);

        WebEngine webEngineConsult = web_zone.getEngine();
        webEngineConsult.load("https://www.ncbi.nlm.nih.gov");

        WebEngine webEngineArbre = webview_arbre.getEngine();
        webEngineArbre.load("http://phylo.io/index.html");
//        webEngineArbre.

        text_seq_ARN.setEditable(false);
        text_seq_ADN.setEditable(false);

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
                combo_type_prot.getItems().removeAll(combo_type_prot.getItems());
                combo_nom_prot.getItems().removeAll(combo_nom_prot.getItems());
                text_nom_gene.setVisible(false);
                text_lien_ncbi.setVisible(false);
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
                combo_nom_prot.getItems().removeAll(combo_nom_prot.getItems());
                text_nom_gene.setVisible(false);
                text_lien_ncbi.setVisible(false);
                try {
                    combo_nom_prot.setItems(getNomProt(combo_nom_plante.getSelectionModel().getSelectedItem().toString(), combo_type_prot.getSelectionModel().getSelectedItem().toString()));
                } catch (SQLException | ClassNotFoundException ex) {
                    Logger.getLogger(FXMLController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

        combo_nom_prot.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    String nom_plante = combo_nom_plante.getSelectionModel().getSelectedItem().toString();
                    String nom_prot = combo_nom_prot.getSelectionModel().getSelectedItem().toString();

                    text_seq_ARN.clear();
                    text_seq_ADN.clear();

                    tab_CIS.getItems().removeAll(tab_CIS.getItems());

                    text_nom_gene.setText(nom_prot);
                    text_lien_ncbi.setText(getLienNCBI(nom_plante, nom_prot));
                    text_nom_gene.setVisible(true);
                    text_lien_ncbi.setVisible(true);

                    ObservableList<CIS> list_CIS = getElementCIS(nom_plante, nom_prot);
                    
                    text_seq_ARN.setText(getARN(nom_prot, nom_plante));
                    text_seq_ADN.setText(getADN(nom_prot, nom_plante));
                  
                    ObservableList<CIS> list_CIS = getElementCIS(nom_plante, nom_prot);

                    col_nom_CIS.setCellValueFactory(
                            new PropertyValueFactory<CIS, String>("Name"));
                    col_pos1_CIS.setCellValueFactory(
                            new PropertyValueFactory<CIS, Integer>("Start_position"));
                    col_pos2_CIS.setCellValueFactory(
                            new PropertyValueFactory<CIS, Integer>("End_position"));
                    col_seq_CIS.setCellValueFactory(
                            new PropertyValueFactory<CIS, String>("Sequence_CIS"));
                    col_nom_CIS.setSortable(false);
                    col_pos1_CIS.setSortable(false);
                    col_pos2_CIS.setSortable(false);
                    col_seq_CIS.setSortable(false);
                    col_seq_CIS.setEditable(true);
                    tab_CIS.setItems(list_CIS);
                } catch (SQLException | ClassNotFoundException ex) {
                    Logger.getLogger(FXMLController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

        //// Onglet ANALYSE ////
        combo_ref_nom_plante.setItems(list_plante);
        try {
            combo_analyse_type.setItems(getTypeProtAna());
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(FXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }

        button_add.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    ajouter_ref(ref_nom_plante.getText(), ref_nom_prot.getText(), ref_seq.getText(), ref_type_prot.getText());

                } catch (SQLException ex) {
                    Logger.getLogger(FXMLController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

        text_lien_ncbi.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String url = text_lien_ncbi.getText();
                WebEngine webEngine = web_zone.getEngine();
                webEngine.load(url);
            }
        });

        combo_ref_nom_plante.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                combo_ref_type_prot.getItems().removeAll(combo_type_prot.getItems());
                try {
                    combo_ref_type_prot.setItems(getAllTypeProt());
                } catch (SQLException | ClassNotFoundException ex) {
                    Logger.getLogger(FXMLController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

        add_plante.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (combo_ref_nom_plante.isVisible()) {
                    combo_ref_nom_plante.setVisible(false);
                    ref_nom_plante.setVisible(true);
                } else {
                    combo_ref_nom_plante.setVisible(true);
                    ref_nom_plante.setVisible(false);
                }
            }
        });

        add_type_prot.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (combo_ref_type_prot.isVisible()) {
                    combo_ref_type_prot.setVisible(false);
                    ref_type_prot.setVisible(true);
                } else {
                    combo_ref_type_prot.setVisible(true);
                    ref_type_prot.setVisible(false);
                }
            }
        });
    }


    public void ajouter_ref(String nom_plante, String nom_prot, String fasta, String type_prot) throws SQLException {
        Connection con = dataAccess.getCon();
        //execute query ...
        Statement stmt = con.createStatement();
        // Verification de l'existence des données en BDD
        ResultSet rs = stmt.executeQuery("select * from sequences natural join plante " 
                                       + "natural join type_proteine where nom_plante ='" 
                                       + nom_plante + "' and nom_prot = '" + nom_prot + "' "
                                       + "and nom_type = '" + type_prot + "'");
        // Si la plante n'existe pas 
        int id_plante;
        if (rs == null) {
            // On récupère l'id_plante ...
            Statement stmt_id = con.createStatement();
            ResultSet rs_id = stmt_id.executeQuery("select id_plante from PLANTE where nom_plante = '" + nom_plante + "'");
            id_plante = rs_id.getInt(1);
            // On insère ...
            Statement stmt_final = con.createStatement();
            ResultSet rs_final = stmt_final.executeQuery("insert into SEQUENCES (nom_prot, seq_ADN, id_plante, nom_type) values ('" + nom_prot + ",'" + fasta + "," + id_plante + ",'" + type_prot + "'");
        } else {
            ref_seq.setText("SEQUENCE DEJA PRESENTE EN BASE DE DONNEES.");
        }
//        Statement stmt1 = con.createStatement();
//        Statement stmt2 = con.createStatement();
//        Statement stmt3 = con.createStatement();
//        Statement stmt4 = con.createStatement();
//        Statement stmt5 = con.createStatement();
        
        
//        System.out.println("le nom de la plante " + nom_plante);
//        ResultSet rs = stmt.executeQuery("select id_plante from PLANTE where nom_plante= '" + nom_plante + "'");
//        ResultSet rs1 = stmt1.executeQuery("select * from TYPE_PROTEINE where nom_type= '" + type_prot + "'");
//
//        String id_plante = "";
//        if (rs.next()) {
//            System.out.println("plante existe");
//            id_plante = rs.getString(1);
//        } else {
//            System.out.println("pas de plante");
//            int rs3 = stmt3.executeUpdate("Insert into PLANTE (nom_plante) values ('" + nom_plante + "')");
//            ResultSet rs4 = stmt4.executeQuery("select id_plante from PLANTE where nom_plante = '" + nom_plante + "'");
//            if (rs4.next()) {
//                id_plante = rs4.getString(1);
//            }
//        }
//        if (rs1.next()) {
//            System.out.println("type prot existe");
//            int rs2 = stmt2.executeUpdate("Insert into SEQUENCES (nom_prot, seq_prot, seq_ADN, nom_accession, lien, details, id_plante, nom_type, id_prom) values ('" + nom_prot + "', NULL,'" + fasta + "', NULL, NULL, NULL, '" + id_plante + "','" + type_prot + "', NULL)");
//        } else {
//            System.out.println("pas de type prot");
//            int rs5 = stmt5.executeUpdate("Insert into TYPE_PROTEINE values ('" + nom_prot + "')");
//            int rs2 = stmt2.executeUpdate("Insert into SEQUENCES (nom_prot, seq_prot, seq_ADN, nom_accession, lien, details, id_plante, nom_type, id_prom) values ('" + nom_prot + "', NULL,'" + fasta + "', NULL, NULL, NULL, '" + id_plante + "','" + type_prot + "', NULL)");
//        }

    }

    public ObservableList<String> getAllTypeProt() throws SQLException, ClassNotFoundException {
        Connection con = dataAccess.getCon();
        ObservableList<String> list = FXCollections.observableArrayList();
        // execute query
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery("select * from TYPE_PROTEINE;");
        while (rs.next()) {
            list.add(rs.getString(1));
        }
        Collections.sort(list);
        return list;
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
        ResultSet rs = stmt.executeQuery("select DISTINCT * from SEQUENCES join TYPE_PROTEINE using (nom_type) join PLANTE using (id_plante) where nom_plante = '" + nom_plante + "'");
        while (rs.next()) {
            list_type_prot.add(rs.getString(2));
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

    public ObservableList<String> getNomProt(String nom_plante, String nom_type_prot) throws SQLException, ClassNotFoundException {
        Connection con = dataAccess.getCon();
        ObservableList<String> list_prot = FXCollections.observableArrayList();
        //execute query
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery("select * from SEQUENCES natural join TYPE_PROTEINE natural join PLANTE where nom_plante = '" + nom_plante + "' and nom_type = '" + nom_type_prot + "'");
        while (rs.next()) {
            list_prot.add(rs.getString(4));
        }
        Collections.sort(list_prot);
        return list_prot;
    }

    public String getLienNCBI(String nom_plante, String nom_prot) throws SQLException, ClassNotFoundException {
        Connection con = dataAccess.getCon();
        String lien = "Aucun résultats en base de données.";
        //execute query
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery("select lien from SEQUENCES natural join PLANTE where nom_plante = '" + nom_plante + "' and nom_prot = '" + nom_prot + "'");
        while (rs.next()) {
            lien = rs.getString(1);
        }
        return lien;
    }

    public ObservableList<String> GenerateSeqTypePro(ConnectionDataBase dataAccess, String nom_type_plante) throws SQLException, ClassNotFoundException {
        Connection con = dataAccess.getCon();
        ObservableList<String> list = FXCollections.observableArrayList();
        // execute query
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery("select DISTINCT nom_plante from PLANTE where TYPE_PROTEINE.nom_type ='" + nom_type_plante + "'");
        while (rs.next()) {
            list.add(rs.getString(1));
        }
        Collections.sort(list);
        return list;
    }

    public ObservableList<CIS> getElementCIS(String nom_plante, String nom_prot) throws SQLException, ClassNotFoundException {
        Connection con = dataAccess.getCon();
        ObservableList<CIS> list_CIS = FXCollections.observableArrayList();
        //execute query
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery("Select * from CIS natural join SEQUENCES natural JOIN PLANTE where nom_plante = '" + nom_plante + "' and nom_prot = '" + nom_prot + "'");
        while (rs.next()) {
            list_CIS.add(new CIS(rs.getString(4), rs.getString(5), getInt(6), getInt(7)));
        }
        return list_CIS;
    }

    public String getARN(String nom_prot, String nom_plante) throws SQLException, ClassNotFoundException {
        Connection con = dataAccess.getCon();
        String sequence_ARN = "Aucun résultats en base de données.";
        //execute query
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT seq_prot from SEQUENCES natural join PLANTE where nom_plante = '" + nom_plante + "' and nom_prot = '" + nom_prot + "'");
        while (rs.next()) {
            sequence_ARN = rs.getString(1);
        }
        return sequence_ARN;
    }

    public String getADN(String nom_prot, String nom_plante) throws SQLException, ClassNotFoundException {
        Connection con = dataAccess.getCon();
        String sequence_ARN = "Aucun résultats en base de données.";
        //execute query
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT seq_ADN from SEQUENCES natural join PLANTE where nom_plante = '" + nom_plante + "' and nom_prot = '" + nom_prot + "'");
        while (rs.next()) {
            sequence_ARN = rs.getString(1);
        }
        return sequence_ARN;
    }
    
    @FXML
    void View_tree(MouseEvent event){
//        Generate_tree tree = new Generate_tree(clustal);
//        tree.submit();
    }
    
    @FXML
    void Submission(MouseEvent event){
        
    }

    public String getHTMLStringFromList(List<List<String>> list_seq) {
        String seq = "";
        for (List<String> list : list_seq) {
            for (String ligne : list) {
                seq += list + "<br>" + ligne + "<br/>";
            }
        }
        return seq;
    }

    @FXML
    void launchBlast(MouseEvent event) throws InterruptedException, IOException, SQLException {

        Connection con = dataAccess.getCon();
        ObservableList<String> refSeqAra = FXCollections.observableArrayList();
        ObservableList<String> refProAra = FXCollections.observableArrayList();
        // execute query
        Statement stmt = con.createStatement();

        ResultSet rs = stmt.executeQuery("Select seq_ADN,nom_prot,seq_prot from SEQUENCES where id_plante = (Select id_plante from PLANTE where nom_plante= 'Arabidopsis thaliana') and nom_type ='" + combo_analyse_type.getSelectionModel().getSelectedItem().toString() + "'");
        while (rs.next()) {
            System.out.println("Dans la boucle while");
            refSeqAra.add(">" + rs.getString(2) + "\n" + rs.getString(1) + "\n");
            refProAra.add(">" + rs.getString(2) + "\n" + rs.getString(3) + "\n");
        }
        Collections.sort(refSeqAra);
        LOGGER.info("list " + refSeqAra.size());
        Blast blast = new Blast();
        List<String> blastResult = blast.search(getSeq_nom_plante(), refSeqAra);

        ResultFile file = new ResultFile();
//        file.readFile();
        Clustal clustal = new Clustal();

        clustal.submit(blastResult, refProAra);

        Generate_tree tree = new Generate_tree(clustal.getTree());
        tree.submit();

        combo_analyse_type.setDisable(true);
        seq_nom_plante1.setDisable(true);
        button_search.setDisable(true);
        button_search_silent.setDisable(true);
        progress_indicator.setVisible(true);

//        ////   Arbre  /////
        progress_indicator.setVisible(false);

//        Clustal clustal = new Clustal();
//        clustal.submit(blastResult);
//        Generate_tree tree = new Generate_tree(clustal.getTree());

//        String clustal = "(\n"
//                + "(\n"
//                + "(\n"
//                + "(\n"
//                + "(\n"
//                + "(\n"
//                + "KEH42003.1_23-563:0.00122,\n"
//                + "XP_024633157.1_1-504:-0.00122)\n"
//                + ":0.10261,\n"
//                + "(\n"
//                + "XP_003625858.2_26-569:-0.00066,\n"
//                + "ABD32689.1_26-566:0.00066)\n"
//                + ":0.10839)\n"
//                + ":0.17045,\n"
//                + "(\n"
//                + "(\n"
//                + "(\n"
//                + "AES98262.2_22-570:0.00058,\n"
//                + "XP_024639080.1_22-573:-0.00058)\n"
//                + ":0.00112,\n"
//                + "RHN56258.1_11-362:0.00172)\n"
//                + ":0.00570,\n"
//                + "XP_003615304.3_2-520:-0.00570)\n"
//                + ":0.18619)\n"
//                + ":0.06235,\n"
//                + "(\n"
//                + "(\n"
//                + "KEH18389.1_25-408:0.00615,\n"
//                + "XP_013444360.1_25-571:-0.00355)\n"
//                + ":0.00327,\n"
//                + "KEH18388.1_16-525:-0.00313)\n"
//                + ":0.09633)\n"
//                + ":0.10299,\n"
//                + "AFK37518.1_24-571:0.00306)\n"
//                + ":0.00066,\n"
//                + "(\n"
//                + "ABD28503.1_24-568:0.00012,\n"
//                + "XP_024632592.1_24-571:-0.00012)\n"
//                + ":0.00013,\n"
//                + "KEH39515.1_1-505:-0.00013);";

        initTable();
        /// La liste de séquences à récupérer de je ne sais où pour remplacer le truc d'en dessous
        ObservableList<Sequence> MaListTest = FXCollections.observableArrayList();

        List<String> seq1 = FXCollections.observableArrayList();
        seq1.add("AtCWIN3 Blablabla Nom du gene1");
        seq1.add("ATGACGTGAGTGATGCTGTAGTAGTAGATGTAGCATGCATGTAGATGCTAG");
        seq1.add("ACGACGTGACATGCATGCATGCATGATGCATG");

        List<String> seq2 = FXCollections.observableArrayList();
        seq2.add("AtCWIN3 Blablabla Nom du gene1");
        seq2.add("ERHHERZVBZOVREOVZNEIVNZEINVZEROCJVZNEORNVZOEVNHZOERHV");
        seq2.add("ZERIBVZIERBNVIZERNROGEHNZOGNVZEORNVGZOERNHIZEBFCZREBIV");

        List<List<String>> list_test_arbre = FXCollections.observableArrayList();
        list_test_arbre.add(seq1);
        list_test_arbre.add(seq2);

        System.out.println(getHTMLStringFromList(list_test_arbre));

        // !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! 
        // On peut executer du code javascript dans le web browser donc potentiellement ajouter du texte dans phylo.io (liste de sequences)
        // id du textarea phylo.io = newickInSingle.
        // A HTML Content with a javascript function.
        WebEngine webEngineArbre = webview_arbre.getEngine();
        webEngineArbre.setJavaScriptEnabled(true);

        String HTML_STRING;
        HTML_STRING
                = "<html>"
                + "<head> "
                + "  <script language='javascript'> "
                + "     function setTextArbre()  { "
                + "       document.getElementById(\"newickInSingle\").value = "
                + getHTMLStringFromList(list_test_arbre)
                + "     } "
                + "  </script> "
                + "</head> "
                + "</html> ";

        webEngineArbre.load("http://phylo.io/index.html");
        webEngineArbre.executeScript("     function setTextArbre()  { "
                + "       document.getElementById(\"newickInSingle\").value = "
                + getHTMLStringFromList(list_test_arbre)
                + "     } ");

        MaListTest.add(new Sequence(new CheckBox(), "Le nom de la sequence", "elle est cool"));
        System.out.println("Liste " + MaListTest.get(0).getNom());
        System.out.println("Liste " + MaListTest.get(0).getDetails());
        System.out.println("Liste " + MaListTest.get(0).getSelection());

        loadData(MaListTest);
        tab_arbre.setVisible(true);
        text_arbre.setVisible(true);
        button_arbre.setVisible(true);
        text_info_arbre.setVisible(true);
        button_soumettre.setVisible(true);
        //Place place = new Place();
        //          place.tBlastN(getSeq_nom_plante());
        //          place.place();
        //        file.deleteFile();
    }

    private void initTable() {
        initColumn();
    }

    private void initColumn() {
        col_check_arbre.setCellValueFactory(new PropertyValueFactory<Sequence, CheckBox>("selection"));
        col_nom_arbre.setCellValueFactory(new PropertyValueFactory<Sequence, String>("nom"));
        col_details_arbre.setCellValueFactory(new PropertyValueFactory<Sequence, String>("details"));
    }

    /**
     * loadData permet mettre les données dans le tableview
     */
    private void loadData(ObservableList<Sequence> ListSeq) {
        System.out.println("Liste " + ListSeq.get(0).getNom());
        System.out.println("Liste " + ListSeq.get(0).getDetails());
        System.out.println("Liste " + ListSeq.get(0).getSelection());

        System.out.println("arbre " + tab_arbre);
        tab_arbre.setItems(ListSeq);
    }

    public String getSeq_nom_plante() {
        return seq_nom_plante1.getText();
    }

    public String getSeq() {
        return ref_seq.getText();
    }

    /// POP HELP ///
    public void help_nom_plante(MouseEvent event) throws IOException {
        info_nom_plante = new Tooltip();
        info_nom_plante.setText("Renseignez le nom complet de la \n "
                + "plante en utilisant de préférence son nom dans \n"
                + "la nomenclature officielle (latin).");
        pop_nom_plante.setTooltip(info_nom_plante);
    }

    public void help_type_prot(MouseEvent event) throws IOException {
        info_type_prot = new Tooltip();
        info_type_prot.setText("Renseignez le type de protéine concernée. \n"
                + "Par exemple : invertase. Si ce dernier est déjà enregistré \n"
                + "en base de données, utilisez la fonction d'auto-remplissage.");
        pop_type_prot.setTooltip(info_type_prot);
    }

    public void help_nom_prot(MouseEvent event) throws IOException {
        info_nom_prot = new Tooltip();
        info_nom_prot.setText("Renseignez le nom complet de la \n "
                + "protéine ou celui utilisé dans la nomenclature \n"
                + "officielle.");
        pop_nom_prot.setTooltip(info_nom_prot);
    }
}
