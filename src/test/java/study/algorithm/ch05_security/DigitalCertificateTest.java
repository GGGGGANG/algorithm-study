package study.algorithm.ch05_security;

import org.junit.jupiter.api.Test;

import java.security.SecureRandom;

import static org.junit.jupiter.api.Assertions.*;

class DigitalCertificateTest {

    @Test
    void CA_의_공개키로_인증서가_검증된다() {
        SecureRandom rand = new SecureRandom("cert-seed".getBytes());
        PublicKeyCipher.KeyPair ca = PublicKeyCipher.generate(512, rand);
        PublicKeyCipher.KeyPair subject = PublicKeyCipher.generate(512, rand);

        DigitalCertificate.Certificate cert = DigitalCertificate.issue(
                "alice.example.com",
                subject.publicKey(),
                "Example Root CA",
                ca.privateKey());

        assertTrue(DigitalCertificate.verify(cert, ca.publicKey()));
    }

    @Test
    void 다른_CA_의_공개키로는_검증이_실패한다() {
        SecureRandom rand = new SecureRandom("cert-seed-2".getBytes());
        PublicKeyCipher.KeyPair ca = PublicKeyCipher.generate(512, rand);
        PublicKeyCipher.KeyPair fakeCa = PublicKeyCipher.generate(512, rand);
        PublicKeyCipher.KeyPair subject = PublicKeyCipher.generate(512, rand);

        DigitalCertificate.Certificate cert = DigitalCertificate.issue(
                "bob.example.com",
                subject.publicKey(),
                "Real CA",
                ca.privateKey());

        assertFalse(DigitalCertificate.verify(cert, fakeCa.publicKey()));
    }
}
