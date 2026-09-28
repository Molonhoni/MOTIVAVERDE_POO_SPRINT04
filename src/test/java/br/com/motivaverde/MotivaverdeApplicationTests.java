package br.com.motivaverde;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class MotivaverdeApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void deveCarregarContextoDaAplicacao() {
    }

    @Test
    void deveListarEquipesComStatus200() throws Exception {

        mockMvc.perform(
                get("/api/equipes")
        )
        .andExpect(status().isOk());
    }

    @Test
    void deveCriarEquipeComStatus201() throws Exception {

        String json = """
                {
                    "nome": "Equipe Teste MockMvc",
                    "especialidade": "Rocada Mecanizada"
                }
                """;

        mockMvc.perform(
                post("/api/equipes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
        )
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.id").exists())
        .andExpect(jsonPath("$.nome")
                .value("Equipe Teste MockMvc"))
        .andExpect(jsonPath("$.especialidade")
                .value("Rocada Mecanizada"));
    }

    @Test
    void deveRetornar400AoCriarEquipeSemNome()
            throws Exception {

        String json = """
                {
                    "nome": "",
                    "especialidade": "Rocada Mecanizada"
                }
                """;

        mockMvc.perform(
                post("/api/equipes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
        )
        .andExpect(status().isBadRequest());
    }

    @Test
    void deveRetornar404ParaEquipeInexistente()
            throws Exception {

        mockMvc.perform(
                get("/api/equipes/999999")
        )
        .andExpect(status().isNotFound());
    }
}