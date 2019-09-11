/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.sequence_comparator2;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 *
 * @author Natasha
 */
public class SelectionSeq {
    @FXML 
    private TableView<Sequence> tab_arbre;
    @FXML
    private TableColumn<Sequence,CheckBox> col_check_arbre;
    @FXML
    private TableColumn<Sequence,String> col_nom_arbre;
    @FXML
    private TableColumn<Sequence,String> col_details_arbre; 
    
    
    private void initTable(){
        initColumn();
    }
    
    private void initColumn(){
        col_check_arbre.setCellValueFactory(new PropertyValueFactory<Sequence, CheckBox>("selection"));
        col_nom_arbre.setCellValueFactory(new PropertyValueFactory<Sequence, String>("nom"));
        col_details_arbre.setCellValueFactory(new PropertyValueFactory<Sequence, String>("details"));
    }

        
    /**
     * loadData permet mettre les données dans le tableview
     */
    private void loadData(ObservableList<Sequence> data_commande_att) {
        tab_arbre.setItems(data_commande_att);
    }

//    public ObservableList<Sequence> getData_commande_att() {
//        return data_commande_att;
//    }

    public TableView<Sequence> getTab_attente() {
        return tab_arbre;
    }
    
}
