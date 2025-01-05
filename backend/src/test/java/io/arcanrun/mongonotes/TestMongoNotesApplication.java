package io.arcanrun.mongonotes;

import org.springframework.boot.SpringApplication;

public class TestMongoNotesApplication {

  public static void main(String[] args) {
    SpringApplication.from(MongoNotesApplication::main)
        .with(TestcontainersConfiguration.class)
        .run(args);
  }
}
