package com.naeddoco.nsmwspring.legacyController.async;

import java.io.IOException;
import java.io.PrintWriter;

import com.naeddoco.nsmwspring.legacyModel.dao.ProductDAO;
import com.naeddoco.nsmwspring.legacyModel.dto.ProductDTO;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/checkQty")
public class CheckQty extends HttpServlet {
	private static final long serialVersionUID = 1L;
    public CheckQty() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// 재고 비교
		ProductDTO pDTO = new ProductDTO();
		ProductDAO pDAO = new ProductDAO();
		PrintWriter out = response.getWriter();
		
		String[] PIDs = request.getParameterValues("PID[]");
		String[] qtys = request.getParameterValues("qty[]");
		for (int i = 0; i < PIDs.length; i++) {
			pDTO.setSearchCondition("상품상세정보");
			pDTO.setPID(Integer.parseInt(PIDs[i]));
			pDTO = pDAO.selectOne(pDTO);
			if (Integer.parseInt(qtys[i]) > pDTO.getpQty()) {
				out.print("fail");
 				return ;
			}
		}
		out.print("suc");
	}
}
