package net.ollysk.pr.web.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import javax.json.JsonMergePatch;
import javax.json.JsonValue;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class RestPatchService {

  private final ObjectMapper mapper;

  public <T> T mergePatch(JsonMergePatch patch, T targetBean, Class<T> beanClass) {
    JsonValue target = mapper.convertValue(targetBean, JsonValue.class);
    JsonValue patched = applyMergePatch(patch, target);
    return convertAndValidate(patched, beanClass);
  }

  private JsonValue applyMergePatch(JsonMergePatch patch, JsonValue target) {
    try {
      return patch.apply(target);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  private <T> T convertAndValidate(JsonValue jsonValue, Class<T> beanClass) {
    return mapper.convertValue(jsonValue, beanClass);
  }
}
