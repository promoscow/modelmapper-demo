package ru.xpendence.modelmapperdemo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import ru.xpendence.modelmapperdemo.attributes.Color;
import ru.xpendence.modelmapperdemo.attributes.Filling;
import ru.xpendence.modelmapperdemo.dto.UnicornDto;
import ru.xpendence.modelmapperdemo.entity.Cupcake;
import ru.xpendence.modelmapperdemo.entity.Droid;
import ru.xpendence.modelmapperdemo.entity.Unicorn;
import ru.xpendence.modelmapperdemo.mapper.UnicornMapper;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ModelmapperDemoApplicationTests {

        private List<Cupcake> cupcakes;
        private List<Droid> droids;
        private Unicorn unicorn;

        private Long idCounter;

        @Autowired
        private UnicornMapper unicornMapper;

        @Test
        public void mapperTest() {
            idCounter = 1L;
            createEntities();
            UnicornDto unicornDto = unicornMapper.toDto(unicorn);
            System.out.println();
        }

        private void createEntities() {
            unicorn = new Unicorn();
            unicorn.setId(idCounter);
            unicorn.setName("Unicorn " + idCounter++);
            unicorn.setColor(Arrays.stream(Color.values()).findAny().orElse(Color.BLACK));
            unicorn.setDroids(createDroids(unicorn));
        }

        private List<Droid> createDroids(Unicorn unicorn) {
            return Stream.generate(() -> {
                Droid droid = new Droid("Droid " + idCounter++, unicorn, true);
                droid.setCupcakes(createCupcakes(droid));
                return droid;
            })
                    .limit(10L)
                    .collect(Collectors.toList());
        }

        private List<Cupcake> createCupcakes(Droid droid) {
            return Stream.generate(() -> new Cupcake(Arrays.stream(Filling.values()).findAny().orElse(Filling.CHERRY), droid))
                    .limit(10L)
                    .collect(Collectors.toList());
        }

}

