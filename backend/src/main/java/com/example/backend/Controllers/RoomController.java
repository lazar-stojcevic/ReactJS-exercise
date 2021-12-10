package com.example.backend.Controllers;

import com.example.backend.Beans.Cottage;
import com.example.backend.Beans.Room;
import com.example.backend.Dtos.CottageDto;
import com.example.backend.Dtos.RoomDto;
import com.example.backend.Services.CottageOwnerService;
import com.example.backend.Services.CottageService;
import com.example.backend.Services.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@CrossOrigin(allowedHeaders = "*")
@RestController
@RequestMapping("room")
public class RoomController {
    @Autowired
    private RoomService roomService;

    @Autowired
    private CottageService cottageService;

    public RoomController(RoomService roomService){this.roomService = roomService;}

    @GetMapping(path = "{id}")
    public ResponseEntity<Room> getRoomById(@PathVariable long id){
        Room room = roomService.findById(id);
        if (room != null)
            return new ResponseEntity<>(room, HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping(path = "/cottage/{id}")
    public ResponseEntity<Collection<Room>> getCottageRoomById(@PathVariable long id){
        Collection<Room> rooms = cottageService.findById(id).getRooms();
        if (rooms != null)
            return new ResponseEntity<>(rooms, HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping(consumes = "application/json")
    public Room save(@RequestBody RoomDto roomDto){
        return this.roomService.saveRoom(roomDto);
    }

    @PutMapping(consumes = "application/json")
    public Room update(@RequestBody RoomDto roomDto){
        return this.roomService.updateRoom(roomDto);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<?> delete(@PathVariable long id){
        roomService.deleteRoom(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
