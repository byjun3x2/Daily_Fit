package kr.ac.kopo.service;

import kr.ac.kopo.dao.ProductDAO;
import kr.ac.kopo.vo.ProductVO;
import java.util.List;

public class ProductService {

	private ProductDAO dao = new ProductDAO();

	public List<ProductVO> getAllProducts() {
		return dao.selectAll();
	}

	public List<ProductVO> searchByName(String name) {
		return dao.selectByName(name);
	}

	public List<ProductVO> searchByCategory(String category) {
		return dao.selectByCategory(category);
	}

	public List<ProductVO> searchByPriceRange(int min, int max) {
		return dao.selectByPriceRange(min, max);
	}

	public ProductVO getProductById(int productId) {
		return dao.selectById(productId);
	}

	public void addProduct(ProductVO product) {
		dao.insert(product);
	}

	public void updateProduct(ProductVO product) {
		dao.update(product);
	}

	public void deleteProduct(int productId) {
		dao.delete(productId);
	}

	public void reduceStock(int productId, int quantity) {
		dao.reduceStock(productId, quantity);
	}

	public List<ProductVO> getProductsByReviewCountDesc() {
		return dao.selectByReviewCountDesc();
	}
}
