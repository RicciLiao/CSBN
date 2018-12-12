package com.note.common;

/**************************************************************************
 * MODIFICATION HISTORY
 * Name             Date                     Description
 * ==========  ==============  =======================================
 * Ricci               30-Apr-2018       Initial Version
 **************************************************************************/


public class Constants {
    public static final String LOGIN_ERR = "loginErr";
    public static final String PASSWORD_ERR = "passwordErr";
    public static final String NAME_ERR = "nameErr";

    public static final String EMAIL_INFO = "emailInfo";
    public static final String PHONE_INFO = "phoneInfo";
    public static final String NAME_INFO = "nameInfo";
    public static final String PASSWORD_LENGTH_INFO = "passwordLengthInfo";
    public static final String PASSWORD_CHAR_INFO = "passwordCharInfo";
    public static final String PASSWORD_SPACE_INFO = "passwordSpaceInfo";
    public static final String RE_PASSWORD_INFO = "rePasswordInfo";

    public static final String USER_EMAIL_REGEX = "^(([0-9a-zA-Z]+)|([0-9a-zA-Z]+[_.0-9a-zA-Z-]*[0-9a-zA-Z]+))@([a-zA-Z0-9-]+[.])+([a-zA-Z]{2}|net|NET|com|COM|gov|GOV|mil|MIL|org|ORG|edu|EDU|int|INT)$";
    public static final String USER_PHONE_REGEX = "^(13|14|15|18)[0-9]{9}$";
    public static final String USER_NAME_REGEX = "[A-Za-z0-9_+\\u4e00-\\u9fa5]+$";
    public static final String NUMBER_REGEX = "^[0-9]+$";

    public static final Integer MIN_USER_NAME_LENGTH = 2;
    public static final Integer MAX_USER_NAME_LENGTH = 11;
    public static final Integer MIN_USER_PASSWORD_LENGTH = 6;
    public static final Integer MAX_USER_PASSWORD_LENGTH = 14;

    public static final String AJAX_COMMON_RESULT = "result";
    public static final String SUCCESS = "success";
    public static final String EXISTED = "existed";
    public static final String FORMAT_ERR = "formatERR";
    public static final String MIN_LENGTH_ERR = "minLengthERR";
    public static final String MAX_LENGTH_ERR = "maxLengthERR";
    public static final String EMPTY = "empty";
    public static final String ERROR = "error";

    public static final String SERVER_PATH = "E:\\CSBN\\Server\\";
    public static final String SERVER_TEMP_BAK_PATH = "E:\\CSBN\\temp\\bak\\";
    public static final String SERVER_TEMP_DL_PATH = "E:\\CSBN\\temp\\download\\";
    public static final Integer SERVER_SPACE_SIZE_DEF = 2;
    public static final String SERVER_SPACE_UNIT = "G";
    public static final Integer USER_TYPE_DEF = 1;
    public static final String J_SESSION_ID = "jsessionid";

    public static final Integer ITEM_TYPE_DIR = 1;
    public static final Integer ITEM_TYPE_TXT = 2;

    public static final String ITEM_NAME = "itemName";
    public static final String ITEM_TYPE_DESC = "itemTypeDesc";
    public static final String ITEM_TYPE = "itemType";
    public static final String ITEM_PASSWORD = "itemPassword";
    public static final String ITEM_GUID = "itemGuid";
    public static final String ITEM_INFO = "itemInfo";
    public static final String ITEM_CONTENT = "itemContent";
    public static final String ITEM_CREATE_DATE = "itemCreateDate";
    public static final String ITEM_LAST_MODIFY_DATE = "itemLastModifyDate";
    public static final String ITEM_CLASS = "itemClass";
    public static final String ITEM_SIZE = "itemSize";

    public static final String ITEM_PATH_REX = "\\";
    public static final String ITEM_CONTENT_LINE_REX = "\n";

    public static final String SERVER_AES_KEY_DEF = "f53a4cbfa1b44ac382fe524aaf5a6628";

    public static final String CLASS_ID = "classId";
    public static final String CLASS_NAME = "className";
    public static final String CLASS_DESC = "classDesc";
    public static final String ITEM_CLASSES = "itemClasses";

}
