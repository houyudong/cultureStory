package com.story.culture.database;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.xutil.Singlton;

import com.story.utils.DateUtils;

import java.util.ArrayList;
import java.util.Date;

/**
 * @Description 数据库操作类，在此类写数据库逻辑
 * @author: wennan
 * @see:
 * @since:
 * @copyright © ciyun.cn
 * @Date:2015-8-14
 */

public class DbOperator extends DbHelper {

    private static final String TAG = "DbOperator";
    private SQLiteDatabase mSQLiteDatabase;

    public static DbOperator getInstance() {
        return Singlton.getInstance(DbOperator.class);
    }



    /**
     * 根据名字查询学生信息
     * @param name
     * @return
     */
    public ArrayList<StudentInfo> getSutdentByName(String name) {
        String tableName = DbHelper.STUDENT_INFO_TABLE;
        return getStudentInfo("select * from " + tableName + " where student_name = '" + name+ "' ");
    }
    /**
     * 根据重点关注查询学生信息
     * @return
     */
    public ArrayList<StudentInfo> getSutdentByAttention() {
        String tableName = DbHelper.STUDENT_INFO_TABLE;
        return getStudentInfo("select * from " + tableName + " where attention = '" + 1+ "' ");
    }

    /**
     * 根据手机查询学生信息
     * @param phone
     * @return
     */
    public ArrayList<StudentInfo> getSutdentByPhone(String phone) {
        String tableName = DbHelper.STUDENT_INFO_TABLE;
        return getStudentInfo("select * from " + tableName + " where student_phonenumber = '" + phone+ "' ");
    }


    /**
     * 根据id查询学生信息
     * @param id
     * @return
     */
    public StudentInfo getSutdentById(int id) {
        String tableName = DbHelper.STUDENT_INFO_TABLE;
        return getStudentInfo("select * from " + tableName + " where id = '" + id+ "' ").get(0);
    }
    /**
     * 查询所有学生信息
     * @return
     */
    public ArrayList<StudentInfo> getAllSutdent() {
        String tableName = DbHelper.STUDENT_INFO_TABLE;
        return getStudentInfo("select * from " + tableName);
    }   
    /**
     * 查询课程即将结束的学生信息
     * @return
     */
    public ArrayList<StudentInfo> querySutdent() {
        String tableName = DbHelper.STUDENT_INFO_TABLE;
        ArrayList<StudentInfo> studentInfos = new ArrayList<>();
        ArrayList<StudentInfo> infos = getStudentInfo("select * from " + tableName);
        for (StudentInfo info : infos) {
            ArrayList<CourseInfo> courseInfos = getCourseByStudentId(info.id);
            for (CourseInfo courseInfo : courseInfos) {
               if (isNeedsToBuyCourse(courseInfo)){
                   studentInfos.add(info);
                   break;
               }
            }
        }
        return studentInfos;
    }

    private boolean isNeedsToBuyCourse(CourseInfo courseInfo) {
       if(courseInfo.type.equals("按次")){
         return  Integer.valueOf(courseInfo.available_class_hour) <=3;
       }else{
       int days=    DateUtils.differentDays(new Date(),new Date(courseInfo.overdue_date));
       if(days>0 && days > 15){
           return false;
       }
           return true;
       }
    }


    private ArrayList<StudentInfo> getStudentInfo(String query) {
        mSQLiteDatabase = getWritableDatabase();

        ArrayList<StudentInfo> localArrayList = new ArrayList<StudentInfo>();
        Cursor localCursor = null;
        try {
            localCursor = mSQLiteDatabase.rawQuery(query, null);
            while (localCursor.moveToNext()) {
                StudentInfo model = GetStudentInfo(localCursor);
                localArrayList.add(0, model);
            }

        } catch (Exception e) {

        } finally {
            if (localCursor != null) {
                localCursor.close();
            }
        }
        return localArrayList;
    }

    private static StudentInfo GetStudentInfo(Cursor localCursor) {
        StudentInfo item = new StudentInfo();
        int index;
        index = localCursor.getColumnIndex("student_phonenumber");
        item.studentPhonenumber = localCursor.getString(index);

        index = localCursor.getColumnIndex("id");
        item.id = localCursor.getInt(index);

        index = localCursor.getColumnIndex("student_name");
        item.studentName = localCursor.getString(index);

        index = localCursor.getColumnIndex("sex");
        item.sex = localCursor.getInt(index);

        index = localCursor.getColumnIndex("attention");
        item.attention = localCursor.getInt(index) == 1 ? true : false;

        index = localCursor.getColumnIndex("recommend_people");
        item.recommendPeople = localCursor.getString(index);

        index = localCursor.getColumnIndex("source");
        item.source = localCursor.getString(index);

        index = localCursor.getColumnIndex("qq_number");
        item.qqNumber = localCursor.getString(index);

        index = localCursor.getColumnIndex("wechat_number");
        item.wechatNumber = localCursor.getString(index);
        index = localCursor.getColumnIndex("birthday");
        item.birthday = localCursor.getString(index);
        index = localCursor.getColumnIndex("start_date");
        item.startDate = localCursor.getString(index);
        index = localCursor.getColumnIndex("photo");
        item.photo = localCursor.getString(index);
        index = localCursor.getColumnIndex("vedio");
        item.vedio = localCursor.getString(index);
        return item;
    }

    /**
     * 插入学生信息
     *
     * @param model
     * @return
     */
    public int insertStudentInfo(StudentInfo model) {
        mSQLiteDatabase = getWritableDatabase();
        String tableName = DbHelper.STUDENT_INFO_TABLE;
        try {
            mSQLiteDatabase.beginTransaction();
            Object[] arrayOfObject2 = new Object[12];
            arrayOfObject2[0] = model.studentPhonenumber;
            arrayOfObject2[1] = model.studentName;
            arrayOfObject2[2] = model.sex;
            arrayOfObject2[3] = model.attention ? 1 : 0;
            arrayOfObject2[4] = model.source;
            arrayOfObject2[5] = model.recommendPeople;
            arrayOfObject2[6] = model.qqNumber;
            arrayOfObject2[7] = model.wechatNumber;
            arrayOfObject2[8] = model.birthday;
            arrayOfObject2[9] = model.startDate;
            arrayOfObject2[10] = model.photo;
            arrayOfObject2[11] = model.vedio;
            mSQLiteDatabase.execSQL(
                    "insert into  " + tableName
                            + "  (student_phonenumber,student_name,sex,attention,source,recommend_people,qq_number,wechat_number,birthday,start_date,photo,vedio) values (?,?,?,?,?,?,?,?,?,?,?,?)",
                    arrayOfObject2);
            mSQLiteDatabase.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            mSQLiteDatabase.endTransaction();
        }
        return 1;
    }
    /**
     * 更新学生信息
     * @param model
     */
    public void updateStudentInfo(StudentInfo model) {
        mSQLiteDatabase = getWritableDatabase();
        String tableName = DbHelper.STUDENT_INFO_TABLE;
        mSQLiteDatabase.execSQL("update " + tableName + " set student_phonenumber=?,student_name=?,sex=?,attention=?,source=?,recommend_people=?,qq_number=?,wechat_number=?,birthday=?,start_date=?,photo=?,vedio=? where id=? ",
                new String[]{model.studentPhonenumber,
                        model.studentName,
                       String.valueOf( model.sex),
                        String.valueOf( model.attention ? 1 : 0),
                        model.source,
                        model.recommendPeople,
                        model.qqNumber,
                        model.wechatNumber,
                        model.birthday,
                        model.startDate,
                        model.photo,
                        model.vedio, String.valueOf(model.id)});
    }
    //-----------------------------------------------------------------------------------------------------------------------------------------------

    /**
     * 根据手机查询学生课程信息
     * @param phone
     * @return
     */
    public ArrayList<CourseInfo> getCourseByPhone(String phone) {
        String tableName = DbHelper.COURSE_TABLE;
        return getCourseInfo("select * from " + tableName + " where phone_number = '" + phone+"'");
    }


    /**
     * 根据id查询学生课程信息
     * @param id
     * @return
     */
    public  ArrayList<CourseInfo> getCourseByStudentId(int id) {
        String tableName = DbHelper.COURSE_TABLE;
        return getCourseInfo("select * from " + tableName + " where student_id = '" + id+"'");
    }

    /**
     * 根据id查询学生课程消费信息
     * @param id
     * @return
     */
    public  ArrayList<ConsumeClassTimeInfo> getConsumeClassTimeInfoById(int id) {
        String tableName = DbHelper.CONSUME_CLASS_TIME_TABLE;
        return getConsumeClassTimeInfo("select * from " + tableName + " where course_id = '" + id+"'");
    }



    private ArrayList<ConsumeClassTimeInfo> getConsumeClassTimeInfo(String query) {
        mSQLiteDatabase = getWritableDatabase();

        ArrayList<ConsumeClassTimeInfo> localArrayList = new ArrayList<ConsumeClassTimeInfo>();
        Cursor localCursor = null;
        try {
            localCursor = mSQLiteDatabase.rawQuery(query, null);
            while (localCursor.moveToNext()) {
                ConsumeClassTimeInfo model = getConsumeClassTimeInfo(localCursor);
                localArrayList.add(0, model);
            }

        } catch (Exception e) {

        } finally {
            if (localCursor != null) {
                localCursor.close();
            }
        }
        return localArrayList;
    }
    private ArrayList<CourseInfo> getCourseInfo(String query) {
        mSQLiteDatabase = getWritableDatabase();

        ArrayList<CourseInfo> localArrayList = new ArrayList<CourseInfo>();
        Cursor localCursor = null;
        try {
            localCursor = mSQLiteDatabase.rawQuery(query, null);
            while (localCursor.moveToNext()) {
                CourseInfo model = getCourseInfo(localCursor);
                localArrayList.add(0, model);
            }

        } catch (Exception e) {

        } finally {
            if (localCursor != null) {
                localCursor.close();
            }
        }
        return localArrayList;
    }

    private static ConsumeClassTimeInfo getConsumeClassTimeInfo(Cursor localCursor) {
        ConsumeClassTimeInfo item = new ConsumeClassTimeInfo();
        int index;
        index = localCursor.getColumnIndex("course_name");
        item.course_name = localCursor.getString(index);
        index = localCursor.getColumnIndex("course_class_hour");
        item.course_class_hour = localCursor.getString(index);
        index = localCursor.getColumnIndex("id");
        item.id = localCursor.getInt(index);
        index = localCursor.getColumnIndex("student_id");
        item.student_id = localCursor.getInt(index);
        index = localCursor.getColumnIndex("student_name");
        item.student_name = localCursor.getString(index);
        index = localCursor.getColumnIndex("photo");
        item.photo = localCursor.getString(index);
        index = localCursor.getColumnIndex("date");
        item.date = localCursor.getString(index);
        index = localCursor.getColumnIndex("time");
        item.time = localCursor.getString(index);
        index = localCursor.getColumnIndex("memo");
        item.memo = localCursor.getString(index);
        index = localCursor.getColumnIndex("teacher");
        item.teacher = localCursor.getString(index);
        index = localCursor.getColumnIndex("phone_number");
        item.phone_number = localCursor.getString(index);
        index = localCursor.getColumnIndex("course_id");
        item.course_id = localCursor.getInt(index);
        return item;
    }




private static CourseInfo getCourseInfo(Cursor localCursor) {
        CourseInfo item = new CourseInfo();
        int index;
        index = localCursor.getColumnIndex("course_name");
        item.course_name = localCursor.getString(index);

        index = localCursor.getColumnIndex("course_class_hour");
        item.course_class_hour = localCursor.getString(index);

        index = localCursor.getColumnIndex("id");
        item.id = localCursor.getInt(index);

        index = localCursor.getColumnIndex("student_id");
        item.student_id = localCursor.getInt(index);
        index = localCursor.getColumnIndex("course_state");
        item.course_state = localCursor.getInt(index);

        index = localCursor.getColumnIndex("available_class_hour");
        item.available_class_hour = localCursor.getString(index);

        index = localCursor.getColumnIndex("course_price");
        item.course_price = localCursor.getString(index);

        index = localCursor.getColumnIndex("course_sale");
        item.course_sale = localCursor.getString(index);

        index = localCursor.getColumnIndex("course_actual_price");
        item.course_actual_price = localCursor.getString(index);
        index = localCursor.getColumnIndex("memo");
        item.memo = localCursor.getString(index);
        index = localCursor.getColumnIndex("date");
        item.date = localCursor.getString(index);
        index = localCursor.getColumnIndex("overdue_date");
        item.overdue_date = localCursor.getString(index);
        index = localCursor.getColumnIndex("type");
        item.type = localCursor.getString(index);
        index = localCursor.getColumnIndex("teacher");
        item.teacher = localCursor.getString(index);
        index = localCursor.getColumnIndex("phone_number");
        item.phone_number = localCursor.getString(index);
        return item;
    }





    /**
     * 插入课程信息
     *
     * @param model
     * @return
     */
    public int insertCourseInfo(CourseInfo model) {
        mSQLiteDatabase = getWritableDatabase();
        String tableName = DbHelper.COURSE_TABLE;
        try {
            mSQLiteDatabase.beginTransaction();
            Object[] arrayOfObject2 = new Object[14];
            arrayOfObject2[0] = model.course_name;
            arrayOfObject2[1] = model.course_state;
            arrayOfObject2[2] = model.course_class_hour;
            arrayOfObject2[3] = model.available_class_hour;
            arrayOfObject2[4] = model.course_price;
            arrayOfObject2[5] = model.course_sale;
            arrayOfObject2[6] = model.course_actual_price;
            arrayOfObject2[7] = model.memo;
            arrayOfObject2[8] = model.date;
            arrayOfObject2[9] = model.overdue_date;
            arrayOfObject2[10] = model.type;
            arrayOfObject2[11] = model.teacher;
            arrayOfObject2[12] = model.phone_number;
            arrayOfObject2[13] = model.student_id;
            mSQLiteDatabase.execSQL(
                    "insert into  " + tableName
                            + "  (course_name,course_state,course_class_hour,available_class_hour,course_price,course_sale,course_actual_price,memo,date,overdue_date,type,teacher,phone_number,student_id) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?)",
                    arrayOfObject2);
            mSQLiteDatabase.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            mSQLiteDatabase.endTransaction();
        }
        return 1;
    }

    /**
     * 插入消费课程信息
     *
     * @param model
     * @return
     */
    public int insertConsumeClassTimeInfo(ConsumeClassTimeInfo model) {
        mSQLiteDatabase = getWritableDatabase();
        String tableName = DbHelper.CONSUME_CLASS_TIME_TABLE;
        try {
            mSQLiteDatabase.beginTransaction();
            Object[] arrayOfObject2 = new Object[11];
            arrayOfObject2[0] = model.course_name;
            arrayOfObject2[1] = model.student_name;
            arrayOfObject2[2] = model.course_class_hour;
            arrayOfObject2[3] = model.photo;
            arrayOfObject2[4] = model.time;
            arrayOfObject2[5] = model.date;
            arrayOfObject2[6] = model.teacher;
            arrayOfObject2[7] = model.phone_number;
            arrayOfObject2[8] = model.student_id;
            arrayOfObject2[9] = model.course_id;
            arrayOfObject2[10] = model.memo;
            mSQLiteDatabase.execSQL(
                    "insert into  " + tableName
                            + "  (course_name,student_name,course_class_hour,photo,time,date,teacher,phone_number,student_id,course_id,memo) values (?,?,?,?,?,?,?,?,?,?,?)",
                    arrayOfObject2);
            mSQLiteDatabase.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            mSQLiteDatabase.endTransaction();
        }
        return 1;
    }

    /**
     * 查询学员是否需要续费
     * @param id
     * @return true 需要续费 false 不需要续费
     */
    public boolean querySutdent(int id) {
            ArrayList<CourseInfo> courseInfos = getCourseByStudentId(id);
            for (CourseInfo courseInfo : courseInfos) {
                if (isNeedsToBuyCourse(courseInfo)){
                   return true;
                }
            }
        return false;
    }
}
