package repz.app.service.user;

import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NullMarked;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import repz.app.message.Mensagens;
import repz.app.persistence.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;
    private final Mensagens mensagens;

    @NullMarked
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        var user = userRepository.findByEmail(email);
        if (user.isEmpty()) {
            throw new UsernameNotFoundException(mensagens.get("usuario.nao.encontrado.email", email));
        }
        return user.get();
    }
}
