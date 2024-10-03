package mart.mono.commerce.cart;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CartJpaRepository extends JpaRepository<CartItemEntity, UUID> {

}
