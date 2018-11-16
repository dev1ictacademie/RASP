package nl.pameijer.ictacademie.rasp.view.inputstudent.fxml;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.math.BigInteger;
import nl.pameijer.ictacademie.rasp.model.Student;


/**
 * Dialog to edit details of a person.
 */
public class PersonEditDialogController {

    @FXML
    private TextField firstNameField, middleNameField, lastNameField, postalCodeField, emailField,
    	bankField, phone1Field, phone2Field, phoneRField;


    private Stage dialogStage;
    private Student person;
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
     * @param dialogStage
     */
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    /**
     * Sets the person to be edited in the dialog.
     * 
     * @param person
     */
    public void setPerson(Student person) {
        this.person = person;

        firstNameField.setText(person.getFName());
        middleNameField.setText(person.getNamePrefix());
        lastNameField.setText(person.getLName());
        postalCodeField.setText(person.getPostalCode());
        emailField.setText(person.getEmail());
        bankField.setText(person.getBank());
        phone1Field.setText(person.getPhonePrimary());
        phone2Field.setText(person.getPhoneSecondary());
        phoneRField.setText(person.getPhoneRelation());
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
    private void handleOk() {
        if (isInputValid()) {
            person.setFName(firstNameField.getText());
            person.setNamePrefix(middleNameField.getText());
            person.setLName(lastNameField.getText());
            person.setPostalCode(postalCodeField.getText());
            person.setEmail(emailField.getText());
            person.setBank(bankField.getText());
            person.setPhonePrimary(phone1Field.getText());
            person.setPhoneSecondary(phone2Field.getText());
            person.setPhoneRelation(phoneRField.getText());

            okClicked = true;
            dialogStage.close();
        }
    }

    /**
     * Called when the user clicks cancel.
     */
    @FXML
    private void handleCancel() {
        dialogStage.close();
    }

    /**
     * Validates the user input in the text fields.
     * 
     * @return true if the input is valid
     */
    private boolean isInputValid() {
        String errorMessage = "";

        if (firstNameField.getText() == null || firstNameField.getText().length() == 0) {
            errorMessage += "No valid first name!\n"; 
        }
        
        if (lastNameField.getText() == null || lastNameField.getText().length() == 0) {
            errorMessage += "No valid last name!\n"; 
        }

        if(!postalCodeField.getText().equals("") && !postalCodeField.getText().matches("[0-9]{4}[A-Z]{2}.*")) {errorMessage += "Invalid postal code\n";}

        if(!emailField.getText().equals("")) {
            if(!emailField.getText().matches("(.*)@(.*).(.*)")) {
                errorMessage += "Invalid email\n";
            }
        }
        
        if(!bankField.getText().equals("")) {
            if(bankField.getText().length() == 18) {
                String temp = bankField.getText();
                temp = temp.concat(temp.substring(0,4)).substring(4);
                String b = "";
                for(char c : temp.toCharArray()) {
                    if(Character.isLetter(c)) {
                        b+=Character.getNumericValue(c);
                    }
                    else{b+=c;}
                }
                BigInteger bla = new BigInteger(b);
                if(!bla.remainder(BigInteger.valueOf(97)).equals(BigInteger.ONE))
                    errorMessage += "Invalid bank";
            }
            else {errorMessage += "Invalid bank";}
        }
        
        if(!phone1Field.getText().equals("")) {
            if(!phone1Field.getText().matches("([0-9]{10})")) {
                errorMessage += "Invalid primary phone\n";
            }
        }
        
        if(!phone2Field.getText().equals("")) {
            if(!phone2Field.getText().matches("([0-9]{10})")) {
                errorMessage += "Invalid secondary phone\n";
            }
        }
        
        if(!phoneRField.getText().equals("")) {
            if(!phoneRField.getText().matches("([0-9]{10})")) {
                errorMessage += "Invalid relation phone\n";
            }
        }
        
        if (errorMessage.length() == 0) {
            return true;
        } else {
            // Show the error message.
            Alert alert = new Alert(AlertType.ERROR);
            alert.initOwner(dialogStage);
            alert.setTitle("Invalid Fields");
            alert.setHeaderText("Please correct invalid fields");
            alert.setContentText(errorMessage);
            
            alert.showAndWait();
            
            return false;
        }
    }
}