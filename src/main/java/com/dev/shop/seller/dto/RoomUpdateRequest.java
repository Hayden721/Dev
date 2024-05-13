package com.dev.shop.seller.dto;


import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class RoomUpdateRequest {
    private List<UpdateRoomInfoDto> roomInfo;
    private List<UpdateRoomOptionInfoDto> optionList;

}
