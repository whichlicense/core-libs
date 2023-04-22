package com.whichlicense.metadata.sourcing;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

public class Test {
    public static void main(String[] args) throws MalformedURLException {
        var chain = MetadataSourceResolverProvider.loadChain();
        System.out.println(chain);
        //var root = chain.resolve(new URL("https://github.com/whichlicense/frontend"));
        var root = chain.resolve(new URL("https://github.com/whichlicense/frontend/"));
        //var root = chain.resolve(new URL("https://github.com/whichlicense/frontend/archive/refs/heads/main.zip"));
        System.out.println(root);

        root.ifPresent(source -> {
            try {
                Files.walkFileTree(source.path(), new SimpleFileVisitor<>() {
                    @Override
                    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                        System.out.println(file);
                        return super.visitFile(file, attrs);
                    }
                });
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });




                //.reduce(null, (curr, prev) -> next -> prev.apply(curr.apply(next)));
    }
}
