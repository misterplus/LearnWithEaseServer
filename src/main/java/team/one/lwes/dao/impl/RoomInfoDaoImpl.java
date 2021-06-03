package team.one.lwes.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import team.one.lwes.bean.ChatRoomInfo;
import team.one.lwes.dao.RoomInfoDao;

import java.util.List;

@Component
public class RoomInfoDaoImpl implements RoomInfoDao {

    @Autowired
    private JdbcTemplate db;

    @Override
    public void saveChatRoomInfo(int room_id, int content_study, int gender, String province, String city, String area, String school) {
        db.update("insert into chat_room(room_id, content_study, gender, province, city, area, school) values(?,?,?,?,?,?,?)",
                room_id, content_study, gender, province, city, area, school);
    }

    @Override
    public int getContentStudy(int room_id) {
        List<ChatRoomInfo> info = db.query("select content_study from chat_room where room_id = ?", new BeanPropertyRowMapper(ChatRoomInfo.class), room_id);
        if (info.size() > 0)
            return info.get(0).getContent_study();
        return -1;
    }

    @Override
    public ChatRoomInfo getChatRoomInfo(int room_id) {
        List<ChatRoomInfo> info = db.query("select content_study from chat_room where room_id = ?", new BeanPropertyRowMapper(ChatRoomInfo.class), room_id);
        if (info.size() > 0)
            return info.get(0);
        return null;
    }

    @Override
    public int getGender(int room_id) {
        List<ChatRoomInfo> info = db.query("select content_study from chat_room where room_id = ?", new BeanPropertyRowMapper(ChatRoomInfo.class), room_id);
        if (info.size() > 0)
            return info.get(0).getGender();
        return -1;
    }

    @Override
    public String getProvince(int room_id) {
        List<ChatRoomInfo> info = db.query("select content_study from chat_room where room_id = ?", new BeanPropertyRowMapper(ChatRoomInfo.class), room_id);
        if (info.size() > 0)
            return info.get(0).getProvince();
        return null;
    }

    @Override
    public String getCity(int room_id) {
        List<ChatRoomInfo> info = db.query("select content_study from chat_room where room_id = ?", new BeanPropertyRowMapper(ChatRoomInfo.class), room_id);
        if (info.size() > 0)
            return info.get(0).getCity();
        return null;
    }

    @Override
    public String getArea(int room_id) {
        List<ChatRoomInfo> info = db.query("select content_study from chat_room where room_id = ?", new BeanPropertyRowMapper(ChatRoomInfo.class), room_id);
        if (info.size() > 0)
            return info.get(0).getArea();
        return null;
    }

    @Override
    public String getSchool(int room_id) {
        List<ChatRoomInfo> info = db.query("select content_study from chat_room where room_id = ?", new BeanPropertyRowMapper(ChatRoomInfo.class), room_id);
        if (info.size() > 0)
            return info.get(0).getSchool();
        return null;
    }

}
