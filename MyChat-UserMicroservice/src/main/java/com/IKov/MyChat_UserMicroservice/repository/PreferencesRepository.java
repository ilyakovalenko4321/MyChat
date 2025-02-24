package com.IKov.MyChat_UserMicroservice.repository;

import com.IKov.MyChat_UserMicroservice.domain.preferences.Preferences;
import org.springframework.data.jpa.repository.JpaRepository;


public interface PreferencesRepository extends JpaRepository<Preferences, Long> {
}
