package seedu.addressbook.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import seedu.addressbook.data.exception.IllegalValueException;
import seedu.addressbook.data.person.*;
import seedu.addressbook.data.tag.Tag;
import seedu.addressbook.data.tag.UniqueTagList;

public class AddCommandWindow {

    @FXML
    private TextField nameField;

    @FXML
    private TextField phoneField;

    @FXML
    private TextField emailField;

    @FXML
    private TextField addressField;

    @FXML
    private TextField tagsField;

    private Stage addCommandStage;
    private Name name;
    private Phone phone;
    private Email email;
    private Address address;
    private UniqueTagList tags;
    private boolean okClicked = false;

    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {
    }

    /**
     * Sets the stage of this dialog.
     *
     * @param addCommandStage
     */
    public void setAddStage(Stage addCommandStage) {
        this.addCommandStage = addCommandStage;
    }



    /**
     * Returns true if the user clicked OK, false otherwise.
     *
     * @return
     */
    public boolean isOkClicked() {
        return okClicked;
    }

    /**
     * Called when the user clicks ok.
     */
    @FXML
    private void handleOk() throws IllegalValueException{
        if (isInputValid()) {
            name = new Name(nameField.getText());
            phone = new Phone(phoneField.getText(), false);
            email = new Email(emailField.getText(), false);
            address = new Address(addressField.getText(), false);
            Tag tag = new Tag(tagsField.getText());
            tags = new UniqueTagList(tag);

            okClicked = true;
            addCommandStage.close();
        }
    }

    /**
     * Called when the user clicks cancel.
     */
    @FXML
    private void handleCancel() {
        addCommandStage.close();
    }

    private boolean isInputValid() {
        String errorMessage = "";

        if (nameField.getText() == null || nameField.getText().length() == 0
                || !Name.isValidName(nameField.getText())) {
            errorMessage += Name.MESSAGE_NAME_CONSTRAINTS + "\n";
        }
        if (phoneField.getText() == null || phoneField.getText().length() == 0
                || !Phone.isValidPhone(phoneField.getText())) {
            errorMessage += Phone.MESSAGE_PHONE_CONSTRAINTS + "\n";
        }
        if (emailField.getText() == null || emailField.getText().length() == 0
                || !Email.isValidEmail(emailField.getText()))
            errorMessage += Email.MESSAGE_EMAIL_CONSTRAINTS + "\n";

        if (addressField.getText() == null || addressField.getText().length() == 0
                || !Address.isValidAddress(addressField.getText())) {
            errorMessage += Address.MESSAGE_ADDRESS_CONSTRAINTS + "\n";
        }
        if(Tag.isValidTagName(tagsField.getText())) {
            errorMessage += Tag.MESSAGE_TAG_CONSTRAINTS + "\n";
        }

        if (errorMessage.length() == 0) {
            return true;
        }
        else {
            // Show the error message.
            Alert alert = new Alert(AlertType.ERROR);
            alert.initOwner(addCommandStage);
            alert.setTitle("Invalid Fields");
            alert.setHeaderText("Please correct invalid fields");
            alert.setContentText(errorMessage);

            alert.showAndWait();

            return false;
        }
    }
    

}
