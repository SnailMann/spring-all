package com.snailmann.me.spring.shell.opt.cmd.impl;

import com.snailmann.me.spring.shell.opt.cmd.CommandService;
import org.junit.Test;

public class CommandServiceImplTest {


    @Test
    public void cmd_test_success() {
        CommandService service = new CommandServiceImpl();
        String res = service.execute("cd /Users/wenjie.li/Desktop && tail -n 5 log.log");
        System.out.println(res);
    }

    @Test
    public void cmd_test_3() {
        CommandService service = new CommandServiceImpl();
        String res = service.execute("grep");
        System.out.println(res);
    }

    @Test
    public void cmd_test_4() {
        CommandService service = new CommandServiceImpl();
        String cmd = String.format("cd /Users/wenjie.li/Desktop/temp/%s/log && tail -n 5 console_%s.log", "video-roughrank-pb-st-v9-pk-e8c615b2", "7");
        String res = service.execute(cmd);
        System.out.println(res);
    }
}