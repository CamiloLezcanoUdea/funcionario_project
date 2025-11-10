package com.rh.app;

import com.rh.model.Funcionario;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class FuncionarioFormDialog extends JDialog {
    private boolean saved = false;
    private Funcionario funcionario;

    private JTextField txtTipo, txtNumero, txtNombres, txtApellidos, txtEstado, txtSexo, txtDireccion, txtTelefono, txtFecha;

    public FuncionarioFormDialog(Frame owner, Funcionario f) {
        super(owner, true);
        this.funcionario = f == null ? new Funcionario() : f;
        setTitle(f == null ? "Crear Funcionario" : "Editar Funcionario");
        initUI();
        pack();
        setLocationRelativeTo(owner);
    }

    private void initUI() {
        setLayout(new BorderLayout());
        JPanel p = new JPanel(new GridLayout(0, 2, 6, 6));

        txtTipo = new JTextField(funcionario.getTipoIdentificacion());
        txtNumero = new JTextField(funcionario.getNumeroIdentificacion());
        txtNombres = new JTextField(funcionario.getNombres());
        txtApellidos = new JTextField(funcionario.getApellidos());
        txtEstado = new JTextField(funcionario.getEstadoCivil());
        txtSexo = new JTextField(funcionario.getSexo());
        txtDireccion = new JTextField(funcionario.getDireccion());
        txtTelefono = new JTextField(funcionario.getTelefono());
        txtFecha = new JTextField(funcionario.getFechaNacimiento() == null ? "" : funcionario.getFechaNacimiento().toString());

        p.add(new JLabel("Tipo Identificación:")); p.add(txtTipo);
        p.add(new JLabel("Número Identificación:")); p.add(txtNumero);
        p.add(new JLabel("Nombres:")); p.add(txtNombres);
        p.add(new JLabel("Apellidos:")); p.add(txtApellidos);
        p.add(new JLabel("Estado Civil:")); p.add(txtEstado);
        p.add(new JLabel("Sexo (M/F):")); p.add(txtSexo);
        p.add(new JLabel("Dirección:")); p.add(txtDireccion);
        p.add(new JLabel("Teléfono:")); p.add(txtTelefono);
        p.add(new JLabel("Fecha Nacimiento (YYYY-MM-DD):")); p.add(txtFecha);

        JPanel botones = new JPanel();
        JButton btnGuardar = new JButton("Guardar");
        JButton btnCancelar = new JButton("Cancelar");

        btnGuardar.addActionListener(e -> onSave());
        btnCancelar.addActionListener(e -> dispose());

        botones.add(btnGuardar);
        botones.add(btnCancelar);

        add(p, BorderLayout.CENTER);
        add(botones, BorderLayout.SOUTH);
    }

    private void onSave() {
        if (txtNumero.getText().trim().isEmpty() || txtNombres.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Número y nombres son obligatorios", "Validación", JOptionPane.WARNING_MESSAGE);
            return;
        }

        funcionario.setTipoIdentificacion(txtTipo.getText().trim());
        funcionario.setNumeroIdentificacion(txtNumero.getText().trim());
        funcionario.setNombres(txtNombres.getText().trim());
        funcionario.setApellidos(txtApellidos.getText().trim());
        funcionario.setEstadoCivil(txtEstado.getText().trim());
        funcionario.setSexo(txtSexo.getText().trim());
        funcionario.setDireccion(txtDireccion.getText().trim());
        funcionario.setTelefono(txtTelefono.getText().trim());

        String f = txtFecha.getText().trim();
        if (!f.isEmpty()) {
            try {
                funcionario.setFechaNacimiento(LocalDate.parse(f));
            } catch (DateTimeParseException ex) {
                JOptionPane.showMessageDialog(this, "Formato de fecha inválido. Use YYYY-MM-DD.", "Validación", JOptionPane.WARNING_MESSAGE);
                return;
            }
        } else {
            funcionario.setFechaNacimiento(null);
        }

        saved = true;
        dispose();
    }

    public boolean isSaved() {
        return saved;
    }

    public Funcionario getFuncionario() {
        return funcionario;
    }
}
