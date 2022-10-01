package com.behnam.university.service.interfaces;

import com.behnam.university.dto.user.AppUserCreateDto;
import com.behnam.university.dto.user.AppUserListDto;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @author Behnam Si (https://github.com/behnamsi/)
 * @version 1.0
 * @since 9/20/2022
 */

public interface AppUserService {

    public List<AppUserListDto> getAllUsers(Pageable pageable);

    public void addUser(AppUserCreateDto dto);

    public void deleteUser(String username);

}
