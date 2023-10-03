package com.example.tgbotanimalshelter.service;

import com.example.tgbotanimalshelter.entity.DogParent;
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
class DogParentChangeStatusTest {
    @Mock
    private SendMessageService sendMessageService;

    @Mock
    private DogParentService dogParentService;

    @InjectMocks
    private DogParentChangeStatus dogParentChangeStatus;


    @Test
    void inviteTrialStatusTest() {
        DogParent dogParent = DogParent.builder().chatId(1L).build();

        when(dogParentService.findById(dogParent.getChatId())).thenReturn(dogParent);

        dogParentChangeStatus.inviteTrialStatus(dogParent.getChatId());

        verify(dogParentService).update(dogParent.getChatId(), dogParent);
        verify(sendMessageService).sendMassage(dogParent.getChatId(), "поздравляем вы взяли питомца");
        assertThat(dogParent.getStatus()).isEqualTo(TRIAL_PERIOD);
    }

    @Test
    void inviteApprovedStatusTest() {
        DogParent dogParent = DogParent.builder().chatId(1L).build();

        when(dogParentService.findById(dogParent.getChatId())).thenReturn(dogParent);

        dogParentChangeStatus.inviteApprovedStatus(dogParent.getChatId());

        verify(dogParentService).update(dogParent.getChatId(), dogParent);
        verify(sendMessageService).sendMassage(dogParent.getChatId(), "поздравляем вы стали полноценным хозяином питомца");
        assertThat(dogParent.getStatus()).isEqualTo(APPROVED);
    }

    @Test
    void inviteRefusedStatusTest() {
        DogParent dogParent = DogParent.builder().chatId(1L).build();

        when(dogParentService.findById(dogParent.getChatId())).thenReturn(dogParent);

        dogParentChangeStatus.inviteRefusedStatus(dogParent.getChatId());

        verify(dogParentService).update(dogParent.getChatId(), dogParent);
        verify(sendMessageService).sendMassage(dogParent.getChatId(), "заявка отклонена");
        assertThat(dogParent.getStatus()).isEqualTo(REFUSED);
    }
}