package com.devertelo.webflux.controller;

import java.time.LocalDateTime;

public record BookDTO(Long id, String name, String author, LocalDateTime createdAt) {

}
