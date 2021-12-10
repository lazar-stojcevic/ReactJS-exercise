package com.example.backend.Services.Interfaces;

import com.example.backend.Beans.Cottage;
import com.example.backend.Beans.Room;
import com.example.backend.Dtos.CottageDto;
import com.example.backend.Dtos.RoomDto;
import lombok.NoArgsConstructor;

import java.util.Collection;

public interface IRoomService {
    Collection<Room> findAllRooms();
    Room findById(long id);
    Room saveRoom(RoomDto newRoom);
    Room updateRoom(RoomDto newRoom);
    void deleteRoom(long id);
}
