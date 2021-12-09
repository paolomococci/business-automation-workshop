package local.mocaccino.planning;

import javax.enterprise.context.Dependent;

@Dependent
public class GreetService {

    public String greet(String name) {
        if (name == null || name.isEmpty()) {
            return "Hello guest!";
        } else {
            return "Hello " + name + "!";
        }
    }
}
