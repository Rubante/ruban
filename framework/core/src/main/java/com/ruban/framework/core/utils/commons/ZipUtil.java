package com.ruban.framework.core.utils.commons;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ruban.framework.core.utils.Constants;

/**
 * Description User: I8800
 */
public class ZipUtil implements Serializable {

    private static final long serialVersionUID = 0xd8a6498d482bb45dL;

    private final static Logger logger = LoggerFactory.getLogger(ZipUtil.class);

    /**
     * 获得zip压缩包中文件数(包括目录)
     * 
     * @param zipFile
     * @return
     */
    public static int getFiles(final ZipFile zipFile) {
        return zipFile.size();
    }

    /**
     * 获得zip压缩包二进制数组中文件数(包括目录)
     * 
     * @param zipBytes
     * @return
     * @throws IOException
     */
    public static int getFiles(final byte[] zipBytes) throws IOException {
        int files = 0;
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(zipBytes);
        ZipInputStream zipInputStream = new ZipInputStream(byteArrayInputStream);
        while (zipInputStream.getNextEntry() != null) {
            files++;
        }
        zipInputStream.closeEntry();
        zipInputStream.close();
        byteArrayInputStream.close();
        return files;
    }

    /**
     * 获得zip压缩包中文件数(包括目录)
     * 
     * @param filename
     * @return
     * @throws IOException
     */
    public static int getFiles(final String filename) throws IOException {
        ZipFile zipFile = new ZipFile(filename);
        int files = getFiles(zipFile);
        zipFile.close();
        return files;
    }

    /**
     * 获得zip压缩包中文件数(包括目录)
     * 
     * @param file
     * @return
     * @throws IOException
     */
    public static int getFiles(final File file) throws IOException {
        ZipFile zipFile = new ZipFile(file);
        int files = getFiles(zipFile);
        zipFile.close();
        return files;
    }

    /**
     * 从zip包中读取给定文件名的内容
     * 
     * @param zipFilename
     * @param name
     *            获得内容的文件全文件名 注：大小写敏感
     * @return
     * @throws IOException
     */
    public static byte[] getContent(final String zipFilename, final String name) throws IOException {
        ZipFile zipFile = new ZipFile(zipFilename);
        byte[] bytes = getContent(zipFile, name);
        zipFile.close();
        return bytes;
    }

    /**
     * 从zip包中读取给定文件名的内容
     * 
     * @param file
     * @param name
     *            获得内容的文件全文件名 注：大小写敏感
     * @return
     * @throws IOException
     */
    public static byte[] getContent(final File file, final String name) throws IOException {
        ZipFile zipFile = new ZipFile(file);
        byte[] bytes = getContent(zipFile, name);
        zipFile.close();
        return bytes;
    }

    /**
     * 从zip包中读取给定文件名的内容
     * 
     * @param zipFile
     * @param name
     *            获得内容的文件全文件名 注：大小写敏感
     * @return
     * @throws IOException
     */
    public static byte[] getContent(final ZipFile zipFile, final String name) throws IOException {
        ZipEntry zipEntry = zipFile.getEntry(name);
        return getContent(zipFile, zipEntry);
    }

    /**
     * 从zip包中读取给定文件名的内容
     * 
     * @param zipFile
     * @param zipEntry
     * @return
     * @throws IOException
     */
    public static byte[] getContent(final ZipFile zipFile, final ZipEntry zipEntry) throws IOException {
        InputStream inputStream = zipFile.getInputStream(zipEntry);
        byte[] buffer = new byte[1024];
        byte[] bytes = new byte[0];
        int length;
        while ((length = (inputStream.read(buffer))) != -1) {
            byte[] readBytes = new byte[length];
            System.arraycopy(buffer, 0, readBytes, 0, length);
            bytes = ByteUtil.merge(bytes, readBytes);
        }
        inputStream.close();
        return bytes;
    }

    /**
     * 从zip包中获取指定文件的输入流
     * 
     * @param file
     *            压缩包名
     * @param name
     *            压缩包中指定的文件名
     * @return
     * @throws IOException
     */
    public static InputStream getContentStream(final File file, final String name) throws IOException {

        String targetPaht = file.getParent() + File.separator + file.getName().replaceAll("\\.tmp", "_tmp");
        // 文件解压
        ZipUtil.decompress(file, targetPaht);

        File targetFile = new File(targetPaht + File.separator + name);
        FileInputStream fis = new FileInputStream(targetFile);

        return fis;
    }

    /**
     * 从二进制zip包byte数组中获取给定文件名的内容 注释掉原有代码，重写了该方法 updated at 2013-09-12 by i8800
     * 
     * @param zipBytes
     * @param name
     * @return
     * @throws IOException
     * @throws InterruptedException
     */
    public static byte[] getContent(final byte[] zipBytes, final String name) {

        try {
            ByteArrayInputStream byteArrayOutputStream = new ByteArrayInputStream(zipBytes);
            ZipInputStream zipInputStream = new ZipInputStream(byteArrayOutputStream);
            ZipEntry zipEntry;
            byte[] bytes = null;
            // 移到name所指向的entry
            while ((zipEntry = zipInputStream.getNextEntry()) != null) {
                String filename = zipEntry.getName();
                if (filename.equals(name)) {
                    break;
                }
            }
            if (!StringUtil.isNullOrEmpty(zipEntry)) {
                bytes = new byte[0];
                byte[] buffer = new byte[1024];
                int length;
                while ((length = zipInputStream.read(buffer)) != -1) {
                    byte[] tmpBytes = new byte[length];
                    System.arraycopy(buffer, 0, tmpBytes, 0, length);
                    bytes = ByteUtil.merge(bytes, tmpBytes);
                }
            }
            zipInputStream.close();
            byteArrayOutputStream.close();
            return bytes;
        } catch (IOException e) {
            if (logger.isErrorEnabled()) {
                logger.error("get ZipFile Content error.", e);
            }
            throw new RuntimeException("get ZipFile Content error.");
        }
    }

    /**
     * @param zipFilename
     * @param filename
     * @throws IOException
     */
    public static void add(final String zipFilename, final String filename) throws IOException {
        File zipFile = new File(zipFilename);
        File file = new File(filename);
        add(zipFile, file);
    }

    /**
     * @param zipFile
     * @param file
     * @throws IOException
     */
    public static void add(final File zipFile, final File file) throws IOException {
        FileInputStream fileInputStream = new FileInputStream(file);
        int length = fileInputStream.available();
        byte[] bytes = new byte[length];
        fileInputStream.read(bytes);
        fileInputStream.close();
        add(zipFile, file.getName(), bytes);
    }

    /**
     * @param zipFile
     * @param filename
     * @param bytes
     * @throws IOException
     */
    public static void add(final File zipFile, final String filename, final byte[] bytes) throws IOException {
        ZipFile zip = new ZipFile(zipFile);
        Enumeration entries = zip.entries();
        FileOutputStream outputStream = new FileOutputStream(zipFile, true);
        ZipOutputStream zipOutputStream = new ZipOutputStream(outputStream);
        while (entries.hasMoreElements()) {
            ZipEntry entry = (ZipEntry) entries.nextElement();
            addEntry(zipOutputStream, entry, getContent(zipFile, entry.getName()));
        }
        ZipEntry zipEntry = new ZipEntry(filename);
        addEntry(zipOutputStream, zipEntry, bytes);

        zipOutputStream.flush();
        zipOutputStream.close();

        outputStream.flush();
        outputStream.close();
        zip.close();
    }

    /**
     * @param zipFile
     * @param filename
     * @throws IOException
     */
    public static void delete(final File zipFile, final String filename) throws IOException {
        ZipFile zip = new ZipFile(zipFile);
        Enumeration entries = zip.entries();
        FileOutputStream outputStream = new FileOutputStream(zipFile, true);
        ZipOutputStream zipOutputStream = new ZipOutputStream(outputStream);
        while (entries.hasMoreElements()) {
            ZipEntry entry = (ZipEntry) entries.nextElement();
            if (!entry.getName().equalsIgnoreCase(filename)) {
                addEntry(zipOutputStream, entry, getContent(zipFile, entry.getName()));
            }
        }
        zipOutputStream.flush();
        zipOutputStream.close();

        outputStream.flush();
        outputStream.close();
        zip.close();
    }

    /**
     * 添加单个ZipEntry
     * 
     * @param zipOutputStream
     * @param zipEntry
     * @param bytes
     * @throws IOException
     */
    private static void addEntry(ZipOutputStream zipOutputStream, ZipEntry zipEntry, byte[] bytes) throws IOException {
        zipOutputStream.putNextEntry(zipEntry);
        zipOutputStream.write(bytes);
        zipOutputStream.flush();
        zipOutputStream.closeEntry();
    }

    /**
     * 获得zip包中的文件名列表
     * 
     * @param zipFile
     * @return
     */
    public static List<String> list(final ZipFile zipFile) {
        List<String> list = new ArrayList<String>();
        Enumeration zipEntries = zipFile.entries();
        while (zipEntries.hasMoreElements()) {
            ZipEntry entry = (ZipEntry) zipEntries.nextElement();
            list.add(entry.getName());
        }
        return list;
    }

    /**
     * 获得zip包中的文件名列表
     * 
     * @param file
     * @return
     */
    public static List<String> list(final File file) throws IOException {
        ZipFile zipFile = new ZipFile(file);
        List<String> filenameList = list(zipFile);
        zipFile.close();
        return filenameList;
    }

    /**
     * 获得zip包中的文件名列表
     * 
     * @param filename
     * @return
     * @throws IOException
     */
    public static List<String> list(final String filename) throws IOException {
        File file = new File(filename);
        return list(file);
    }

    /**
     * 把源文件夹压缩成zip包
     * 
     * @param zipFile
     * @param srcFolder
     * @throws IOException
     */
    public static void compress(final File zipFile, final String srcFolder) throws IOException {
        File folder = new File(srcFolder);
        FileOutputStream fileOutputStream = new FileOutputStream(zipFile);
        BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(fileOutputStream);
        ZipOutputStream zipOutputStream = new ZipOutputStream(bufferedOutputStream);
        if (!folder.isDirectory()) {
            compress(zipOutputStream, folder, srcFolder);
        } else {
            List<String> filenameList = listFilename(srcFolder);
            for (String filename : filenameList) {
                File file = new File(filename);
                compress(zipOutputStream, file, srcFolder);
            }
        }
        zipOutputStream.flush();
        zipOutputStream.close();
        bufferedOutputStream.flush();
        bufferedOutputStream.close();
        fileOutputStream.flush();
        fileOutputStream.close();
    }

    /**
     * @param zipOutputStream
     * @param file
     * @throws IOException
     */
    private static void compress(final ZipOutputStream zipOutputStream, final File file, final String srcFolder)
            throws IOException {
        FileInputStream fileInputStream = new FileInputStream(file);
        BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream, Constants.K);
        ZipEntry entry = new ZipEntry(file.getPath().substring(srcFolder.length() + 1));
        zipOutputStream.putNextEntry(entry);
        byte[] data = new byte[bufferedInputStream.available()];
        bufferedInputStream.read(data);
        zipOutputStream.write(data);
        zipOutputStream.flush();
        bufferedInputStream.close();
        fileInputStream.close();
    }

    /**
     * 解压zip包到目标目录里面
     * 
     * @param zipFile
     * @param targetFolder
     * @throws IOException
     */
    public static void decompress(final File zipFile, final String targetFolder) throws IOException {
        List<String> filenameList = list(zipFile);
        for (String filename : filenameList) {
            File file = new File(targetFolder + File.separatorChar + filename);
            if (!file.exists()) {
                File parentPath = file.getParentFile();
                if (!parentPath.exists()) {
                    parentPath.mkdirs();
                }
                file.createNewFile();
            }
            write(file, getContent(zipFile, filename));
        }
    }

    /**
     * 解压zip包二进制数组到目标目录里面
     * 
     * @param zipBytes
     * @param targetFolder
     * @throws IOException
     */
    public static int decompress(final byte[] zipBytes, final String targetFolder) {
        try {
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(zipBytes);
            ZipInputStream zipInputStream = new ZipInputStream(byteArrayInputStream);
            ZipEntry zipEntry;
            int total = 0;// 解压文件总数
            while ((zipEntry = zipInputStream.getNextEntry()) != null) {
                String filename = zipEntry.getName();
                byte[] content = getContent(zipBytes, filename);
                File file = new File(targetFolder + File.separator + filename);
                if (!file.exists()) {
                    File parentPath = file.getParentFile();
                    if (!parentPath.exists()) {
                        parentPath.mkdirs();
                    }
                    file.createNewFile();
                }
                write(file, content);
                total++;
            }
            zipInputStream.close();
            byteArrayInputStream.close();
            return total;
        } catch (IOException e) {
            if (logger.isErrorEnabled()) {
                logger.error("Decompress zip file error.", e);
            }
            throw new RuntimeException("Decompress zip file error.");
        }
    }

    /**
     * 写入单个文件
     * 
     * @param file
     * @param bytes
     * @throws IOException
     */
    private static void write(final File file, final byte[] bytes) throws IOException {
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        fileOutputStream.write(bytes);
        fileOutputStream.flush();
        fileOutputStream.close();
    }

    /**
     * 遍历出所有的文件名和目录名
     * 
     * @param path
     * @return
     */
    public static List<String> listFilename(final String path) {
        List<String> list = new ArrayList<String>();
        File folder = new File(path);
        if (folder.isDirectory()) {
            File[] files = folder.listFiles();
            if (!StringUtil.isNullOrEmpty(files)) {
                for (File file : files) {
                    if (file.isDirectory()) {
                        for (String filename : listFilename(file.getPath())) {
                            list.add(filename);
                        }
                    } else {
                        list.add(file.getPath());
                    }
                }
            } else {
                list.add(folder.getPath());
            }
        } else {
            list.add(folder.getPath());
        }
        return list;
    }
}
