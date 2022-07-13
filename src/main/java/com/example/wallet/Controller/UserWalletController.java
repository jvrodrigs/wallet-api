package com.example.wallet.Controller;

import com.example.wallet.Model.Dto.UserWalletDTO;
import com.example.wallet.Model.User;
import com.example.wallet.Model.UserWallet;
import com.example.wallet.Model.Wallet;
import com.example.wallet.Service.UserWalletService;
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
@RequestMapping("user-wallet")
public class UserWalletController {

    @Autowired
    private UserWalletService service;


    @PostMapping("create")
    public ResponseEntity<Response<UserWalletDTO>> create(@Valid @RequestBody UserWalletDTO dto, BindingResult result){
        Response<UserWalletDTO> response = new Response<UserWalletDTO>();

        if (result.hasErrors()){
            result.getAllErrors().forEach(e -> response.getErrors().add(e.getDefaultMessage()));
            return ResponseEntity.badRequest().body(response);
        }

        UserWallet w = service.save(convertDtoToEntityUserWallet(dto));

        response.setData(convertEntityToDto(w));
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    public UserWallet convertDtoToEntityUserWallet(UserWalletDTO dto){
        UserWallet w = new UserWallet();

        User u = new User();
        u.setId(dto.getUsers());
        Wallet wallet = new Wallet();
        wallet.setId(dto.getWallet());

        w.setId(dto.getId());
        w.setUsers(u);
        w.setWallet(wallet);

        return w;
    }

    public UserWalletDTO convertEntityToDto(UserWallet uw){
         UserWalletDTO dto = new UserWalletDTO();

         dto.setId(uw.getId());
         dto.setUsers(uw.getUsers().getId());
         dto.setWallet(uw.getWallet().getId());

         return dto;
    }
}
