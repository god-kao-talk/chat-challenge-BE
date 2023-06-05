// package com.challenge.chat.config;
//
// import static org.assertj.core.api.Assertions.*;
//
// import org.jasypt.encryption.StringEncryptor;
// import org.junit.jupiter.api.DisplayName;
// import org.junit.jupiter.api.Test;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.beans.factory.annotation.Qualifier;
// import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
// import org.springframework.boot.test.mock.mockito.MockBean;
// import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
//
// @WebMvcTest(JasyptConfig.class)
// @MockBean(JpaMetamodelMappingContext.class)
// class JasyptConfigTest {
//
// 	@Autowired
// 	@Qualifier("jasyptStringEncryptor")
// 	StringEncryptor encryptor;
//
// 	@Test
// 	@DisplayName("Jasypt 암복호화 테스트")
// 	public void jasyptEncryptDecryptTest() {
// 		String plainText = "TestText";
//
// 		String encryptedText = encryptor.encrypt(plainText);
// 		String decryptedText = encryptor.decrypt(encryptedText);
//
// 		assertThat(plainText).isEqualTo(decryptedText);
// 	}
// }