package com.courseproject.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.courseproject.model.student.Student;

// класс для работы со студентов в БД
public class StudentDataBaseHadler extends BaseClassDataBaseHadler<Student> {

    private String GET_BY_ID = SELECT_ALL + NAME_TABLE_STUDENT + " WHERE " + KEY_ID + " = ?";
    private String GET_BY_STYDENT_CARD = SELECT_ALL + NAME_TABLE_STUDENT + " WHERE " + KEY_NUMBER_STUDENT_CARD + " = ?";

    public StudentDataBaseHadler(Context context) {
        super(context);
    }

    @Override
    public void add(Student student) {// перопределенный метод
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_ID,student.getId());
        values.put(KEY_NAME_STUDENT, student.getName());
        values.put(KEY_SURNAME_STUDENT, student.getSurName());
        values.put(KEY_SECOND_NAME_STUDENT, student.getSecondName());
        values.put(KEY_ID_GROUP, student.getGroup().getId());
        values.put(KEY_NUMBER_STUDENT_CARD, student.getNumberStudentCard());
        values.put(KEY_FOTO, student.getFoto());
        db.insert(NAME_TABLE_STUDENT, null, values);
        db.close();
    }

    @Override
    public Student getById(long id) {// перопределенный метод
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(GET_BY_ID, new String[]{String.valueOf(id)});
        Student student = new Student();
        if(cursor.moveToFirst())
        {
            int idIndex = cursor.getColumnIndex(KEY_ID);
            int nameIndex = cursor.getColumnIndex(KEY_NAME_STUDENT);
            int surNameIndex = cursor.getColumnIndex(KEY_SURNAME_STUDENT);
            int secondNameIndex = cursor.getColumnIndex(KEY_SECOND_NAME_STUDENT);
            int groupIdIndex = cursor.getColumnIndex(KEY_ID_GROUP);
            int numberStudentCardIndex = cursor.getColumnIndex(KEY_NUMBER_STUDENT_CARD);
            int fotoIndex = cursor.getColumnIndex(KEY_FOTO);
            student.setId(cursor.getInt(idIndex));
            student.setName(cursor.getString(nameIndex));
            student.setFoto(cursor.getString(fotoIndex));
            student.setGroup(new GroupDataBaseHadler(context).getById(cursor.getLong(groupIdIndex)));
            student.setSurName(cursor.getString(surNameIndex));
            student.setSecondName(cursor.getString(secondNameIndex));
            student.setNumberStudentCard(cursor.getString(numberStudentCardIndex));
        }
        cursor.close();
        db.close();
        return student;
    }

    @Override
    public int update(Student student) {// перопределенный метод
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("UPDATE " + NAME_TABLE_STUDENT + " SET " + KEY_NAME_STUDENT + " = '" + student.getName() + "', " +
                    KEY_SURNAME_STUDENT + " = '" + student.getSurName() + "', " +
                    KEY_SECOND_NAME_STUDENT + " = '" + student.getSecondName() + "', " +
                    KEY_ID_GROUP + " = '" + student.getGroup().getId() + "', " +
                    KEY_NUMBER_STUDENT_CARD + " = '" + student.getNumberStudentCard() + "', " +
                    KEY_FOTO + " = ''"  + " WHERE " + KEY_ID + " = " + student.getId());
        return 1;
    }

    public long getByStydentCard(String stydentCard) {// получения инфы о студента по студаку
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(GET_BY_STYDENT_CARD, new String[]{stydentCard});
        long id = 0;
        if(cursor.moveToFirst())
        {
            int idIndex = cursor.getColumnIndex(KEY_ID);
            id = cursor.getInt(idIndex);
        }
        cursor.close();
        db.close();
        return id;
    }
}
