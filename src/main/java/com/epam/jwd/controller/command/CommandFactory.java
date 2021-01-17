package com.epam.jwd.controller.command;

import com.epam.jwd.controller.command.impl.*;

public class CommandFactory {

    public static Command command(String command) {
        if (command == null) {
            command = "main";
        }
        switch (command) {
            case "addUser":
                return new AddUserCommand();
            case "addMessage":
                return new WriteMessageCommand();
            case "addSection":
                return new AddSectionCommand();
            case "addConference":
                return new AddConferenceCommand();
            case "addRequest":
                return new JoinSectionRequestCommand();
            case "editUser":
                return new GetEditUserFormCommand();
            case "editConference":
                return new GetEditConferenceFormCommand();
            case "editSection":
                return new GetEditSectionFormCommand();
            case "saveChangeUserAccount":
                return new EditUserCommand();
            case "saveConference":
                return new UpdateConferenceCommand();
            case "profile":
                return new GetEditUserProfileFormCommand();
            case "updateUserInfo":
                return new EditProfileCommand();
            case "showAllUsers":
                return new ShowAllUsersCommand();
            case "sections":
                return new ShowAllSectionsCommand();
            case "section":
                return new ShowSectionCommand();
            case "messages":
                return new GetShowMessagesFormCommand();
            case "toMessageReplyForm":
                return new GetMessageReplyFormCommand();
            case "allUserRequests":
                return new ShowUserRequestsCommand();
            case "showNewRequests":
                return new ShowNewRequestsCommand();
            case "loginPage":
                return new GetLoginFormCommand();
            case "login":
                return new LoginCommand();
            case "registration":
                return new GetRegistrationFormCommand();
            case "logout":
                return new LogoutCommand();
            case "toWriteMessagePage":
                return new GetWriteMessageFormCommand();
            case "setLocale":
                return new SetLocaleCommand();
            case "toEditConferencesPage":
                return new GetShowAndEditConferencesFormCommand();
            case "toCreateConferencePage":
                return new GetAddConferenceFormCommand();
            case "toEditSections":
                return new GetShowAndEditSectionsFormCommand();
            case "toCreateSectionForm":
                return new GetAddSectionForm();
            case "deleteRequest":
                return new DeleteRequestCommand();
            case "changeStateRequest":
                return new ChangeStateRequestCommand();
            case "deleteUser":
                return new DeleteUserCommand();
            case "deleteConference":
                return new DeleteConferenceCommand();
            case "deleteSection":
                return new DeleteSectionCommand();
            case "sendMessage":
                return new SendMessageCommand();
            default:
                return new MainCommand();
        }
    }
}
