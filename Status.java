public class Status {
    //good
    String success = "200";
    String not_done = "201";    //recv_command done but keep listening
    String done = "202";        //thread end

    //user error
    String user_not_found = "300";
    String incorrect_password = "301";

    //file error
    String file_not_found = "400";
    String file_exists = "401";

    //other error
    String fail = "600";
}