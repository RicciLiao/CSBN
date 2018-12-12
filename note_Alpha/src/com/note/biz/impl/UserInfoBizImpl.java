package com.note.biz.impl;

/**************************************************************************
 * MODIFICATION HISTORY
 * Name             Date                     Description
 * ==========  ==============  =======================================
 * Ricci               30-Apr-2018       Initial Version
 **************************************************************************/

import com.note.biz.UserInfoBiz;
import com.note.common.Common;
import com.note.common.Constants;
import com.note.common.MD5Util;
import com.note.dao.UserInfoDao;
import com.note.entity.UserInfo;

import java.io.File;
import java.util.HashMap;
import java.util.Map;


public class UserInfoBizImpl implements UserInfoBiz {
    // 依赖注入 UserInfoDao
    UserInfoDao userInfoDao;

    public void setUserInfoDao(UserInfoDao userInfoDao) {
        this.userInfoDao = userInfoDao;
    }

    @Override
    public Map<String, Object> login(UserInfo unUser) {
        Map<String, Object> mapResult = new HashMap<>();
        UserInfo userInfo = userInfoDao.findByName(unUser.getUserName());
        if (userInfo != null) {
            // MD5
            String unUserPsw = MD5Util.Encryption(unUser.getUserPassword());
            if (unUserPsw.equals(userInfo.getUserPassword())) {
                mapResult.put(Constants.SUCCESS, userInfo);
            } else {
                mapResult.put(Constants.AJAX_COMMON_RESULT, Constants.PASSWORD_ERR);
            }
        } else {
            mapResult.put(Constants.AJAX_COMMON_RESULT, Constants.NAME_ERR);
        }
        return mapResult;
    }

    @Override
    public Map<String, String> signUp(UserInfo unUser) {
        Map<String, String> mapResult = new HashMap<>();
        try {
            // UserEmail
            if (!Common.isNullOrSpace(unUser.getUserEmail())) {
                if (unUser.getUserEmail().matches(Constants.USER_EMAIL_REGEX)) {
                    mapResult.put(Constants.EMAIL_INFO, Constants.SUCCESS);
                } else {
                    mapResult.put(Constants.EMAIL_INFO, Constants.FORMAT_ERR);
                }
            }
            // UserPhone
            if (!Common.isNullOrSpace(unUser.getUserPhone())) {
                if (unUser.getUserPhone().matches(Constants.USER_PHONE_REGEX)) {
                    mapResult.put(Constants.PHONE_INFO, Constants.SUCCESS);
                } else {
                    mapResult.put(Constants.PHONE_INFO, Constants.FORMAT_ERR);
                }
            }
            // UserPhone && UserEmail
            if (Common.isNullOrSpace(unUser.getUserEmail()) && Common.isNullOrSpace(unUser.getUserPhone())) {
                mapResult.put(Constants.EMAIL_INFO, Constants.EMPTY);
                mapResult.put(Constants.PHONE_INFO, Constants.EMPTY);
            }
            // UserName
            if (!Common.isNullOrSpace(unUser.getUserName())) {
                if (unUser.getUserName().matches(Constants.USER_NAME_REGEX)
                        && !unUser.getUserName().matches(Constants.NUMBER_REGEX)) {
                    if (unUser.getUserName().length() <= Constants.MIN_USER_NAME_LENGTH) {
                        mapResult.put(Constants.NAME_INFO, Constants.MIN_LENGTH_ERR);
                    } else if (unUser.getUserName().length() >= Constants.MAX_USER_NAME_LENGTH) {
                        mapResult.put(Constants.NAME_INFO, Constants.MAX_LENGTH_ERR);
                    } else {
                        if (userInfoDao.findByName(unUser.getUserName()) == null) {
                            mapResult.put(Constants.NAME_INFO, Constants.SUCCESS);
                        } else {
                            mapResult.put(Constants.NAME_INFO, Constants.EXISTED);
                        }
                    }
                } else {
                    mapResult.put(Constants.NAME_INFO, Constants.FORMAT_ERR);
                }
            } else {
                mapResult.put(Constants.NAME_INFO, Constants.EMPTY);
            }
            // UserPassword
            if (!Common.isNullOrSpace(unUser.getUserPassword())) {
                if (unUser.getUserPassword().length() >= Constants.MIN_USER_PASSWORD_LENGTH
                        && unUser.getUserPassword().length() <= Constants.MAX_USER_PASSWORD_LENGTH) {
                    mapResult.put(Constants.PASSWORD_LENGTH_INFO, Constants.SUCCESS);
                } else {
                    mapResult.put(Constants.PASSWORD_LENGTH_INFO, Constants.ERROR);
                }
                if (Common.isContainSpace(unUser.getUserPassword())) {
                    mapResult.put(Constants.PASSWORD_CHAR_INFO, Constants.ERROR);
                    mapResult.put(Constants.PASSWORD_SPACE_INFO, Constants.ERROR);
                } else {
                    mapResult.put(Constants.PASSWORD_CHAR_INFO, Constants.SUCCESS);
                    mapResult.put(Constants.PASSWORD_SPACE_INFO, Constants.SUCCESS);
                }
            } else {
                mapResult.put(Constants.PASSWORD_LENGTH_INFO, Constants.ERROR);
            }
            // UserRePassword
            mapResult.put(Constants.RE_PASSWORD_INFO, unUser.getUserPassword().equals(unUser.getUserRePassword()) ? Constants.SUCCESS : Constants.ERROR);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return mapResult;
        }
    }

    @Override
    public UserInfo findByName(String userName) {
        return userInfoDao.findByName(userName);
    }

    @Override
    public boolean createUser(UserInfo userInfo) {
        String strPswMD5 = "";
        String strPhoneMD5 = "";
        String strEmailMD5 = "";
        String strGUID = "";
        String oriUserPsw = userInfo.getUserPassword();
        String oriUserPhone = userInfo.getUserPhone();
        String oriUserEmail = userInfo.getUserEmail();
        try {
            strGUID = Common.generateGUID();
            // FOR MD5 Enc
            strPhoneMD5 = MD5Util.Encryption(oriUserPhone);
            strEmailMD5 = MD5Util.Encryption(oriUserEmail);
            strPswMD5 = MD5Util.Encryption(oriUserPsw);
            if (Common.isNullOrSpace(oriUserPhone)) {
                userInfo.setUserPhone("");
            } else {
                userInfo.setUserPhone(strPhoneMD5);
            }
            if (Common.isNullOrSpace(strEmailMD5)) {
                userInfo.setUserEmail("");
            } else {
                userInfo.setUserEmail(strEmailMD5);
            }
            userInfo.setUserPassword(strPswMD5);

            userInfo.setUserGuid(strGUID);
            userInfo.setUserCreateDate(Common.getCurrentDate());
            userInfo.setUserPath(Constants.SERVER_PATH + strGUID);
            userInfo.setUserSpaceSize(Constants.SERVER_SPACE_SIZE_DEF);
            userInfo.setUserType(Constants.USER_TYPE_DEF);
            userInfo.setStatus(0);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return userInfoDao.create(userInfo);
        }
    }

    @Override
    public boolean createUserSpace(String userPath) {
        File file = null;
        boolean result = false;
        try {
            file = new File(userPath);
            if (file.mkdir()) {
                result = true;
            } else {
                result = false;
            }
        } catch (Exception e) {
            result = false;
            e.printStackTrace();
        } finally {
            return result;
        }
    }
}













