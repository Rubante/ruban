package com.ruban.learning.nio.file;

import java.io.File;
import java.nio.file.FileStore;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.FileTime;
import java.nio.file.attribute.UserPrincipal;

import org.junit.Test;

public class PathsTest {

    @Test
    public void testPath() {

        try {
            Iterable<FileStore> fileStores = FileSystems.getDefault().getFileStores();
            for (FileStore fileStore : fileStores) {
                System.out.println(fileStore.toString());
                System.out.println(fileStore.getTotalSpace());
            }

            Iterable<Path> paths = FileSystems.getDefault().getRootDirectories();
            for (Path pathRoot : paths) {
                System.out.println(pathRoot);
            }

            File[] roots = File.listRoots();
            for (File root : roots) {
                System.out.println(root.getPath());
            }

            String file = this.getClass().getResource("/testFile").getProtocol();

            System.out.println(file);
            Path path = Paths.get("C:/Users/yjwang/git/ruban/other/learning/target/test-classes/testFile");

            // 用户身份
            UserPrincipal userPrincipal = path.getFileSystem().getUserPrincipalLookupService()
                    .lookupPrincipalByName("yjwang");
            System.out.println(userPrincipal.getName());

            Path slibing = path.resolveSibling("FileSlibing");

            FileTime fileTime = (FileTime) Files.getAttribute(path, "basic:creationTime");

            System.out.println("fileTime:" + fileTime.toString());

            System.out.println("slibing:" + slibing.toString());
            System.out.println(slibing.toFile().exists());

            // 创建软连接
            Path link = FileSystems.getDefault().getPath(System.getProperty("java.io.tmpdir"), "link");
            System.out.println(link);
            Files.createSymbolicLink(link, path);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
