/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic;

import javax.ws.rs.core.GenericType;
import restful.CourseRESTClient;
import restful.CourseSubjectRESTClient;
import restful.ExamRESTClient;
import restful.ExamSessionRESTClient;
import restful.LastSignInRESTClient;
import restful.StudentRESTClient;
import restful.SubjectRESTClient;
import restful.TeacherCourseRESTClient;
import restful.TeacherCourseSubjectRESTClient;
import restful.TeacherRESTClient;
import restful.UserRESTClient;

/**
 *
 * @author Zeeshan Yaqoobs
 */
public class RESTfulFactory {

    public Object getRESTClient(RESTfulClientType type) {
        Object client = null;
        switch (type) {
            case USER:
                client = new UserRESTClient();
                return client;
            case STUDENT:
                client = new StudentRESTClient();
                return client;
            case TEACHER:
                client = new TeacherRESTClient();
                return client;
            case COURSE:
                client = new CourseRESTClient();
                return client;
            case COURSE_SUBJECT:
                client = new CourseSubjectRESTClient();
                return client;
            case EXAM:
                client = new ExamRESTClient();
                return client;
            case EXAM_SESSION:
                client = new ExamSessionRESTClient();
                return client;
            case LAST_SIGN_IN:
                client = new LastSignInRESTClient();
                return client;
            case SUBJECT:
                client = new SubjectRESTClient();
                return client;
            case TEACHER_COURSE:
                client = new TeacherCourseRESTClient();
                return client;
            case TEACHER_COURSE_SUBJECT:
                client = new TeacherCourseSubjectRESTClient();
                return client;
        }

        return client;
    }

}
