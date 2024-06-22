package com.access.repo;

import org.springframework.stereotype.Component;

import java.util.List;

public interface AccessDb {
    List<String> getServiceNames();
}
