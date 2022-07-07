package com.example.wallet.Controller;

import com.example.wallet.Model.Dto.WalletDTO;
import com.example.wallet.Model.Wallet;
import com.example.wallet.Service.WalletService;
import com.example.wallet.Utils.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("wallet")
public class WalletController {

    @Autowired
    private WalletService service;

    @PostMapping("create")
    public ResponseEntity<Response<WalletDTO>> create(@Valid @RequestBody WalletDTO dto, BindingResult result){
        Response<WalletDTO> response = new Response<WalletDTO>();

        if (result.hasErrors()){
            result.getAllErrors().forEach(e -> response.getErrors().add(e.getDefaultMessage()));
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        Wallet wallet = service.save(convertDtoToEntityWallet(dto));

        response.setData(convertEntityToDtoWallet(wallet));

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    private Wallet convertDtoToEntityWallet(WalletDTO dto){
        Wallet wallet = new Wallet();
        wallet.setId(dto.getId());
        wallet.setName(dto.getName());
        wallet.setValue(dto.getValue());

        return wallet;
    }

    private WalletDTO convertEntityToDtoWallet(Wallet wallet){
        WalletDTO walletDTO = new WalletDTO();
        walletDTO.setId(wallet.getId());
        walletDTO.setName(wallet.getName());
        walletDTO.setValue(wallet.getValue());

        return walletDTO;
    }
}
