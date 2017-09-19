package seedu.addressbook.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import seedu.addressbook.commands.AddCommand;
import seedu.addressbook.commands.CommandResult;
import seedu.addressbook.commands.Command;
import seedu.addressbook.data.exception.IllegalValueException;
import seedu.addressbook.data.person.*;
import seedu.addressbook.data.tag.Tag;
import seedu.addressbook.data.tag.UniqueTagList;
import seedu.addressbook.logic.Logic;

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
    private Logic logic;

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
    public void setAddStage(Stage addCommandStage, Logic logic) {
        this.addCommandStage = addCommandStage;
        this.logic = logic;
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
    private void handleOk() throws Exception{
        if(isInputValid()) {
            name = new Name(nameField.getText().trim());
            phone = new Phone(phoneField.getText().trim(), false);
            email = new Email(emailField.getText().trim(), false);
            address = new Address(addressField.getText().trim(), false);
            tags = new UniqueTagList((tagsField.getText().trim()));

            okClicked = true;
            Person newPerson = new Person(name, phone, email, address, tags);
            AddCommand adder = new AddCommand(newPerson);
            logic.execute(adder);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.getDialogPane().setMinSize(200,175);
            alert.setTitle("AddressBook Add Command");
            alert.setHeaderText("Add Successful");
            alert.setContentText("Person added successfully");

            alert.showAndWait();

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

        if (!Name.isValidName(nameField.getText().trim())) {
            errorMessage += Name.MESSAGE_NAME_CONSTRAINTS + "\n";
        }
        if (!Phone.isValidPhone(phoneField.getText().trim())) {
            errorMessage += Phone.MESSAGE_PHONE_CONSTRAINTS + "\n";
        }
        if (!Email.isValidEmail(emailField.getText().trim()))
            errorMessage += Email.MESSAGE_EMAIL_CONSTRAINTS + "\n";

        if (!Address.isValidAddress(addressField.getText().trim())) {
            errorMessage += Address.MESSAGE_ADDRESS_CONSTRAINTS + "\n";
        }
        if(!Tag.isValidTags(tagsField.getText().trim())) {
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
