package com.dev.pilates.services.role;

import com.dev.pilates.ENUMS.RoleEnum;
import org.springframework.stereotype.Service;

@Service
public class RoleService {

    public String getRoleNameById(long id) {
        switch ((int) id) {
            case 3: return RoleEnum.ROLE_PROFESSOR.toString();
            default: return RoleEnum.ROLE_ADMIN.toString();
        }
    }
}
