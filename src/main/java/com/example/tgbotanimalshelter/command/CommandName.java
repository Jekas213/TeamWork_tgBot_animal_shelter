package com.example.tgbotanimalshelter.command;

public enum CommandName {
    START("/start"),
    DOGS("/dogs"),
    CATS("/cats"),
    INFO_DOGS("/infoDogs"),
    INFO_CATS("/infoCats"),
    TAKE_DOG_COM("/howTakeDog"),
    TAKE_CAT_COM("/howTakeCat"),
    DOG_REPORT("/sendReportDogs"),
    CAT_REPORT("/sendReportCats"),
    DESCRIPTION_CAT("/descriptionCat"),
    ADDRESS_CAT("/addressCat"),
    CONTACT_CAT("/contactCat"),
    SAFETY_CAT("/safetyCat"),
    RECORDING_CAT("/recordingCat"),
    DESCRIPTION_DOG("/descriptionDog"),
    ADDRESS_DOG("/addressDog"),
    CONTACT_DOG("/contactDog"),
    SAFETY_DOG("/safetyDog"),
    RECORDING_DOG("/recordingDog"),
    DATING_RULES_DOG_COM("/rulesDog"),
    DOCUMENTS_DOG_COM("/documentDog"),
    TRANSPORTATION_DOG_COM("/transportationDog"),
    ARRANGING_PUPPY_COM("/arrangingPuppy"),
    ARRANGING_DOG_COM("/arrangingDog"),
    DISABILITIES_DOG_COM("/disabilitiesDog"),
    TIPS_DOG_HANDLER_COM("/tipsDogHandler"),
    LIST_DOG_HANDLER_COM("/listDogHandler"),
    REFUSALS_DOG_COM("/refusalsDog"),
    DATING_RULES_CAT_COM("/rulesCat"),
    DOCUMENTS_CAT_COM("/documentCat"),
    TRANSPORTATION_CAT_COM("/transportationCat"),
    ARRANGING_KITTY_COM("/arrangingKitty"),
    ARRANGING_CAT_COM("/arrangingCat"),
    DISABILITIES_CAT_COM("/disabilitiesCat"),
    REFUSALS_CAT_COM("/refusalsCat"),
    CALL_VOLUNTEER("/callVolunteer");


    private final String commandName;

    CommandName(String commandName) {
        this.commandName = commandName;
    }

    public String getCommandName() {
        return commandName;
    }
}
