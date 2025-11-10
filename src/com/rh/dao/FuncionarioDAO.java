package com.rh.dao;

import com.rh.model.Funcionario;
import java.util.List;

public interface FuncionarioDAO {
    List<Funcionario> listar() throws DAOException;
    Funcionario buscarPorId(int id) throws DAOException;
    void crear(Funcionario f) throws DAOException;
    void actualizar(Funcionario f) throws DAOException;
    void eliminar(int id) throws DAOException;
}
