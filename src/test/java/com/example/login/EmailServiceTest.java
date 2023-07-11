import com.example.login.EmailService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.client.RestTemplate;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;

public class EmailServiceTest {

    @Mock
    private JavaMailSender javaMailSender;

    @Mock
    private RestTemplate restTemplate;

    private EmailService emailService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        emailService = new EmailService(javaMailSender, restTemplate);
    }

    @Test
    public void sendVerificationCode_Success() {
        String email = "test@example.com";
        String code = "0000";
        String url = "http://localhost:8080/email/store/{key}/{value}";

        emailService.sendVerificationCode(email);

        verify(javaMailSender).send(any(SimpleMailMessage.class));
        verify(restTemplate).getForObject(url, String.class, email, code);
    }

    @Test
    public void sendVerificationCode_MailException() {
        String email = "test@example.com";

        doThrow(new MailException("Failed to send email")).when(javaMailSender).send(any(SimpleMailMessage.class));

        emailService.sendVerificationCode(email);

        // 예외가 발생해도 특별한 동작을 하지 않음을 검증할 수 있습니다.
        // 예를 들어, 예외를 로깅하거나 다른 동작을 수행할 수도 있습니다.
    }

    @Test
    public void sendWelcomeMessage_Success() {
        String id = "user123";
        String email = "test@example.com";

        emailService.sendWelcomeMessage(id, email);

        verify(javaMailSender).send(any(SimpleMailMessage.class));
    }

}
