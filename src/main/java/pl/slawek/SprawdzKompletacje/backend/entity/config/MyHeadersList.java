package pl.slawek.SprawdzKompletacje.backend.entity.config;

import lombok.Getter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Getter
@Component
public class MyHeadersList {
    private static List<Header> myHeadersList = new ArrayList<>();

    public MyHeadersList() {
       createList();
    }

    private void createList() {
        myHeadersList.clear();
        myHeadersList.add(new Header(0, "Kod kreskowy"));
        myHeadersList.add(new Header(1, "Nazwa produktu"));
        myHeadersList.add(new Header(2, "Ilość zamówiona"));
    }

    public void removeFromList(Header header) {
        myHeadersList.remove(header);
    }

    public List<Header> getMyHeadersList() {
        return myHeadersList;
    }
}
