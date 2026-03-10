package Dto.Person.Admin;

import Dto.Person.PersonResponse;
import lombok.Data;

import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AdminResponse extends PersonResponse {
    private int permissions;
}