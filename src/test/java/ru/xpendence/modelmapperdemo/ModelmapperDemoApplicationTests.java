package ru.xpendence.modelmapperdemo;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import ru.xpendence.modelmapperdemo.attributes.Color;
import ru.xpendence.modelmapperdemo.attributes.Filling;
import ru.xpendence.modelmapperdemo.dto.UnicornDto;
import ru.xpendence.modelmapperdemo.entity.Cupcake;
import ru.xpendence.modelmapperdemo.entity.Droid;
import ru.xpendence.modelmapperdemo.entity.Unicorn;
import ru.xpendence.modelmapperdemo.mapper.UnicornMapper;
import ru.xpendence.modelmapperdemo.repository.CupcakeRepository;
import ru.xpendence.modelmapperdemo.repository.DroidRepository;
import ru.xpendence.modelmapperdemo.repository.UnicornRepository;

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

        @Autowired
        private UnicornRepository unicornRepository;

        @Autowired
        private DroidRepository droidRepository;

        @Autowired
        private CupcakeRepository cupcakeRepository;

        @Test
        @Transactional
        public void mapperTest() throws Exception {
            idCounter = 1L;
            createEntities();

            droids = createDroids(unicorn);
            droids.forEach(this::createCupcakes);

            UnicornDto unicornDto = unicornMapper.toDto(unicorn);
            System.out.println(new ObjectMapper().writeValueAsString(unicornDto));
        }

        private void createEntities() {
            unicorn = new Unicorn();
            unicorn.setId(idCounter);
            unicorn.setName("Unicorn " + idCounter++);
            unicorn.setColor(Arrays.stream(Color.values()).findAny().orElse(Color.BLACK));
            unicorn.setDroids(createDroids(unicorn));
            unicornRepository.save(unicorn);
        }

        private List<Droid> createDroids(Unicorn unicorn) {
            return Stream.generate(() -> {
                Droid droid = new Droid("Droid " + idCounter++, unicorn, true);
                droid.setCupcakes(createCupcakes(droid));
                droidRepository.save(droid);
                return droid;
            })
                    .limit(3L)
                    .collect(Collectors.toList());
        }

        private List<Cupcake> createCupcakes(Droid droid) {
            return Stream.generate(() -> cupcakeRepository.save(
                    new Cupcake(Arrays.stream(Filling.values()).findAny().orElse(Filling.CHERRY), droid))
            )
                    .limit(3L)
                    .collect(Collectors.toList());
        }

}

