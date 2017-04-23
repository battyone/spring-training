package spittr.web;

import org.junit.Assert;
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

import java.util.*;

import static org.hamcrest.CoreMatchers.*;

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
                        .attribute("spittleList", hasItem(expectedSpittles.toArray()))
                );
    }

    @Test
    public void equalsTest() throws Exception {
        Collection<Spittle> c = createSpittles(10);
        Set<Spittle> spittleSet = new HashSet<>(c);
        spittleSet.addAll(c);
        LOGGER.info("SPITTLE SET {}", spittleSet);
        Assert.assertEquals(10, spittleSet.size());
    }

    private List<Spittle> createSpittles(int count) {
        List<Spittle> result = new ArrayList<>(count);
        for (int i = 0; i < count; i++) {
            result.add(new Spittle("Spittle #" + i, new Date((long) (Math.random() * 100_000))));
        }

        return result;
    }
}