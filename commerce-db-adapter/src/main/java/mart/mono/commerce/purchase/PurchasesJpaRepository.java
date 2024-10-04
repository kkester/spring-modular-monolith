package mart.mono.commerce.purchase;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PurchasesJpaRepository extends JpaRepository<PurchaseEntity, UUID> {

}
