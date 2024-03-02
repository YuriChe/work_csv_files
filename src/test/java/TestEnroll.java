import appCSV.readers.EnrollEntityGeo;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;

import appCSV.entity.CustomerWBGeo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TestEnroll {

        @Test
        public void testYourMethod() {
            // Создаем макет объекта customerWBGeo
            CustomerWBGeo customerWBGeo = mock(CustomerWBGeo.class);
            EnrollEntityGeo enrollEntityGeo = mock(EnrollEntityGeo.class);
            // Создаем массив row для тестирования
            List<String[]> row = new ArrayList<>();

                    row.add(new String[]
                    {"488577",
                    "74995555555",
                    "general",
                    "4965885",
                    "value5",
                    "56.4587",
                    "24.781",
                    "value8",
                    "value9",
                    "value10",
                    "value11"

                    });
            List<CustomerWBGeo> listCustomer = new ArrayList<>();

            // Вызываем метод, который вы хотите протестировать
            enrollEntityGeo.enrollToCustomersGeo(row, listCustomer);

            // Проверяем, что правильные методы объекта customerWBGeo были вызваны
            // В данном случае, проверяем, что методы setComment и setEmail были вызваны
            /*verify(customerWBGeo).setComment(anyString());
            verify(customerWBGeo).setEmail(anyString());*/

            System.out.println(listCustomer.get(0));

        }
    }


