package com.example.demo.Acoplamento;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public class AcoplamentoRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public AcoplamentoRepository(@Qualifier("secondaryNamedParameterJdbcTemplate") NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<Acoplamento> rowMapper = new RowMapper<Acoplamento>() {
        @Override
        public Acoplamento mapRow(ResultSet rs, int rowNum) throws SQLException {
            Acoplamento acoplamento = new Acoplamento();
            
            Long id = rs.getObject("id") != null ? rs.getLong("id") : null;
            acoplamento.setId(id);

            Long idCavalo = rs.getObject("id_cavalo") != null ? rs.getLong("id_cavalo") : null;
            acoplamento.setIdCavalo(idCavalo);

            Long idCarreta = rs.getObject("id_carreta") != null ? rs.getLong("id_carreta") : null;
            acoplamento.setIdCarreta(idCarreta);

            acoplamento.setTag(rs.getString("tag"));

            Timestamp dataEngate = rs.getTimestamp("data_engate");
            if (dataEngate != null) {
                acoplamento.setDataEngate(dataEngate.toLocalDateTime());
            }

            Timestamp dataDesengate = rs.getTimestamp("data_desengate");
            if (dataDesengate != null) {
                acoplamento.setDataDesengate(dataDesengate.toLocalDateTime());
            }

            return acoplamento;
        }
    };

    public List<Acoplamento> findAll() {
        String sql = "SELECT * FROM acoplamentos";
        return jdbcTemplate.query(sql, rowMapper);
    }

    public Optional<Acoplamento> findById(Long id) {
        String sql = "SELECT * FROM acoplamentos WHERE id = :id";
        MapSqlParameterSource params = new MapSqlParameterSource("id", id);
        List<Acoplamento> result = jdbcTemplate.query(sql, params, rowMapper);
        return result.stream().findFirst();
    }

    public Acoplamento save(Acoplamento acoplamento) {
        String sql = "INSERT INTO acoplamentos (id_cavalo, id_carreta, tag, data_engate, data_desengate) " +
                     "VALUES (:idCavalo, :idCarreta, :tag, :dataEngate, :dataDesengate)";

        LocalDateTime dataEngate = acoplamento.getDataEngate() != null ? acoplamento.getDataEngate() : LocalDateTime.now();

        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("idCavalo", acoplamento.getIdCavalo())
                .addValue("idCarreta", acoplamento.getIdCarreta())
                .addValue("tag", acoplamento.getTag())
                .addValue("dataEngate", Timestamp.valueOf(dataEngate))
                .addValue("dataDesengate", acoplamento.getDataDesengate() != null ? Timestamp.valueOf(acoplamento.getDataDesengate()) : null);

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(sql, params, keyHolder, new String[]{"id"});

        if (keyHolder.getKey() != null) {
            acoplamento.setId(keyHolder.getKey().longValue());
        }
        acoplamento.setDataEngate(dataEngate);
        return acoplamento;
    }

    public int update(Long id, Acoplamento acoplamento) {
        String sql = "UPDATE acoplamentos SET id_cavalo = :idCavalo, id_carreta = :idCarreta, " +
                     "tag = :tag, data_engate = :dataEngate, data_desengate = :dataDesengate " +
                     "WHERE id = :id";

        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("id", id)
                .addValue("idCavalo", acoplamento.getIdCavalo())
                .addValue("idCarreta", acoplamento.getIdCarreta())
                .addValue("tag", acoplamento.getTag())
                .addValue("dataEngate", acoplamento.getDataEngate() != null ? Timestamp.valueOf(acoplamento.getDataEngate()) : null)
                .addValue("dataDesengate", acoplamento.getDataDesengate() != null ? Timestamp.valueOf(acoplamento.getDataDesengate()) : null);

        return jdbcTemplate.update(sql, params);
    }

    public int deleteById(Long id) {
        String sql = "DELETE FROM acoplamentos WHERE id = :id";
        MapSqlParameterSource params = new MapSqlParameterSource("id", id);
        return jdbcTemplate.update(sql, params);
    }
}