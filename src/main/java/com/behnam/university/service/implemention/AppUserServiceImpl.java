package com.behnam.university.service.implemention;

import com.behnam.university.dto.user.AppUserCreateDto;
import com.behnam.university.dto.user.AppUserListDto;
import com.behnam.university.mapper.static_mapper.StaticMapper;
import com.behnam.university.model.AppUser;
import com.behnam.university.repository.AppUserRepository;
import com.behnam.university.service.interfaces.AppUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static com.behnam.university.security.roles.AppUserRoles.*;

/**
 * @author Behnam Si (https://github.com/behnamsi/)
 * @version 1.0
 * @since 9/18/2022
 */

@Service
@Primary
public class AppUserServiceImpl implements UserDetailsService, AppUserService {

    private final AppUserRepository repository;
    private final PasswordEncoder encoder;

    @Autowired
    public AppUserServiceImpl(AppUserRepository repository, PasswordEncoder encoder) {
        this.repository = repository;
        this.encoder = encoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUser user = repository.findAppUserByUsername(username)
                .orElseThrow(() -> new IllegalStateException("invalid username"));
        int size = user.getAuthorities().size();
        return user;
    }

    @Override
    public List<AppUserListDto> getAllUsers(Pageable pageable) {
        List<AppUser> users = repository.findAll(pageable).getContent();
        List<AppUserListDto> listDto = new ArrayList<>();
        for (AppUser u :
                users) {
            try {
                AppUserListDto dto = new AppUserListDto();
                StaticMapper.mapper(u, dto);
                listDto.add(dto);
            } catch (InvocationTargetException | NoSuchMethodException | IllegalAccessException e) {
                e.printStackTrace();
                throw new IllegalStateException("could not get the list");
            }
        }
        return listDto;
    }

    @Override
    public void addUser(AppUserCreateDto dto) {
        String username = dto.getUsername();
        String password = dto.getPassword();
        Set<SimpleGrantedAuthority> authorities = null;
        switch (dto.getRole()) {
            case 0:
                authorities = ADMIN.getAuthorities();
                break;
            case 1:
                authorities = MANAGER.getAuthorities();
                break;
            case 2:
                authorities = PROFESSOR.getAuthorities();
                break;
            case 3:
                authorities = STUDENT.getAuthorities();
                break;
            default:
                authorities = MANAGER.getAuthorities();

        }
        if (repository.existsAppUserByUsername(username)) {
            throw new IllegalStateException("username has already taken");
        }
        if (!password.equals(dto.getConfirmPassword()))
            throw new IllegalStateException("password and confirm password dose not match");
        if (dto.getPassword().length() < 8)
            throw new IllegalStateException("password not strong");
        AppUser user = new AppUser(authorities,
                username,
                password,
                true, true, true, true);

        user.setPassword(encoder.encode(user.getPassword()));
        repository.save(user);
    }

    @Override
    @Transactional
    public void deleteUser(String username) {
        repository.deleteAppUserByUsername(username);
    }
}
