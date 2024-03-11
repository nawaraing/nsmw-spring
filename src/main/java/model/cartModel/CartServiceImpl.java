package model.cartModel;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service("CartService")
public class CartServiceImpl implements CartService{
	
	@Autowired
	private CartDAO cDAO;
	
	
	@Override
	public List<CartDTO> selectAll(CartDTO cDTO) {
		return cDAO.selectAll(cDTO);
	}

	@Override
	public CartDTO selectOne(CartDTO cDTO) {
		return cDAO.selectOne(cDTO);
	}

	@Override
	public boolean insert(CartDTO cDTO) {
		return cDAO.insert(cDTO);
	}

	@Override
	public boolean update(CartDTO cDTO) {
		return cDAO.update(cDTO);
	}

	@Override
	public boolean delete(CartDTO cDTO) {
		return cDAO.delete(cDTO);
	}
		
}
