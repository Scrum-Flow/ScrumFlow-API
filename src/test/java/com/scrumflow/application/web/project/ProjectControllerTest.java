package com.scrumflow.application.web.project;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.scrumflow.TestMockConfig;
import com.scrumflow.domain.service.ProjectService;
import com.scrumflow.mock.ProjectMock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

@WebMvcTest(ProjectController.class)
@ContextConfiguration(classes = {TestMockConfig.class, ProjectController.class})
class ProjectControllerTest {

    @MockBean ProjectService projectService;

    @Autowired MockMvc mockMvc;

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(new ProjectController(projectService)).build();
    }

    @Test
    void deveCriarProjetoComSucesso() throws Exception {
        var request = ProjectMock.projectRequestMock();
        var response = ProjectMock.projectResponseMock();

        when(projectService.createProject(any())).thenReturn(response);

        mockMvc
                .perform(
                        post("/api/v1/project")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(
                                        "{\"name\":\"Sample Project\",\"description\":\"Sample Description\",\"startDate\":\"2024-01-01\",\"endDate\":\"2024-12-31\",\"active\":true}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Sample Project"));

        verify(projectService, times(1)).createProject(any());
    }

    @Test
    void deveRetornarListaDeProjetos() throws Exception {
        var response = List.of(ProjectMock.projectResponseMock());

        when(projectService.findAllProjects()).thenReturn(response);

        mockMvc
                .perform(get("/api/v1/project").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Sample Project"));

        verify(projectService, times(1)).findAllProjects();
    }

    @Test
    void deveRetornarProjetoPorId() throws Exception {
        var response = ProjectMock.projectResponseMock();

        when(projectService.findProjectById(eq(1L))).thenReturn(response);

        mockMvc
                .perform(get("/api/v1/project/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Sample Project"));

        verify(projectService, times(1)).findProjectById(eq(1L));
    }

    @Test
    void deveRetornarDetalhesDoProjeto() throws Exception {
        var response = ProjectMock.projectDetailsResponseMock();

        when(projectService.findProjectDetailsById(eq(1L))).thenReturn(response);

        mockMvc
                .perform(get("/api/v1/project/1/details").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.description").value("Detailed Description"));

        verify(projectService, times(1)).findProjectDetailsById(eq(1L));
    }

    @Test
    void deveAtualizarProjetoComSucesso() throws Exception {
        doNothing().when(projectService).updateProject(eq(1L), any());

        mockMvc
                .perform(
                        put("/api/v1/project/1")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{\"name\":\"Updated Project\",\"active\":true}"))
                .andExpect(status().isOk());

        verify(projectService, times(1)).updateProject(eq(1L), any());
    }

    @Test
    void deveDeletarProjetoComSucesso() throws Exception {
        doNothing().when(projectService).deleteProject(eq(1L));

        mockMvc
                .perform(delete("/api/v1/project/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(projectService, times(1)).deleteProject(eq(1L));
    }

    @Test
    void deveAtualizarColunasKanbanDoProjeto() throws Exception {
        var request = ProjectMock.projectKanbanColumnsRequestMock();

        doNothing().when(projectService).updateProjectKanbanColumns(eq(1L), any());

        mockMvc
                .perform(
                        post("/api/v1/project/1/kanban-columns")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{\"columns\":[\"TO_DO\",\"IN_PROGRESS\",\"DONE\"]}"))
                .andExpect(status().isOk());

        verify(projectService, times(1)).updateProjectKanbanColumns(eq(1L), any());
    }
}
