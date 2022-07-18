package com.example.wallet.testRepository;

import com.example.wallet.Model.Wallet;
import com.example.wallet.Model.WalletItem;
import com.example.wallet.Repository.WalletItemRepository;
import com.example.wallet.Repository.WalletRepository;
import com.example.wallet.Utils.Enums.TypeEnumWalletItem;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import javax.validation.ConstraintViolationException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest
@Profile("test")
public class WalletItemRepositoryTest {

    private static final Date DATE = new Date();
    private static final TypeEnumWalletItem TYPE = TypeEnumWalletItem.EN;
    private static final String DESCRIPTION = "Conta de Alguma coisa aí";
    private static final BigDecimal VALUE = BigDecimal.valueOf(65);
    private Long savingWalletItemIdTest = null;
    private Long savingWalletIdTest = null;

    @Autowired
    WalletItemRepository repository;
    @Autowired
    WalletRepository walletRepository;

    @Before
    public void setUp(){
        Wallet w = new Wallet();
        w.setName("Carteira de Teste Up");
        w.setValue(BigDecimal.valueOf(500));
        walletRepository.save(w);

        WalletItem wI = new WalletItem(null, w, DATE, TYPE, DESCRIPTION, VALUE);
        repository.save(wI);

        savingWalletItemIdTest = wI.getId();
        savingWalletIdTest = w.getId();
    }

    @After
    public void tearDown(){
        repository.deleteAll();
        walletRepository.deleteAll();
    }

    @Test
    public void testSaveWalletItem(){
        Wallet w = new Wallet();
        w.setName("Carteira TesteWalletItem01");
        w.setValue(BigDecimal.valueOf(100));
        walletRepository.save(w);

        WalletItem wI = new WalletItem(1L, w, DATE, TYPE, DESCRIPTION, VALUE);

        WalletItem response = repository.save(wI);

        Assert.assertNotNull(response);
        Assert.assertEquals(response.getDescription(), DESCRIPTION);
        Assert.assertEquals(response.getType(), TYPE);
        Assert.assertEquals(response.getValue(), VALUE);
        Assert.assertEquals(response.getWallet().getId(), w.getId());
    }

    @Test(expected = ConstraintViolationException.class)
    public void testSaveInvalidWalletItemNotCustomException(){
        WalletItem wI = new WalletItem(1L, null, DATE, TYPE, DESCRIPTION, null);
        repository.save(wI);
    }

    @Test
    public void testUpdateWalletItem(){
        Optional<WalletItem> wi = repository.findById(savingWalletItemIdTest);

        String newDescriptionTest = "Nova descrição para teste";

        WalletItem changed = wi.get();
        changed.setDescription(newDescriptionTest);
        repository.save(changed);

        Optional<WalletItem> newWalletItem = repository.findById(savingWalletItemIdTest);
        Assertions.assertEquals(newDescriptionTest, newWalletItem.get().getDescription());
    }

    @Test
    public void testDeleteWalletItem(){
        Optional<Wallet> wallet = walletRepository.findById(savingWalletIdTest);
        WalletItem wI = new WalletItem(null, wallet.get(), DATE, TYPE, DESCRIPTION, VALUE);

        repository.save(wI);

        repository.deleteById(wI.getId());
        Optional<WalletItem> reponse = repository.findById(wI.getId());

        Assert.assertFalse(reponse.isPresent());
    }

    @Test
    public void testFindBetweenDates(){
        Optional<Wallet> w = walletRepository.findById(savingWalletIdTest);

        LocalDateTime localDateTime = DATE.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();

        Date currentDatePlusFiveDays = Date.from(localDateTime.plusDays(5).atZone(ZoneId.systemDefault()).toInstant());
        Date currentDatePlusSevenDays = Date.from(localDateTime.plusDays(7).atZone(ZoneId.systemDefault()).toInstant());

        repository.save(new WalletItem(null, w.get(), currentDatePlusFiveDays, TYPE, DESCRIPTION, VALUE));
        repository.save(new WalletItem(null, w.get(), currentDatePlusSevenDays, TYPE, DESCRIPTION, VALUE));

        PageRequest pagination = PageRequest.of(0, 10);
        Page<WalletItem> response = repository.findAllByWalletIdAndDateGreaterThanEqualAndDateLessThanEqual(savingWalletIdTest, DATE, currentDatePlusFiveDays, pagination);

        Assertions.assertEquals(response.getContent().size(), 2);
        Assertions.assertEquals(response.getTotalElements(), 2);
        Assertions.assertEquals(response.getContent().get(0).getWallet().getId(), savingWalletIdTest);
    }

    @Test
    public void testFindByType(){
        List<WalletItem> response = repository.findByWalletIdAndType(savingWalletIdTest, TYPE);

        Assertions.assertEquals(response.size(), 1);
        Assertions.assertEquals(response.get(0).getType(), TYPE);
    }

    @Test
    public void testFindByTypeExit(){
        Optional<Wallet> w = walletRepository.findById(savingWalletIdTest);

        repository.save(new WalletItem(null, w.get(), DATE, TypeEnumWalletItem.SD, DESCRIPTION, VALUE));

        List<WalletItem> response = repository.findByWalletIdAndType(savingWalletIdTest, TypeEnumWalletItem.SD);

        Assertions.assertEquals(response.size(), 1);
        Assertions.assertEquals(response.get(0).getType(), TypeEnumWalletItem.SD);
    }

    @Test
    public void testSumWallet(){
        Optional<Wallet> w = walletRepository.findById(savingWalletIdTest);

        repository.save(new WalletItem(null, w.get(), DATE, TypeEnumWalletItem.SD, DESCRIPTION, BigDecimal.valueOf(150.80)));

        BigDecimal response = repository.sumWalletId(savingWalletIdTest);
        Assertions.assertEquals(response.compareTo(BigDecimal.valueOf(215.8)), 0);
    }
}
