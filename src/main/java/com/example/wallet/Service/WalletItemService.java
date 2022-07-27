package com.example.wallet.Service;

import com.example.wallet.Model.WalletItem;
import com.example.wallet.Repository.WalletItemRepository;
import com.example.wallet.Utils.Enums.TypeEnumWalletItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class WalletItemService {

    @Autowired
    private WalletItemRepository repository;
    @Value("${pagination.items_per_page}")
    private int itemsPerPage;

    public WalletItem save(WalletItem wI){
        return repository.save(wI);
    }

    public Page<WalletItem> findBetweenDates(Long wallet, Date start, Date end, int page){
        PageRequest pg = PageRequest.of(page, itemsPerPage);
        return repository.findAllByWalletIdAndDateGreaterThanEqualAndDateLessThanEqual(wallet, start, end, pg);
    }

    public List<WalletItem> findByWalletAndType(Long wallet, TypeEnumWalletItem type) {
        return repository.findByWalletIdAndType(wallet, type);
    }

    public BigDecimal sumByWalletId(Long wallet) {
        return repository.sumWalletId(wallet);
    }

    public Optional<WalletItem> findById(Long id) {
        return repository.findById(id);
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}
