package com.backend.disney.controllers;

import com.backend.disney.models.Character;
import com.backend.disney.modelsDTO.CharacterDTO;
import com.backend.disney.modelsDTO.CharacterDTOComplete;
import com.backend.disney.services.ICharacterService;
import com.fasterxml.jackson.databind.ObjectMapper;
import javassist.NotFoundException;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;

@WebMvcTest(CharacterController.class)
public class CharacterControllerTest {
    @MockBean
    private ICharacterService service;
    @Autowired
    private MockMvc mockMvc;

    ObjectMapper jsonMapper = new ObjectMapper();

    @Nested
    class saveCharacterTest {
        @Test
        @DisplayName("Valid case")
        @WithMockUser(username = "mock.user@mockmail.mock")
        void test1() throws Exception {

            Character dto = generateCharacterDTOComplete();

            Mockito.when(service.save(Mockito.any())).thenReturn(dto);

            mockMvc.perform(MockMvcRequestBuilders.post("/cars")
                            .content(jsonMapper.writeValueAsString(dto))
                            .contentType(MediaType.APPLICATION_JSON)
                    )
                    .andExpect(MockMvcResultMatchers.status().isCreated())
                    .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                    .andExpect(MockMvcResultMatchers.content().json(jsonMapper.writeValueAsString(dto)))
                    .andDo(MockMvcResultHandlers.print());

            Mockito.verify(service).save(Mockito.any());
        }

        @Test
        @DisplayName("No token provided")
        void testCreateContactWithoutToken() throws Exception {


            Mockito.when(jwtService.isBearer(Mockito.any())).thenReturn(false);

            ContactDTORequest request = ContactControllerTests.createMockContactDTORequest();
            ContactDTOResponse response = ContactControllerTests.createMockContactDTOResponse();
            Mockito.when(service.create(Mockito.any())).thenReturn(response);

            mockMvc.perform(MockMvcRequestBuilders.post(GlobalConstants.Endpoints.CONTACT)
                            .content(jsonMapper.writeValueAsString(request))
                            .contentType(MediaType.APPLICATION_JSON)
                    )
                    .andExpect(MockMvcResultMatchers.status().isForbidden())
                    .andExpect(MockMvcResultMatchers.content().string(Matchers.not(Matchers.containsString("Somos"))))
                    .andDo(MockMvcResultHandlers.print());

            Mockito.verify(service, Mockito.never()).create(Mockito.any());
        }

        @Test
        @DisplayName("Token not valid")
        void testCreateContactWithInvalidToken() throws Exception {

            Mockito.when(jwtService.isBearer(Mockito.any())).thenReturn(true);
            Mockito.when(jwtService.verify(Mockito.any())).thenThrow(new Exception());

            ContactDTORequest request = ContactControllerTests.createMockContactDTORequest();
            ContactDTOResponse response = ContactControllerTests.createMockContactDTOResponse();
            Mockito.when(service.create(Mockito.any())).thenReturn(response);

            mockMvc.perform(MockMvcRequestBuilders.post(GlobalConstants.Endpoints.CONTACT)
                            .content(jsonMapper.writeValueAsString(request))
                            .contentType(MediaType.APPLICATION_JSON)
                    )
                    .andExpect(MockMvcResultMatchers.status().isForbidden())
                    .andExpect(MockMvcResultMatchers.content().string(Matchers.not(Matchers.containsString("Somos"))))
                    .andDo(MockMvcResultHandlers.print());

            Mockito.verify(service, Mockito.never()).create(Mockito.any());
        }
        @ParameterizedTest
        @WithMockUser(username = "mock.user@mockmail.mock")
        @MethodSource("com.vtv.TechnicalTestDaffunchio.controllers.CarControllerTest#generateRequestMissingMandatoryAttributes")
        @DisplayName("Mandatory attributes missing")
        void test3(CarDTO requestWithMissingAttribute) throws Exception {
            CarDTO dto = generateCharacterDTOComplete();
            Mockito.when(service.save(Mockito.any())).thenReturn(dto);

            mockMvc.perform(MockMvcRequestBuilders.post("/cars")
                            .content(jsonMapper.writeValueAsString(requestWithMissingAttribute))
                            .contentType(MediaType.APPLICATION_JSON)
                    )
                    .andExpect(MockMvcResultMatchers.status().isBadRequest())
                    .andExpect(MockMvcResultMatchers.content().string(Matchers.not(Matchers.containsString("Car Test DTO"))))
                    .andDo(MockMvcResultHandlers.print());

            Mockito.verify(service, Mockito.never()).save(Mockito.any());
        }

        @ParameterizedTest
        @WithMockUser(username = "mock.user@mockmail.mock")
        @MethodSource("com.vtv.TechnicalTestDaffunchio.controllers.CarControllerTest#generateRequestWithBrokenAttributes")
        @DisplayName("Broken attributes")
        void test4(CarDTO requestWithBrokenAttribute) throws Exception {
            CarDTO dto = generateCharacterDTOComplete();
            Mockito.when(service.save(Mockito.any())).thenReturn(dto);

            mockMvc.perform(MockMvcRequestBuilders.post("/cars")
                            .content(jsonMapper.writeValueAsString(requestWithBrokenAttribute))
                            .contentType(MediaType.APPLICATION_JSON)
                    )
                    .andExpect(MockMvcResultMatchers.status().isBadRequest())
                    .andExpect(MockMvcResultMatchers.content().string(Matchers.not(Matchers.containsString("Car Test DTO"))))
                    .andDo(MockMvcResultHandlers.print());

            Mockito.verify(service, Mockito.never()).save(Mockito.any());

        }

    }

    @Nested
    class updateCarTest {

        @Test
        @DisplayName("Valid case")
        @WithMockUser(username = "mock.user@mockmail.mock")
        void test1() throws Exception {

            CarDTO dto = generateCharacterDTOComplete();
            String id = "25489dwssd";
            Mockito.when(service.update(Mockito.any(), Mockito.any())).thenReturn(dto);

            mockMvc.perform(MockMvcRequestBuilders.put("/cars?id_car=" + id)
                            .content(jsonMapper.writeValueAsString(dto))
                            .contentType(MediaType.APPLICATION_JSON)
                    )
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                    .andExpect(MockMvcResultMatchers.content().json(jsonMapper.writeValueAsString(dto)))
                    .andDo(MockMvcResultHandlers.print());

            Mockito.verify(service).update(Mockito.any(), Mockito.any());
        }
        @Test
        @DisplayName("No token provided")
        void testCreateContactWithoutToken() throws Exception {


            Mockito.when(jwtService.isBearer(Mockito.any())).thenReturn(false);

            ContactDTORequest request = ContactControllerTests.createMockContactDTORequest();
            ContactDTOResponse response = ContactControllerTests.createMockContactDTOResponse();
            Mockito.when(service.create(Mockito.any())).thenReturn(response);

            mockMvc.perform(MockMvcRequestBuilders.post(GlobalConstants.Endpoints.CONTACT)
                            .content(jsonMapper.writeValueAsString(request))
                            .contentType(MediaType.APPLICATION_JSON)
                    )
                    .andExpect(MockMvcResultMatchers.status().isForbidden())
                    .andExpect(MockMvcResultMatchers.content().string(Matchers.not(Matchers.containsString("Somos"))))
                    .andDo(MockMvcResultHandlers.print());

            Mockito.verify(service, Mockito.never()).create(Mockito.any());
        }

        @Test
        @DisplayName("Token not valid")
        void testCreateContactWithInvalidToken() throws Exception {

            Mockito.when(jwtService.isBearer(Mockito.any())).thenReturn(true);
            Mockito.when(jwtService.verify(Mockito.any())).thenThrow(new Exception());

            ContactDTORequest request = ContactControllerTests.createMockContactDTORequest();
            ContactDTOResponse response = ContactControllerTests.createMockContactDTOResponse();
            Mockito.when(service.create(Mockito.any())).thenReturn(response);

            mockMvc.perform(MockMvcRequestBuilders.post(GlobalConstants.Endpoints.CONTACT)
                            .content(jsonMapper.writeValueAsString(request))
                            .contentType(MediaType.APPLICATION_JSON)
                    )
                    .andExpect(MockMvcResultMatchers.status().isForbidden())
                    .andExpect(MockMvcResultMatchers.content().string(Matchers.not(Matchers.containsString("Somos"))))
                    .andDo(MockMvcResultHandlers.print());

            Mockito.verify(service, Mockito.never()).create(Mockito.any());
        }
        @Test
        @DisplayName(" Non-existing ID")
        @WithMockUser(username = "mock.user@mockmail.mock")
        void test2() throws Exception {

            CarDTO dto = generateCharacterDTOComplete();
            String id = "25489dwssd";
            Mockito.when(service.update(Mockito.any(), Mockito.any())).thenThrow(new NotFoundException("Car not found"));

            mockMvc.perform(MockMvcRequestBuilders.put("/cars?id_car=" + id)
                            .content(jsonMapper.writeValueAsString(dto))
                            .contentType(MediaType.APPLICATION_JSON)
                    )
                    .andExpect(MockMvcResultMatchers.status().isNotFound())
                    .andExpect(MockMvcResultMatchers.content().string(Matchers.not(Matchers.containsString("Car Test DTO"))))
                    .andDo(MockMvcResultHandlers.print());

            Mockito.verify(service).update(Mockito.any(), Mockito.any());
        }


        @ParameterizedTest
        @MethodSource("com.vtv.TechnicalTestDaffunchio.controllers.CarControllerTest#generateRequestMissingMandatoryAttributes")
        @DisplayName("Mandatory attributes missing")
        @WithMockUser(username = "mock.user@mockmail.mock")
        void test4(CarDTO requestWithMissingAttribute) throws Exception {
            CarDTO dto = generateCharacterDTOComplete();
            String id = "25489dwssd";
            Mockito.when(service.update(Mockito.any(), Mockito.any())).thenReturn(dto);

            mockMvc.perform(MockMvcRequestBuilders.put("/cars?id_car=" + id)
                            .content(jsonMapper.writeValueAsString(requestWithMissingAttribute))
                            .contentType(MediaType.APPLICATION_JSON)
                    )
                    .andExpect(MockMvcResultMatchers.status().isBadRequest())
                    .andExpect(MockMvcResultMatchers.content().string(Matchers.not(Matchers.containsString("Car Test DTO"))))
                    .andDo(MockMvcResultHandlers.print());

            Mockito.verify(service, Mockito.never()).save(Mockito.any());
        }

        @ParameterizedTest
        @MethodSource("com.vtv.TechnicalTestDaffunchio.controllers.CarControllerTest#generateRequestWithBrokenAttributes")
        @DisplayName("Broken attributes")
        @WithMockUser(username = "mock.user@mockmail.mock")
        void test5(CarDTO requestWithBrokenAttribute) throws Exception {
            CarDTO dto = generateCharacterDTOComplete();
            String id = "25489dwssd";
            Mockito.when(service.update(Mockito.any(), Mockito.any())).thenReturn(dto);

            mockMvc.perform(MockMvcRequestBuilders.put("/cars?id_car=" + id)
                            .content(jsonMapper.writeValueAsString(requestWithBrokenAttribute))
                            .contentType(MediaType.APPLICATION_JSON)
                    )
                    .andExpect(MockMvcResultMatchers.status().isBadRequest())
                    .andExpect(MockMvcResultMatchers.content().string(Matchers.not(Matchers.containsString("Car Test DTO"))))
                    .andDo(MockMvcResultHandlers.print());

            Mockito.verify(service, Mockito.never()).save(Mockito.any());

        }
    }

    @Nested
    class testDeleteCar {

        @Test
        @DisplayName("Valid case")
        @WithMockUser(username = "mock.user@mockmail.mock")
        void test1() throws Exception {

            String id = "55896fdd";

            mockMvc.perform(MockMvcRequestBuilders.delete("/cars?id_car=" + id))
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andDo(MockMvcResultHandlers.print());

            Mockito.verify(service).delete(Mockito.any());
        }

        @Test
        @DisplayName("Non-existing ID")
        @WithMockUser(username = "mock.user@mockmail.mock")
        void test2() throws Exception {

            String id = "55896fdd";
            Mockito.doThrow(new NotFoundException("Car not found")).when(service).delete(Mockito.any());

            mockMvc.perform(MockMvcRequestBuilders.delete("/cars?id_car=" + id)
                    )
                    .andExpect(MockMvcResultMatchers.status().isNotFound())
                    .andExpect(MockMvcResultMatchers.content().string(Matchers.not(Matchers.containsString("Car Test DTO"))))
                    .andDo(MockMvcResultHandlers.print());

            Mockito.verify(service).delete(Mockito.any());
        }

        @Test
        @DisplayName("No token provided")
        void testCreateContactWithoutToken() throws Exception {


            Mockito.when(jwtService.isBearer(Mockito.any())).thenReturn(false);

            ContactDTORequest request = ContactControllerTests.createMockContactDTORequest();
            ContactDTOResponse response = ContactControllerTests.createMockContactDTOResponse();
            Mockito.when(service.create(Mockito.any())).thenReturn(response);

            mockMvc.perform(MockMvcRequestBuilders.post(GlobalConstants.Endpoints.CONTACT)
                            .content(jsonMapper.writeValueAsString(request))
                            .contentType(MediaType.APPLICATION_JSON)
                    )
                    .andExpect(MockMvcResultMatchers.status().isForbidden())
                    .andExpect(MockMvcResultMatchers.content().string(Matchers.not(Matchers.containsString("Somos"))))
                    .andDo(MockMvcResultHandlers.print());

            Mockito.verify(service, Mockito.never()).create(Mockito.any());
        }

        @Test
        @DisplayName("Token not valid")
        void testCreateContactWithInvalidToken() throws Exception {

            Mockito.when(jwtService.isBearer(Mockito.any())).thenReturn(true);
            Mockito.when(jwtService.verify(Mockito.any())).thenThrow(new Exception());

            ContactDTORequest request = ContactControllerTests.createMockContactDTORequest();
            ContactDTOResponse response = ContactControllerTests.createMockContactDTOResponse();
            Mockito.when(service.create(Mockito.any())).thenReturn(response);

            mockMvc.perform(MockMvcRequestBuilders.post(GlobalConstants.Endpoints.CONTACT)
                            .content(jsonMapper.writeValueAsString(request))
                            .contentType(MediaType.APPLICATION_JSON)
                    )
                    .andExpect(MockMvcResultMatchers.status().isForbidden())
                    .andExpect(MockMvcResultMatchers.content().string(Matchers.not(Matchers.containsString("Somos"))))
                    .andDo(MockMvcResultHandlers.print());

            Mockito.verify(service, Mockito.never()).create(Mockito.any());
        }
    }

    @Nested
    class testGetAllCars {

        @Test
        @DisplayName("Valid case")
        @WithMockUser(username = "mock.user@mockmail.mock")
        void test1() throws Exception {

            List<CarDTOList> list = generateListCharacterDTO();

            Mockito.when(service.getAll()).thenReturn(list);

            mockMvc.perform(MockMvcRequestBuilders.get("/cars")
                    )
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                    .andExpect(MockMvcResultMatchers.content().json(jsonMapper.writeValueAsString(list)))
                    .andDo(MockMvcResultHandlers.print());

            Mockito.verify(service).getAll();
        }
        @Test
        @DisplayName("No token provided")
        void testCreateContactWithoutToken() throws Exception {


            Mockito.when(jwtService.isBearer(Mockito.any())).thenReturn(false);

            ContactDTORequest request = ContactControllerTests.createMockContactDTORequest();
            ContactDTOResponse response = ContactControllerTests.createMockContactDTOResponse();
            Mockito.when(service.create(Mockito.any())).thenReturn(response);

            mockMvc.perform(MockMvcRequestBuilders.post(GlobalConstants.Endpoints.CONTACT)
                            .content(jsonMapper.writeValueAsString(request))
                            .contentType(MediaType.APPLICATION_JSON)
                    )
                    .andExpect(MockMvcResultMatchers.status().isForbidden())
                    .andExpect(MockMvcResultMatchers.content().string(Matchers.not(Matchers.containsString("Somos"))))
                    .andDo(MockMvcResultHandlers.print());

            Mockito.verify(service, Mockito.never()).create(Mockito.any());
        }

        @Test
        @DisplayName("Token not valid")
        void testCreateContactWithInvalidToken() throws Exception {

            Mockito.when(jwtService.isBearer(Mockito.any())).thenReturn(true);
            Mockito.when(jwtService.verify(Mockito.any())).thenThrow(new Exception());

            ContactDTORequest request = ContactControllerTests.createMockContactDTORequest();
            ContactDTOResponse response = ContactControllerTests.createMockContactDTOResponse();
            Mockito.when(service.create(Mockito.any())).thenReturn(response);

            mockMvc.perform(MockMvcRequestBuilders.post(GlobalConstants.Endpoints.CONTACT)
                            .content(jsonMapper.writeValueAsString(request))
                            .contentType(MediaType.APPLICATION_JSON)
                    )
                    .andExpect(MockMvcResultMatchers.status().isForbidden())
                    .andExpect(MockMvcResultMatchers.content().string(Matchers.not(Matchers.containsString("Somos"))))
                    .andDo(MockMvcResultHandlers.print());

            Mockito.verify(service, Mockito.never()).create(Mockito.any());
        }
    }

    @Nested
    class testGetCarDTO {

        @Test
        @DisplayName("Valid case")
        @WithMockUser(username = "mock.user@mockmail.mock")
        void test1() throws Exception {

            CarDTO dto = generateCharacterDTOComplete();
            String id = "55896fdd";
            Mockito.when(service.getById(Mockito.any())).thenReturn(dto);

            mockMvc.perform(MockMvcRequestBuilders.get("/cars/car/response?id_car=" + id))
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                    .andExpect(MockMvcResultMatchers.content().json(jsonMapper.writeValueAsString(dto)))
                    .andDo(MockMvcResultHandlers.print());

            Mockito.verify(service).getById(Mockito.any());
        }

        @Test
        @DisplayName("Non-existing ID")
        @WithMockUser(username = "mock.user@mockmail.mock")
        void test2() throws Exception {

            CarDTO dto = generateCharacterDTOComplete();
            String id = "55896fdd";
            Mockito.when(service.getById(Mockito.any())).thenThrow(new NotFoundException("Car not found"));

            mockMvc.perform(MockMvcRequestBuilders.get("/cars/car/response?id_car=" + id)
                    )
                    .andExpect(MockMvcResultMatchers.status().isNotFound())
                    .andExpect(MockMvcResultMatchers.content().string(Matchers.not(Matchers.containsString("Car Test DTO"))))
                    .andDo(MockMvcResultHandlers.print());

            Mockito.verify(service).getById(Mockito.any());
        }
        @Test
        @DisplayName("No token provided")
        void testCreateContactWithoutToken() throws Exception {


            Mockito.when(jwtService.isBearer(Mockito.any())).thenReturn(false);

            ContactDTORequest request = ContactControllerTests.createMockContactDTORequest();
            ContactDTOResponse response = ContactControllerTests.createMockContactDTOResponse();
            Mockito.when(service.create(Mockito.any())).thenReturn(response);

            mockMvc.perform(MockMvcRequestBuilders.post(GlobalConstants.Endpoints.CONTACT)
                            .content(jsonMapper.writeValueAsString(request))
                            .contentType(MediaType.APPLICATION_JSON)
                    )
                    .andExpect(MockMvcResultMatchers.status().isForbidden())
                    .andExpect(MockMvcResultMatchers.content().string(Matchers.not(Matchers.containsString("Somos"))))
                    .andDo(MockMvcResultHandlers.print());

            Mockito.verify(service, Mockito.never()).create(Mockito.any());
        }

        @Test
        @DisplayName("Token not valid")
        void testCreateContactWithInvalidToken() throws Exception {

            Mockito.when(jwtService.isBearer(Mockito.any())).thenReturn(true);
            Mockito.when(jwtService.verify(Mockito.any())).thenThrow(new Exception());

            ContactDTORequest request = ContactControllerTests.createMockContactDTORequest();
            ContactDTOResponse response = ContactControllerTests.createMockContactDTOResponse();
            Mockito.when(service.create(Mockito.any())).thenReturn(response);

            mockMvc.perform(MockMvcRequestBuilders.post(GlobalConstants.Endpoints.CONTACT)
                            .content(jsonMapper.writeValueAsString(request))
                            .contentType(MediaType.APPLICATION_JSON)
                    )
                    .andExpect(MockMvcResultMatchers.status().isForbidden())
                    .andExpect(MockMvcResultMatchers.content().string(Matchers.not(Matchers.containsString("Somos"))))
                    .andDo(MockMvcResultHandlers.print());

            Mockito.verify(service, Mockito.never()).create(Mockito.any());
        }
    }


    @Nested
    class testGetOwnerCars {

        @Test
        @DisplayName("Valid case")
        @WithMockUser(username = "mock.user@mockmail.mock")
        void test1() throws Exception {

            List<CarDTOList> list = generateListCharacterDTO();
            String owner = "Daffunchio Brenda";
            Mockito.when(service.getAllByOwner(Mockito.any())).thenReturn(list);

            mockMvc.perform(MockMvcRequestBuilders.get("/cars/owner?owner=" + owner)
                    )
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                    .andExpect(MockMvcResultMatchers.content().json(jsonMapper.writeValueAsString(list)))
                    .andDo(MockMvcResultHandlers.print());

            Mockito.verify(service).getAllByOwner(Mockito.any());
        }


    }

    @Nested
    class testGetSuitableCars {

        @Test
        @DisplayName("Valid case")
        @WithMockUser(username = "mock.user@mockmail.mock")
        void test1() throws Exception {

            List<CarDTOList> list = generateListCharacterDTO();

            Mockito.when(service.getAllSuitableCars()).thenReturn(list);

            mockMvc.perform(MockMvcRequestBuilders.get("/cars/suitable")

                    )
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                    .andExpect(MockMvcResultMatchers.content().json(jsonMapper.writeValueAsString(list)))
                    .andDo(MockMvcResultHandlers.print());

            Mockito.verify(service).getAllSuitableCars();
        }
    }
    @Nested
    class testGetConditionalCars {

        @Test
        @DisplayName("Valid case")
        @WithMockUser(username = "mock.user@mockmail.mock")
        void test1() throws Exception {

            List<CarDTOList> list = generateListCharacterDTO();

            Mockito.when(service.getAllConditionalCars()).thenReturn(list);

            mockMvc.perform(MockMvcRequestBuilders.get("/cars/conditional")
                    )
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                    .andExpect(MockMvcResultMatchers.content().json(jsonMapper.writeValueAsString(list)))
                    .andDo(MockMvcResultHandlers.print());

            Mockito.verify(service).getAllConditionalCars();
        }
    }



    private static CharacterDTOComplete generateCharacterDTOComplete() {
        CharacterDTOComplete dto = new CharacterDTOComplete();
       dto.setName("Alicia");
       dto.setAge(85);
       dto.setWeight(45);
       dto.setHistory("Alicia...");
        return dto;
    }

    private static Character generateCharacterEntity() {
       Character character = new Character();
character.setAge(52);
character.setName("Alicia");
character.setHistory("Alicia...");
character.setWeight(52);
character.setSoft_delete(false);

        return character;
    }

    private static List<CarDTO> generateRequestMissingMandatoryAttributes() {
        List<CarDTO> requests = new ArrayList<>();
        CarDTO singleRequest;

        // CASE 1: Missing brand
        singleRequest = generateCharacterDTOComplete();
        singleRequest.setBrand(null);
        requests.add(singleRequest);

        // CASE 2: Missing domain
        singleRequest = generateCharacterDTOComplete();
        singleRequest.setDomain(null);
        requests.add(singleRequest);

        // CASE 3: Missing domain
        singleRequest = generateCharacterDTOComplete();
        singleRequest.setModel(null);
        requests.add(singleRequest);


        return requests;
    }

    private static List<CharacterDTOComplete> generateRequestWithBrokenAttributes() {
        List<CharacterDTOComplete> requests = new ArrayList<>();
        CharacterDTOComplete singleRequest;

        // CASE 1: Blank brand
        singleRequest = generateCharacterDTOComplete();
        singleRequest.setBrand("");
        requests.add(singleRequest);

        // CASE 2: Blank domain
        singleRequest = generateCharacterDTOComplete();
        singleRequest.setDomain("");
        requests.add(singleRequest);

        // CASE 3: Blank domain
        singleRequest = generateCharacterDTOComplete();
        singleRequest.setModel("");
        requests.add(singleRequest);

        return requests;
    }

    private static CharacterDTO generateCharacterDTO() {
        CharacterDTO dto = new CharacterDTO();
        dto.setImage("image.jpg");
        dto.setName("Alicia");
        return dto;
    }

    private static List<CharacterDTO> generateListCharacterDTO() {
        List<CharacterDTO> list = new ArrayList<>();

        for (int i = 0; i < 4; i++) {

            list.add(generateCharacterDTO());
        }
        return list;
    }

}

}
