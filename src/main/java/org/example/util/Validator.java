package org.example.util;

import org.example.exception.InvalidParameterException;

import java.util.UUID;

public class Validator {

    public static int convertIdToInt(String id) {
        if (id == null || id.isBlank()) {
            throw new InvalidParameterException("Missing parameter - player id");
        }
        try {
            int num = Integer.parseInt(id);
            if (num < 1) {
                throw new InvalidParameterException("id can not be 0 or less");
            }
            return num;
        } catch (IllegalArgumentException e) {
            throw new InvalidParameterException("Invalid player id format");
        }
    }

    public static UUID convertToUUID(String uuid) {

        if (uuid == null || uuid.isBlank()) {
            throw new InvalidParameterException("Missing parameter - uuid");
        }

        try {
            return UUID.fromString(uuid);
        } catch (IllegalArgumentException e) {
            throw new InvalidParameterException("Invalid UUID format");
        }
    }

    public static String validateName(String name) {
        if (name == null || name.isBlank()) {
            return null;
        }

        if (!name.chars().allMatch(Character::isLetter)) {
            return null;
        }
        return name;
    }

    public static int parsePageNumber(String pageParam) {
        if (pageParam == null || pageParam.isBlank()) {
            return 1;
        }
        try {
            int numPage = Integer.parseInt(pageParam);
            if (numPage <= 0) {
                return 1;
            }
            return numPage;
        } catch (IllegalArgumentException e) {
            throw new InvalidParameterException("page can be only digit");
        }
    }
}
