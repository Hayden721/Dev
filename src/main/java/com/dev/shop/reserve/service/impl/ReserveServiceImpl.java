package com.dev.shop.reserve.service.impl;

import com.dev.shop.item.dto.FileResponse;
import com.dev.shop.reserve.dao.ReserveDao;
import com.dev.shop.reserve.dto.*;
import com.dev.shop.reserve.service.ReserveService;

import com.dev.shop.utils.Pagination;
import com.dev.shop.utils.PagingResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReserveServiceImpl implements ReserveService {

    private final ReserveDao reserveDao;

    @Override
    public PagingResponse<ReserveRoomListDto> findAllRoom(CriteriaDto params) {
        // 조건에 해당하는 데이터가 없는 경우, 응답 데이터에 비어있는 리스트와 null을 담아 반환

        // 게시물 카운트
        int count = reserveDao.countAllList(params);
        log.info("count {}", count);
        if (count < 1) {
            return new PagingResponse<>(Collections.emptyList(), null);
        }

        // Pagination 객체를 생성해서 페이지 정보 계산 후 SearchDto 타입의 객체인 params에 계산된 페이지 정보 저장
        Pagination pagination = new Pagination(count, params);
        params.setPagination(pagination);
        // 계산된 페이지 정보의 일부(limitStart, recordSize)를 기준으로 리스트 데이터 조회 후 응답 데이터 반환
        List<ReserveRoomListDto> list = reserveDao.selectRoomList(params);
        return new PagingResponse<>(list, pagination);

    }

    @Override
    public RoomInfoRequest findRoomInfo(Long roomNo) {
        return reserveDao.selectRoomInfoByRoomNo(roomNo);
    }

    @Override
    public Map<String, ArrayList<Integer>> getAvailableReservationTime(String selectDate, Long roomNo, Long optionNo) {
        //start_time, end_time Map
        Map<String, ArrayList<Integer>> timeMap = new HashMap<>();

        ArrayList<Integer> startTimeValues = new ArrayList<>();
        ArrayList<Integer> endTimeValues = new ArrayList<>();

        for (int i=1; i<=24; i++) {
            startTimeValues.add(i);
            endTimeValues.add(i);
        }

        timeMap.put("start_time", startTimeValues);
        timeMap.put("end_time", endTimeValues);

        for (Map.Entry<String, ArrayList<Integer>> entry : timeMap.entrySet()) {
            System.out.println(entry.getKey() + ":" + entry.getValue());
        }

        //----------------------------------------------------------

        List<Map<String, Integer>> getReservationTime = reserveDao.getReservedAllTime(selectDate, roomNo, optionNo);

        ArrayList<Integer> removeStartTimeValues = new ArrayList<>();
        ArrayList<Integer> removeEndTimeValues = new ArrayList<>();
        log.info("예약된 전체 시간 조회 : {}", getReservationTime);

        for(Map<String, Integer> reservation : getReservationTime) {
            if(reservation.containsKey("start_time") && reservation.containsKey("end_time")) {
                int startTime = reservation.get("start_time");
                int endTime = reservation.get("end_time");

                if (endTime - startTime >= 2) {
                    for (int i = startTime; i < endTime; i++) {
                        removeStartTimeValues.add(i);
                    }

                    for (int j = startTime + 1; j<=endTime; j++) {
                        removeEndTimeValues.add(j);
                    }

                } else {
                    removeStartTimeValues.add(startTime);
                    removeEndTimeValues.add(endTime);
                }
            }
        }

        timeMap.get("start_time").removeAll(removeStartTimeValues);
        timeMap.get("end_time").removeAll(removeEndTimeValues);

        log.info("removeStart 확인 : {}", removeStartTimeValues);
        log.info("removeEnd 확인 : {}", removeEndTimeValues);
        log.info("예약 가능한 시간 확인 : {}", timeMap);

        return timeMap;

    }

    @Override
    public void insertReservation(String selectDate, Integer reserveStartTime, Integer reserveEndTime, Long sellerNo, Long memberNo, Long roomNo, Long optionNo) {
        log.info("================================================================{}",selectDate);

        // 날짜 format변경, 형 변환 ex) xxxx/xx/xx
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-M-d");
        // 날짜
        LocalDate parsingDate = LocalDate.parse(selectDate, formatter);

        // end_date_time insert
        // 시간
        LocalTime parsingEndTime = LocalTime.of(reserveEndTime, 0);

        // 날짜 + 시간
        LocalDateTime endDateTime = LocalDateTime.of(parsingDate, parsingEndTime);
        log.debug("/reserveServiceImpl endDateTime : {}", endDateTime);


        reserveDao.insertReserveInfo(parsingDate, reserveStartTime, reserveEndTime, sellerNo, memberNo, roomNo, optionNo, endDateTime);
    }

    @Override
    public void updateReservationStatus() {
        reserveDao.updateReservationStatus();
    }

    @Override
    public FileResponse getThumbnailImage(Long roomNo) {
        return reserveDao.selectThumbnailImage(roomNo);
    }

    @Override
    public List<FileResponse> getExtraImage(Long roomNo) {
        return reserveDao.selectExtraImage(roomNo);
    }

    @Override
    public boolean getBookmarkValue(String memberId, Long roomNo) {
        Long memberNo = reserveDao.selectMemberNo(memberId);
        log.info("memberNo {}", memberNo);
        return reserveDao.selectBookmarkData(memberNo, roomNo);
    }

    @Override
    public List<OptionAddImageDto> getOptionAndImageByRoomNo(Long roomNo) {
        return reserveDao.selectOptionAndImage(roomNo);
    }

    /**
     * 계정아이디를 통해 memberNo를 가지고 온다
     * @param authId - 북마크하려는 멤버의 id
     * @return member_no
     */
    @Override
    public Long getMemberNo(String authId) {
        return reserveDao.selectMemberNo(authId);
    }

    /**
     * 해당 방을 북마크 했는지 안했는지 확인한다.
     * @param memberId - 로그인한 유저의 아이디
     * @param roomNo - 북마크하려는 방 넘버
     * @return 북마크를 상태를 boolean 값으로 반환
     */
    @Override
    public boolean roomBookmark(String memberId, Long roomNo) {
        //memberId로 memberNo 조회
        Long memberNo = reserveDao.selectMemberNo(memberId);

        // 1. 북마크하려는 유저와 방 번호를 사용해서 db에 조회
        Boolean bookmark = reserveDao.selectBookmarkData(memberNo, roomNo);
        log.info("bookmark : {}", bookmark);
        if(!bookmark) {
            reserveDao.insertBookmark(memberNo, roomNo);
            // 북마크 insert 후 true 반환
            return true;
        }else {

            reserveDao.deleteBookmark(memberNo, roomNo);
            // 북마크 delete 후 false 반환
            return false;
        }

        //2. 만약 북마크가 존재하지 않으면 insert / 존재한다면 delete
    }

    @Override
    public String getRoomInfo(Long roomNo, Long optionNo) {

        return reserveDao.selectRoomInfo(roomNo, optionNo);
    }


}
