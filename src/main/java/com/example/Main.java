/*
 * Copyright 2002-2014 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;

@Controller
@SpringBootApplication
public class Main {

  public static void main(String[] args) throws Exception {

    (new Thread(() -> {
      while (true) {
        List<ByteBuffer> buffers = new ArrayList<>();
        for (int i=0; i < 30; i++) {
          buffers.add(ByteBuffer.allocateDirect(160000));
        }
        try {
          Thread.sleep(1000l);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }
    })).start();

    (new Thread(() -> {
      while (true) {
        ByteBuffer b;
        int length = 0x800; // 128 Mb
        try {
          b = new RandomAccessFile("target/java-getting-started-1.0.jar", "rw").getChannel().map(FileChannel.MapMode.READ_WRITE, 0, length);
          Thread.sleep(1000l);
          b = null;
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
    })).start();

    SpringApplication.run(Main.class, args);
  }

  @RequestMapping("/")
  String index() {
    return "index";
  }
}
