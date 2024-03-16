package com.naeddoco.nsmwspring.model.orderModel;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository("orderDAO")
public class OrderDAO {

	// 의존관계 ▶ DI(의존주입)
	@Autowired
	private JdbcTemplate jdbcTemplate;

	private static final String SELECTALL = "";

	private static final String SELECTONE = "";

	private static final String INSERT = "";

	private static final String UPDATE = "";

	private static final String DELETE = "";

	/*-------------------------------------------------------------------------------------------------------------------------------------------------------------------------*/

	public List<OrderDTO> selectAll(OrderDTO orderDTO) {

		try {
			
			return (List<OrderDTO>) jdbcTemplate.query(SELECTALL, new selectAllRowMapper1());

		} catch (Exception e) {

			return null;

		}

	}

}

class selectAllRowMapper1 implements RowMapper<OrderDTO> {

	@Override
	public OrderDTO mapRow(ResultSet rs, int rowNum) throws SQLException {

		OrderDTO orderDTO = new OrderDTO();

		return orderDTO;

	}

}
