package team.one.lwes.dao.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import team.one.lwes.LearnWithEaseServerApplication;
import team.one.lwes.bean.LoginInfo;
import team.one.lwes.bean.StudyRoomInfo;
import team.one.lwes.dao.StudyRoomInfoDao;

import java.util.List;

@Component
public class StudyRoomInfoDaoImpl implements StudyRoomInfoDao {

    @Autowired
    private JdbcTemplate db;

    @Override
    public void clear() {
        db.update("delete from study_room where roomId like '%%'");
    }

    @Override
    public void saveStudyRoomInfo(String roomId, int timeStudy, int timeRest, int content_study, int gender, String province, String city, String area, String school, String creator) {
        db.update("insert into study_room(roomId, timeStudy, timeRest, contentStudy, gender, province, city, area, school, creator) values(?,?,?,?,?,?,?,?,?,?)",
                roomId, timeStudy, timeRest, content_study, gender, province, city, area, school, creator);
    }

    @Override
    public void removeStudyRoomInfo(String roomId) {
        db.update("delete from study_room where roomId = ?", roomId);
    }

    @Override
    public List<StudyRoomInfo> fetchRecs(int timeStudy, int timeRest, int contentStudy, String gender, String province, String city, String area, String school) {
        List<StudyRoomInfo> info = db.query("select * from study_room where timeStudy like ? or timeRest like ? or contentStudy like ? or province like ? or city like ? or area like ? or school like ? order by rand() limit 8", new BeanPropertyRowMapper(StudyRoomInfo.class), timeStudy, timeRest, contentStudy, province, city, area, school);
        if (info.size() > 0)
            return info;
        return null;
    }

    @Override
    public void updateTimeStudy(String roomId, int timeStudy) {
        db.update("update study_room set timeStudy = ? where roomId = ?", timeStudy, roomId);
    }

    @Override
    public void updateTimeRest(String roomId, int timeRest) {
        db.update("update study_room set timeRest = ? where roomId = ?", timeRest, roomId);
    }

    @Override
    public void updateContentStudy(String roomId, int contentStudy) {
        db.update("update study_room set contentStudy = ? where roomId = ?", contentStudy, roomId);
    }

    @Override
    public StudyRoomInfo getRoom(String roomId) {
        List<StudyRoomInfo> info = db.query("select * from study_room where roomId = ?", new BeanPropertyRowMapper(StudyRoomInfo.class), roomId);
        if (info.size() > 0)
            return info.get(0);
        return null;
    }

}
