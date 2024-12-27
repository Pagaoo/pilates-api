package com.dev.pilates.services.role;

import com.dev.pilates.ENUMS.RoleEnum;
import org.springframework.stereotype.Service;

@Service
public class RoleService {

    public String getRoleNameById(long id) {
        if ((int) id == 2) {
            return RoleEnum.ROLE_PROFESSOR.toString();
        }
        return RoleEnum.ROLE_ADMIN.toString();
    }
}
