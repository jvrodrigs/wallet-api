package com.example.wallet.testController;

import com.example.wallet.Model.Dto.UserDTO;
import com.example.wallet.Model.User;
import com.example.wallet.Service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest
public class UserControllerTest {

    @MockBean
    UserService service;

    @Autowired
    MockMvc mvc;
    private static final Long ID = 1L;
    private static final String EMAIL = "email@teste.com.br";
    private static final String NAME = "João Vitor";
    private static final String PASSWORD = "jv@123";
    private static final String URL = "/user/create";

    @Test
    public void testSave() throws Exception {
        BDDMockito.given(service.save(Mockito.any(User.class))).willReturn(getMockUserCreate());

        mvc.perform(MockMvcRequestBuilders.post(URL) //Invocando um POST
                .content(getJsonPayLoad(ID, NAME, EMAIL, PASSWORD)) //body da request
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())//Esperamos que seja criado e retornado o status de CREATED.
                .andExpect(jsonPath("$.data.id").value(ID))
                .andExpect(jsonPath("$.data.name").value(NAME))
                .andExpect(jsonPath("$.data.email").value(EMAIL))
                .andExpect(jsonPath("$.data.password").value(PASSWORD));
    }

    @Test
    public void testSaveUserInvalid() throws Exception {
        mvc.perform(MockMvcRequestBuilders.post(URL) //Invocando um POST
                        .content(getJsonPayLoad(ID, NAME, "email", PASSWORD)) //body da request
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest()) //esperamos um erro
                .andExpect(jsonPath("$.errors[0]").value("Email está inválido, por favor, verifique-o"));
    }

    public User getMockUserCreate(){
        User user = new User();
        user.setId(ID);
        user.setName(NAME);
        user.setEmail(EMAIL);
        user.setPassword(PASSWORD);

        return user;
    }

    public String getJsonPayLoad(Long id, String name, String email, String password) throws JsonProcessingException {
        UserDTO dto = new UserDTO();
        dto.setId(id);
        dto.setName(name);
        dto.setEmail(email);
        dto.setPassword(password);

        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(dto);
    }
}
