package guess.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.thymeleaf.spring6.templateresolver.SpringResourceTemplateResolver;

import javax.annotation.PostConstruct;
@Slf4j
@Component
public class DeoupledTemplateLogicImpl {
    // == field ==
    private final SpringResourceTemplateResolver templateResolver;

    // == constructor ==
    public DeoupledTemplateLogicImpl(SpringResourceTemplateResolver templateResolver) {
        this.templateResolver = templateResolver;
    }

    @PostConstruct
    public void init(){
        templateResolver.setUseDecoupledLogic(true);
        log.info("Decoupled setup enabled!");
    }

}
