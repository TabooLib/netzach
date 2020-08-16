package io.izzel.netzach.shop;

import io.izzel.netzach.Netzach;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Iterator;

public class ShopManager {

    public void load() {
        try {
            Path shops = Netzach.instance().getDataFolder().toPath().resolve("shops");
            Iterator<Path> iterator = Files.walk(shops).filter(it -> it.toString().endsWith(".yml")).iterator();
            while (iterator.hasNext()) {
                Path next = iterator.next();

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
