package Enum;

import lombok.Getter;

@Getter
public enum Permissions {

    ALL(0);

    private final int value;

    Permissions(int value) {
        this.value = value;
    }

}