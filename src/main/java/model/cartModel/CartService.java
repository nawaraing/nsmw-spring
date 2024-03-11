package model.cartModel;

import java.util.List;


public interface CartService {
		public List<CartDTO> selectAll(CartDTO cDTO);
		public CartDTO selectOne(CartDTO cDTO);
		public boolean insert(CartDTO cDTO);
		public boolean update(CartDTO cDTO);
		public boolean delete(CartDTO cDTO);

}
