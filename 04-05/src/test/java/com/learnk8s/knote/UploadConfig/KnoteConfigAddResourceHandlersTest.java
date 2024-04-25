package com.learnk8s.knote.UploadConfig;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.resource.PathResourceResolver;

import com.learnk8s.knote.KnoteProperties;
import com.learnk8s.knote.KnoteConfig;

import static org.mockito.Mockito.*;

public class KnoteConfigAddResourceHandlersTest {

	@Mock
	private ResourceHandlerRegistry registry;

	@Mock
	private KnoteProperties properties;

	@Mock
	private ResourceHandlerRegistration registration;

	private KnoteConfig knoteConfig;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		knoteConfig = new KnoteConfig();
		knoteConfig.setProperties(properties);
	}

	@Test
    public void validateResourceHandlerRegistration() {
        when(properties.getUploadDir()).thenReturn("/valid/directory");
        when(registry.addResourceHandler("/uploads/**")).thenReturn(registration);
        when(registration.addResourceLocations(anyString())).thenReturn(registration);
        when(registration.setCachePeriod(anyInt())).thenReturn(registration);
        when(registration.resourceChain(anyBoolean())).thenReturn(registration);
        when(registration.addResolver(any(PathResourceResolver.class))).thenReturn(registration);

        knoteConfig.addResourceHandlers(registry);

        verify(registry).addResourceHandler("/uploads/**");
        verify(registration).addResourceLocations("file:/valid/directory");
        verify(registration).setCachePeriod(3600);
        verify(registration).resourceChain(true);
        verify(registration).addResolver(any(PathResourceResolver.class));
    }

	@Test(expected = RuntimeException.class)
    public void handleInvalidUploadDirectory() {
        when(properties.getUploadDir()).thenReturn("/invalid/directory");
        when(registry.addResourceHandler("/uploads/**")).thenThrow(RuntimeException.class);

        knoteConfig.addResourceHandlers(registry);
    }

	@Test(expected = NullPointerException.class)
	public void handleNullResourceHandlerRegistry() {
		knoteConfig.addResourceHandlers(null);
	}

	@Test
    public void validateCachePeriodConfiguration() {
        when(properties.getUploadDir()).thenReturn("/valid/directory");
        when(registry.addResourceHandler("/uploads/**")).thenReturn(registration);
        when(registration.addResourceLocations(anyString())).thenReturn(registration);
        when(registration.setCachePeriod(anyInt())).thenReturn(registration);
        when(registration.resourceChain(anyBoolean())).thenReturn(registration);
        when(registration.addResolver(any(PathResourceResolver.class))).thenReturn(registration);

        knoteConfig.addResourceHandlers(registry);

        verify(registration).setCachePeriod(3600);
    }

}
