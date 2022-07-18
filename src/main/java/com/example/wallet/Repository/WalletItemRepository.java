package com.example.wallet.Repository;

import com.example.wallet.Model.WalletItem;
import com.example.wallet.Utils.Enums.TypeEnumWalletItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Repository
public interface WalletItemRepository extends JpaRepository<WalletItem, Long> {

    Page<WalletItem> findAllByWalletIdAndDateGreaterThanEqualAndDateLessThanEqual(Long wallet, Date init, Date end, Pageable page);

    List<WalletItem> findByWalletIdAndType(Long wallet, TypeEnumWalletItem type);

    @Query(value = "SELECT sum(value) FROM WalletItem wI WHERE wI.wallet.id = :wallet")
    BigDecimal sumWalletId(@Param("wallet") Long wallet);
}
