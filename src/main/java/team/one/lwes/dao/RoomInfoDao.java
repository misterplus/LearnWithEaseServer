package team.one.lwes.dao;

import team.one.lwes.bean.ChatRoomInfo;

public interface RoomInfoDao {

    void saveChatRoomInfo(int room_id, int content_study, int gender, String province, String city, String area, String school);

    int getContentStudy(int room_id);

    int getGender(int room_id);

    String getProvince(int room_id);

    String getCity(int room_id);

    String getArea(int room_id);

    String getSchool(int room_id);

    ChatRoomInfo getChatRoomInfo(int room_id);
}
