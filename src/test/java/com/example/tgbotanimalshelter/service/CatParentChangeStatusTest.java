package com.example.tgbotanimalshelter.service;

import com.example.tgbotanimalshelter.entity.CatParent;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.example.tgbotanimalshelter.entity.Status.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CatParentChangeStatusTest {

    @Mock
    private CatParentService catParentService;

    @Mock
    private SendMessageService sendMessageService;

    @InjectMocks
    private CatParentChangeStatus catParentChangeStatus;

    @Test
    void inviteTrialStatusTest() {
        CatParent catParent = CatParent.builder().chatId(1L).build();

        when(catParentService.findById(catParent.getChatId())).thenReturn(catParent);

        catParentChangeStatus.inviteTrialStatus(catParent.getChatId());

        verify(catParentService).update(catParent.getChatId(), catParent);
        verify(sendMessageService).sendMassage(catParent.getChatId(), "поздравляем вы взяли питомца");
        assertThat(catParent.getStatus()).isEqualTo(TRIAL_PERIOD);
    }

    @Test
    void inviteApprovedStatusTest() {
        CatParent catParent = CatParent.builder().chatId(1L).build();

        when(catParentService.findById(catParent.getChatId())).thenReturn(catParent);

        catParentChangeStatus.inviteApprovedStatus(catParent.getChatId());

        verify(catParentService).update(catParent.getChatId(), catParent);
        verify(sendMessageService).sendMassage(catParent.getChatId(), "поздравляем вы стали полноценным хозяином питомца");
        assertThat(catParent.getStatus()).isEqualTo(APPROVED);
    }

    @Test
    void inviteRefusedStatusTest() {
        CatParent catParent = CatParent.builder().chatId(1L).build();

        when(catParentService.findById(catParent.getChatId())).thenReturn(catParent);

        catParentChangeStatus.inviteRefusedStatus(catParent.getChatId());

        verify(catParentService).update(catParent.getChatId(), catParent);
        verify(sendMessageService).sendMassage(catParent.getChatId(), "заявка отклонена");
        assertThat(catParent.getStatus()).isEqualTo(REFUSED);
    }
}