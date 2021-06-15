package team.one.lwes.dao;

import team.one.lwes.bean.StudyRoomInfo;

import java.util.List;

public interface StudyRoomInfoDao {

    void clear();

    void saveStudyRoomInfo(String roomId, int timeStudy, int timeRest, int contentStudy, int gender, String province, String city, String area, String school, String creator);

    void removeStudyRoomInfo(String roomId);

    List<StudyRoomInfo> fetchRecs(int timeStudy, int timeRest, int contentStudy, String gender, String province, String city, String area, String school);

    void updateTimeStudy(String roomId, int timeStudy);

    void updateTimeRest(String roomId, int timeRest);

    void updateContentStudy(String roomId, int contentStudy);

    StudyRoomInfo getRoom(String roomId);
}
