package com.example.backend.Dtos;

import com.example.backend.Beans.Cottage;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
@Getter
@Setter
@NoArgsConstructor
public class CottageImageDto {
    private long id;
    private long cottageId;
    private String base64;
}
