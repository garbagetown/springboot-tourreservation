package garbagetown.app.menu;

import org.junit.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

/**
 * Created by garbagetown on 10/25/15.
 */
public class MenuControllerTest {

    MockMvc mockMvc;

    @Test
    public void testInit() throws Exception {
        MenuController controller = new MenuController();
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
        mockMvc.perform(MockMvcRequestBuilders.get("/")).andExpect(view().name("menu/menu"));
    }
}
