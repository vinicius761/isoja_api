package com.example.demo.Proprietario;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class ProprietarioRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public ProprietarioRepository(@Qualifier("secondaryNamedParameterJdbcTemplate") NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<Proprietario> rowMapper = (rs, rowNum) -> new Proprietario(
            rs.getInt("id"),
            rs.getString("nome"),
            rs.getString("cpf_cnpj"),
            rs.getString("telefone"),
            rs.getString("email")
    );

    public List<Proprietario> findAll() {
        String sql = "SELECT id, nome, cpf_cnpj, telefone, email FROM proprietarios";
        return jdbcTemplate.query(sql, rowMapper);
    }

    public Optional<Proprietario> findById(Integer id) {
        String sql = "SELECT id, nome, cpf_cnpj, telefone, email FROM proprietarios WHERE id= :id";
        MapSqlParameterSource params = new MapSqlParameterSource("id", id);
        return jdbcTemplate.query(sql, params, rowMapper).stream().findFirst();
    }

    // MÉTODO NOVO: Verifica se o CPF/CNPJ já existe
    public boolean existsByCpfCnpj(String cpfCnpj) {
        String sql = "SELECT COUNT(*) FROM proprietarios WHERE cpf_cnpj = :cpfCnpj";
        MapSqlParameterSource params = new MapSqlParameterSource("cpfCnpj", cpfCnpj);
        Integer count = jdbcTemplate.queryForObject(sql, params, Integer.class);
        return count != null && count > 0;
    }

    // MÉTODO NOVO: Verifica duplicidade ao atualizar ignorando o próprio ID
    public boolean existsByCpfCnpjAndIdNot(String cpfCnpj, Integer id) {
        String sql = "SELECT COUNT(*) FROM proprietarios WHERE cpf_cnpj = :cpfCnpj AND id <> :id";
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("cpfCnpj", cpfCnpj)
                .addValue("id", id);
        Integer count = jdbcTemplate.queryForObject(sql, params, Integer.class);
        return count != null && count > 0;
    }

    public Proprietario save(Proprietario proprietario) {
        String sql = "INSERT INTO proprietarios (nome, cpf_cnpj, telefone, email) VALUES (:nome, :cpfCnpj, :telefone, :email)";
        
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("nome", proprietario.getNome())
                .addValue("cpfCnpj", proprietario.getCpfCnpj())
                .addValue("telefone", proprietario.getTelefone())
                .addValue("email", proprietario.getEmail());

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(sql, params, keyHolder, new String[]{"id"});

        if (keyHolder.getKey() != null) {
            proprietario.setId(keyHolder.getKey().intValue());
        }

        return proprietario;
    }

    public boolean update(Integer id, Proprietario proprietario) {
        String sql = "UPDATE proprietarios SET nome = :nome, cpf_cnpj = :cpfCnpj, telefone = :telefone, email = :email WHERE id= :id";
        
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("id", id)
                .addValue("nome", proprietario.getNome())
                .addValue("cpfCnpj", proprietario.getCpfCnpj())
                .addValue("telefone", proprietario.getTelefone())
                .addValue("email", proprietario.getEmail());

        return jdbcTemplate.update(sql, params) > 0;
    }

    public boolean deleteById(Integer id) {
        String sql = "DELETE FROM proprietarios WHERE id= :id";
        MapSqlParameterSource params = new MapSqlParameterSource("id", id);
        return jdbcTemplate.update(sql, params) > 0;
    }
}