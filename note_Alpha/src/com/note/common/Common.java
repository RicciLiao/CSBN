package com.note.common;

import net.sf.json.JSONObject;

import java.io.File;
import java.nio.file.Files;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**************************************************************************
 * MODIFICATION HISTORY
 * Name             Date                     Description
 * ==========  ==============  =======================================
 * Ricci               30-Apr-2018       Initial Version
 **************************************************************************/


public class Common {

    public static String convertToString(Object target) {
        String result = "";
        try {
            if (target != null) {
                result = target.toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return result;
        }
    }

    public static boolean isNullOrSpace(String target) {
        if (target == null) {
            return true;
        } else if (target.trim().equals("")) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean isContainSpace(String target) {
        if (target.indexOf(" ") != -1) {
            return true;
        }
        return false;
    }

    public static String generateGUID() {
        String strGuid = UUID.randomUUID().toString();
        strGuid = strGuid.replace("-", "");
        return strGuid;
    }

    public static Date getCurrentDate() {
        Calendar ca = Calendar.getInstance();
        Date now = ca.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String textDate = sdf.format(now);
        Date newDate = null;
        try {
            newDate = sdf.parse(textDate);
        } catch (ParseException e) {
            e.printStackTrace();
        } finally {
            return newDate;
        }
    }

    public static String mapToJson(Map map) {
        String result = "";
        JSONObject jsonObject = null;
        try {
            if (map != null) {
                jsonObject = JSONObject.fromObject(map);
                if (jsonObject != null) {
                    result = jsonObject.toString();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return result;
        }
    }

    public static boolean deleteItem(String s) {
        File file = null;
        boolean result = true;
        try {
            file = new File(s);
            if (file.isDirectory() && file.listFiles().length > 0) {
                for (File a : file.listFiles()) {
                    deleteItem(a.getPath());
                }
                deleteItem(s);
            } else {
                file.delete();
            }
        } catch (Exception e) {
            result = false;
            e.printStackTrace();
        } finally {
            return result;
        }
    }

    public static long getItemSize(File item) {
        long file_size = 0;
        File flist[] = item.listFiles();
        for (int i = 0; i < flist.length; i++) {
            if (flist[i].isDirectory()) {
                file_size += getItemSize(flist[i]);
            } else {
                file_size += flist[i].length();
            }
        }
        return file_size;
    }

    public static String FormatFileSize(long fileS) {
        DecimalFormat df = new DecimalFormat("#.##");
        String fileSizeString = "";
        if (fileS < 1024) {
            fileSizeString = df.format((double) fileS) + "B";
        } else if (fileS < 1048576) {
            fileSizeString = df.format((double) fileS / 1024) + "K";
        } else if (fileS < 1073741824) {
            fileSizeString = df.format((double) fileS / 1048576) + "M";
        } else {
            fileSizeString = df.format((double) fileS / 1073741824) + "G";
        }
        return fileSizeString;
    }

    public static Integer convertToInteger(Object target) {
        Integer result = null;
        String str = convertToString(target);
        if (!isNullOrSpace(str)) {
            if (str.matches(Constants.NUMBER_REGEX)) {
                result = Integer.valueOf(target.toString());
            }
        }
        return result;
    }

    public static boolean copyItem(File tarFile, File bakFile) {
        boolean result = true;
        try {
            Files.copy(tarFile.toPath(), bakFile.toPath());
        } catch (Exception e) {
            result = false;
            e.printStackTrace();
        } finally {
            return result;
        }
    }

    public static String dateToString(Date date) {
        String result = "";
        SimpleDateFormat simpleDateFormat = null;
        try {
            if (date != null) {
                simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd E HH:mm");
                result = simpleDateFormat.format(date);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return result;
        }
    }

    public static String dateToString2(Date date) {
        String result = "";
        SimpleDateFormat simpleDateFormat = null;
        try {
            if (date != null) {
                simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                result = simpleDateFormat.format(date);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return result;
        }
    }

    public static String dateToString3(Date date) {
        String result = "";
        SimpleDateFormat simpleDateFormat = null;
        try {
            if (date != null) {
                simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                result = simpleDateFormat.format(date);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return result;
        }
    }

    public static Date stringToDate(String strDate) {
        Date result = null;
        SimpleDateFormat simpleDateFormat = null;
        try {
            if (!isNullOrSpace(strDate)) {
                simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                result = simpleDateFormat.parse(strDate);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return result;
        }
    }

    public static Date objectToDate(Object obj) {
        Date result = null;
        try {
            if (obj != null) {
                result = (Date)obj;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return result;
        }
    }



}
