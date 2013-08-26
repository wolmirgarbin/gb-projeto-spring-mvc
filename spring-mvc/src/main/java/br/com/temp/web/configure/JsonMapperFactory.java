package br.com.viasoft.portaldef.configure;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.Version;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializerProvider;
import org.codehaus.jackson.map.module.SimpleModule;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;

public class JsonMapperFactory implements FactoryBean<ObjectMapper>, InitializingBean{

	private final ObjectMapper objectMapper = new ObjectMapper();

	private List<Class<Enum<?>>> enumsTypes = new ArrayList<Class<Enum<?>>>();
	
	@Override
	public ObjectMapper getObject() throws Exception {
		return objectMapper;
	}

	@Override
	public Class<?> getObjectType() {
		return ObjectMapper.class;
	}

	@Override
	public boolean isSingleton() {
		return true;
	}

	public List<Class<Enum<?>>> getEnumsTypes() {
		return enumsTypes;
	}

	public void setEnumsTypes(List<Class<Enum<?>>> enumsTypes) {
		this.enumsTypes = enumsTypes;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		final SimpleModule testModule = new SimpleModule("MyModule", new Version(1, 0, 0, null));
		
		for(final Class<Enum<?>> type:enumsTypes){
			testModule.addSerializer(type, new JsonSerializer<Enum<?>>(){

				@Override
				public void serialize(Enum<?> value, JsonGenerator jgen,
						SerializerProvider provider) throws IOException,
						JsonProcessingException {
					final Map<String, String> map = new HashMap<String, String>();
					map.put("value",value.name());
					map.put("descricao",value.toString());
					jgen.writeObject(map);
				}
				
			});
		}
		
		objectMapper.registerModule(testModule);
	}

}
