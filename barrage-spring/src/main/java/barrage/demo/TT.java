package barrage.demo;


import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
import java.util.concurrent.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @Author TomShiDi
 * @Since 2020/8/22
 * @Version 1.0
 */
public class TT {

    private static List<Person> personList = new ArrayList<>();

    static {
        personList.add(new TT().new Person("tom").params(Arrays.asList(1, 2, 3, 4)));
        personList.add(new TT().new Person("tohka").params(Arrays.asList(1, 2, 3, 4, 5)));
        personList.add(new TT().new Person("yoxino").params(Arrays.asList(1, 2, 3)));
    }

    public static void main(String[] args) {
        List<Person> conditionList = new ArrayList<>();
        personList.stream().forEach(person -> {
            Map<String, Integer> map = person.params.stream().filter(e -> e > 2).collect(Collectors.toMap(Integer::toHexString,
                    Function.identity(), (key1, key2) -> key2
            ));
            System.out.println(map.entrySet().stream().map(entry -> entry.getKey() + ":" + entry.getValue() + ",").collect(Collectors.joining()));
        });

    }

    class Person {
        public String name;

        public List<Integer> params;

        public Person(String name) {
            this.name = name;
        }

        public Person params(List<Integer> params) {
            this.params = params;
            return this;
        }

        public String getName() {
            return name;
        }

    }

}
