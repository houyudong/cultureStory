package com.story.culture.database;
import android.database.sqlite.SQLiteDatabase;
import android.xutil.SQLiteHelper;
import com.story.culture.StoryApplication;

/**
 * @Description:数据库操作类
 * @author:wennan
 * @see:
 * @since:
 * @copyright © ciyun.cn
 * @Date:2014年8月14日
 */
public class DbHelper extends SQLiteHelper {
    private SQLiteDatabase db;
    protected static final String DATABASENAME = "culture.db";
    protected static final String CONSUME_CLASS_TIME_TABLE = "_consume_class_time_table";//消费学时表
    protected static final String STUDENT_INFO_TABLE = "_student_info_table";//学员信息
    protected static final String COURSE_TABLE = "_course_table";//课程信息表
    protected static final int VERSION = 1;
    public DbHelper() {
        super(StoryApplication.getContext(), DATABASENAME, null, VERSION);
    }
    @Override
    public SQLiteDatabase getWritableDatabase() {
        if (db == null) {
            db = super.getWritableDatabase();
        }
        StringBuffer sql_create_consume_class_time_table = new StringBuffer();
        sql_create_consume_class_time_table.append("create table if not exists ");
        sql_create_consume_class_time_table.append(CONSUME_CLASS_TIME_TABLE);
        sql_create_consume_class_time_table
                .append(" (id integer primary key autoincrement ,student_name text,phone_number text,course_name text,course_class_hour text, " +
                        "time text, photo text,date text, teacher text )");


        StringBuffer sql_create_student_info_table = new StringBuffer();
        sql_create_student_info_table.append("create table if not exists ");
        sql_create_student_info_table.append(STUDENT_INFO_TABLE);
        sql_create_student_info_table
                .append(" (id integer primary key autoincrement ,student_name text,student_phonenumber text,recommend_people text, qq_number text, wechat_number text, " +
                        "birthday text,start_date text, photo text, vedio text, attention integer,source text, sex integer )");

        StringBuffer sql_create_course_table = new StringBuffer();
        sql_create_course_table.append("create table if not exists ");
        sql_create_course_table.append(COURSE_TABLE);
        sql_create_course_table
                .append(" (id integer primary key autoincrement ,student_id integer,course_name text,course_state integer,course_class_hour text, available_class_hour text, course_price text, " +
                        "course_sale text,course_actual_price text, memo text , date text, overdue_date text, " +
                        "type text,teacher text,phone_number text )");

        db.execSQL(sql_create_consume_class_time_table.toString());
        db.execSQL(sql_create_student_info_table.toString());
        db.execSQL(sql_create_course_table.toString());
        return db;
    }





    @Override
    public void onCreate(SQLiteDatabase db) {


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

//        String sql_drop_downLoad = "DROP TABLE IF EXISTS "
//                + HealthApplication.mUserCache.getUserId() + TABLENAME_SUFFIX;
//
//        String sql_drop_body_composition = "DROP TABLE IF EXISTS "
//                + HealthApplication.mUserCache.getUserId() + TABLENAME_BODY_COMPOSITION;
//
//
//        String sql_drop_pedometer_sync = "DROP TABLE IF EXISTS "
//                + TABLENAME_PEDOMETER_SYNC;
//
//        db.execSQL(sql_drop_downLoad);
//        db.execSQL(sql_drop_body_composition);
//        db.execSQL(sql_drop_pedometer_sync);

    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
     
//        String sql_drop_downLoad = "DROP TABLE IF EXISTS "
//                + HealthApplication.mUserCache.getUserId() + TABLENAME_SUFFIX;
//
//        String sql_drop_body_composition = "DROP TABLE IF EXISTS "
//                + HealthApplication.mUserCache.getUserId() + TABLENAME_BODY_COMPOSITION;
//        db.execSQL(sql_drop_downLoad);
//        db.execSQL(sql_drop_body_composition);


    }
}
