package com.kpanchal.mailtools;

import java.io.IOException;
import java.io.FileOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import java.nio.file.Path;
import java.nio.file.Files;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.FileVisitResult;
import java.nio.file.attribute.BasicFileAttributes;

public class Zipper {
    public static void zipFolder(Path source, Path dest) throws IOException {
        ZipOutputStream outputStream = new ZipOutputStream(new FileOutputStream(dest.toFile()));
        Files.walkFileTree(source, new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attributes) throws IOException {
                outputStream.putNextEntry(new ZipEntry(source.relativize(file).toString()));
                Files.copy(file, outputStream);
                outputStream.closeEntry();
                return FileVisitResult.CONTINUE;
            }
        });
        outputStream.close();
    }
}