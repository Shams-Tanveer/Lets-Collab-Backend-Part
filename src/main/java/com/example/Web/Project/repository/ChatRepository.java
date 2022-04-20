package com.example.Web.Project.repository;

import com.example.Web.Project.model.Chat;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRepository extends JpaRepository<Chat,String> {
}
