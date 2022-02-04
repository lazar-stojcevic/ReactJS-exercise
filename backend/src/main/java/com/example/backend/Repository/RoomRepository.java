package com.example.backend.Repository;

import com.example.backend.Beans.Room;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomRepository extends JpaRepository<Room, Long> {
}
