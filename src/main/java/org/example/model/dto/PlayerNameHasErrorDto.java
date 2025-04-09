package org.example.model.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PlayerNameHasErrorDto {
    private boolean playerOneNameNotValid = false;
    private boolean playerTwoNameNotValid = false;
    private boolean playerNamesAreSame = false;
    private String playerOneError;
    private String playerTwoError;
    private String commonError;
}
