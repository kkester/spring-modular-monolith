package mart.mono.commerce.cart;

import mart.mono.commerce.purchase.PurchasesService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CartServiceTest {

    @InjectMocks
    CartService cartService;

    @Mock
    CartQueryRepository cartQueryRepository;

    @Mock
    CartCommandRepository cartCommandRepository;

    @Mock
    PurchasesService purchasesService;

    @Test
    void checkOutTest() {
        List<CartItem> cartItems = List.of(CartItem.builder().build());
        when(cartQueryRepository.findAll()).thenReturn(cartItems);
        when(purchasesService.purchase(cartItems)).thenReturn(true);

        cartService.checkOut();

        verify(cartCommandRepository).deleteAll();
    }
}