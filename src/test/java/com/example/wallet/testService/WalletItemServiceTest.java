package com.example.wallet.testService;

import com.example.wallet.Model.Wallet;
import com.example.wallet.Model.WalletItem;
import com.example.wallet.Repository.WalletItemRepository;
import com.example.wallet.Service.WalletItemService;
import com.example.wallet.Utils.Enums.TypeEnumWalletItem;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@Profile("test")
public class WalletItemServiceTest {
    @MockBean
    WalletItemRepository repository;
    @Autowired
    WalletItemService service;

    private static final Date DATE = new Date();
    private static final TypeEnumWalletItem TYPE = TypeEnumWalletItem.EN;
    private static final String DESCRIPTION = "Conta do Celular";
    private static final BigDecimal VALUE = BigDecimal.valueOf(60);

    @Test
    public void testSave() {
        BDDMockito.given(repository.save(Mockito.any(WalletItem.class))).willReturn(getMockWalletItem());

        WalletItem response = service.save(new WalletItem());

        Assertions.assertNotNull(response);
        Assertions.assertEquals(response.getDescription(), DESCRIPTION);
        Assertions.assertEquals(response.getValue().compareTo(VALUE), 0);
    }

    @Test
    public void testFindBetweenDates() {
        List<WalletItem> list = new ArrayList<>();
        list.add(getMockWalletItem());
        Page<WalletItem> page = new PageImpl(list);

        BDDMockito.given(repository.findAllByWalletIdAndDateGreaterThanEqualAndDateLessThanEqual(Mockito.anyLong(), Mockito.any(Date.class), Mockito.any(Date.class), Mockito.any(PageRequest.class))).willReturn(page);

        Page<WalletItem> response = service.findBetweenDates(1L, new Date(), new Date(), 0);

        Assertions.assertNotNull(response);
        Assertions.assertEquals(response.getContent().size(), 1);
        Assertions.assertEquals(response.getContent().get(0).getDescription(), DESCRIPTION);

    }

    @Test
    public void testFindByType() {
        List<WalletItem> list = new ArrayList<>();
        list.add(getMockWalletItem());

        BDDMockito.given(repository.findByWalletIdAndType(Mockito.anyLong(), Mockito.any(TypeEnumWalletItem.class))).willReturn(list);

        List<WalletItem> response = service.findByWalletAndType(1L, TypeEnumWalletItem.EN);

        Assertions.assertNotNull(response);
        Assertions.assertEquals(response.get(0).getType(), TYPE);
    }

    @Test
    public void testSumByWallet() {
        BigDecimal value = BigDecimal.valueOf(45);

        BDDMockito.given(repository.sumWalletId(Mockito.anyLong())).willReturn(value);

        BigDecimal response = service.sumByWalletId(1L);

        Assertions.assertEquals(response.compareTo(value), 0);
    }

    private WalletItem getMockWalletItem() {
        Wallet w = new Wallet();
        w.setId(1L);

        WalletItem wi = new WalletItem(1L, w, DATE, TYPE, DESCRIPTION, VALUE);
        return wi;
    }
}
