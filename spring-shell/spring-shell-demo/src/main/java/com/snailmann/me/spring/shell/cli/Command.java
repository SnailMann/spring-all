package com.snailmann.me.spring.shell.cli;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

@ShellComponent
public class Command {

    @ShellMethod("输入两个整数，获取相加结果")
    public int add(int a, int b) {
        return a + b;
    }

}
