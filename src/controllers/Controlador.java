/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import models.Persona;
import models.PersonaDAO;
import views.Main;

/**
 * 
 * @author Alexander Granados <alexander.granados.dev@gmail.com>
 */
public class Controlador implements ActionListener{
    
    PersonaDAO dao = new PersonaDAO();
    Persona p = new Persona();
    Main vista = new Main();
    DefaultTableModel modelo = new DefaultTableModel();
    
    public Controlador(Main v){
        this.vista = v;
        this.vista.btnListar.addActionListener(this);
        this.vista.btnAgregar.addActionListener(this);
        this.vista.btnModificar.addActionListener(this);
        this.vista.btnEditar.addActionListener(this);
        this.vista.btnEliminar.addActionListener(this);
        
        listar(vista.tablaDatos);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
       if(e.getSource() == vista.btnListar){
           limpiarTabla();
           listar(vista.tablaDatos);
       }
       if(e.getSource() == vista.btnAgregar){
           agregar();
           limpiarTabla();
           listar(vista.tablaDatos);
       }
       
       if(e.getSource() == vista.btnModificar){
           int fila = vista.tablaDatos.getSelectedRow();
           
           if(fila == -1){
               JOptionPane.showMessageDialog(vista, "Debe seleccionar fila");
           }else{
               
               String dni = (String)vista.tablaDatos.getValueAt(fila, 1);
               String nombre = (String)vista.tablaDatos.getValueAt(fila, 2);
               
               vista.txtDni.setText(dni);
               vista.txtNombre.setText(nombre);
           }
       }
       
       if(e.getSource() == vista.btnEditar){
           editar();
           limpiarTabla();
           listar(vista.tablaDatos);
       }
       
       
       if(e.getSource() == vista.btnEliminar){
          eliminar();
          limpiarTabla();
           listar(vista.tablaDatos);
       }
    }
    
    public void eliminar(){
        int fila = vista.tablaDatos.getSelectedRow();
        if(fila == -1){
            JOptionPane.showMessageDialog(vista, "Debe seleccionar usuario");
        }else{
            int id = Integer.parseInt((String)vista.tablaDatos.getValueAt(fila, 0).toString());
            dao.eliminar(id);
            JOptionPane.showMessageDialog(vista, "usuario eliminado");
        }
    }
    
    
    public void limpiarTabla(){
        vista.txtDni.setText("");
        vista.txtNombre.setText("");
        for (int i = 0; i < vista.tablaDatos.getRowCount(); i++) {
            modelo.removeRow(i);
            
            i -= 1;
        }
    }
    
    
    public void editar(){
        int fila = vista.tablaDatos.getSelectedRow();
        int id = (int)vista.tablaDatos.getValueAt(fila, 0);
        String dni = vista.txtDni.getText();
        String nombre = vista.txtNombre.getText();
        
        p.setId(id);
        p.setDni(dni);
        p.setNombre(nombre);
        
        int r = dao.actualizar(p);
        if(r == 1){
            JOptionPane.showMessageDialog(vista, "El usuario se actualizo");
        }else{
            JOptionPane.showMessageDialog(vista, "Error");
        }
    }
    
    public void agregar(){
        String dni = vista.txtDni.getText();
        String nombre = vista.txtNombre.getText();
        p.setDni(dni);
        p.setNombre(nombre);
        int r = dao.agregar(p);
        
        if(r == 1){
            JOptionPane.showMessageDialog(vista, "Usuario agregado");
        }else{
            JOptionPane.showMessageDialog(vista, "Error");
        }
    }
    
    
    
    public void listar(JTable tabla){
    
        modelo = (DefaultTableModel)tabla.getModel();
        List<Persona>lista = dao.listar();
        Object[] object = new Object[3];
        for (int i = 0; i < lista.size(); i++){
            object[0] = lista.get(i).getId();
            object[1] = lista.get(i).getDni();
            object[2] = lista.get(i).getNombre();
            modelo.addRow(object);
        }
        
        vista.tablaDatos.setModel(modelo);
    }
    
}
