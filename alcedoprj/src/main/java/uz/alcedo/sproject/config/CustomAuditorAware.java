package uz.alcedo.sproject.config;

import java.util.Optional;

import org.springframework.data.domain.AuditorAware;

import uz.alcedo.sproject.security.SecurityUtils;

public class CustomAuditorAware implements AuditorAware<String> {

	@Override
	public Optional<String> getCurrentAuditor() {
		System.out.println("================" + SecurityUtils.getCurrentUserLogin().orElse(Constants.SYSTEM_ACCOUNT));
		return Optional.of(SecurityUtils.getCurrentUserLogin().orElse(Constants.SYSTEM_ACCOUNT));
	}

}
