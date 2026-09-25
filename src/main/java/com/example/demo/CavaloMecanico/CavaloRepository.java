package com.example.demo.CavaloMecanico;


import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

@Repository
public class CavaloRepository {

    private final JdbcTemplate jdbcTemplate;

    public CavaloRepository(@Qualifier("secondaryJdbcTemplate") JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<CavaloMecanico> rowMapper = (rs, rowNum) -> {
        CavaloMecanico cavalo = new CavaloMecanico();
        cavalo.setId(rs.getInt("id"));
        cavalo.setPlaca(rs.getString("placa"));
        cavalo.setRenavam(rs.getString("renavam"));
        cavalo.setModelo(rs.getString("modelo"));
        cavalo.setMarca(rs.getString("marca"));
        
        int ano = rs.getInt("ano_fabricacao");
        cavalo.setAnoFabricacao(rs.wasNull() ? null : ano);

        int proprietario = rs.getInt("id_proprietario");
        cavalo.setIdProprietario(rs.wasNull() ? null : proprietario);

        return cavalo;
    };

    public List<CavaloMecanico> findAll() {
        String sql = "SELECT id, placa, renavam, modelo, marca, ano_fabricacao, id_proprietario FROM cavalos";
        return jdbcTemplate.query(sql, rowMapper);
    }

    public Optional<CavaloMecanico> findById(Integer id) {
        String sql = "SELECT id, placa, renavam, modelo, marca, ano_fabricacao, id_proprietario FROM cavalos WHERE id = ?";
        List<CavaloMecanico> result = jdbcTemplate.query(sql, rowMapper, id);
        return result.stream().findFirst();
    }

    public CavaloMecanico save(CavaloMecanico cavalo) {
        String sql = "INSERT INTO cavalos (placa, renavam, modelo, marca, ano_fabricacao, id_proprietario) VALUES (?, ?, ?, ?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, cavalo.getPlaca());
            ps.setString(2, cavalo.getRenavam());
            ps.setString(3, cavalo.getModelo());
            ps.setString(4, cavalo.getMarca());
            
            if (cavalo.getAnoFabricacao() != null) {
                ps.setInt(5, cavalo.getAnoFabricacao());
            } else {
                ps.setNull(5, java.sql.Types.INTEGER);
            }

            if (cavalo.getIdProprietario() != null) {
                ps.setInt(6, cavalo.getIdProprietario());
            } else {
                ps.setNull(6, java.sql.Types.INTEGER);
            }

            return ps;
        }, keyHolder);

        if (keyHolder.getKeys() != null && keyHolder.getKeys().containsKey("id")) {
            cavalo.setId(((Number) keyHolder.getKeys().get("id")).intValue());
        }

        return cavalo;
    }

    public boolean update(Integer id, CavaloMecanico cavalo) {
        String sql = "UPDATE cavalos SET placa = ?, renavam = ?, modelo = ?, marca = ?, ano_fabricacao = ?, id_proprietario = ? WHERE id = ?";
        
        int rowsAffected = jdbcTemplate.update(
            sql,
            cavalo.getPlaca(),
            cavalo.getRenavam(),
            cavalo.getModelo(),
            cavalo.getMarca(),
            cavalo.getAnoFabricacao(),
            cavalo.getIdProprietario(),
            id
        );

        return rowsAffected > 0;
    }

    public boolean deleteById(Integer id) {
        String sql = "DELETE FROM cavalos WHERE id = ?";
        int rowsAffected = jdbcTemplate.update(sql, id);
        return rowsAffected > 0;
    }
}
