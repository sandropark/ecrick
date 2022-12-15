package sandro.elib.crawler;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Disabled
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class ThreadAndBeanTest {

    @Autowired ObjectProvider<PrototypeBean> prototypeBeanProvider;

    @Test
    void threadPrototypeBean() throws Exception {
        PrototypeBean bean1 = prototypeBeanProvider.getObject();
        PrototypeBean bean2 = prototypeBeanProvider.getObject();

        bean1.run();
        bean2.run();
    }
}

@Scope("prototype")
@Component
class PrototypeBean implements Runnable {
    @Override
    public void run() {
        System.out.println("[" + this + "] PrototypeBean.run");
    }
}
