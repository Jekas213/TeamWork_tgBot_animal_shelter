package com.example.tgbotanimalshelter.controller;

import com.example.tgbotanimalshelter.service.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static com.example.tgbotanimalshelter.service.ServiceMessage.BAD_REPORT;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(controllers = VolunteerController.class)
class VolunteerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private VolunteerService volunteerService;

    @MockBean
    private SendMessageService sendMessageService;

    @MockBean
    private VolunteerChatService volunteerChatService;

    @MockBean
    private UserChatService userChatService;

    @MockBean
    private DogParentChangeStatus dogParentChangeStatus;

    @MockBean
    private CatParentChangeStatus catParentChangeStatus;

    private static final String ROOT = "/volunteer";

    @Test
    void sendMassageBadReportTest() throws Exception {
        long chatId = 1L;
        String text = "text";

        mockMvc.perform(patch(ROOT + "/badReport/{id}", chatId)
                        .param("text", text))
                .andExpect(status().isOk());

        verify(sendMessageService).sendMassage(chatId, text);
    }

    @Test
    void sendMassageBadReportWhenTextIsNullTest() throws Exception {
        long chatId = 1L;

        mockMvc.perform(patch(ROOT + "/badReport/{id}", chatId))
                .andExpect(status().isOk());

        verify(sendMessageService).sendMassage(chatId, BAD_REPORT.getCommandName());
    }

    @Test
    void sendMassageFromVolunteerTest() throws Exception {
        long userId = 1L, volunteerId = 1L;
        String text = "text";

        mockMvc.perform(patch(ROOT + "/message/{userId}/{volunteerId}", userId, volunteerId)
                        .param("text", text))
                .andExpect(status().isOk());

        verify(volunteerChatService).sendMessageToUser(userId, volunteerId, text);
    }

    @Test
    void openChatByVolunteerTest() throws Exception {
        long id = 1L;

        mockMvc.perform(patch(ROOT + "/openChat/{id}", id))
                .andExpect(status().isOk());

        verify(userChatService).inviteOpenChatStatus(id);
    }

    @Test
    void closeChatByVolunteerTest() throws Exception {
        long id = 1L;

        mockMvc.perform(patch(ROOT + "/closeChat/{id}", id))
                .andExpect(status().isOk());

        verify(userChatService).inviteBasicStatus(id);
    }

    @Test
    void changeByTrialDogTest() throws Exception {
        long id = 1L;

        mockMvc.perform(patch(ROOT + "/dog/trial/{id}", id))
                .andExpect(status().isOk());

        verify(dogParentChangeStatus).inviteTrialStatus(id);
    }

    @Test
    void changeByApprovedDogTest() throws Exception {
        long id = 1L;

        mockMvc.perform(patch(ROOT + "/dog/approved/{id}", id))
                .andExpect(status().isOk());

        verify(dogParentChangeStatus).inviteApprovedStatus(id);
    }

    @Test
    void changeByRefusedDogTest() throws Exception {
        long id = 1L;

        mockMvc.perform(patch(ROOT + "/dog/refused/{id}", id))
                .andExpect(status().isOk());

        verify(dogParentChangeStatus).inviteRefusedStatus(id);
    }

    @Test
    void changeByTrialCatTest() throws Exception {
        long id = 1L;

        mockMvc.perform(patch(ROOT + "/cat/trial/{id}", id))
                .andExpect(status().isOk());

        verify(catParentChangeStatus).inviteTrialStatus(id);
    }

    @Test
    void changeByApprovedCatTest() throws Exception {
        long id = 1L;

        mockMvc.perform(patch(ROOT + "/cat/approved/{id}", id))
                .andExpect(status().isOk());

        verify(catParentChangeStatus).inviteApprovedStatus(id);
    }

    @Test
    void changeByRefusedCatTest() throws Exception {
        long id = 1L;

        mockMvc.perform(patch(ROOT + "/cat/refused/{id}", id))
                .andExpect(status().isOk());

        verify(catParentChangeStatus).inviteRefusedStatus(id);
    }
}