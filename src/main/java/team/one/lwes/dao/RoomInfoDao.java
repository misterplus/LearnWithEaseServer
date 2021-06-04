package team.one.lwes.dao;

import team.one.lwes.bean.ChatRoomInfo;

public interface RoomInfoDao {

    void saveChatRoomInfo(String roomId, int timeStudy, int timeRest, int contentStudy, int gender, String province, String city, String area, String school);

    void removeChatRoomInfo(String roomId);

    int getTimeStudy(String roomId);

    int getTimeRest(String roomId);

    int getContentStudy(String roomId);

    int getGender(String roomId);

    String getProvince(String roomId);

    String getCity(String roomId);

    String getArea(String roomId);

    String getSchool(String roomId);

    ChatRoomInfo getChatRoomInfo(String roomId);
}
