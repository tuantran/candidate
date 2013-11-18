package no.api.candidate.webapp.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.UUID;

import no.api.candidate.webapp.model.CandidateModel;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

@Repository
public class CandidateDAO {
    private static final Logger log = LoggerFactory.getLogger(CandidateDAO.class);
    private static final String TABLE_NAME = "candidate";
    private static final String COLUMNS [] = { "id", "uuid", "name", "age", "gender", "createdtime","modifiedtime" };

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public CandidateModel save(CandidateModel candidateModel) {
        if (candidateModel == null) {
            throw new IllegalArgumentException("Exception throw when try to save null");
        }
        CandidateModel result;
        candidateModel.setModifiedTime(DateTime.now());
        if (candidateModel.getId() == null) {
            candidateModel.setUuid(UUID.randomUUID().toString());
            candidateModel.setCreatedTime(DateTime.now());
            result = add(candidateModel);
        } else {
            result = update(candidateModel);
        }
        return result;
    }
   
    public CandidateModel load(Long id) {
        StringBuffer sql = new StringBuffer();
        sql.append("SELECT  ");
        sql.append(getColumns(0, false));
        sql.append(" FROM  ");
        sql.append(TABLE_NAME);
        sql.append(" WHERE id =?");
        RowMapper<CandidateModel> mapper = new CandidateModelRowMapper();
        log.debug("Executing sql: "+sql.toString());
        try {
        	return jdbcTemplate.queryForObject(sql.toString(), mapper, id);
        }
        catch (EmptyResultDataAccessException e) {
        	log.warn("Empty result for id {}",id);
        	return null;
        }
    }

    public CandidateModel loadByUuid(String uuid) {
        StringBuffer sql = new StringBuffer();
        sql.append("SELECT  ");
        sql.append(getColumns(0, false));
        sql.append(" FROM  ");
        sql.append(TABLE_NAME);
        sql.append(" WHERE uuid = ?");
        RowMapper<CandidateModel> mapper = new CandidateModelRowMapper();
        log.debug("Executing sql: "+sql.toString());
        try {
        	return jdbcTemplate.queryForObject(sql.toString(), mapper, uuid);
        }
        catch (EmptyResultDataAccessException e) {
        	log.warn("Empty result for uuid {}", uuid);
        	return null;
        }
    }

    private CandidateModel add(CandidateModel candidateModel) {
        CandidateModel candidateModelResult = candidateModel;
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(new CandidatePrepareStatementCreator(candidateModelResult), keyHolder);
        Long id = keyHolder.getKey().longValue();
        candidateModelResult.setId(id);
        return candidateModelResult;

    }

    private CandidateModel update(CandidateModel candidateModel) {
        StringBuffer sql = new StringBuffer();
        sql.append("UPDATE ");
        sql.append(TABLE_NAME + " SET ");
        sql.append(getColumns(1,true));
        sql.append(" WHERE id=?");
        log.debug("Executing sql: "+sql.toString());
        jdbcTemplate.update(sql.toString(), candidateModel.getUuid(), candidateModel.getName(),
                candidateModel.getAge(), candidateModel.getGender(), candidateModel.getCreatedTime().getMillis(),candidateModel.getModifiedTime().getMillis(), candidateModel.getId());
        return candidateModel;
    }

    private static String getColumns(int i, boolean forUpdate) {
        StringBuffer sql = new StringBuffer();    
        for (;i<COLUMNS.length;i++) {
            sql.append(COLUMNS[i]);
            if (forUpdate) { sql.append("=?"); }
            if (i<COLUMNS.length-1) {
                sql.append(", "); 
            }
        }                
        return sql.toString();
    }

    private static class CandidatePrepareStatementCreator implements PreparedStatementCreator {
        private CandidateModel candidateModel;

        public CandidatePrepareStatementCreator(CandidateModel candidateModel) {
            this.candidateModel = candidateModel;
        }

        @Override
        public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
            StringBuffer sql = new StringBuffer();
            sql.append("INSERT INTO ");
            sql.append(TABLE_NAME);
            sql.append(" (");
            sql.append(getColumns(1,false));
            sql.append(") VALUES(?,?,?,?,?,?)");
            PreparedStatement preparedStatement = connection.prepareStatement(sql.toString(), Statement.RETURN_GENERATED_KEYS);
            int parameterIndex = 1;
            preparedStatement.setString(parameterIndex++, candidateModel.getUuid());
            preparedStatement.setString(parameterIndex++, candidateModel.getName());
            preparedStatement.setInt(parameterIndex++, candidateModel.getAge());
            preparedStatement.setString(parameterIndex++, candidateModel.getGender());
            preparedStatement.setLong(parameterIndex++, candidateModel.getCreatedTime().getMillis());
            preparedStatement.setLong(parameterIndex++, candidateModel.getModifiedTime().getMillis());
            log.debug(""+preparedStatement.toString());
            return preparedStatement;
        }
    }

    private static class CandidateModelRowMapper implements RowMapper<CandidateModel> {
        @Override
        public CandidateModel mapRow(ResultSet rs, int rowNum) throws SQLException {
            CandidateModel candidateModel = new CandidateModel();
            candidateModel.setId(rs.getLong("id"));
            candidateModel.setUuid(rs.getString("uuid"));
            candidateModel.setName(rs.getString("name"));
            candidateModel.setAge(rs.getInt("age"));
            candidateModel.setGender(rs.getString("gender"));
            candidateModel.setCreatedTime(new DateTime(rs.getLong("createdtime")));
            candidateModel.setModifiedTime(new DateTime(rs.getLong("modifiedtime")));
            return candidateModel;
        }
    }
}
