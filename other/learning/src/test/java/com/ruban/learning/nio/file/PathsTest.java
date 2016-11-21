package com.ruban.learning.nio.file;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Test;

public class PathsTest {

    @Test
    public void testPath() {
        Path path = Paths.get("D:\\all-workspace.rar");

        Path sibling = path.resolveSibling("manual_test-0.0.1-SNAPSHOT.jar");

        System.out.println(sibling.toString());
        System.out.println(sibling.toFile().exists());

    }
}
