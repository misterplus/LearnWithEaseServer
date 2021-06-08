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
    public void removeStudyRoomInfo(String roomId) {
        db.execute("delete * from study_room where roomId = " + roomId);
    }

    @Override
    public int getTimeStudy(String roomId) {
        List<StudyRoomInfo> info = db.query("select timeStudy from study_room where roomId = ?", new BeanPropertyRowMapper(StudyRoomInfo.class), roomId);
        if (info.size() > 0)
            return info.get(0).getTimeStudy();
        return -1;
    }

    @Override
    public int getTimeRest(String roomId) {
        List<StudyRoomInfo> info = db.query("select timeRest from study_room where roomId = ?", new BeanPropertyRowMapper(StudyRoomInfo.class), roomId);
        if (info.size() > 0)
            return info.get(0).getTimeRest();
        return -1;
    }

    @Override
    public int getContentStudy(String roomId) {
        List<StudyRoomInfo> info = db.query("select contentStudy from study_room where roomId = ?", new BeanPropertyRowMapper(StudyRoomInfo.class), roomId);
        if (info.size() > 0)
            return info.get(0).getContentStudy();
        return -1;
    }

    @Override
    public StudyRoomInfo getStudyRoomInfo(String roomId) {
        List<StudyRoomInfo> info = db.query("select * from study_room where roomId = ?", new BeanPropertyRowMapper(StudyRoomInfo.class), roomId);
        if (info.size() > 0)
            return info.get(0);
        return null;
    }

    @Override
    public int getGender(String roomId) {
        List<StudyRoomInfo> info = db.query("select gender from study_room where roomId = ?", new BeanPropertyRowMapper(StudyRoomInfo.class), roomId);
        if (info.size() > 0)
            return info.get(0).getGender();
        return -1;
    }

    @Override
    public String getProvince(String roomId) {
        List<StudyRoomInfo> info = db.query("select province from study_room where roomId = ?", new BeanPropertyRowMapper(StudyRoomInfo.class), roomId);
        if (info.size() > 0)
            return info.get(0).getProvince();
        return null;
    }

    @Override
    public String getCity(String roomId) {
        List<StudyRoomInfo> info = db.query("select city from study_room where roomId = ?", new BeanPropertyRowMapper(StudyRoomInfo.class), roomId);
        if (info.size() > 0)
            return info.get(0).getCity();
        return null;
    }

    @Override
    public String getArea(String roomId) {
        List<StudyRoomInfo> info = db.query("select area from study_room where roomId = ?", new BeanPropertyRowMapper(StudyRoomInfo.class), roomId);
        if (info.size() > 0)
            return info.get(0).getArea();
        return null;
    }

    @Override
    public String getSchool(String roomId) {
        List<StudyRoomInfo> info = db.query("select school from study_room where roomId = ?", new BeanPropertyRowMapper(StudyRoomInfo.class), roomId);
        if (info.size() > 0)
            return info.get(0).getSchool();
        return null;
    }

}
