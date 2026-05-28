package com.stubserver.backend.util;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.zip.Deflater;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public final class ZipUtil {

    private ZipUtil() {}

    public static void writeZip(List<Path> files, OutputStream out) throws IOException {
        try (ZipOutputStream zos = new ZipOutputStream(new BufferedOutputStream(out, 65536))) {
            zos.setLevel(Deflater.BEST_SPEED);
            for (Path file : files) {
                ZipEntry entry = new ZipEntry(file.getFileName().toString());
                zos.putNextEntry(entry);
                Files.copy(file, zos);
                zos.closeEntry();
            }
        }
    }
}
