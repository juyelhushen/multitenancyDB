package com.example.demo.config;


import org.springframework.cache.Cache;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;

import com.example.demo.constant.MultiTenantConstants;

public class CustomCacheManager extends RedisCacheManager {
	
	public CustomCacheManager(RedisCacheWriter cacheWriter,RedisCacheConfiguration defaultCacheConfiguration)
	{
		super(cacheWriter,defaultCacheConfiguration);
		RedisCacheManager.builder().cacheWriter(cacheWriter).cacheDefaults(defaultCacheConfiguration).build();
	}
	
	@Override
	public Cache getCache(String name)
	{
		String tenantId = TenantContext.getCurrentTenant();
		if (tenantId.equals(MultiTenantConstants.CURRENT_TENANT_IDENTIFIER)){
			return super.getCache(name);
		} else if (name.startsWith(tenantId)) {
			return super.getCache(name);
			
		}
		return super.getCache(tenantId+"_"+name);
	}
	

}
