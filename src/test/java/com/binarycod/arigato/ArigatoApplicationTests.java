package com.binarycod.arigato;

import com.binarycod.arigato.services.ProductService;
import javafx.beans.binding.Bindings;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
class ArigatoApplicationTests {

	@Autowired
	ProductService productService;
	

	@Test
	 void contextLoads() throws Exception {
		assertThat(productService).isNotNull();



	}

}
