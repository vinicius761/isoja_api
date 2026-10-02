package com.example.demo.Veiculo;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class VeiculoRepository {

    private final NamedParameterJdbcTemplate namedJdbcTemplate;
    private final JdbcTemplate jdbcTemplate;

    public VeiculoRepository(
            @Qualifier("secondaryNamedParameterJdbcTemplate") NamedParameterJdbcTemplate namedJdbcTemplate,
            @Qualifier("secondaryJdbcTemplate") JdbcTemplate jdbcTemplate) {
        this.namedJdbcTemplate = namedJdbcTemplate;
        this.jdbcTemplate = jdbcTemplate;
    }

    private String safeString(String value, int maxLength) {
        if (value == null) {
            return "";
        }
        String trimmed = value.trim();
        return trimmed.length() > maxLength ? trimmed.substring(0, maxLength) : trimmed;
    }

    private final RowMapper<Veiculo> rowMapper = (rs, rowNum) -> {
        Veiculo v = new Veiculo();
        v.setDa3Id(rs.getLong("DA3_ID")); // <-- Mapeamento do DA3_ID adicionado
        v.setFilial(rs.getString("DA3_FILIAL"));
        v.setCod(rs.getString("DA3_COD"));
        v.setDescricao(rs.getString("DA3_DESC"));
        v.setPlaca(rs.getString("DA3_PLACA"));
        v.setEstpla(rs.getString("DA3_ESTPLA"));
        v.setCodmun(rs.getString("DA3_CODMUN"));
        v.setCapacn(rs.getDouble("DA3_CAPACN"));
        v.setCapacm(rs.getDouble("DA3_CAPACM"));
        v.setVolmax(rs.getDouble("DA3_VOLMAX"));
        v.setMotori(rs.getString("DA3_MOTORI"));
        v.setLimmax(rs.getDouble("DA3_LIMMAX"));
        v.setAtivo(rs.getString("DA3_ATIVO"));
        v.setQtduni(rs.getDouble("DA3_QTDUNI"));
        v.setUnitiz(rs.getString("DA3_UNITIZ"));
        v.setCodgru(rs.getString("DA3_CODGRU"));
        v.setAltint(rs.getDouble("DA3_ALTINT"));
        v.setLarint(rs.getDouble("DA3_LARINT"));
        v.setComint(rs.getDouble("DA3_COMINT"));
        v.setAltext(rs.getDouble("DA3_ALTEXT"));
        v.setComext(rs.getDouble("DA3_COMEXT"));
        v.setLarext(rs.getDouble("DA3_LAREXT"));
        v.setFilatu(rs.getString("DA3_FILATU"));
        v.setFilvga(rs.getString("DA3_FILVGA"));
        v.setNumvga(rs.getString("DA3_NUMVGA"));
        v.setFrovei(rs.getString("DA3_FROVEI"));
        v.setCodbem(rs.getString("DA3_CODBEM"));
        v.setCodfor(rs.getString("DA3_CODFOR"));
        v.setLojfor(rs.getString("DA3_LOJFOR"));
        v.setMarvei(rs.getString("DA3_MARVEI"));
        v.setCorvei(rs.getString("DA3_CORVEI"));
        v.setAnomod(rs.getString("DA3_ANOMOD"));
        v.setAnofab(rs.getString("DA3_ANOFAB"));
        v.setChassi(rs.getString("DA3_CHASSI"));
        v.setTipvei(rs.getString("DA3_TIPVEI"));
        v.setQtdeix(rs.getDouble("DA3_QTDEIX"));
        v.setBitmap(rs.getString("DA3_BITMAP"));
        v.setVeiras(rs.getString("DA3_VEIRAS"));
        v.setCusto1(rs.getDouble("DA3_CUSTO1"));
        v.setCusto2(rs.getDouble("DA3_CUSTO2"));
        v.setCusto3(rs.getDouble("DA3_CUSTO3"));
        v.setCusto4(rs.getDouble("DA3_CUSTO4"));
        v.setCusto5(rs.getDouble("DA3_CUSTO5"));
        v.setStatus(rs.getString("DA3_STATUS"));
        v.setRenava(rs.getString("DA3_RENAVA"));
        v.setFilbas(rs.getString("DA3_FILBAS"));
        v.setTara(rs.getDouble("DA3_TARA"));
        v.setTipgrp(rs.getString("DA3_TIPGRP"));
        v.setTiptra(rs.getString("DA3_TIPTRA"));
        v.setCodfav(rs.getString("DA3_CODFAV"));
        v.setLojfav(rs.getString("DA3_LOJFAV"));
        v.setLibseg(rs.getString("DA3_LIBSEG"));
        v.setSertms(rs.getString("DA3_SERTMS"));
        v.setDtivsg(rs.getString("DA3_DTIVSG"));
        v.setDtfvsg(rs.getString("DA3_DTFVSG"));
        v.setCiv(rs.getString("DA3_CIV"));
        v.setCipp(rs.getDouble("DA3_CIPP"));
        v.setGstdmd(rs.getString("DA3_GSTDMD"));
        v.setFilprv(rs.getString("DA3_FILPRV"));
        v.setDatprv(rs.getString("DA3_DATPRV"));
        v.setHorprv(rs.getString("DA3_HORPRV"));
        v.setDatsts(rs.getString("DA3_DATSTS"));
        v.setHorsts(rs.getString("DA3_HORSTS"));
        v.setVeloc(rs.getDouble("DA3_VELOC"));
        v.setQteixv(rs.getDouble("DA3_QTEIXV"));
        v.setMunpla(rs.getString("DA3_MUNPLA"));
        v.setRodage(rs.getString("DA3_RODAGE"));
        v.setIntope(rs.getString("DA3_INTOPE"));
        v.setIntegr(rs.getString("DA3_INTEGR"));
        v.setMults(rs.getString("DA3_MULTS"));
        v.setDelet(rs.getString("D_E_L_E_T_"));
        v.setMaxvol(rs.getDouble("DA3_MAXVOL"));
        v.setTag(rs.getString("DA3_TAG"));

        if (rs.getTimestamp("created_at") != null) {
            v.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
        }
        if (rs.getTimestamp("updated_at") != null) {
            v.setUpdatedAt(rs.getTimestamp("updated_at").toLocalDateTime());
        }
        return v;
    };

    public List<Veiculo> findAll() {
        String sql = "SELECT * FROM DA3010 WHERE D_E_L_E_T_ != '*'";
        return jdbcTemplate.query(sql, rowMapper);
    }

    public Optional<Veiculo> findByCod(String cod) {
        String sql = "SELECT * FROM DA3010 WHERE DA3_COD = :cod AND D_E_L_E_T_ != '*'";
        MapSqlParameterSource params = new MapSqlParameterSource("cod", cod);
        List<Veiculo> results = namedJdbcTemplate.query(sql, params, rowMapper);
        return results.stream().findFirst();
    }

    public Optional<Veiculo> findByTag(String tag) {
        String sql = "SELECT * FROM DA3010 WHERE DA3_TAG = :tag AND D_E_L_E_T_ != '*'";
        MapSqlParameterSource params = new MapSqlParameterSource("tag", tag);
        List<Veiculo> results = namedJdbcTemplate.query(sql, params, rowMapper);
        return results.stream().findFirst();
    }

    public void save(Veiculo v) {
        v.setFilial(safeString(v.getFilial(), 6));
        v.setCod(safeString(v.getCod(), 8));
        v.setDescricao(safeString(v.getDescricao(), 30));
        v.setPlaca(safeString(v.getPlaca(), 8));
        v.setEstpla(safeString(v.getEstpla(), 2));
        v.setCodmun(safeString(v.getCodmun(), 5));
        v.setMotori(safeString(v.getMotori(), 6));
        v.setAtivo(safeString(v.getAtivo(), 1));
        v.setUnitiz(safeString(v.getUnitiz(), 6));
        v.setCodgru(safeString(v.getCodgru(), 3));
        v.setFilatu(safeString(v.getFilatu(), 6));
        v.setFilvga(safeString(v.getFilvga(), 6));
        v.setNumvga(safeString(v.getNumvga(), 6));
        v.setFrovei(safeString(v.getFrovei(), 1));
        v.setCodbem(safeString(v.getCodbem(), 16));
        v.setCodfor(safeString(v.getCodfor(), 9));
        v.setLojfor(safeString(v.getLojfor(), 3));
        v.setMarvei(safeString(v.getMarvei(), 2));
        v.setCorvei(safeString(v.getCorvei(), 2));
        v.setAnomod(safeString(v.getAnomod(), 4));
        v.setAnofab(safeString(v.getAnofab(), 4));
        v.setChassi(safeString(v.getChassi(), 20));
        v.setTipvei(safeString(v.getTipvei(), 2));
        v.setBitmap(safeString(v.getBitmap(), 8));
        v.setVeiras(safeString(v.getVeiras(), 1));
        v.setStatus(safeString(v.getStatus(), 1));
        v.setRenava(safeString(v.getRenava(), 11));
        v.setFilbas(safeString(v.getFilbas(), 6));
        v.setTipgrp(safeString(v.getTipgrp(), 2));
        v.setTiptra(safeString(v.getTiptra(), 1));
        v.setCodfav(safeString(v.getCodfav(), 9));
        v.setLojfav(safeString(v.getLojfav(), 3));
        v.setLibseg(safeString(v.getLibseg(), 10));
        v.setSertms(safeString(v.getSertms(), 1));
        v.setDtivsg(safeString(v.getDtivsg(), 8));
        v.setDtfvsg(safeString(v.getDtfvsg(), 8));
        v.setCiv(safeString(v.getCiv(), 10));
        v.setGstdmd(safeString(v.getGstdmd(), 1));
        v.setFilprv(safeString(v.getFilprv(), 6));
        v.setDatprv(safeString(v.getDatprv(), 8));
        v.setHorprv(safeString(v.getHorprv(), 4));
        v.setDatsts(safeString(v.getDatsts(), 8));
        v.setHorsts(safeString(v.getHorsts(), 4));
        v.setMunpla(safeString(v.getMunpla(), 15));
        v.setRodage(safeString(v.getRodage(), 1));
        v.setIntope(safeString(v.getIntope(), 1));
        v.setIntegr(safeString(v.getIntegr(), 1));
        v.setMults(safeString(v.getMults(), 1));
        v.setTag(safeString(v.getTag(), 50));

        String sql = """
            INSERT INTO DA3010 (
                DA3_FILIAL, DA3_COD, DA3_DESC, DA3_PLACA, DA3_ESTPLA, DA3_CODMUN, DA3_CAPACN, DA3_CAPACM, DA3_VOLMAX, DA3_MOTORI,
                DA3_LIMMAX, DA3_ATIVO, DA3_QTDUNI, DA3_UNITIZ, DA3_CODGRU, DA3_ALTINT, DA3_LARINT, DA3_COMINT, DA3_ALTEXT, DA3_COMEXT,
                DA3_LAREXT, DA3_FILATU, DA3_FILVGA, DA3_NUMVGA, DA3_FROVEI, DA3_CODBEM, DA3_CODFOR, DA3_LOJFOR, DA3_MARVEI, DA3_CORVEI,
                DA3_ANOMOD, DA3_ANOFAB, DA3_CHASSI, DA3_TIPVEI, DA3_QTDEIX, DA3_BITMAP, DA3_VEIRAS, DA3_CUSTO1, DA3_CUSTO2, DA3_CUSTO3,
                DA3_CUSTO4, DA3_CUSTO5, DA3_STATUS, DA3_RENAVA, DA3_FILBAS, DA3_TARA, DA3_TIPGRP, DA3_TIPTRA, DA3_CODFAV, DA3_LOJFAV,
                DA3_LIBSEG, DA3_SERTMS, DA3_DTIVSG, DA3_DTFVSG, DA3_CIV, DA3_CIPP, DA3_GSTDMD, DA3_FILPRV, DA3_DATPRV, DA3_HORPRV,
                DA3_DATSTS, DA3_HORSTS, DA3_VELOC, DA3_QTEIXV, DA3_MUNPLA, DA3_RODAGE, DA3_INTOPE, DA3_INTEGR, DA3_MULTS, DA3_TAG,
                D_E_L_E_T_, DA3_MAXVOL, created_at
            ) VALUES (
                :filial, :cod, :descricao, :placa, :estpla, :codmun, :capacn, :capacm, :volmax, :motori,
                :limmax, :ativo, :qtduni, :unitiz, :codgru, :altint, :larint, :comint, :altext, :comext,
                :larext, :filatu, :filvga, :numvga, :frovei, :codbem, :codfor, :lojfor, :marvei, :corvei,
                :anomod, :anofab, :chassi, :tipvei, :qtdeix, :bitmap, :veiras, :custo1, :custo2, :custo3,
                :custo4, :custo5, :status, :renava, :filbas, :tara, :tipgrp, :tiptra, :codfav, :lojfav,
                :libseg, :sertms, :dtivsg, :dtfvsg, :civ, :cipp, :gstdmd, :filprv, :datprv, :horprv,
                :datsts, :horsts, :veloc, :qteixv, :munpla, :rodage, :intope, :integr, :mults, :tag,
                ' ', :maxvol, CURRENT_TIMESTAMP
            )
        """;
        BeanPropertySqlParameterSource params = new BeanPropertySqlParameterSource(v);
        namedJdbcTemplate.update(sql, params);
    }

    public void updateTag(String cod, String tag) {
        String sql = """
            UPDATE DA3010 
            SET DA3_TAG = :tag, updated_at = CURRENT_TIMESTAMP 
            WHERE DA3_COD = :cod AND D_E_L_E_T_ != '*'
        """;
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("tag", safeString(tag, 50))
                .addValue("cod", safeString(cod, 8));
        namedJdbcTemplate.update(sql, params);
    }
}