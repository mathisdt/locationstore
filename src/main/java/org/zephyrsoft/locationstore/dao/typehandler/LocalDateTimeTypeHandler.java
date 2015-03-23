package org.zephyrsoft.locationstore.dao.typehandler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

public class LocalDateTimeTypeHandler extends BaseTypeHandler<LocalDateTime> {

	@Override
	public void setNonNullParameter(final PreparedStatement ps, final int i, final LocalDateTime parameter,
		final JdbcType jdbcType)
		throws SQLException {
		ps.setTimestamp(i, Timestamp.valueOf(parameter));
	}

	@Override
	public LocalDateTime getNullableResult(final ResultSet rs, final String columnName) throws SQLException {
		final Timestamp timestamp = rs.getTimestamp(columnName);
		if (rs.wasNull()) {
			return null;
		} else {
			return timestamp.toLocalDateTime();
		}
	}

	@Override
	public LocalDateTime getNullableResult(final ResultSet rs, final int columnIndex) throws SQLException {
		final Timestamp timestamp = rs.getTimestamp(columnIndex);
		if (rs.wasNull()) {
			return null;
		} else {
			return timestamp.toLocalDateTime();
		}
	}

	@Override
	public LocalDateTime getNullableResult(final CallableStatement cs, final int columnIndex) throws SQLException {
		final Timestamp timestamp = cs.getTimestamp(columnIndex);
		if (cs.wasNull()) {
			return null;
		} else {
			return timestamp.toLocalDateTime();
		}
	}

}
