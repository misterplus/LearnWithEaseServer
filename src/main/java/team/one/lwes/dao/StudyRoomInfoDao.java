package team.one.lwes.dao;

import team.one.lwes.bean.StudyRoomInfo;

import java.util.List;

public interface StudyRoomInfoDao {

    void saveStudyRoomInfo(String roomId, int timeStudy, int timeRest, int contentStudy, int gender, String province, String city, String area, String school);

    void removeStudyRoomInfo(String roomId);

    List<String> getRoomsByTimeStudy(int timeStudy);

    List<String> getRoomsByTimeRest(int timeRest);

    List<String> getRoomsByContentStudy(int contentStudy);

    List<String> getRoomsByGender(int gender);

    List<String> getRoomsByPlace(String province, String city, String area);

    List<String> getRoomsByPlace(String province, String city);

    List<String> getRoomsByPlace(String province);

    List<String> getRoomsBySchool(String school);

    StudyRoomInfo getStudyRoomInfo(String roomId);
}
