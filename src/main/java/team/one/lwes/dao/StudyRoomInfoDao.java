package team.one.lwes.dao;

import team.one.lwes.bean.StudyRoomInfo;

import java.util.List;

public interface StudyRoomInfoDao {

    void saveStudyRoomInfo(String roomId, int timeStudy, int timeRest, int contentStudy, int gender, String province, String city, String area, String school);

    void removeStudyRoomInfo(String roomId);

    List<String> getRoomIdByTimeStudy(int timeStudy);

    List getRoomIdByTimeRest(int timeRest);

    List getRoomIdByContentStudy(int contentStudy);

    List getRoomIdByGender(int gender);

    List getRoomIdByPlace(String province, String city, String area);

    List getRoomIdBySchool(String school);

    StudyRoomInfo getStudyRoomInfo(String roomId);
}
