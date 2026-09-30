package com.zerosupper.security;

import com.zerosupper.user.Role;

public record CurrentUser(Long id, String email, Role role) {
}
