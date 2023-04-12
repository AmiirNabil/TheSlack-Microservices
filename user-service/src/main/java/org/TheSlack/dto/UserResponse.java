package org.TheSlack.dto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.TheSlack.domain.Permission;
import org.TheSlack.domain.Role;
import java.util.List;
import java.math.BigDecimal;
import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {
    private Integer id;
    private String firstName;
    private String lastName;
    private String email;
    private Date dob;
    private Role role;
    private List<Permission> permissions;
}
