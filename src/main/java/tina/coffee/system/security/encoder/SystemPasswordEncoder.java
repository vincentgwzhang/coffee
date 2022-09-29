package tina.coffee.system.security.encoder;

import org.springframework.security.crypto.password.PasswordEncoder;

public class SystemPasswordEncoder implements PasswordEncoder{

    @Override
    public String encode(CharSequence rawPassword) {
        return DigestUtils.encodeSHA512Hex(rawPassword.toString().getBytes());
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        return encode(rawPassword).equalsIgnoreCase(encodedPassword);
    }

    public static void main(String[] args) {
        System.out.println(new SystemPasswordEncoder().encode("Summer"));
    }

}
