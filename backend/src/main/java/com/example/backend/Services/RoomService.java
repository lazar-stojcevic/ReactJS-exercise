package com.example.backend.Services;

import com.example.backend.Beans.Cottage;
import com.example.backend.Beans.Room;
import com.example.backend.Dtos.RoomDto;
import com.example.backend.Repository.RoomRepository;
import com.example.backend.Services.Interfaces.IRoomService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collection;
import java.util.Optional;

public class RoomService implements IRoomService {

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private CottageService cottageService;

    public RoomService(RoomRepository roomRepository){ this.roomRepository = roomRepository; }

    @Override
    public Collection<Room> findAllRooms() {
        return roomRepository.findAll();
    }

    @Override
    public Room findById(long id) {
        Optional<Room> room = roomRepository.findById(id);
        return room.orElse(null);
    }

    @Override
    public Room saveRoom(Room newRoom) {
        return roomRepository.save(newRoom);
    }

    @Override
    public Room updateRoom(RoomDto newRoom) {
        Room room = findById(newRoom.getId());
        room.setRoomDescription(newRoom.getRoomDescription());
        room.setNumberOfBeds(newRoom.getNumberOfBeds());
        room.setCottage(cottageService.findById(newRoom.getCottageId()));
        return roomRepository.save(room);
    }

    @Override
    public void deleteRoom(long id) {
        roomRepository.deleteById(id);
    }
}
