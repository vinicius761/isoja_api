package com.example.demo.Carreta;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Repository
public class CarretaRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public CarretaRepository(@Qualifier("secondaryNamedParameterJdbcTemplate") NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<Carreta> rowMapper = new RowMapper<Carreta>() {
        @Override
        public Carreta mapRow(ResultSet rs, int rowNum) throws SQLException {
            Carreta carreta = new Carreta();
            carreta.setId(rs.getLong("id"));
            carreta.setPlaca(rs.getString("placa"));
            carreta.setRenavam(rs.getString("renavam"));
            carreta.setTipo(rs.getString("tipo"));
            carreta.setEixos(rs.getObject("eixos", Integer.class));
            carreta.setCapacidadeCargaKg(rs.getBigDecimal("capacidade_carga_kg"));
            carreta.setAnoFabricacao(rs.getObject("ano_fabricacao", Integer.class));
            
            // Leitura segura de int4 (INTEGER) para Long
            Long idProprietario = rs.getObject("id_proprietario") != null ? rs.getLong("id_proprietario") : null;
            carreta.setIdProprietario(idProprietario);

            return carreta;
        }
    };

    public List<Carreta> findAll() {
        String sql = "SELECT * FROM carretas";
        return jdbcTemplate.query(sql, rowMapper);
    }

    public Optional<Carreta> findById(Long id) {
        String sql = "SELECT * FROM carretas WHERE id = :id";
        MapSqlParameterSource params = new MapSqlParameterSource("id", id);
        List<Carreta> result = jdbcTemplate.query(sql, params, rowMapper);
        return result.stream().findFirst();
    }

    public Carreta save(Carreta carreta) {
        String sql = "INSERT INTO carretas (placa, renavam, tipo, eixos, capacidade_carga_kg, ano_fabricacao, id_proprietario) " +
                     "VALUES (:placa, :renavam, :tipo, :eixos, :capacidadeCargaKg, :anoFabricacao, :idProprietario)";

        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("placa", carreta.getPlaca())
                .addValue("renavam", carreta.getRenavam())
                .addValue("tipo", carreta.getTipo())
                .addValue("eixos", carreta.getEixos())
                .addValue("capacidadeCargaKg", carreta.getCapacidadeCargaKg())
                .addValue("anoFabricacao", carreta.getAnoFabricacao())
                .addValue("idProprietario", carreta.getIdProprietario());

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(sql, params, keyHolder, new String[]{"id"});

        if (keyHolder.getKey() != null) {
            carreta.setId(keyHolder.getKey().longValue());
        }
        return carreta;
    }

    public int update(Long id, Carreta carreta) {
        String sql = "UPDATE carretas SET placa = :placa, renavam = :renavam, tipo = :tipo, " +
                     "eixos = :eixos, capacidade_carga_kg = :capacidadeCargaKg, " +
                     "ano_fabricacao = :anoFabricacao, id_proprietario = :idProprietario " +
                     "WHERE id = :id";

        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("id", id)
                .addValue("placa", carreta.getPlaca())
                .addValue("renavam", carreta.getRenavam())
                .addValue("tipo", carreta.getTipo())
                .addValue("eixos", carreta.getEixos())
                .addValue("capacidadeCargaKg", carreta.getCapacidadeCargaKg())
                .addValue("anoFabricacao", carreta.getAnoFabricacao())
                .addValue("idProprietario", carreta.getIdProprietario());

        return jdbcTemplate.update(sql, params);
    }

    public int deleteById(Long id) {
        String sql = "DELETE FROM carretas WHERE id = :id";
        MapSqlParameterSource params = new MapSqlParameterSource("id", id);
        return jdbcTemplate.update(sql, params);
    }
}