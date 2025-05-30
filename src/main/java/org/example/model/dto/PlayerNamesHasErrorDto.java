package org.example.model.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PlayerNamesHasErrorDto {

    private boolean playerOneNameNotValid;
    private boolean playerTwoNameNotValid;
    private boolean playerNamesAreSame;
    private String playerOneError;
    private String playerTwoError;
    private String commonError;
}