package team.one.lwes.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
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
    public List getRoomIdByTimeStudy(int timeStudy) {
        List roomId = db.query("select roomId from study_room where timeStudy = ?", new BeanPropertyRowMapper(StudyRoomInfo.class), timeStudy);
        if (roomId.size() > 0)
            return roomId;
        return null;
    }

    @Override
    public List getRoomIdByTimeRest(int timeRest) {
        List roomId = db.query("select roomId from study_room where timeRest = ?", new BeanPropertyRowMapper(StudyRoomInfo.class), timeRest);
        if (roomId.size() > 0)
            return roomId;
        return null;
    }

    @Override
    public List getRoomIdByContentStudy(int contentStudy) {
        List roomId = db.query("select roomId from study_room where contentStudy = ?", new BeanPropertyRowMapper(StudyRoomInfo.class), contentStudy);
        if (roomId.size() > 0)
            return roomId;
        return null;
    }

    @Override
    public List getRoomIdByGender(int gender) {
        List roomId = db.query("select roomId from study_room where gender = ?", new BeanPropertyRowMapper(StudyRoomInfo.class), gender);
        if (roomId.size() > 0)
            return roomId;
        return null;
    }

    @Override
    public List getRoomIdByPlace(String province, String city, String area) {
        List roomId = db.query("select roomId from study_room where province = ? and city = ? and area = ?", new BeanPropertyRowMapper(StudyRoomInfo.class), province, city, area);
        if (roomId.size() > 0)
            return roomId;
        return null;
    }

    @Override
    public List getRoomIdBySchool(String school) {
        List roomId = db.query("select roomId from study_room where school = ?", new BeanPropertyRowMapper(StudyRoomInfo.class), school);
        if (roomId.size() > 0)
            return roomId;
        return null;
    }

    @Override
    public void removeStudyRoomInfo(String roomId) {
        db.execute("delete * from study_room where roomId = " + roomId);
    }

    @Override
    public StudyRoomInfo getStudyRoomInfo(String roomId) {
        List<StudyRoomInfo> info = db.query("select * from study_room where roomId = ?", new BeanPropertyRowMapper(StudyRoomInfo.class), roomId);
        if (info.size() > 0)
            return info.get(0);
        return null;
    }

}
