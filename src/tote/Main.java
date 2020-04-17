package tote;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public final class Main {

    private Main() {
    }

    public static void main(final String[] args) {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("/web/WEB-INF/classes/context.xml");
    }
}
