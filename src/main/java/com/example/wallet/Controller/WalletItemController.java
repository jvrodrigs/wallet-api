package com.example.wallet.Controller;

import com.example.wallet.Model.Dto.WalletItemDTO;
import com.example.wallet.Model.Wallet;
import com.example.wallet.Model.WalletItem;
import com.example.wallet.Service.WalletItemService;
import com.example.wallet.Utils.Enums.TypeEnumWalletItem;
import com.example.wallet.Utils.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("wallet-item")
public class WalletItemController {

    @Autowired
    private WalletItemService service;

    private static final String FORMAT_DATE = "dd-MM-yyyy";

    @PostMapping("create")
    public ResponseEntity<Response<WalletItemDTO>> create(@Valid @RequestBody WalletItemDTO dto, BindingResult result){
        Response<WalletItemDTO> response = new Response<WalletItemDTO>();

        if (result.hasErrors()){
            result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
            return ResponseEntity.badRequest().body(response);
        }

        WalletItem wItem = service.save(convertDtoToWalletItem(dto));

        response.setData(convertWalletItemToDto(wItem));
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Response<Page<WalletItemDTO>>> findBetweenDates(@PathVariable("id") Long id, @RequestParam("startDate") @DateTimeFormat(pattern = FORMAT_DATE) Date startDate,
                                                                          @RequestParam("endDate") @DateTimeFormat(pattern = FORMAT_DATE) Date endDate,
                                                                          @RequestParam(name = "page",defaultValue = "0") int pg){
        Response<Page<WalletItemDTO>> response = new Response<Page<WalletItemDTO>>();
        Page<WalletItem> items = service.findBetweenDates(id, startDate, endDate, pg);
        Page<WalletItemDTO> dto = items.map(this::convertWalletItemToDto);
        response.setData(dto);
        return ResponseEntity.ok().body(response);
    }

   @GetMapping("/type/{w}")
   public ResponseEntity<Response<List<WalletItemDTO>>> findByWalletAndType(@PathVariable("w")Long w, @RequestParam("type") String type){
        Response<List<WalletItemDTO>> response = new Response<List<WalletItemDTO>>();
        List<WalletItem> walletItemList = service.findByWalletAndType(w, TypeEnumWalletItem.getEnum(type));

        List<WalletItemDTO> dto = new ArrayList<>();
        walletItemList.forEach(walletItem -> dto.add(convertWalletItemToDto(walletItem)));
        response.setData(dto);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/total/{w}")
    public ResponseEntity<Response<BigDecimal>> sumByWallet(@PathVariable("w") Long w) {

        Response<BigDecimal> response = new Response<BigDecimal>();
        BigDecimal value = service.sumByWalletId(w);
        response.setData(value == null ? BigDecimal.ZERO : value);

        return ResponseEntity.ok().body(response);
    }


    @PutMapping
    public ResponseEntity<Response<WalletItemDTO>> update(@Valid @RequestBody WalletItemDTO dto, BindingResult result) {

        Response<WalletItemDTO> response = new Response<WalletItemDTO>();

        Optional<WalletItem> wi = service.findById(dto.getId());

        if (!wi.isPresent()) {
            result.addError(new ObjectError("WalletItem", "WalletItem não encontrado"));
        } else if (wi.get().getWallet().getId().compareTo(dto.getWallet()) != 0) {
            result.addError(new ObjectError("WalletItemChanged", "Você não pode alterar a carteira"));
        }

        if (result.hasErrors()) {
            result.getAllErrors().forEach(r -> response.getErrors().add(r.getDefaultMessage()));

            return ResponseEntity.badRequest().body(response);
        }

        WalletItem saved = service.save(convertDtoToWalletItem(dto));

        response.setData(convertWalletItemToDto(saved));
        return ResponseEntity.ok().body(response);
    }

    @DeleteMapping(value = "/{walletItemId}")
    public ResponseEntity<Response<String>> delete(@PathVariable("walletItemId") Long walletItemId) {
        Response<String> response = new Response<String>();

        Optional<WalletItem> wi = service.findById(walletItemId);

        if (!wi.isPresent()) {
            response.getErrors().add("Carteira de id " + walletItemId + " não encontrada");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

        service.deleteById(walletItemId);
        response.setData("Carteira de id "+ walletItemId + " apagada com sucesso");
        return ResponseEntity.ok().body(response);
    }

    private WalletItem convertDtoToWalletItem(WalletItemDTO dto) {
        WalletItem wi = new WalletItem();
        wi.setDate(dto.getDate());
        wi.setDescription(dto.getDescription());
        wi.setId(dto.getId());
        wi.setType(TypeEnumWalletItem.getEnum(dto.getType()));
        wi.setValue(dto.getValue());

        Wallet w = new Wallet();
        w.setId(dto.getWallet());
        wi.setWallet(w);

        return wi;
    }

    private WalletItemDTO convertWalletItemToDto(WalletItem wi) {
        WalletItemDTO dto = new WalletItemDTO();
        dto.setDate(wi.getDate());
        dto.setDescription(wi.getDescription());
        dto.setId(wi.getId());
        dto.setType(wi.getType().getValue());
        dto.setValue(wi.getValue());
        dto.setWallet(wi.getWallet().getId());

        return dto;
    }
}
