package edu.pucmm.eict.practica4;
import com.hazelcast.config.*;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import edu.pucmm.eict.practica4.servicios.seguridad.SeguridadServices;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.session.MapSession;
import org.springframework.session.hazelcast.HazelcastIndexedSessionRepository;
import org.springframework.session.hazelcast.HazelcastSessionSerializer;
import org.springframework.session.hazelcast.config.annotation.web.http.EnableHazelcastHttpSession;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import java.util.Date;

@EnableHazelcastHttpSession
@SpringBootApplication
public class Practica4Application implements WebMvcConfigurer {

	private final static String SESSIONS_MAP_NAME = "EJEMPLO-HAZELCAST";
	private final LocaleChangeInterceptor localeChangeInterceptor;

	public Practica4Application(LocaleChangeInterceptor localeChangeInterceptor) {
		this.localeChangeInterceptor = localeChangeInterceptor;
	}

	@Override
	public void addInterceptors(InterceptorRegistry interceptorRegistry) {
		interceptorRegistry.addInterceptor(localeChangeInterceptor);
	}

	public static void main(String[] args) {
		ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
		messageSource.setBasenames("lang/messages");
		messageSource.setDefaultEncoding("UTF-8");

		ApplicationContext applicationContext = SpringApplication.run(Practica4Application.class, args);

		String[] lista = applicationContext.getBeanDefinitionNames();
		System.out.println("====== Beans Registrados =====");
		for(String bean : lista){
			System.out.println(""+bean);
		}
		System.out.println("====== FIN Beans Registrados =====");

		SeguridadServices seguridadServices = (SeguridadServices) applicationContext.getBean("seguridadServices");
		seguridadServices.crearUsuarios();
	}

	@Bean
	public HazelcastInstance hazelcastInstance() {
		Config config = new Config();
		config.setClusterName("spring-session-cluster");

		// Add this attribute to be able to query sessions by their PRINCIPAL_NAME_ATTRIBUTE's
		AttributeConfig attributeConfig = new AttributeConfig()
				.setName(HazelcastIndexedSessionRepository.PRINCIPAL_NAME_ATTRIBUTE)
				.setExtractorClassName(HazelcastIndexedSessionRepository.class.getName());

		// Configure the sessions map
		config.getMapConfig(SESSIONS_MAP_NAME)
				.addAttributeConfig(attributeConfig).addIndexConfig(
						new IndexConfig(IndexType.HASH, HazelcastIndexedSessionRepository.PRINCIPAL_NAME_ATTRIBUTE));

		// Use custom serializer to de/serialize sessions faster. This is optional.
		SerializerConfig serializerConfig = new SerializerConfig();
		serializerConfig.setImplementation(new HazelcastSessionSerializer()).setTypeClass(MapSession.class);
		config.getSerializationConfig().addSerializerConfig(serializerConfig);

		return Hazelcast.newHazelcastInstance(config);
	}

}
