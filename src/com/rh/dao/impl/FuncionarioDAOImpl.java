package com.rh.dao.impl;

import com.rh.dao.DAOException;
import com.rh.dao.FuncionarioDAO;
import com.rh.model.Funcionario;
import com.rh.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FuncionarioDAOImpl implements FuncionarioDAO {
    private final Connection conn;

    public FuncionarioDAOImpl() throws DAOException {
        try {
            this.conn = DBConnection.getConnection();
        } catch (SQLException ex) {
            throw new DAOException("Error al obtener conexión", ex);
        }
    }

    @Override
    public List<Funcionario> listar() throws DAOException {
        String sql = "SELECT * FROM funcionario ORDER BY id";
        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            List<Funcionario> lista = new ArrayList<>();
            while (rs.next()) {
                lista.add(mapRow(rs));
            }
            return lista;
        } catch (SQLException ex) {
            throw new DAOException("Error listando funcionarios", ex);
        }
    }

    @Override
    public Funcionario buscarPorId(int id) throws DAOException {
        String sql = "SELECT * FROM funcionario WHERE id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? mapRow(rs) : null;
            }
        } catch (SQLException ex) {
            throw new DAOException("Error buscando funcionario", ex);
        }
    }

    @Override
    public void crear(Funcionario f) throws DAOException {
        String sql = "INSERT INTO funcionario (tipo_identificacion, numero_identificacion, nombres, apellidos, estado_civil, sexo, direccion, telefono, fecha_nacimiento) VALUES (?,?,?,?,?,?,?,?,?)";
        try (PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, f.getTipoIdentificacion());
            ps.setString(2, f.getNumeroIdentificacion());
            ps.setString(3, f.getNombres());
            ps.setString(4, f.getApellidos());
            ps.setString(5, f.getEstadoCivil());
            ps.setString(6, f.getSexo());
            ps.setString(7, f.getDireccion());
            ps.setString(8, f.getTelefono());
            ps.setDate(9, f.getFechaNacimiento() == null ? null : Date.valueOf(f.getFechaNacimiento()));
            ps.executeUpdate();
        } catch (SQLException ex) {
            throw new DAOException("Error creando funcionario", ex);
        }
    }

    @Override
    public void actualizar(Funcionario f) throws DAOException {
        String sql = "UPDATE funcionario SET tipo_identificacion=?, numero_identificacion=?, nombres=?, apellidos=?, estado_civil=?, sexo=?, direccion=?, telefono=?, fecha_nacimiento=? WHERE id=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, f.getTipoIdentificacion());
            ps.setString(2, f.getNumeroIdentificacion());
            ps.setString(3, f.getNombres());
            ps.setString(4, f.getApellidos());
            ps.setString(5, f.getEstadoCivil());
            ps.setString(6, f.getSexo());
            ps.setString(7, f.getDireccion());
            ps.setString(8, f.getTelefono());
            ps.setDate(9, f.getFechaNacimiento() == null ? null : Date.valueOf(f.getFechaNacimiento()));
            ps.setInt(10, f.getId());
            ps.executeUpdate();
        } catch (SQLException ex) {
            throw new DAOException("Error actualizando funcionario", ex);
        }
    }

    @Override
    public void eliminar(int id) throws DAOException {
        String sql = "DELETE FROM funcionario WHERE id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException ex) {
            throw new DAOException("Error eliminando funcionario", ex);
        }
    }

    private Funcionario mapRow(ResultSet rs) throws SQLException {
        Funcionario f = new Funcionario();
        f.setId(rs.getInt("id"));
        f.setTipoIdentificacion(rs.getString("tipo_identificacion"));
        f.setNumeroIdentificacion(rs.getString("numero_identificacion"));
        f.setNombres(rs.getString("nombres"));
        f.setApellidos(rs.getString("apellidos"));
        f.setEstadoCivil(rs.getString("estado_civil"));
        f.setSexo(rs.getString("sexo"));
        f.setDireccion(rs.getString("direccion"));
        f.setTelefono(rs.getString("telefono"));
        Date d = rs.getDate("fecha_nacimiento");
        if (d != null) f.setFechaNacimiento(d.toLocalDate());
        return f;
    }
}
