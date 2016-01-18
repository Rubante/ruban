package com.ruban.framework.core.utils.commons;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Description User: I8800
 */
public class VersionUtil {

    private final static Logger logger = LoggerFactory.getLogger(VersionUtil.class);

    /**
     * 把版本字符串转为数组，如："0001.0002.0003"转为{"0001","0002","0003"}；<br/>
     * 如果子版本位数不够，自动在前面补0(由常量类中指定)，如："1.22.333"转为{"0001","0022","0333"}；<br/>
     * 如果子版本级数不够，自动在后面级数补全0(由常量类中指定),如："1.22.333"转为{"0001","0022","0333","0000"}
     * ；<br/>
     * 如果子版本位数多余，或者级数多余，抛出异常，如："1.22.333,44444,5555"则抛出异常。
     * 
     * @param version
     *            版本 String
     * @param subversionLength
     *            子版本位数 int
     * @param subversinLevels
     *            子版本级数 int
     * @return
     */
    public static String[] version2array(final String version, final int subversionLength, final int subversinLevels) {
        String[] subversions = version.split("\\.");
        if (subversions.length > subversinLevels) {
            if (logger.isErrorEnabled()) {
                logger.error("version error");
            }
            throw new RuntimeException("version string error.");
        }
        String[] targetVersions = new String[subversinLevels];
        for (int i = 0; i < subversinLevels; i++) {
            if (i < subversions.length) {
                if (subversions[i].length() < subversionLength) {
                    targetVersions[i] = StringUtil.padding(Constants.PADDING_CHAR,
                            subversionLength - subversions[i].length())
                            + subversions[i];
                } else {
                    targetVersions[i] = subversions[i].substring(0, subversionLength);
                }
            } else {
                String paddingSubVersion = StringUtil.padding(Constants.PADDING_CHAR, subversionLength);
                targetVersions[i] = paddingSubVersion;
            }
        }
        return targetVersions;
    }

    /**
     * 把版本字符串转为数组，如："0001.0002.0003"转为{"0001","0002","0003"}<br/>
     * 默认常量类指定子版本位数和级数
     * 
     * @param version
     * @return
     */
    public static String[] version2array(final String version) {
        return version2array(version, Constants.SUBVERSION_LENGTH, Constants.SUBVERSION_LEVELS);
    }

    /**
     * 把版本数组转为字符串，如：{"0001","0002","0003"}转为"0001.0002.0003"；<br/>
     * 如果子版本位数不够，自动在前面补0(由常量类中指定)，如：{"1","22","3333"}转为"0001.0022.0333"；<br/>
     * 如果子版本级数不够，自动在后面级数补全0(由常量类中指定),如：{"1","22","3333"}转为"0001.0022.0333.0000"；<br/>
     * 如果子版本位数多余，或者级数多余，自动截断。
     * 
     * @param versions
     *            版本字符串 String[]
     * @param subversionLength
     *            每个子版本位数 int
     * @param subversionLevels
     *            子版本的总级数 int
     * @return 总版本 String
     */
    public static String array2version(final String[] versions, final int subversionLength, final int subversionLevels) {
        if (versions.length > subversionLevels) {
            if (logger.isErrorEnabled()) {
                logger.error("versions error");
            }
            throw new RuntimeException("versions error");
        }
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < subversionLevels; i++) {
            if (i < versions.length) {
                String paddingSubVersion;
                if (versions[i].length() < subversionLength) {
                    paddingSubVersion = StringUtil.padding(Constants.PADDING_CHAR,
                            subversionLength - versions[i].length())
                            + versions[i];
                } else {
                    paddingSubVersion = versions[i].substring(0, subversionLength);
                }
                buffer.append(paddingSubVersion);
            } else {
                String paddingSubVersion = StringUtil.padding(Constants.PADDING_CHAR, subversionLength);
                buffer.append(paddingSubVersion.substring(0, Constants.SUBVERSION_LENGTH));
            }
            if (i < subversionLevels - 1) {
                buffer.append(Constants.DOT);
            }
        }
        return buffer.toString();
    }

    /**
     * 把版本数组转为字符串<br/>
     * 默认常量类指定子版本位数和级数
     * 
     * @param versions
     *            版本数组 String[]
     * @return 版本 String
     */
    public static String array2version(final String[] versions) {
        return array2version(versions, Constants.SUBVERSION_LENGTH, Constants.SUBVERSION_LEVELS);
    }

    /**
     * 两个版本比较
     * 
     * @param version1
     *            版本1 String
     * @param version2
     *            版本2 String
     * @return 比较结果 int
     */
    public static int compare(String version1, String version2) {
        String[] versions1 = version2array(version1);
        String[] versions2 = version2array(version2);
        return compare(versions1, versions2);
    }

    /**
     * 两个版本比较，如果包含有字符
     * 
     * @param versions1
     *            版本1 String[]
     * @param versions2
     *            版本2 String[]
     * @return 比较结果 int
     */
    public static int compare(String[] versions1, String[] versions2) {
        String version1 = array2version(versions1);
        String version2 = array2version(versions2);
        return version1.compareTo(version2);
    }

    /**
     * 把给定版本字符串转换为符合规定的版本字符串
     * 
     * @param version
     * @return
     */
    public static String format(final String version) {
        return array2version(version2array(version));
    }

    /**
     * 把给定版本字符串数组转换为符合规定的版本字符串数组
     * 
     * @param versions
     * @return
     */
    public static String[] format(final String[] versions) {
        return version2array(array2version(versions));
    }
}
