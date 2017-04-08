package soundsystem;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import soundsystem.dessert.Dessert;
import soundsystem.dessert.DessertConfig;
import soundsystem.dessert.annotations.Cold;
import soundsystem.dessert.annotations.Fruity;

/**
 * Created by cresh on 22.01.17.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = DessertConfig.class)
public class DessertTest {

    @Autowired
    @Cold
    @Fruity
    private Dessert dessert;

    @Test
    public void ambiguousTest() throws Exception {
        System.out.println(dessert.tasty());
    }
}
