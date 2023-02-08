package com.example.demoroot.pojo;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author PeiDong Yan
 * @date 2023/01/16 11:27
 */
@Data
@Accessors(chain = true)
public class DemoTask implements Runnable {
    private String name;

    @Override
    public void run() {
        System.out.println(this.name);
    }
}
