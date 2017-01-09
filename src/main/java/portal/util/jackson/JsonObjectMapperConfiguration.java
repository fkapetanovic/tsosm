package portal.util.jackson;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Component;

@Component
public class JsonObjectMapperConfiguration implements BeanPostProcessor {
  public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
      return bean;
  }

  public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
    if (bean instanceof MappingJackson2HttpMessageConverter ) {
    	MappingJackson2HttpMessageConverter  jsonConverter = (MappingJackson2HttpMessageConverter ) bean;
      ObjectMapper newObjectMapper = new ObjectMapper();
      newObjectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
      newObjectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
      newObjectMapper.configure(SerializationFeature.INDENT_OUTPUT, true);
      newObjectMapper.registerModule( new JacksonAnnotationsModule());

      jsonConverter.setObjectMapper(newObjectMapper);
    }
    return bean;
  }
}
