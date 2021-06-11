package team.one.lwes.dao.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import team.one.lwes.LearnWithEaseServerApplication;
import team.one.lwes.bean.StudyRoomInfo;
import team.one.lwes.dao.StudyRoomInfoDao;

import java.util.List;

@Component
public class StudyRoomInfoDaoImpl implements StudyRoomInfoDao {

    @Autowired
    private JdbcTemplate db;

    @Override
    public void saveStudyRoomInfo(String roomId, int timeStudy, int timeRest, int content_study, int gender, String province, String city, String area, String school) {
        db.update("insert into study_room(roomId, timeStudy, timeRest, contentStudy, gender, province, city, area, school) values(?,?,?,?,?,?,?,?,?)",
                roomId, timeStudy, timeRest, content_study, gender, province, city, area, school);
    }

    @Override
    public List<String> getRoomsByTimeStudy(int timeStudy) {
        List<String> rooms = db.queryForList("select roomId from study_room where timeStudy = ?", String.class, timeStudy);
        if (rooms.size() > 0)
            return rooms;
        return null;
    }

    @Override
    public List<String> getRoomsByTimeRest(int timeRest) {
        List<String> rooms = db.queryForList("select roomId from study_room where timeRest = ?", String.class, timeRest);
        if (rooms.size() > 0)
            return rooms;
        return null;
    }

    @Override
    public List<String> getRoomsByContentStudy(int contentStudy) {
        List<String> rooms = db.queryForList("select roomId from study_room where contentStudy = ?", String.class, contentStudy);
        if (rooms.size() > 0)
            return rooms;
        return null;
    }

    @Override
    public List<String> getRoomsByGender(int gender) {
        List<String> rooms = db.queryForList("select roomId from study_room where gender = ?", String.class, gender);
        if (rooms.size() > 0)
            return rooms;
        return null;
    }

    @Override
    public List<String> getRoomsByPlace(String province, String city, String area) {
        List<String> rooms = db.queryForList("select roomId from study_room where province = ? and city = ? and area = ?", String.class, province, city, area);
        if (rooms.size() > 0)
            return rooms;
        return null;
    }

    @Override
    public List<String> getRoomsByPlace(String province, String city) {
        List<String> rooms = db.queryForList("select roomId from study_room where province = ? and city = ?", String.class, province, city);
        if (rooms.size() > 0)
            return rooms;
        return null;
    }

    @Override
    public List<String> getRoomsByPlace(String province) {
        List<String> rooms = db.queryForList("select roomId from study_room where province = ?", String.class, province);
        if (rooms.size() > 0)
            return rooms;
        return null;
    }

    @Override
    public List<String> getRoomsBySchool(String school) {
        List<String> rooms = db.queryForList("select roomId from study_room where school = ?", String.class, school);
        if (rooms.size() > 0)
            return rooms;
        return null;
    }

    @Override
    public void removeStudyRoomInfo(String roomId) {
        db.update("delete from study_room where roomId = ?", roomId);
    }

    @Override
    public StudyRoomInfo getStudyRoomInfo(String roomId) {
        List<StudyRoomInfo> info = db.query("select * from study_room where roomId = ?", new BeanPropertyRowMapper(StudyRoomInfo.class), roomId);
        if (info.size() > 0)
            return info.get(0);
        return null;
    }

    @Override
    public List<StudyRoomInfo> fetchRecs(int timeStudy, int timeRest, int contentStudy, String gender, String province, String city, String area, String school) {
        List<StudyRoomInfo> info = db.query("select * from study_room where timeStudy like ? or timeRest like ? or contentStudy like ? or province like ? or city like ? or area like ? or school like ? order by rand() limit 8", new BeanPropertyRowMapper(StudyRoomInfo.class), timeStudy, timeRest, contentStudy, province, city, area, school);
        if (info.size() > 0)
            return info;
        return null;
    }

}
