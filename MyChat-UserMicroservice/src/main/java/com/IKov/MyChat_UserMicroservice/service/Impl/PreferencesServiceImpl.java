package com.IKov.MyChat_UserMicroservice.service.Impl;

import com.IKov.MyChat_UserMicroservice.domain.preferences.Preferences;
import com.IKov.MyChat_UserMicroservice.repository.PreferencesRepository;
import com.IKov.MyChat_UserMicroservice.service.PreferencesService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PreferencesServiceImpl implements PreferencesService {
    private final PreferencesRepository preferencesRepository;

    @Transactional
    @Override
    public void createPreferences(Preferences preferences) {
        preferencesRepository.save(preferences);
    }
}
