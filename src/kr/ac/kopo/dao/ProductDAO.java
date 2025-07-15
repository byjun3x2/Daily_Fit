package kr.ac.kopo.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import kr.ac.kopo.util.ConnectionFactory;
import kr.ac.kopo.vo.ProductVO;

public class ProductDAO {

	// 전체 상품 조회
	public List<ProductVO> selectAll() {
	    List<ProductVO> list = new ArrayList<>();
	    String sql = "SELECT p.*, NVL(r.review_count, 0) AS review_count " +
	                 "FROM products p " +
	                 "LEFT JOIN (SELECT product_id, COUNT(*) AS review_count FROM reviews GROUP BY product_id) r " +
	                 "ON p.product_id = r.product_id " +
	                 "ORDER BY p.product_id";
	    try (Connection conn = new ConnectionFactory().getConnection();
	         PreparedStatement pstmt = conn.prepareStatement(sql);
	         ResultSet rs = pstmt.executeQuery()) {
	        while (rs.next()) {
	            ProductVO product = new ProductVO();
	            product.setProductId(rs.getInt("product_id"));
	            product.setName(rs.getString("name"));
	            product.setCategory(rs.getString("category"));
	            product.setPrice(rs.getInt("price"));
	            product.setStock(rs.getInt("stock"));
	            product.setProductSize(rs.getString("product_size"));
	            product.setColor(rs.getString("color"));
	            product.setReviewCount(rs.getInt("review_count")); // ✅ 리뷰 수 매핑
	            list.add(product);
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return list;
	}


	// 상품명으로 검색
	public List<ProductVO> selectByName(String name) {
		List<ProductVO> list = new ArrayList<>();
		String sql = "SELECT * FROM products WHERE name LIKE ?";
		try (Connection conn = new ConnectionFactory().getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setString(1, "%" + name + "%");
			try (ResultSet rs = pstmt.executeQuery()) {
				while (rs.next()) {
					list.add(makeProductVO(rs));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	// 카테고리로 검색
	public List<ProductVO> selectByCategory(String category) {
		List<ProductVO> list = new ArrayList<>();
		String sql = "SELECT * FROM products WHERE category = ?";
		try (Connection conn = new ConnectionFactory().getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setString(1, category);
			try (ResultSet rs = pstmt.executeQuery()) {
				while (rs.next()) {
					list.add(makeProductVO(rs));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	// 가격대별 검색
	public List<ProductVO> selectByPriceRange(int min, int max) {
		List<ProductVO> list = new ArrayList<>();
		String sql = "SELECT * FROM products WHERE price BETWEEN ? AND ?";
		try (Connection conn = new ConnectionFactory().getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setInt(1, min);
			pstmt.setInt(2, max);
			try (ResultSet rs = pstmt.executeQuery()) {
				while (rs.next()) {
					list.add(makeProductVO(rs));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	// 상품 상세 조회 (상품ID로 상품명 등 조회)
	public ProductVO selectById(int productId) {
		String sql = "SELECT * FROM products WHERE product_id = ?";
		try (Connection conn = new ConnectionFactory().getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setInt(1, productId);
			try (ResultSet rs = pstmt.executeQuery()) {
				if (rs.next()) {
					return makeProductVO(rs);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	// 상품 등록
	public void insert(ProductVO product) {
		String sql = "INSERT INTO products(product_id, name, category, price, stock, product_size, color, description) VALUES(products_seq.nextval, ?, ?, ?, ?, ?, ?, ?)";
		try (Connection conn = new ConnectionFactory().getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setString(1, product.getName());
			pstmt.setString(2, product.getCategory());
			pstmt.setInt(3, product.getPrice());
			pstmt.setInt(4, product.getStock());
			pstmt.setString(5, product.getProductSize());
			pstmt.setString(6, product.getColor());
			pstmt.setString(7, product.getDescription());
			pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 상품 수정
	public void update(ProductVO product) {
		String sql = "UPDATE products SET name=?, category=?, price=?, stock=?, product_size=?, color=?, description=? WHERE product_id=?";
		try (Connection conn = new ConnectionFactory().getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setString(1, product.getName());
			pstmt.setString(2, product.getCategory());
			pstmt.setInt(3, product.getPrice());
			pstmt.setInt(4, product.getStock());
			pstmt.setString(5, product.getProductSize());
			pstmt.setString(6, product.getColor());
			pstmt.setString(7, product.getDescription());
			pstmt.setInt(8, product.getProductId());
			pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 상품 삭제
	public void delete(int productId) {
		String sql = "DELETE FROM products WHERE product_id=?";
		try (Connection conn = new ConnectionFactory().getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setInt(1, productId);
			pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 재고 차감
	public void reduceStock(int productId, int quantity) {
		String sql = "UPDATE products SET stock = stock - ? WHERE product_id = ?";
		try (Connection conn = new ConnectionFactory().getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setInt(1, quantity);
			pstmt.setInt(2, productId);
			pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 리뷰 많은 순으로 상품 조회
	public List<ProductVO> selectByReviewCountDesc() {
		List<ProductVO> list = new ArrayList<>();
		String sql = "SELECT p.*, NVL(r.review_count, 0) AS review_count " + "FROM products p "
				+ "LEFT JOIN (SELECT product_id, COUNT(*) AS review_count FROM reviews GROUP BY product_id) r "
				+ "ON p.product_id = r.product_id " + "ORDER BY review_count DESC, p.product_id ASC";
		try (Connection conn = new ConnectionFactory().getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql);
				ResultSet rs = pstmt.executeQuery()) {
			while (rs.next()) {
				ProductVO vo = makeProductVO(rs);
				vo.setReviewCount(rs.getInt("review_count")); // ProductVO에 reviewCount 필드 필요
				list.add(vo);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	// ResultSet -> ProductVO 변환
	private ProductVO makeProductVO(ResultSet rs) throws SQLException {
		ProductVO vo = new ProductVO();
		vo.setProductId(rs.getInt("product_id"));
		vo.setName(rs.getString("name"));
		vo.setCategory(rs.getString("category"));
		vo.setPrice(rs.getInt("price"));
		vo.setStock(rs.getInt("stock"));
		vo.setProductSize(rs.getString("product_size"));
		vo.setColor(rs.getString("color"));
		vo.setDescription(rs.getString("description"));
		// reviewCount 컬럼이 있을 경우만 세팅 (기본값 0)
		try {
			vo.setReviewCount(rs.getInt("review_count"));
		} catch (SQLException ignore) {
			vo.setReviewCount(0);
		}
		return vo;
	}
}
