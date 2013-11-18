package no.api.candidate.webapp.dao;

import java.io.ByteArrayInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import no.api.candidate.webapp.model.CandidateMediaModel;

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
public class CandidateMediaDAO {
	private static final Logger log = LoggerFactory
			.getLogger(CandidateMediaDAO.class);
	private static final String TABLE_NAME = "candidatemedia";
	private static final String COLUMNS[] = { "id", "candidate_id",
			"mediablob", "modifiedtime" };

	@Autowired
	private CandidateManager candidateManager;

	@Autowired
	private JdbcTemplate jdbcTemplate;

	public CandidateMediaModel save(CandidateMediaModel candidateMediaModel) {
		if (candidateMediaModel == null) {
			throw new IllegalArgumentException(
					"Exception throw when try to save null");
		}
		CandidateMediaModel result;
		candidateMediaModel.setModifiedTime(DateTime.now());
		if (candidateMediaModel.getId() == null) {
			result = add(candidateMediaModel);
		} else {
			result = update(candidateMediaModel);
		}
		return result;
	}

	public CandidateMediaModel load(Long id) {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT  ");
		sql.append(getColumns(0, false));
		sql.append(" FROM  ");
		sql.append(TABLE_NAME);
		sql.append(" WHERE id =?");
		RowMapper<CandidateMediaModel> mapper = new CandidateMediaModelRowMapper();
		log.debug("Executing sql: " + sql.toString());
		try {
			return jdbcTemplate.queryForObject(sql.toString(), mapper, id);
		}
		catch (EmptyResultDataAccessException e) {
			log.warn("Empty result for id {}",id);
			return null;
		}
	}

	private CandidateMediaModel add(CandidateMediaModel candidateMediaModel) {
		CandidateMediaModel candidateMediaModelResult = candidateMediaModel;
		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(new CandidateMediaPrepareStatementCreator(
				candidateMediaModelResult), keyHolder);
		Long id = keyHolder.getKey().longValue();
		candidateMediaModelResult.setId(id);
		return candidateMediaModelResult;

	}

	private CandidateMediaModel update(CandidateMediaModel candidateMediaModel) {
		StringBuffer sql = new StringBuffer();
		sql.append("UPDATE ");
		sql.append(TABLE_NAME + " SET ");
		sql.append(getColumns(1, true));
		sql.append(" WHERE id=?");
		log.debug("Executing sql: " + sql.toString());
		jdbcTemplate.update(sql.toString(), candidateMediaModel
				.getCandidateModel().getId(), candidateMediaModel.getMedia(),
				candidateMediaModel.getModifiedTime().getMillis(),
				candidateMediaModel.getId());
		return candidateMediaModel;
	}

	private static String getColumns(int i, boolean forUpdate) {
		StringBuffer sql = new StringBuffer();
		for (; i < COLUMNS.length; i++) {
			sql.append(COLUMNS[i]);
			if (forUpdate) {
				sql.append("=?");
			}
			if (i < COLUMNS.length - 1) {
				sql.append(", ");
			}
		}
		return sql.toString();
	}

	private static class CandidateMediaPrepareStatementCreator implements
			PreparedStatementCreator {
		private CandidateMediaModel candidateMediaModel;

		public CandidateMediaPrepareStatementCreator(
				CandidateMediaModel candidateMediaModel) {
			this.candidateMediaModel = candidateMediaModel;
		}

		@Override
		public PreparedStatement createPreparedStatement(Connection connection)
				throws SQLException {
			StringBuffer sql = new StringBuffer();
			sql.append("INSERT INTO ");
			sql.append(TABLE_NAME);
			sql.append(" (");
			sql.append(getColumns(1, false));
			sql.append(") VALUES(?,?,?)");
			PreparedStatement preparedStatement = connection.prepareStatement(
					sql.toString(), Statement.RETURN_GENERATED_KEYS);
			int parameterIndex = 1;
			preparedStatement.setLong(parameterIndex++, candidateMediaModel
					.getCandidateModel().getId());
			preparedStatement.setBinaryStream(parameterIndex++,
					new ByteArrayInputStream(candidateMediaModel.getMedia()),
					candidateMediaModel.getMedia().length);
			preparedStatement.setLong(parameterIndex++, candidateMediaModel
					.getModifiedTime().getMillis());
			log.debug("" + preparedStatement.toString());
			return preparedStatement;
		}
	}

	private class CandidateMediaModelRowMapper implements
			RowMapper<CandidateMediaModel> {
		@Override
		public CandidateMediaModel mapRow(ResultSet rs, int rowNum)
				throws SQLException {
			CandidateMediaModel candidateMediaModel = new CandidateMediaModel();
			candidateMediaModel.setId(rs.getLong("id"));
			candidateMediaModel.setCandidateModel(candidateManager.load(rs
					.getLong("candidate_id")));
			candidateMediaModel.setMedia(rs.getBytes("mediablob"));
			candidateMediaModel.setModifiedTime(new DateTime(rs
					.getLong("modifiedtime")));
			return candidateMediaModel;
		}
	}

	public CandidateMediaModel loadByCandidateId(Long candidateId) {
	      StringBuffer sql = new StringBuffer();
	        sql.append("SELECT  ");
	        sql.append(getColumns(0, false));
	        sql.append(" FROM  ");
	        sql.append(TABLE_NAME);
	        sql.append(" WHERE candidate_id =?");
	        RowMapper<CandidateMediaModel> mapper = new CandidateMediaModelRowMapper();
	        log.debug("Executing sql: "+sql.toString());
	        try {
	        	return jdbcTemplate.queryForObject(sql.toString(), mapper, candidateId);
	        }
	        catch (EmptyResultDataAccessException e) {
	        	log.warn("Empty result for candidate id {}", candidateId);
	        	return null;
	        }
	 
	}
}
