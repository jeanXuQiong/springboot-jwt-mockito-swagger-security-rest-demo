package com.example.demo;

import com.example.demo.service.UserService;
import com.example.demo.web.UserController;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


@RunWith(SpringRunner.class)
//@SpringBootTest
//@ContextConfiguration(classes=DemoApplication.class)
@WebMvcTest(UserController.class)
@WithMockUser(username = "root")
public class DemoApplicationTests {
	private final static ObjectMapper objectMapper = new ObjectMapper();
	@Autowired
	private MockMvc mockMvc;

	@Mock
	private UserService userService;

	@Test
	public void testmvc()throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/root/getUserPwd"))
				.andExpect(MockMvcResultMatchers.status().is(200))
				.andExpect(MockMvcResultMatchers.content().string("password"));
	}

	@Test
	public void testService()throws Exception {
		UserService mockedUserService = mock(UserService.class);

		when(mockedUserService.getPwdByUsername("root")).thenReturn("password1");
		when(mockedUserService.getPwdByUsername("admin")).thenReturn("password2");

        Assert.assertEquals("password1", mockedUserService.getPwdByUsername("root"));
        Assert.assertEquals("password2", mockedUserService.getPwdByUsername("admin"));

        Mockito.verify(mockedUserService).getPwdByUsername("root");
        Mockito.verify(mockedUserService).getPwdByUsername("admin");
	}
}
