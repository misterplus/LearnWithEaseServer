package team.one.lwes.dao;

import team.one.lwes.bean.StudyRoomInfo;

public interface StudyRoomInfoDao {

    void saveStudyRoomInfo(String roomId, int timeStudy, int timeRest, int contentStudy, int gender, String province, String city, String area, String school);

    void removeStudyRoomInfo(String roomId);

    int getTimeStudy(String roomId);

    int getTimeRest(String roomId);

    int getContentStudy(String roomId);

    int getGender(String roomId);

    String getProvince(String roomId);

    String getCity(String roomId);

    String getArea(String roomId);

    String getSchool(String roomId);

    StudyRoomInfo getStudyRoomInfo(String roomId);
}
