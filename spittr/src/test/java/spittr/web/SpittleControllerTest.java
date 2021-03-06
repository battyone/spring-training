package spittr.web;

import org.junit.Test;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.view.InternalResourceView;
import spittr.data.SpittleRepository;
import spittr.model.Spittle;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.hamcrest.CoreMatchers.hasItems;

/**
 * Created on 23 Apr, 2017.
 *
 * @author Artemis A. Sirosh
 */
public class SpittleControllerTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(SpittleControllerTest.class);

    @Test
    public void shouldShowRecentSpittles() throws Exception {
        List<Spittle> expectedSpittles = createSpittles(20);
        SpittleRepository mockRepository = Mockito.mock(SpittleRepository.class);
        Mockito
                .when(mockRepository.findSpittles(Long.MAX_VALUE, 20))
                .thenReturn(expectedSpittles);

        SpittleController controller = new SpittleController(mockRepository);

        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .setSingleView(new InternalResourceView("/WEB-INF/views/spittles.jsp"))
                .build();

        mockMvc.perform(MockMvcRequestBuilders.get("/spittles"))
                .andExpect(MockMvcResultMatchers.view().name("spittles"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("spittleList"))
                .andExpect(MockMvcResultMatchers.model()
                        .attribute("spittleList", hasItems(expectedSpittles.toArray()))
                );
    }

    @Test
    public void shouldShowPagedSpittles() throws Exception {
        List<Spittle> expectedSpittles = createSpittles(50);

        // Create mock repository
        SpittleRepository mockRepository = Mockito.mock(SpittleRepository.class);

        // Setup mock behavior
        Mockito.when(mockRepository.findSpittles(238900, 50)).thenReturn(expectedSpittles);

        SpittleController controller = new SpittleController(mockRepository);

        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .setSingleView(new InternalResourceView("/WEB-INF/views/spittles.jsp"))
                .build();

        mockMvc.perform(MockMvcRequestBuilders.get("/spittles?max=238900&count=50"))
                .andExpect(MockMvcResultMatchers.view().name("spittles"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("spittleList"))
                .andExpect(
                        MockMvcResultMatchers.model().attribute("spittleList", hasItems(expectedSpittles.toArray()))
                );



    }

    @Test
    public void testSpittle() throws Exception {
        Spittle expectedSpittle = new Spittle("Hello", new Date());

        SpittleRepository mockRepo = Mockito.mock(SpittleRepository.class);

        Mockito.when(mockRepo.findOne(12345)).thenReturn(expectedSpittle);

        SpittleController controller = new SpittleController(mockRepo);
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(controller).build();

        mockMvc.perform(MockMvcRequestBuilders.get("/spittles/12345"))
                .andExpect(MockMvcResultMatchers.view().name("spittle"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("spittle"))
                .andExpect(MockMvcResultMatchers.model().attribute("spittle", expectedSpittle));


    }

    @Test
    public void fakeRepositoryTest() throws Exception {
        final String[] spittleMsg = {
                "Spittles go fourth!",
                "Spittle spittle spittle",
                "Here another spittle",
                "Hello World! An Spittle!"
        };

        for (int i = 0; i < 1000; i++) {
            LOGGER.info("{}", spittleMsg[Math.abs(Math.round((float) Math.random() * 3))]);
        }

    }

    private List<Spittle> createSpittles(int count) {
        List<Spittle> result = new ArrayList<>(count);
        for (int i = 0; i < count; i++) {
            result.add(new Spittle("Spittle #" + i, new Date((long) (Math.random() * 100_000))));
        }

        LOGGER.info("Created {} spittles", result.size());

        return result;
    }
}
