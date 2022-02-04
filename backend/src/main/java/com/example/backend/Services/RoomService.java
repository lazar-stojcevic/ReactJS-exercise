package com.example.backend.Services;

import com.example.backend.Beans.Cottage;
import com.example.backend.Beans.Room;
import com.example.backend.Dtos.RoomDto;
import com.example.backend.Repository.RoomRepository;
import com.example.backend.Services.Interfaces.IRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

@Service
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
    public Room saveRoom(RoomDto newRoom) {
        Room room = new Room();
        room.setCottage(cottageService.findById(newRoom.getCottageId()));
        room.setRoomDescription(newRoom.getRoomDescription());
        room.setNumberOfBeds(newRoom.getNumberOfBeds());
        return roomRepository.save(room);
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
        roomRepository.delete(findById(id));
    }
}
