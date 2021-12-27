package com.example.backend.Services;

import com.example.backend.Beans.DeleteProfileRequest;
import com.example.backend.Dtos.MakeDeleteProfileRequestDto;
import com.example.backend.Repository.DeleteProfileRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class DeleteProfileRequestService {
    @Autowired
    private final DeleteProfileRequestRepository deleteProfileRequestRepository;
    @Autowired
    private UserService userService;

    public DeleteProfileRequestService(DeleteProfileRequestRepository deleteProfileRequestRepository){
        this.deleteProfileRequestRepository = deleteProfileRequestRepository;
    }

    public Collection<DeleteProfileRequest> getAllNotReviewedRequestsForProfileDeleting(){
        return this.deleteProfileRequestRepository.getAllNotReviewedRequestsForProfileDeleting();
    }

    private DeleteProfileRequest save(DeleteProfileRequest deleteProfileRequest){
        return this.deleteProfileRequestRepository.save(deleteProfileRequest);
    }

    public DeleteProfileRequest findDeleteProfileRequestById(long id){
        return this.deleteProfileRequestRepository.findById(id).orElse(null);
    }

    public void markDeleteProfileRequestAsReviewed(DeleteProfileRequest deleteProfileRequest){
        deleteProfileRequest.setReviewed(true);
        save(deleteProfileRequest);
    }

    public DeleteProfileRequest makeDeleteProfileRequest(MakeDeleteProfileRequestDto dto){
        DeleteProfileRequest deleteProfileRequest = new DeleteProfileRequest();
        deleteProfileRequest.setRequestText(dto.getReason());
        deleteProfileRequest.setUser(userService.findUserById(dto.getUserId()));
        return save(deleteProfileRequest);
    }
}
