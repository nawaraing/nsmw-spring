package com.naeddoco.nsmwspring.legacyModel.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import com.naeddoco.nsmwspring.legacyModel.dto.ProductDTO;
import com.naeddoco.nsmwspring.legacyModel.util.JDBCUtil;

public class ProductDAO {

	private Connection conn;
	private PreparedStatement pstmt;

	// 상품선택(페이지)
	// FROM절에서 가상테이블을 만든다
	// 가상테이블에서 행 번호(ROWNUM)를 별칭(RN)으로 지정
	// 숫자를 입력받아 해당 숫자 사이에 있는 행에서 작성한 컬럼의 데이터를 선택
	private static final String SELECTALL_PRODUCT_PAGE = "SELECT P_ID, P_NAME, P_DETAIL, COST_PRICE, REGULAR_PRICE, SELLING_PRICE, P_QTY, INGREDIENT, CATEGORY, REG_TIME, SELLING_STATE, IMAGE_PATH "
			+ "FROM ("
			+ "    SELECT P_ID, P_NAME, P_DETAIL, COST_PRICE, REGULAR_PRICE, SELLING_PRICE, P_QTY, INGREDIENT, CATEGORY, REG_TIME, SELLING_STATE, IMAGE_PATH, ROWNUM AS RN "
			+ "    FROM PRODUCT "
			+ "	   WHERE SELLING_STATE = '판매중'"
			+ ") "
			+ "WHERE RN BETWEEN ? AND ?";
	
	// 상품필터 PART1
	// 로직에서 쿼리문을 사용자입력에 맞게 조립해서 사용하는 쿼리
	// 상품선택(페이지)에서 필터검색을 추가한 쿼리
	// FROM절의 별칭을 P, 구매내역(구매내역의 가상 테이블)의 별칭을 B로 지정하여 조인 
	// 조건에 WHERE 1=1을 작성해 무조건 true가 나오게 설계
	// 서브쿼리 1 = 판매상태가 판매중인 행만을 가상테이블로
	// 서브쿼르 2 = 판매량 순으로 정렬하기 위해 구매내역과 조인하여 판매량을 선택
	private static final String SELECTALL_PRODUCT_FILTER_PART1 = "SELECT P.P_ID, P.P_NAME, P.P_DETAIL, P.COST_PRICE, P.REGULAR_PRICE, "
			+ "P.SELLING_PRICE, P.P_QTY, P.INGREDIENT, P.CATEGORY, P.REG_TIME, "
			+ "P.SELLING_STATE, P.IMAGE_PATH, NVL(B.TOTAL_B_QTY, 0) AS TOTAL_B_QTY "
			+ "FROM ("
			+ "	SELECT P_ID, P_NAME, P_DETAIL, COST_PRICE, REGULAR_PRICE, "
			+ "	SELLING_PRICE, P_QTY, INGREDIENT, CATEGORY, REG_TIME, "
			+ "	SELLING_STATE, IMAGE_PATH, ROWNUM AS RN "
			+ "		FROM PRODUCT "
			+ "			WHERE SELLING_STATE = '판매중' "
			+ ") P "
			+ "LEFT JOIN ( "
			+ "SELECT P_ID, SUM(B_QTY) AS TOTAL_B_QTY "
			+ "FROM BUYINFO "
			+ "GROUP BY P_ID "
			+ ") "
			+ "B ON P.P_ID = B.P_ID "
			+ "WHERE 1=1 AND RN BETWEEN ? AND ? ";

	// 상품필터 PART2
	// 사용자입력에 맞게 쿼리문을 PART1에 붙여 작성하고
	// PART2를 이어붙인다
	// 정렬 기준은 판매량(TOTAL_B_QTY), 유효기간(REG_TIME)으로 내림차순 정렬
	private static final String SELECTALL_PRODUCT_FILTER_PART2 = "ORDER BY TOTAL_B_QTY DESC, REG_TIME DESC";

	// 상품상세출력
	// 상품목록에서 상품을 선택했을 때 해상 상품의 상세정보를 선택함
	private static final String SELECTONE_PRODUCT_DETAIL = "SELECT " + "P_ID, P_NAME, P_DETAIL, COST_PRICE, REGULAR_PRICE, "
			+ "SELLING_PRICE, P_QTY, INGREDIENT, CATEGORY, REG_TIME, " + "SELLING_STATE, IMAGE_PATH, USAGE, EXP "
			+ "FROM PRODUCT " + "WHERE P_ID = ?";

	// 최대가 반환
	// 상품목록에 처음 진입했을 때 최고가 상품 이하의 상품을 전부 선택
	private static final String SELECTONE_PRODUCT_MAX_PRICE = "SELECT MAX(SELLING_PRICE) AS PRICE FROM PRODUCT WHERE SELLING_STATE = '판매중'";

	// 카테고리 반환
	// 해당 상품의 카테고리를 선택
	private static final String SELECTONE_PRODUCT_CATEGORY = "SELECT CATEGORY FROM PRODUCT WHERE P_ID = ?";

	// 컨트롤러에서 크롤링한 정보로 DB에 상품을 추가한다
	private static final String INSERT_PRODUCT = "INSERT INTO PRODUCT "
			+ "(P_ID, P_NAME, P_DETAIL, COST_PRICE, REGULAR_PRICE, SELLING_PRICE, P_QTY, INGREDIENT, CATEGORY, REG_TIME, SELLING_STATE, IMAGE_PATH) "
			+ "	VALUES "
			+ "(NVL((SELECT MAX(P_ID) FROM PRODUCT), 0) + 1, "
			+ "?, "
			+ "?, "
			+ "?, "
			+ "?, "
			+ "?, "
			+ "?, "
			+ "?, "
			+ "?, "
			+ "SYSTIMESTAMP, "
			+ "?, "
			+ "? "
			+ "	)";

	// 재고변경(상품구매)
	// 상품 구매시 해당 상품의 재고를 구매한 수량을 뺀 수로 갱신
	private static final String UPDATE_PRODUCT_P_QTY = "UPDATE PRODUCT SET P_QTY = P_QTY - ? WHERE P_ID = ?";

	// (관)상품 판매상태 변경(판매중 -> 판매중지)
	// 특이사항으로 상품판매를 중지하는 경우 사용
	// 판매중지로 판매상태를 변경하면 상품목록에 출력되지 않음
	private static final String UPDATE_PRODUCT_SELLING_STATE = "UPDATE PRODUCT SET SELLING_STATE = '판매중지' " + "WHERE P_ID = ?";

	// 상품을 삭제하는 기능은 없음
	private static final String DELETE_PRODUCT = "";

	public ArrayList<ProductDTO> selectAll(ProductDTO pDTO) {

		// DB에서 선택한 값을 Java에서 사용 가능한 형태로 저장하기 위한 변수
		ArrayList<ProductDTO> productList = null;
		ProductDTO productDTO = null;

		// DB 연결
		conn = JDBCUtil.connect();

		// 페이지를 적용해서 상품을 일정 개수만큼 선택
		if (pDTO.getSearchCondition().equals("상품목록페이지")) {
			
			System.out.println("[MODEL_로그_상품목록페이지] 진입");

			// 예외처리
			try {
				// 쿼리문 준비 및 조건 설정
				pstmt = conn.prepareStatement(SELECTALL_PRODUCT_PAGE);
				pstmt.setInt(1, pDTO.getAncSelectMin());
				pstmt.setInt(2, pDTO.getAncSelectMax());
				System.out.println("[MODEL_로그_상품목록페이지] 쿼리문 set");

				// 쿼리문 실행 및 결과 저장
				ResultSet rs = pstmt.executeQuery();
				System.out.println("[MODEL_로그_상품목록페이지] 쿼리문 실행");
				
				// 여러 상품의 정보를 저장할 리스트 객체 생성
				productList = new ArrayList<ProductDTO>();

				// 쿼리문 결과가 있다면 실행
				while (rs.next()) {					
					
					// 상품 하나의 정보를 저장할 객체
					productDTO = new ProductDTO();
					
					// 상품 등록일을 밀리초 단위가 아니라 분까지 보여주기 위해 형식변환
					productDTO.setRegTime(rs.getTimestamp("REG_TIME"));
					SimpleDateFormat timeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
					String regTime = timeFormat.format(productDTO.getRegTime());
					
					// 객체에 상품 하나의 데이터를 저장
					productDTO.setPID(rs.getInt("P_ID"));
					productDTO.setpName(rs.getString("P_NAME"));
					productDTO.setCostPrice(rs.getInt("COST_PRICE"));
					productDTO.setRegularPrice(rs.getInt("REGULAR_PRICE"));
					productDTO.setSellingPrice(rs.getInt("SELLING_PRICE"));
					productDTO.setpQty(rs.getInt("P_QTY"));
					productDTO.setIngredient(rs.getString("INGREDIENT"));
					productDTO.setCategory(rs.getString("CATEGORY"));
					productDTO.setRegTime(rs.getTimestamp("REG_TIME"));
					productDTO.setAncRegTime(regTime);
					productDTO.setSellingState(rs.getString("SELLING_STATE"));
					productDTO.setImagePath(rs.getString("IMAGE_PATH"));
					productDTO.setpDetail(rs.getString("P_DETAIL"));
					
					// 상품리스트에 상품 하나의 정보를 저장
					productList.add(productDTO);
				}

				// ResultSet 객체 종료
				rs.close();

				// 예외처리
			} catch (SQLException e) {
				System.out.println("[MODEL_로그_상품목록페이지] 예외처리");
				e.printStackTrace();
				// DB연결 종료
			} finally {
				JDBCUtil.disconnect(pstmt, conn);
			}
			// 상품리스트가 null이 아나리ㅏ면 성공
			if (productList != null) {
				System.out.println("[MODEL_로그_상품목록페이지] 성공");
				return productList;
			}
			// 필터를 적용해 상품을 입력받은 숫자의 사이에있는 행만 선택해서 반환 
		} else if (pDTO.getSearchCondition().equals("상품출력필터")) {
			
			System.out.println("[MODEL_로그_상품출력필터] 진입");

			// 쿼리문의 조건을 설정하기 위한 객체
			StringBuilder query = new StringBuilder();
			
			// 객체에 조건 전 쿼리를 저장함
			query.append(SELECTALL_PRODUCT_FILTER_PART1);
			
			// 상품이름을 받았다면 쿼리문에 조건 추가
			if (pDTO.getpName() != null) {
				// 상품이름에 입력받은 문자가 포함된 행을 선택
				query.append("AND P.P_NAME LIKE ? ");
			}
			// 카테고리를 받았다면 쿼리문에 조건 추가
			if (pDTO.getCategory() != null) {
				// 카테고리에 입력받은 카테고리가 포함된 행을 선택
				query.append("AND P.CATEGORY LIKE ? ");
			}
			// 판매가격을 받았다면 쿼리문에 조건 추가
			if (pDTO.getSellingPrice() != 0) {
				// 판매가격이 입력받은 값의 이하의 행만 선택
				query.append("AND P.SELLING_PRICE <= ? ");
			}
			
			// 마지막으로 선택된 행들을 정렬할 방법을 작성해둔 쿼리를 이어붙인다
			query.append(SELECTALL_PRODUCT_FILTER_PART2);
			
			//StringBuilder 객체를 직접 사용할 수 없으니 문자열 타입의 변수에 완성된 쿼리 저장
			String SELECTALL_FILTER = query.toString();
			
			System.out.println("[MODEL_로그_상품출력필터] 쿼리문 완성");

			// 바인딩 변수의 위치를 지정할 변수
			// 기본적으로 1과 2는 특정 행 사이의 값을 선택하기 위해서 사용되어 3부터 시작한다
			int bindingIndex = 3;

			try {
				// 쿼리문 준비 및 조건 설정
				pstmt = conn.prepareStatement(SELECTALL_FILTER);
				pstmt.setInt(1, pDTO.getAncSelectMin());
				pstmt.setInt(2, pDTO.getAncSelectMax());
				// 상품이름을 받았을 경우에만 조건 추가
				if (pDTO.getpName() != null) {
					pstmt.setString(bindingIndex++, "%" + pDTO.getpName() + "%");
				}
				if (pDTO.getCategory() != null) {
					pstmt.setString(bindingIndex++, "%" + pDTO.getCategory() + "%");
				}
				if (pDTO.getSellingPrice() != 0) {
					pstmt.setInt(bindingIndex++, pDTO.getSellingPrice());
				}
				System.out.println("[MODEL_로그_상품출력필터] 쿼리문 set");

				// 쿼리문 실행 및 결과 저장
				ResultSet rs = pstmt.executeQuery();
				System.out.println("[MODEL_로그_상품출력필터] 실행");
				
				// 여러 상품데이터를 저장해서 반환될 객체 생성
				productList = new ArrayList<ProductDTO>();

				// 쿼리문 실행 결과가 있을 경우 실행
				while (rs.next()) {

					// 상품 하나의 데이터만 저장하기위한 객체 생성
					productDTO = new ProductDTO();
					
					// 상품 등록일을 분까지만 저장해서 객체에 저장하기위한 코드
					productDTO.setRegTime(rs.getTimestamp("REG_TIME"));
					SimpleDateFormat timeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
					String regTime = timeFormat.format(productDTO.getRegTime());
					
					// 객체에 상품 하나의 데이터를 저장
					productDTO.setPID(rs.getInt("P_ID"));
					productDTO.setpName(rs.getString("P_NAME"));
					productDTO.setCostPrice(rs.getInt("COST_PRICE"));
					productDTO.setRegularPrice(rs.getInt("REGULAR_PRICE"));
					productDTO.setSellingPrice(rs.getInt("SELLING_PRICE"));
					productDTO.setpQty(rs.getInt("P_QTY"));
					productDTO.setIngredient(rs.getString("INGREDIENT"));
					productDTO.setCategory(rs.getString("CATEGORY"));
					productDTO.setRegTime(rs.getTimestamp("REG_TIME"));
					productDTO.setAncRegTime(regTime);
					productDTO.setSellingState(rs.getString("SELLING_STATE"));
					productDTO.setImagePath(rs.getString("IMAGE_PATH"));
					productDTO.setpDetail(rs.getString("P_DETAIL"));
					productDTO.setAncTotalQty(rs.getInt("TOTAL_B_QTY"));
					// 상품리스트에 상품 하나의 정보를 저장한다
					productList.add(productDTO);
				}
				// ResultSet 객체를 종료
				rs.close();

				// 예외처리
			} catch (SQLException e) {
				System.out.println("[MODEL_로그_상품출력필터] 예외처리");
				e.printStackTrace();
				// DB 연결 종료
			} finally {
				JDBCUtil.disconnect(pstmt, conn);
			}
			// 상품리스트가 null이 아니라면 성공
			if (productList != null) {
				System.out.println("[MODEL_로그_상품출력필터] 성공");
				return productList;
			}

		}
		// selectAll 실패
		System.out.println("[MODEL_로그_상품]selectAll 실패");
		return null;
	}

	public ProductDTO selectOne(ProductDTO pDTO) {

		// 선택한 상품의 데이터를 저장해서 반환할 객체 정의
		ProductDTO productDTO = null;
		
		// DB 연결
		conn = JDBCUtil.connect();

		// 해당 상품의 상세정보를 DB에서 선택하고 객체에 저장하여 반환하는 기능
		if (pDTO.getSearchCondition().equals("상품상세정보")) {

			System.out.println("[MODEL_로그_상품상세정보] 진입");

			// 예외처리
			try {
				// 쿼리문 저장 및 조건 설정
				pstmt = conn.prepareStatement(SELECTONE_PRODUCT_DETAIL);
				pstmt.setInt(1, pDTO.getPID());
				System.out.println("[MODEL_로그_상품상세정보] 쿼리문 set");

				// 쿼리문 실행 및 결과 저장
				ResultSet rs = pstmt.executeQuery();
				System.out.println("[MODEL_로그_상품상세정보] 쿼리문 실행");

				// 쿼리문 실행 결과가 있을 경우 실행
				if (rs.next()) {
					
					// 데이터를 저장하고 반환될 객체 생성
					productDTO = new ProductDTO();
					
					// 상품 등록일의 최소 단위를 밀리초에서 분으로 형식 변환하여 저장
					productDTO.setRegTime(rs.getTimestamp("REG_TIME"));
					SimpleDateFormat timeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
					String regTime = timeFormat.format(productDTO.getRegTime());
					
					// 객체에 상품 하나의 데이터를 저장
					productDTO.setPID(rs.getInt("P_ID"));
					productDTO.setpName(rs.getString("P_NAME"));
					productDTO.setpDetail(rs.getString("P_DETAIL"));
					productDTO.setCostPrice(rs.getInt("COST_PRICE"));
					productDTO.setRegularPrice(rs.getInt("REGULAR_PRICE"));
					productDTO.setSellingPrice(rs.getInt("SELLING_PRICE"));
					productDTO.setpQty(rs.getInt("P_QTY"));
					productDTO.setIngredient(rs.getString("INGREDIENT"));
					productDTO.setCategory(rs.getString("CATEGORY"));
					productDTO.setRegTime(rs.getTimestamp("REG_TIME"));
					productDTO.setAncRegTime(regTime);
					productDTO.setSellingState(rs.getString("SELLING_STATE"));
					productDTO.setImagePath(rs.getString("IMAGE_PATH"));
					productDTO.setUsage(rs.getString("USAGE"));
					productDTO.setExp(rs.getString("EXP"));
				} else {
					productDTO = null;
				}

				// ResultSet 객체 종료
				rs.close();

				// 예외처리
			} catch (SQLException e) {
				System.out.println("[MODEL_로그_상품상세정보] 예외처리");
				e.printStackTrace();
				return null;
				// DB연결 종료
			} finally {
				JDBCUtil.disconnect(pstmt, conn);
			}
			// 객체가 null이 아닐경우 성공
			if (productDTO != null) {
				System.out.println("[MODEL_로그_상품상세정보] 성공");
				return productDTO;
			}
			// 상품목록에서 판매가가 가장 높은 값을 반환하는 기능
		} else if (pDTO.getSearchCondition().equals("최대값")) {
			
			System.out.println("[MODEL_로그_최대값] 진입");

			// 예외처리
			try {
				// 쿼리문 준비 및 조건 설정
				pstmt = conn.prepareStatement(SELECTONE_PRODUCT_MAX_PRICE);
				System.out.println("[MODEL_로그_최대값] 쿼리문 set");
				
				// 쿼리무 실행 및 결과 저장
				ResultSet rs = pstmt.executeQuery();
				System.out.println("[MODEL_로그_최대값] 쿼리문 실행");

				// 쿼리문 실행 결과가 있을 꼉우 실행
				if (rs.next()) {
					
					// 데이터를 저장후 반환될 객체 생성
					productDTO = new ProductDTO();
					
					// 객체에 최대값 저장
					productDTO.setSellingPrice(rs.getInt("PRICE"));
				}

				// ResultSet객체 종료
				rs.close();
				
				// 예외처리
			} catch (SQLException e) {
				System.out.println("[MODEL_로그_최대값] 예외처리");
				e.printStackTrace();
				// DB연결 종료
			} finally {
				JDBCUtil.disconnect(pstmt, conn);
			}
			// 최대값이 저장된 객체가 null이 아니라면 성공
			if (productDTO != null) {
				System.out.println("[MODEL_로그_최대값] 성공");
				return productDTO;
			}
			// 해당 상품의 카테고리를 반환하는 기능
		} else if (pDTO.getSearchCondition().equals("카테고리")) {
			
			System.out.println("[MODEL_로그_카테고리] 진입");

			// 예외처리
			try {
				// 쿼리문 준비 및 설정 저장
				pstmt = conn.prepareStatement(SELECTONE_PRODUCT_CATEGORY);
				pstmt.setInt(1, pDTO.getPID());
				System.out.println("[MODEL_로그_카테고리] 쿼리문 set");

				// 쿼리문 실행 및 결과 저장
				ResultSet rs = pstmt.executeQuery();
				System.out.println("[MODEL_로그_카테고리] 쿼리문 실행");

				// 쿼리문 실행 결과가 있을 경우 실행
				if (rs.next()) {
					
					// 해당 상품의 카테고리를 저장해서 반환될 객체 생성
					productDTO = new ProductDTO();
					
					// 객체에 상품의 카테고리를 저장
					productDTO.setCategory(rs.getString("CATEGORY"));					
				} else {
					productDTO = null;
				}

				// ResultSet 객체 종료
				rs.close();

				// 예외처리
			} catch (SQLException e) {
				System.out.println("[MODEL_로그_카테고리] 예외처리");
				e.printStackTrace();
				// DB연결 종료
			} finally {
				JDBCUtil.disconnect(pstmt, conn);
			}
			// 카테고리를 저장한 객체가 null이 아니라면 성공
			if (productDTO != null) {
				System.out.println("[MODEL_로그_카테고리] 성공");
				return productDTO;
			}

		}
		// selectOne 실패
		System.out.println("[MODEL_로그_상품]selectOne 실패");
		return null;
	}

	public boolean insert(ProductDTO pDTO) {

		// 쿼리문 실행 결과를 저장할 변수
		int result = 0;
		
		// DB연결
		conn = JDBCUtil.connect();

		// 크롤링한 데이터를 객체에 저장해 DB에 추가하는 기능
		if (pDTO.getSearchCondition().equals("상품추가")) {
			
			System.out.println("[MODEL_로그_상품추가] 진입");

			// 예외처리
			try {
				// 쿼리문 준비 및 조건 설정
				pstmt = conn.prepareStatement(INSERT_PRODUCT);
				pstmt.setString(1, pDTO.getpName());
				pstmt.setString(2, pDTO.getpDetail());
				pstmt.setInt(3, pDTO.getCostPrice());
				pstmt.setInt(4, pDTO.getRegularPrice());
				pstmt.setInt(5, pDTO.getSellingPrice());
				pstmt.setInt(6, pDTO.getpQty());
				pstmt.setString(7, pDTO.getIngredient());
				pstmt.setString(8, pDTO.getCategory());
				pstmt.setString(9, pDTO.getSellingState());
				pstmt.setString(10, pDTO.getImagePath());
				System.out.println("[MODEL_로그_상품추가] 쿼리문 set");

				// 쿼리문 실행 및 결과 저장
				result = pstmt.executeUpdate();
				System.out.println("[MODEL_로그_상품추가] 쿼리문 실행");

				// 예외처리
			} catch (SQLException e) {
				System.out.println("[MODEL_로그_상품추가] 예외처리");
				e.printStackTrace();
				return false;
				// DB 연결 종료
			} finally {
				JDBCUtil.disconnect(pstmt, conn);
			}
			
			// 쿼리문 실행 결과가 있다면 성공
			if (result > 0) {
				System.out.println("[MODEL_로그_상품추가] 성공");
				return true;
			}
			
		}
		// insert 실패
		System.out.println("[MODEL_로그_상품]insert 실패");
		return false;
	}

	// 재고 및 판매상태 변경
	public boolean update(ProductDTO pDTO) {

		int result;
		
		// DB 연결
		conn = JDBCUtil.connect();

		// 상품이 판매되면 해당 상품의 재고를 갱신하는 기능
		if (pDTO.getSearchCondition().equals("판매완료")) {
			
			System.out.println("[MODEL_로그_판매완료] 진입");

			// 예외처리
			try {
				// 쿼리문 준비 및 조건 설정
				pstmt = conn.prepareStatement(UPDATE_PRODUCT_P_QTY);
				pstmt.setInt(1, pDTO.getpQty());
				pstmt.setInt(2, pDTO.getPID());
				System.out.println("[MODEL_로그_판매완료] 쿼리문 set");

				// 쿼리문 실행 및 결과 저장
				result = pstmt.executeUpdate();
				System.out.println("[MODEL_로그_판매완료] 쿼리문 실행");

				// 예외처리
			} catch (SQLException e) {
				System.out.println("[MODEL_로그_판매완료] 예외처리");
				e.printStackTrace();
				return false;
				// DB연결 종료
			} finally {
				JDBCUtil.disconnect(pstmt, conn);
			}
			
			// 쿼리문 실행결과가 있다면 성공
			if (result > 0) {
				System.out.println("[MODEL_로그_판매완료] 성공");
				return true;
			}

			// 특정 상품을 판매중지 상태로 변경시키는 관리자 기능
		} else if (pDTO.getSearchCondition().equals("판매중지")) {
			
			System.out.println("[MODEL_로그_판매완료] 판매중지");

			// 예외처리
			try {
				// 쿼리문 준비 및 조건 설정
				pstmt = conn.prepareStatement(UPDATE_PRODUCT_SELLING_STATE);
				pstmt.setInt(1, pDTO.getPID());
				System.out.println("[MODEL_로그_판매완료] 쿼리문 set");

				// 쿼리문 실행 및 결과 저장
				result = pstmt.executeUpdate();
				System.out.println("[MODEL_로그_판매완료] 쿼리문 실행");

				// 예외처리
			} catch (SQLException e) {
				System.out.println("[MODEL_로그_판매완료] 예외처리");
				e.printStackTrace();
				return false;
				// DB연결 종료
			} finally {
				JDBCUtil.disconnect(pstmt, conn);
			}
			
			// 쿼리문 실행결과가 있다면 성공
			if (result > 0) {
				System.out.println("[MODEL_로그_판매완료] 성공");
				return true;
			}
		}
		// update 실패
		System.out.println("[MODEL_로그_상품]update 실패");
		return false;
	}

	private boolean delete(ProductDTO pDTO) {
		return false;
	}
}