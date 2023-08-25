package self.sh.comm.common.typehandler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

public class ListStringTypeHandler extends BaseTypeHandler<List<String>> {
	@Override
	public void setNonNullParameter(PreparedStatement ps, int i, List<String> parameter, JdbcType jdbcType) throws SQLException {
	    String joinedString = String.join(",", parameter);
	    ps.setString(i, joinedString);
	}

  
    public List<String> getNullableResult(ResultSet rs, String columnName) throws SQLException {
        String columnValue = rs.getString(columnName);
        return Arrays.asList(columnValue.split(","));
    }

    @Override
    public List<String> getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        String columnValue = cs.getString(columnIndex);
        return Arrays.asList(columnValue.split(","));
    }


	@Override
	public List<String> getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
		  String columnValue = rs.getString(columnIndex);
		    return Arrays.asList(columnValue.split(","));
	}
}