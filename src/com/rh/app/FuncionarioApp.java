package com.rh.app;

import com.rh.dao.DAOException;
import com.rh.dao.FuncionarioDAO;
import com.rh.dao.impl.FuncionarioDAOImpl;
import com.rh.model.Funcionario;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class FuncionarioApp extends JFrame {
    private FuncionarioDAO dao;
    private DefaultTableModel tableModel;
    private JTable table;

    public FuncionarioApp() {
        try {
            dao = new FuncionarioDAOImpl();
        } catch (DAOException ex) {
            showError("No se pudo inicializar DAO: " + ex.getMessage());
            System.exit(1);
        }

        setTitle("Gestión de Funcionarios");
        setSize(900, 450);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        initUI();
        loadData();
    }

    private void initUI() {
        tableModel = new DefaultTableModel(
                new Object[]{"ID", "Tipo ID", "Número", "Nombres", "Apellidos", "Sexo", "Teléfono", "Fecha Nac."}, 0
        ) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        table = new JTable(tableModel);
        JScrollPane scroll = new JScrollPane(table);

        JButton btnCrear = new JButton("Crear");
        btnCrear.addActionListener(this::onCrear);

        JButton btnEditar = new JButton("Editar");
        btnEditar.addActionListener(this::onEditar);

        JButton btnEliminar = new JButton("Eliminar");
        btnEliminar.addActionListener(this::onEliminar);

        JButton btnRefrescar = new JButton("Refrescar");
        btnRefrescar.addActionListener(e -> loadData());

        JPanel top = new JPanel();
        top.add(btnCrear);
        top.add(btnEditar);
        top.add(btnEliminar);
        top.add(btnRefrescar);

        getContentPane().add(top, BorderLayout.NORTH);
        getContentPane().add(scroll, BorderLayout.CENTER);
    }

    private void loadData() {
        try {
            List<Funcionario> lista = dao.listar();
            tableModel.setRowCount(0);
            DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            for (Funcionario f : lista) {
                tableModel.addRow(new Object[]{
                        f.getId(),
                        f.getTipoIdentificacion(),
                        f.getNumeroIdentificacion(),
                        f.getNombres(),
                        f.getApellidos(),
                        f.getSexo(),
                        f.getTelefono(),
                        f.getFechaNacimiento() == null ? "" : f.getFechaNacimiento().format(fmt)
                });
            }
        } catch (DAOException ex) {
            showError("Error cargando datos: " + ex.getMessage());
        }
    }

    private void onCrear(ActionEvent e) {
        FuncionarioFormDialog dlg = new FuncionarioFormDialog(this, null);
        dlg.setVisible(true);
        if (dlg.isSaved()) {
            try {
                dao.crear(dlg.getFuncionario());
                loadData();
            } catch (DAOException ex) {
                showError("No se pudo crear: " + ex.getMessage());
            }
        }
    }

    private void onEditar(ActionEvent e) {
        int row = table.getSelectedRow();
        if (row == -1) {
            showError("Seleccione un funcionario");
            return;
        }

        Integer id = (Integer) tableModel.getValueAt(row, 0);
        try {
            Funcionario f = dao.buscarPorId(id);
            if (f == null) {
                showError("Funcionario no existe");
                return;
            }

            FuncionarioFormDialog dlg = new FuncionarioFormDialog(this, f);
            dlg.setVisible(true);

            if (dlg.isSaved()) {
                dao.actualizar(dlg.getFuncionario());
                loadData();
            }
        } catch (DAOException ex) {
            showError("Error editando: " + ex.getMessage());
        }
    }

    private void onEliminar(ActionEvent e) {
        int row = table.getSelectedRow();
        if (row == -1) {
            showError("Seleccione un funcionario");
            return;
        }

        Integer id = (Integer) tableModel.getValueAt(row, 0);
        if (JOptionPane.showConfirmDialog(
                this,
                "¿Eliminar funcionario ID=" + id + "?",
                "Confirmar",
                JOptionPane.YES_NO_OPTION
        ) == JOptionPane.YES_OPTION) {
            try {
                dao.eliminar(id);
                loadData();
            } catch (DAOException ex) {
                showError("Error eliminando: " + ex.getMessage());
            }
        }
    }

    private void showError(String msg) {
        JOptionPane.showMessageDialog(this, msg, "Error", JOptionPane.ERROR_MESSAGE);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new FuncionarioApp().setVisible(true));
    }
}
